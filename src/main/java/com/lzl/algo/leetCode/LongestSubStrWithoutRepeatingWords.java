package com.lzl.algo.leetCode;

import org.junit.Test;

/**
 * 最大的没有重复字母的子串的长度
 * Created by Lizanle on 2018/2/5.
 */
public class LongestSubStrWithoutRepeatingWords {
    /**
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
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

    @Test
    public void test(){
        System.out.println(lengthOfLongestSubstring("pqkw"));
    }

    @Test
    public void test1(){
        System.out.println(lengthOfLongestSubstring("pwwqkww"));
    }

}
