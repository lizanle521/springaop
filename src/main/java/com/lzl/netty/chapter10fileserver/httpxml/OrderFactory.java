package com.lzl.netty.chapter10fileserver.httpxml;

/**
 * @author lizanle
 * @Date 2019/2/27 10:32
 */
public class OrderFactory {
    public static Order create(long orderID){
        Order order = new Order();
        order.setOrderNumber(orderID);
        order.setTotal(9999.999f);
        Address address = new Address();
        address.setCity("娄底");
        address.setCountry("中国");
        address.setPostCode("123321");
        address.setState("湖南省");
        address.setStreet1("曾国藩大道");
        order.setBillTo(address);
        Custormer customer = new Custormer();
        customer.setCustormerNumber(orderID);
        customer.setFirstName("李");
        customer.setLastName("zanle");
        order.setCustormer(customer);
        order.setShipping(Shipping.INTERNATIONAL_MAIL);
        order.setShipTo(address);
        return order;
    }
}
