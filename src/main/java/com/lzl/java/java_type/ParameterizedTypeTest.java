package com.lzl.java.java_type;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParameterizedTypeTest<T> {
    private List<T> list = null;
    private Set<T> set = null;
    private T t = null;
    private Map.Entry<T,T> entry;

    @Test
    public void test1() throws NoSuchFieldException {
        //ParameterizedType表示参数化类型，也就是泛型，例如List<T>、Set<T>等

        Field list = ParameterizedTypeTest.class.getDeclaredField("entry");
        // 获取属性的类型
        Type genericType = list.getGenericType();

        // 获取实际 的type类型 sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
        // sun.reflect.generics.reflectiveObjects.TypeVariableImpl
        System.out.println("generic type impl class:"+genericType.getClass().getName());
        System.out.println("generic type:"+genericType.getTypeName());
        // 如果这个类型是 T泛型，就不能转
        ParameterizedType type = (ParameterizedType) genericType;
        // 获取属性的类型里边真正的泛型T
        Type[] actualTypeArguments = type.getActualTypeArguments();
        for (Type actualTypeArgument : actualTypeArguments) {
            System.out.println("actual type arg:"+actualTypeArgument);
        }
        // 获取泛型T的拥有者 null
        //  Map  就是 Map.Entry<String,String>的拥有者
        System.out.println("owner type:"+type.getOwnerType());
        //泛型中<>前面的那个值
        System.out.println("raw type:"+type.getRawType());
    }
}
