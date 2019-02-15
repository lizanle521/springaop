package com.lzl.netty.chapter2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lizanle on 2018/3/19.
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8081;
        if( args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {

            }
        }

        ServerSocket server = null;
        try {
             server = new ServerSocket(port);
             System.out.println("time server is start in port:"+port);
            Socket socket = null;
            while (true) {
                 socket = server.accept();
                 new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if(server != null){
                System.out.println("time server close ");
                server.close();
                server = null;
            }
        }
    }
}
