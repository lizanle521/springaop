package com.lzl.algo.leetCode;

import org.junit.Test;

import java.util.HashMap;

/**
 * 最大的没有重复字母的子串的长度
 * Created by Lizanle on 2018/2/5.
 */
public class LongestSubStrWithoutRepeatingWords {
    /**
     *  时间复杂度太大 ，不能满足要求
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring1(String s) {
        int i = 0;
        while(true){
            int result = 0;
            for (int k = 0; k <= i; k++) {
                int tailLength = i - k;
                String tempStr3 = s.substring(k, s.length() - tailLength);
                System.out.println(tempStr3);
                if(existRepeat(tempStr3) == false){
                    result = tempStr3.length();
                }
            }
            i++;
            if(result != 0){
                return result;
            }
        }
    }

    public boolean existRepeat(String s){
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            String prefix = s.substring(0,i);
            String sufix = s.substring(i+1,s.length());
            if((prefix != null && prefix.indexOf(c)>=0) || (sufix != null && sufix.indexOf(c) >= 0)){
                return true;
            }
        }
        return false;
    }


    /**
     * 基本思想是  hashmap保存每个字符出现的位置，
     * 譬如abcdabcd
     * a->0,b->1,c->2,d->3
     * a->4 如果a出现了重复的，那么max=4-0长度为4
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if(s== null || s.length() == 0){
            return 0;
        }
        int result = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0,j=0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            if(map.containsKey(currentChar)){
                // 如果存在的话，j应该要以currentChar上一次出现的后边的位置来计算距离
                j = Math.max(j,map.get(currentChar)+1);
            }
            map.put(currentChar,i);
            result = Math.max(result,i-j+1);
        }
        return result;
    }

    @Test
    public void test(){
        System.out.println(lengthOfLongestSubstring("aab"));
    }

    @Test
    public void test1(){
        System.out.println(lengthOfLongestSubstring1("pwwqkww"));
    }

}
