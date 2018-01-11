package com.server.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Some utility functions
 *
 */
public final class Util {

    private Util() {

    }

    public static String apiEndpoint() {
        return getProperty("api_url") + "?appid=" + getProperty("api_key") + "&units=metric";
    }
    
    /**
     * Get property from lang.* files
     * 
     * @throws Exception
     * 
     */
    public static String getProperty(String name) {

        Properties prop = new Properties();
        try (InputStream input = Util.class.getClassLoader().getResourceAsStream("props.properties")) {

            prop.load(input);
            return prop.getProperty(name);

        } catch (Exception e) {
            System.console().writer().println(e.getMessage());
        }

        return "";

    }


}
