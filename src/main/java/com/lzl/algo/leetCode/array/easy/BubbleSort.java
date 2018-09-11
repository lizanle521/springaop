package com.lzl.algo.leetCode.array.easy;

public class BubbleSort {
    /**
     * 每次找出最小的，需要经过n-1的查找
     * N个数字要排序完成，总共进行N-1趟排序，每i趟的排序次数为(N-i)次，所以可以用双重循环语句，外层控制循环多少趟，内层控制每一趟的循环次数
     * @param nums
     * @return
     */
    public int[] sort(int[] nums){

        for(int i = 0;i < nums.length-1;i++){//外层循环控制次数N-1
            for(int j = 0;j < nums.length -i-1;j++){ // 内层控制每次排序次数为n-i
                if(nums[j+1] < nums[j]){
                    int temp = nums[j+1];
                    nums[j+1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        BubbleSort bubbleSort = new BubbleSort();
        int[] sort = bubbleSort.sort(new int[]{2, 3, 5, 1, 7, 0});
        for (int i : sort) {
            System.out.println(i);
        }
    }
}
