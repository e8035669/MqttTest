/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample;

import java.util.List;

/**
 *
 * @author Jeff
 */
public class ServerProfile {
    private String clientId;
    private String server;
    private int port;
    private String username;
    private String password;
    private List<String> subscribe;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(List<String> subscribe) {
        this.subscribe = subscribe;
    }

    @Override
    public String toString() {
        return "ServerProfile: "+clientId+
               "\nServer : "+server+
               "\nPort : "+port+
               ((username!=null)?("%nUsername :"+username):"")+
               ((password!=null)?("%nPassword :"+password):"")+
               "\nSubscribe :"+subscribe+"\n";
    }

    

    
    
    

}
