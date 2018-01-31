package com.lzl.algo.leetCode;

import org.junit.Test;

/**
 * 判断是否回文数
 * Created by Lizanle on 2018/1/31.
 */
public class Palindrome {
    public boolean  isPalindrome(int x){
        int length = 0;
        if(x == Integer.MAX_VALUE || x == Integer.MIN_VALUE || x < 0){
            return false;
        }
        int temp = x;
        while(temp >= 10){
            length ++;
            temp /= 10;
        }
        temp = x;

        for (int i = length; i > length / 2; i--) {
            if ((x / (int)Math.pow(10, i)) % 10 != temp % 10) {
                return false;
            }
            temp /= 10;
        }
        return true;
    }

    /**
     * 标准答案
     * @param x
     * @return
     */
    public boolean isPalindrome1(int x){
        // Special cases:
        // As discussed above, when x < 0, x is not a palindrome.
        // Also if the last digit of the number is 0, in order to be a palindrome,
        // the first digit of the number also needs to be 0.
        // Only 0 satisfy this property.
        if(x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        while(x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }

        // When the length is an odd number, we can get rid of the middle digit by revertedNumber/10
        // For example when the input is 12321, at the end of the while loop we get x = 12, revertedNumber = 123,
        // since the middle digit doesn't matter in palidrome(it will always equal to itself), we can simply get rid of it.
        return x == revertedNumber || x == revertedNumber/10;
    }

    @Test
    public void test(){
        System.out.println(isPalindrome(11));
    }
}
