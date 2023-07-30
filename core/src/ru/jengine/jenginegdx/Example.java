package ru.jengine.jenginegdx;

import java.io.FileInputStream;
import java.util.Properties;

public class Example {

    public static void main(String[] args) throws Exception {

        String appConfigPath = System.getProperty("user.dir") + "/application.properties";

        Properties properties = new Properties();
        properties.load(new FileInputStream(appConfigPath));

        System.out.println(properties);
    }
}
