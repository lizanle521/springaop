package com.lzl.algo.leetCode.array.easy;

/**
 * Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

   You may assume no duplicates in the array.
 */
public class SearchInsertPosition {

    /**
     * 因为有序又不重复，可以考虑
     * 2分法
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
       int low = 0; int high = nums.length -1;
       while (low <= high){
           int middle = (low+high)/2;
           if(nums[middle] == target){
               return middle;
           }else if(nums[middle] > target){
               high = middle -1;
           }else if(nums[middle] < target){
               low = middle + 1;
           }
       }
       return low;
    }



    public static void main(String[] args) {
        SearchInsertPosition position = new SearchInsertPosition();
        // [1,3] 0 这个输入堆栈溢出
        System.out.println(position.searchInsert(new int[]{1,3},0));

    }
}
