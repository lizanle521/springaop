package com.lzl.springaop;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lizanle on 2017/12/7.
 */
public class TestUnmodifiableMap {
    @Test
    public void test(){
        Map<String,String[]> map = new HashMap<String, String[]>(16);
        map.put("1",new String[]{"2","3"});
        map.put("3",new String[]{"1","2"});
        map.put("5",new String[]{"3","4"});
        map.put("7",new String[]{"5","6"});
        Map<String, String[]> unmodifiableMap = Collections.unmodifiableMap(map);
        // 能够修改
        unmodifiableMap.forEach((k,v)->{
            for (int i = 0; i < v.length; i++) {
                v[i] = "1";
            }
        });
        unmodifiableMap.forEach((k,v)->{
            for (int i = 0; i < v.length; i++) {
                System.out.println(v[i]);
            }
        });
    }
}
