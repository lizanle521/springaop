package com.lzl.algo.leetCode;

import org.junit.Test;

/**
 * 找到最长的字符前缀
 * 固定的索引1，遇到对的前缀，则索引+1，不对则索引减一，设置当前索引否减过的布尔值，如果索引减过，那么对的前缀则不加，
 * Created by Lizanle on 2018/1/31.
 */
public class LongestCommonPrefix {
    public String findLongestCommonPrefix1(String[] strs){
        if(strs == null || strs.length == 0){
            return "";
        }
        // 数组做map，用来保存对应的索引是否是从上一级索引减下来的
        int maxLength = 0;
        int minLength = 1;
        for (String str : strs) {
            if(str == null || str.length() == 0){
                minLength = 0;
            }
            maxLength =  maxLength > str.length() ? maxLength : str.length();
        }
        boolean[] indexArr = new boolean[maxLength];
        int startIndex = minLength == 0 ? 0:1;
        String tempStr = "";
        while (true){
            // 如果一个字符都不符合，返回空串
            if(startIndex == 0){
                return "";
            }
            // 防止字符串长度不够
            if(strs[0].length() < startIndex) {
                startIndex = strs[0].length();
                // 如果已经设置过了，说明这是第二次执行。那么就是他了
                if(indexArr[startIndex-1]){
                    return strs[0].substring(0,startIndex);
                }
                indexArr[startIndex-1] = true;
            }
            boolean exe = false;
            for (int i = 1; i < strs.length; i++) {
                exe = true;
                // 以第一个字符串为基准
                tempStr = strs[0].length() < startIndex ? strs[0] : strs[0].substring(0,startIndex);
                // 如果字符串在当前长度上符合，并且一直符合，那么就索引+1
                if(startIndex < indexArr.length && indexArr[startIndex] == false && strs[i].startsWith(tempStr)){
                    startIndex ++;
                    // 如果字符串的当前长度是由于不合格而降级下来的，并且当前还符合，那么继续匹配下一个
                }else if(startIndex < indexArr.length && indexArr[startIndex] && strs[i].startsWith(tempStr) ) {
                    // 如果匹配到了最后一个还是成功匹配，那么就返回这个字符串
                    if(i == strs.length -1){
                        return tempStr;
                    }
                    // 如果当前字符串不是该串开始的长度，那么需要索引降级
                }else if( strs[i].startsWith(tempStr) == false){
                    startIndex --;
                    indexArr[startIndex] = true;
                    tempStr = strs[0].length() < startIndex ? strs[0] : strs[0].substring(0,startIndex);
                }else if(strs[i].startsWith(tempStr) && i == strs.length -1){
                    return tempStr;
                }

            }
            if(!exe){
                return strs[0];
            }
        }
    }

    /**
     * 采用全遍历的方式，只有全部通过了，才保存到数组中去。然后加一位再全遍历一遍，最后返回数组中最后一个不为空的字符串
     * 否则不通过
     * @param strs
     * @return
     */
    public String findLongestCommonPrefix(String[] strs){
        // 首先获取最大的长度最小的长度
        if(strs == null || strs.length == 0){
            return "";
        }
        int maxLength = strs[0].length();
        int minLength = strs[0].length();
        for (String str : strs) {
            if(str == null || str.length() == 0){
                return "";
            }
            minLength = minLength >= str.length() ? str.length() : minLength;
            maxLength =  maxLength >= str.length() ? maxLength : str.length();
        }
        // 最小长度值+1作为数组长度 因为空字符串也需要
        String[] strArr = new String[minLength+1];
        int startIndex = 0;
        while(startIndex <= minLength){
            String tempStr = strs[0].substring(0,startIndex);
            boolean faild = false;
            for (String str : strs) {
                if(str.startsWith(tempStr) == false){
                    faild = true;
                    for (int i = strArr.length -1 ; i >= 0; i--) {
                        if(strArr[i] != null ){
                            return strArr[i];
                        }
                    }
                }
            }
            if(!faild){
                strArr[startIndex] = tempStr;
            }
            startIndex ++;
        }
        for (int i = strArr.length -1 ; i >= 0; i--) {
            if(strArr[i] != null ){
                return strArr[i];
            }
        }
        return "";
    }

    @Test
    public void test(){
        String s = findLongestCommonPrefix(new String[]{"aa", "aab", "aabc", "aabcd", "aabcde", "aabcdef"});
        System.out.println(s);
    }

    @Test
    public void test1(){
        String s = findLongestCommonPrefix(new String[]{"aabc", "aab", "aabc", "aabcd", "aabcde", "aabcdef"});
        System.out.println(s);
    }

    @Test
    public void test2(){
        String s = findLongestCommonPrefix(new String[]{"a"});
        System.out.println(s);
    }

    @Test
    public void test3(){
        String s = findLongestCommonPrefix(new String[]{"a","b"});
        System.out.println(s);
    }

    @Test
    public void test4(){
        String s = findLongestCommonPrefix(new String[]{"aa","aa"});
        System.out.println(s);
    }

    @Test
    public void test5(){
        String s = findLongestCommonPrefix(new String[]{"flower","flow","flight"});
        System.out.println(s);
    }

    @Test
    public void test6(){
        String s = findLongestCommonPrefix(new String[]{"a","ac"});
        System.out.println(s);
    }

    @Test
    public void test7(){
        String s = findLongestCommonPrefix(new String[]{"cb","cbb","a"});
        System.out.println(s);
    }

    @Test
    public void test8(){
        String s = findLongestCommonPrefix(new String[]{"a","aca","accb","b"});
        System.out.println(s);
    }

    @Test
    public void test9(){
        String s = findLongestCommonPrefix(new String[]{"aac","acab","aa","abba","aa"});
        System.out.println(s);
    }

}
