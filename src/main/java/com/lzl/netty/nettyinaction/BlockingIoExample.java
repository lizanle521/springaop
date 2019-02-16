package com.lzl.netty.nettyinaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lizanle
 * @Date 2019/2/15 17:09
 */
public class BlockingIoExample {

    /**
     * 阻塞IO例子
     * @param portNumber
     * @throws IOException
     */
    public void serve(int portNumber) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket accept = serverSocket.accept();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        PrintWriter printWriter = new PrintWriter(accept.getOutputStream(), false);

        String request,response;
        while ((request = bufferedReader.readLine()) != null){
            if("Done".equals(request)){
                break;
            }
            response = process(request);
            printWriter.println(response);
        }
    }

    private String process(String request){
        return "processed";
    }
}
