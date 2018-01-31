package com.lzl.algo.leetCode;

/**
 * Created by Lizanle on 2018/1/31.
 */
public class ReverseInteger {
    /**
     * 写的有点复杂。不够简明
     * @param x
     * @return
     */
    public int reverse(int x) {
        int temp = Math.abs(x);
        int nagetive = 1;
        if(x < 0){
            nagetive = -1;
        }
        int result = 0;
        int length = 0;
        while(temp >= 10){
            length ++;
            temp = temp / 10;
        }
        temp = Math.abs(x);
        while(temp > 0){
            result += (temp % 10)*Math.pow(10,length--);
            temp /= 10;
        }
        if(result >= Integer.MAX_VALUE || result <= Integer.MIN_VALUE ) {
            return 0;
        }else{
            return result*nagetive;
        }
    }

    public int reverse1(int x){
        int result = 0;
        while(x > 0){
            int tail = x % 10;
            int newresult = result * 10 + tail;
            if ((newresult-tail)/10 != result){
                // 溢出
                return 0;
            }
            result = newresult;
            x /= 10;
        }
        return result ;
    }

}
