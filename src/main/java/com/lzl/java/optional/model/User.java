package com.lzl.java.optional.model;

/**
 * @author lizanle
 * @Date 2019/4/16 9:11
 */
public class User {
    private String username;
    private String passwd;
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public static boolean isNameValid(String s) {
        return true;
    }
}
