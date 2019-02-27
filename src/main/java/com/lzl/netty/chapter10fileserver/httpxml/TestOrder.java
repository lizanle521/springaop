package com.lzl.netty.chapter10fileserver.httpxml;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author lizanle
 * @Date 2019/2/27 10:19
 */
public class TestOrder {
    private IBindingFactory factory = null;
    private StringWriter writer = null;
    private StringReader reader = null;

    private final static String CHARSET_NAME = "UTF-8";

    private String encode2xml(Order order) throws Exception {
        factory = BindingDirectory.getFactory(Order.class);
        writer = new StringWriter();
        IMarshallingContext marshallingContext = factory.createMarshallingContext();
        marshallingContext.setIndent(2);
        marshallingContext.marshalDocument(order,CHARSET_NAME,null,writer);

        String s = writer.toString();
        writer.close();
        System.out.println(s);
        return s;
    }

    private Order decode2Order(String xmlBody) throws Exception {
        reader = new StringReader(xmlBody);
        IUnmarshallingContext unmarshallingContext = factory.createUnmarshallingContext();
        Order o = (Order) unmarshallingContext.unmarshalDocument(reader);
        return o;
    }

    public static void main(String[] args) throws Exception {
        TestOrder testOrder = new TestOrder();
        Order order = OrderFactory.create(123);

        String s = testOrder.encode2xml(order);
        Order order1 = testOrder.decode2Order(s);

        System.out.println(s);
    }
}
