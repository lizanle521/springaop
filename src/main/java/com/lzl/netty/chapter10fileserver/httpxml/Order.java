package com.lzl.netty.chapter10fileserver.httpxml;

/**
 * @author lizanle
 * @data 2019/2/26 8:55 PM
 */
public class Order {
    private Long orderNumber;
    private Custormer custormer;
    private Address billTo;
    private Shipping shipping;
    private Address shipTo;
    private Float total;

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Custormer getCustormer() {
        return custormer;
    }

    public void setCustormer(Custormer custormer) {
        this.custormer = custormer;
    }

    public Address getBillTo() {
        return billTo;
    }

    public void setBillTo(Address billTo) {
        this.billTo = billTo;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Address getShipTo() {
        return shipTo;
    }

    public void setShipTo(Address shipTo) {
        this.shipTo = shipTo;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
