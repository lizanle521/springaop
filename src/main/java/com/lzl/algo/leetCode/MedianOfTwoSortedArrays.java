package com.lzl.algo.leetCode;

import org.junit.Test;

/**
 * 找到两个排序后的数组的中位数
 * 时间复杂度要求为 log(m+n)
 * Created by Lizanle on 2018/2/6.
 */
public class MedianOfTwoSortedArrays {
    public double medianOfTwoSortedArrays(int[] nums1 ,int[] nums2){
        int[] mergeArray = new int[nums1.length + nums2.length];
        int l1 = nums1.length;
        int l2 = nums2.length;
        int nums1Index = 0,nums2Index = 0;
        for (int i = 0; i < mergeArray.length; i++) {
            // 两个索引都还没有超出数组范围
            if(nums1Index < l1 && nums2Index < l2) {
                if (nums1[nums1Index] < nums2[nums2Index]) {
                    mergeArray[i] = nums1[nums1Index++];
                } else {
                    mergeArray[i] = nums2[nums2Index++];
                }
                // 如果数组2已经排完了，那么接下来直接是数组1排
            }else if(nums1Index < l1 && nums2Index == l2){
                mergeArray[i] = nums1[nums1Index++];
            }else if(nums1Index == l1 && nums2Index < l2){
                mergeArray[i] = nums2[nums2Index++];
            }
        }
        if(mergeArray.length % 2 == 0){
            return (mergeArray[mergeArray.length/2] + mergeArray[mergeArray.length/2 -1])*1.0/2;
        }else {
            return mergeArray[mergeArray.length / 2];
        }
    }

    @Test
    public void test(){
        System.out.println(medianOfTwoSortedArrays(new int[]{1,2},new int[]{2,8,7}));
    }

    @Test
    public void test1(){
        System.out.println(medianOfTwoSortedArrays(new int[]{},new int[]{2,7,8}));
    }
}
