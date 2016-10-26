/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.State;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Jeff
 */
public class DataStorage implements Runnable {

    private static final int MESSAGES_THRESHOLD = 5;
    private static int nowMessageCount = 0;
    private static final List<MessageWapper> list = Collections.synchronizedList(new LinkedList<MessageWapper>());
    private static Thread thread = new Thread(new DataStorage());

    public static void saveThisMessage(String topic, MqttMessage message) {
        boolean isFound = false;
        for (MessageWapper wapper : list) {
            if (wapper.topic.equals(topic)) {
                wapper.messages.add(message);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            MessageWapper newWapper = new MessageWapper(topic);
            newWapper.messages.add(message);
            list.add(newWapper);
        }
        nowMessageCount++;
        if (nowMessageCount > MESSAGES_THRESHOLD && (thread.getState() == State.NEW)) {
            thread.start();
            System.out.println("Saving Data...");
            nowMessageCount = 0;
        }
    }

    public static void stop() {
        try {
            thread.join();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        thread.run();
        System.out.println("Force to save ALL THE DATA");
    }

    @Override
    public void run() {

        for (MessageWapper wapper : list) {
            Path path = Paths.get(wapper.topic + String.format("/%tF.txt", new Date()));
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path.getParent());
                } catch (IOException ex) {
                    Logger.getLogger(DataStorage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            File file = path.toFile();
            try(FileWriter fw = new FileWriter(file, true)) {
                while (!wapper.messages.isEmpty()) {
                    fw.write(wapper.messages.poll().toString() + "\r\n");
                }
            } catch (IOException ex) {
                Logger.getLogger(DataStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        thread = new Thread(new DataStorage());
    }
}

class MessageWapper {

    final String topic;
    final LinkedList<MqttMessage> messages = new LinkedList<>();

    public MessageWapper(String topic) {
        this.topic = topic;
    }
}
