package com.lzl.design_pattern.O_Facade.pagemaker;

import com.lzl.design_pattern.O_Facade.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author lizanle
 * @data 2019/4/6 2:15 PM
 */
public class Database {
    private Database (){}
    public static Properties getProperties(String dbname){
        String filename = dbname + ".txt";
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("/com/lzl/design_pattern/O_Facade/maildata.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
