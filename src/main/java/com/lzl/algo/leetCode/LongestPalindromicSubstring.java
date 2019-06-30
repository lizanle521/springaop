package com.lzl.algo.leetCode;

import org.junit.Assert;
import org.junit.Test;

/**
 * 最长的回文子串
 * Created by Lizanle on 2018/2/6.
 */
public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
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
        if (s == null || s.length() == 0 || s.length() == 1) {
            return isPalindrome;
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
        Assert.assertEquals(true,isPalindrome("aba"));
        Assert.assertEquals(true,isPalindrome("aaaa"));
    }
}
