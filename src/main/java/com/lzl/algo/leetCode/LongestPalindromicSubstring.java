package com.lzl.algo.leetCode;

import org.junit.Assert;
import org.junit.Test;

/**
 * 最长的回文子串
 * Created by Lizanle on 2018/2/6.
 */
public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        int len = s.length();
        if(s == null || len  == 0 ){
            return "";
        }
        // 外层循环控制每次选择的字符串长度
        for(int i = len; i > 0; i--){
            // 内循环，不停的从字符串中采用窗口法找出符合要求的子串
            for(int j = 0,startIndex = 0; j<= len -i; j ++,startIndex ++){
                String sub = s.substring(startIndex, j + i );
                if(isPalindrome(sub)){
                    return sub;
                }
            }
        }

        return "";
    }

    /**
     * 给定的字符串是否是回文串
     *
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        boolean isPalindrome = false;
        if (s == null || s.length() == 0 ) {
            return isPalindrome;
        }
        if(s.length() == 1){
            return true;
        }
        int i = 0;
        int len = s.length();

        while (true) {

                if (i > len - i - 1) {
                    isPalindrome = true;
                    break;
                }

                if (s.charAt(i) != s.charAt(len - i - 1)) {
                    break;
                }
                i++;
        }

        return isPalindrome;

    }

    @Test
    public void testIspalindromic(){
        Assert.assertEquals(true,isPalindrome("aa"));
        Assert.assertEquals(true,isPalindrome("a"));
        Assert.assertEquals(true,isPalindrome("aba"));
        Assert.assertEquals(true,isPalindrome("aaaa"));
    }

    @Test
    public void testLongestPalindromicSubString(){
        Assert.assertEquals("bb",longestPalindrome("bb"));
    }
}
