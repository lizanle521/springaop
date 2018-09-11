package com.lzl.algo.leetCode.array.easy;

public class QucikSort {
    /**
     *
     * @param nums
     * @return
     */
    public static int[] sort(int[] nums){
         quicksort(nums,0,nums.length-1);
         return nums;
    }

    public static void quicksort(int[] nums,int left,int right){
        if(left > right){
            return ;
        }
        int temp = nums[left];
        int i = left;
        int j = right;
        while (i != j){
            // 先从右边开始找小于 temp的值，找到就停止
            while (nums[j] >= temp && i < j){
                j --;
            }
            // 从左边找大于temp的值，找到就停止
            while (nums[i] <= temp && i < j){
                i ++;
            }
            // 交换彼此
            if(i < j){
                int t = nums[i];
                nums[i] = nums[j];
                nums[j] = t;
            }
        }
        // 基数归位
        nums[left] = nums[i];
        nums[i] = temp;
        // 继续递归
        quicksort(nums,left,i-1);
        quicksort(nums,i+1,right);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,4,3,7,5,6};
        sort(nums);
        for (int num : nums) {
            System.out.println(num);
        }
    }
}
