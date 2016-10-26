/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author Jeff
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ServerProfile profile = new ServerProfile();
        profile.setClientId("Test");
        profile.setServer("tcp://gpssensor.ddns.net");
        profile.setPort(1883);
        List<String> subscribeList = new ArrayList<>();
        subscribeList.add("LASS/Test/PM25/NUK_RD5F_inside");
        subscribeList.add("LASS/Test/PM25/NUK_H1-207_inside");
        subscribeList.add("LASS/Test/PM25/NUK_518-1_inside");
        subscribeList.add("LASS/Test/PM25/NUK_518-1_outside");
        subscribeList.add("LASS/Test/PM25/NUK_IM5F_outside");
        subscribeList.add("LASS/Test/PM25");
//        subscribeList.add("LASS/Test/PM25");
        profile.setSubscribe(subscribeList);
        
        Subscriber pm25 = new Subscriber(profile);
        pm25.startConnect();
        pm25.startSubscribe();

        try {
            System.in.read();
        } catch (IOException e) {
            //If we can't read we'll just exit
        }

        pm25.stopSubscribe();
        
        DataStorage.stop();
        
        
        
    }

}
