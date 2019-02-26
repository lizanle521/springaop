package com.lzl.netty.chapter10fileserver.httpxml;

import java.util.List;

/**
 * @author lizanle
 * @data 2019/2/26 8:57 PM
 */
public class Custormer {
    private long custormerNumber;
    private String firstName;
    private String lastName;
    private List<String> middleNames;

    public long getCustormerNumber() {
        return custormerNumber;
    }

    public void setCustormerNumber(long custormerNumber) {
        this.custormerNumber = custormerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(List<String> middleNames) {
        this.middleNames = middleNames;
    }
}
