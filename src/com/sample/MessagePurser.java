/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample;

import java.util.Date;
import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Jeff
 */
public class MessagePurser {
    
    private String deviceId;
    private Date date;
    private double PM25;
    private double PM10;
    private double temperature;
    private double humidity;
    
    public MessagePurser(MqttMessage message){
        Scanner sc = new Scanner(message.toString());
        sc.useDelimiter("[|]|=");
        
        
        
        
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Date getDate() {
        return date;
    }

    public double getPM25() {
        return PM25;
    }

    public double getPM10() {
        return PM10;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}