package com.lzl.java.optional;

import com.lzl.java.optional.model.Company;
import com.lzl.java.optional.model.User;
import org.junit.Test;

import java.util.Optional;

/**
 * @author lizanle
 * @Date 2019/4/16 9:09
 */
public class OptionalTest {
    @Test
    public void test1(){
        User user = new User();
        String s = Optional.ofNullable(user).map(user1 -> user1.getPasswd()).orElse("");
        System.out.println(s);
        Company company = new Company();
        String s1 = Optional.ofNullable(company).map(c -> c.getUserList()).map(l -> l.get(0)).map(d -> d.getAddress()).map(a -> a.getCity()).orElse("unkonw");
        System.out.println(s1);
        Optional.ofNullable(company).map(c -> c.getUserList()).map(l -> l.get(0)).map(d -> d.getAddress()).map(a -> a.getCity()).orElseThrow(()->new RuntimeException(""));

    }
}
