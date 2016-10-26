/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Jeff
 */
public class DataStorage implements Runnable {

    private static final LinkedList<MessageWapper> list = new LinkedList<>();
    private static Thread thread = new Thread(new DataStorage());
    public static void saveThisMessage(String topic, MqttMessage message) {
        list.add(new MessageWapper(topic, message));
        if (list.size() > 3 && !thread.isAlive()) {
            thread.start();
            System.out.println("Saving Data...");
        }

    }

    @Override
    public void run() {
        while (!list.isEmpty()) {
            Path path = Paths.get(list.peek().topic + ".txt");
            try {
                if(!Files.exists(path)){
                    Files.createDirectories(path.getParent());
                }
            } catch (IOException ex) {
                Logger.getLogger(DataStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
            File file = path.toFile();
            try (FileWriter fw = new FileWriter(file, true)) {
                fw.write(list.poll().message.toString() + "\r\n");
            } catch (IOException ex) {
                Logger.getLogger(DataStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        thread = new Thread(new DataStorage());
    }
    

}

class MessageWapper {

    final String topic;
    final MqttMessage message;

    public MessageWapper(String topic, MqttMessage message) {
        this.topic = topic;
        this.message = message;
    }
}
