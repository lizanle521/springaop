package com.lzl.java.optional.model;

import java.util.List;

/**
 * @author lizanle
 * @Date 2019/4/16 9:32
 */
public class Company {
    private Address address;
    private List<User> userList;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
