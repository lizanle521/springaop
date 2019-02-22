package com.lzl.netty.chapter6_encode_decode.javaserilization;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @author lizanle
 * @Date 2019/2/22 10:54
 */
public class TestUserInfoSerilization {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void test() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("1000");
        userInfo.setUserName("lizanle");
        userInfo.setAddress("hunan");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(userInfo);
        os.flush();
        os.close();
        byte[] bytes = bos.toByteArray();
        System.out.println(bytes.length);
        System.out.println(userInfo.codec().length);
        bos.close();
    }

    @Test
    public void performTest() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("1000");
        userInfo.setUserName("lizanle");
        userInfo.setAddress("hunan");

        ByteArrayOutputStream bos = null;//new ByteArrayOutputStream();
        ObjectOutputStream os = null;//new ObjectOutputStream(bos);
        int loop = 1000000;

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("jdkserialization");
        for (int i = 0; i < loop; i++) {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(userInfo);
            os.flush();
            os.close();
            byte[] bytes = bos.toByteArray();
            bos.close();
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        stopWatch.start("bytebufferserilization");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for (int i = 0; i < loop; i++) {
            userInfo.codec(buffer);
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
