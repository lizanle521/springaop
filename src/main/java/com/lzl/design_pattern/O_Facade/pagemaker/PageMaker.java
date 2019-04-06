package com.lzl.design_pattern.O_Facade.pagemaker;

import java.io.FileWriter;
import java.util.Properties;

/**
 * @author lizanle
 * @data 2019/4/6 2:12 PM
 */
public class PageMaker {
    private PageMaker(){}

    public static void makeWelComePage(String mailaddr,String filename){
        try {
            Properties prop = Database.getProperties("maildata");
            String username = prop.getProperty(mailaddr);
            HtmlWriter htmlWriter = new HtmlWriter(new FileWriter(filename));
            htmlWriter.title("Welcome to" + username + "s page");
            htmlWriter.paragraph(username + "欢迎来到" + username + "的主页");
            htmlWriter.paragraph("等着你的邮件哟！");
            htmlWriter.mailto(mailaddr,username);
            htmlWriter.close();
            System.out.println(filename + "is created for" + mailaddr + "(" + username + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
