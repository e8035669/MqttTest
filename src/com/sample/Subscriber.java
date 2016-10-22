/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
/**
 *
 * @author Jeff
 */
public class Subscriber {
    ServerProfile profile;
    MqttClient client;
    MqttConnectOptions connOpts;
    MqttMessage message;
    
    
    public Subscriber(ServerProfile profile) {
        try {
            this.profile = profile;
            this.client = new MqttClient(profile.getServer(),profile.getClientId());
            this.client.setCallback(new MqttCallback(){
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost... will be reconnect automatically soon.");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                     System.out.print("Receive from :"+topic);
                     System.out.printf(" at %tF %<tr%n", new Date());
                     System.out.println("\t"+message+"\n");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            
            });
            this.connOpts = new MqttConnectOptions();
            connOpts.setAutomaticReconnect(true);
            connOpts.setCleanSession(true);
        } catch (MqttException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void startSubscribe(){
        try {
            client.connect(connOpts);
            System.out.println("Starting Connection");
            client.subscribe(profile.getSubscribe().toArray(new String[0]));
        } catch (MqttException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    void stopSubscribe(){
        try {
            client.unsubscribe(profile.getSubscribe().toArray(new String[0]));
            client.disconnect();
            client.close();
        } catch (MqttException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}