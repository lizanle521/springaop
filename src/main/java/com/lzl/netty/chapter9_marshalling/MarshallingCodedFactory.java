package com.lzl.netty.chapter9_marshalling;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * @author lizanle
 * @data 2019/2/28 9:59 PM
 */
public final class MarshallingCodedFactory {

    /**
     * 创建jboss marshalling解码器 marshallingDecoder
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        // 现获取MarshallerFactory实例
        final MarshallerFactory marshallerFactory =
                Marshalling.getProvidedMarshallerFactory("serial");

        // 创建marshallingConfiguration，将版本号设置为5。
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        // 根据marshallerFactory 和 configuration创建 provider
        DefaultUnmarshallerProvider provider =
                new DefaultUnmarshallerProvider(marshallerFactory, configuration);

        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
        return decoder;
    }

    public static MarshallingEncoder buildMarshallingEncoder(){
        // 现获取MarshallerFactory实例
        final MarshallerFactory marshallerFactory =
                Marshalling.getProvidedMarshallerFactory("serial");

        // 创建marshallingConfiguration，将版本号设置为5。
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        // 根据marshallerFactory 和 configuration创建 provider
        DefaultMarshallerProvider provider =
                new DefaultMarshallerProvider(marshallerFactory, configuration);

        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }


    public static Unmarshaller buildUnMarshalling() {
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");

        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        try {
            Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(configuration);
            return unmarshaller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Marshaller buildMarshalling(){
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");

        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        try {
            Marshaller marshaller = marshallerFactory.createMarshaller(configuration);
            return marshaller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
