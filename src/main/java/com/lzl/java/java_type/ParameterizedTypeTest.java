package com.lzl.java.java_type;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParameterizedTypeTest<T> {
    private List<T> list = null;
    private Set<T> set = null;
    private T t = null;
    private Map.Entry<T,T> entry;
    private T[] ts = null;
    private List<String>[] lists = null;

    @Test
    public void test1() throws NoSuchFieldException {
        //ParameterizedType表示参数化类型，也就是泛型，例如List<T>、Set<T>等

        Field list = ParameterizedTypeTest.class.getDeclaredField("t");
        // 获取属性的类型
        Type genericType = list.getGenericType();

        // 获取实际 的type类型 sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
        // sun.reflect.generics.reflectiveObjects.TypeVariableImpl
        System.out.println("generic type impl class:"+genericType.getClass().getName());
        System.out.println("generic type:"+genericType.getTypeName());
        // 如果这个类型是 T泛型，就不能转
        if(genericType instanceof  ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericType;
            // 获取属性的类型里边真正的泛型T
            Type[] actualTypeArguments = type.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println("actual type arg:" + actualTypeArgument);
            }

            // 获取泛型T的拥有者 null
            //  Map  就是 Map.Entry<String,String>的拥有者
            System.out.println("owner type:" + type.getOwnerType());
            //泛型中<>前面的那个值
            System.out.println("raw type:" + type.getRawType());
        }else if(genericType instanceof GenericArrayType) {
            GenericArrayType type = (GenericArrayType) genericType;
            // 打印数组来办的具体组成类类型
            System.out.println("array component type:"+type.getGenericComponentType());
        }else if(genericType instanceof TypeVariable){
            TypeVariable typeVariable = (TypeVariable) genericType;
            // 获取T类型的父级类List<T extends Number> ，Number就是类型变量T的父级类。默认Object
            Type[] bounds = typeVariable.getBounds();
            for (Type s : bounds) {
                System.out.println(s);
            }
            // 打印泛型名称
            System.out.println(typeVariable.getName());
            // 获取声明该类型变量实体 ,譬如T类型是 ParameterizedTypeTest声明的
            GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();

            System.out.println(genericDeclaration);
        }
    }
}
