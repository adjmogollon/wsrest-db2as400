package com.adjmogollon.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adjmo
 */
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum ApplicationProperties {
    INSTANCE;

    private final Properties properties;

    ApplicationProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String getIpAs400() {
        return properties.getProperty("IpAs400");
    }
    public String getUsuarioAs400() {
        return properties.getProperty("UsuarioAs400");
    }
    public String getPasswordAs400() {
        return properties.getProperty("PasswordAs400");
    }
    public String getHostServidorSMTP() {
        return properties.getProperty("HostServidorSMTP");
    }
    public String getCorreoOrigen() {
        return properties.getProperty("CorreoOrigen");
    }
    public int getDuracionTOTP() {
        return Integer.parseInt(properties.getProperty("DuracionTOTP"));
    }
    
    public String getNacJur() {
        return properties.getProperty("NacJur");
    }
    
    public String getCedJur() {
        return properties.getProperty("CedJur");
    }
    
    public String getPortServidorSMTP() {
        return properties.getProperty("PortServidorSMTP");
    }

    public String getPorcentajeCashOut() {
        return properties.getProperty("PorcentajeCashOut");
    }
        
}
