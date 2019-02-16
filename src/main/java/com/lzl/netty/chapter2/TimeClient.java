package com.lzl.netty.chapter2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author lizanle
 * @data 2019/2/15 8:10 PM
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8081;
        if(args != null && args.length > 0)
        {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            socket = new Socket("127.0.0.1", port);

            out = new PrintWriter(socket.getOutputStream(),true);

            out.println("QUERY TIME ORDER\r\n");

            System.out.println("Send order 2 server succeed.");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String s = in.readLine();

            System.out.println("now is " + s);

        }catch (Exception e){

        }finally {
            if(out != null){
                out.close();
                out = null;
            }

            if(in != null)
            {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }

            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }

        }
    }
}
