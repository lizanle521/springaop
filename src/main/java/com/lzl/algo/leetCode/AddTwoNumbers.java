package com.lzl.algo.leetCode;

import org.junit.Test;

import java.util.List;

/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.

   You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * Created by Lizanle on 2018/1/30.
 */
public class AddTwoNumbers {

    public class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }

    /**
     *  Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
     Output: 7 -> 0 -> 8
     Explanation: 342 + 465 = 807.
     要考虑大数溢出
     */
    public ListNode addTwoNumbers(ListNode node1,ListNode node2){
        int maxLength1 = 0;
        int maxLength2 = 0;
        int leading1 = 0;
        int leading2 = 0;
        int val1 = 0;
        int val2 = 0;

        ListNode p1 = node1;
        ListNode p2 = node2;
        while (p1  != null ){
            maxLength1 ++;
            leading1 = p1.val;
            p1 = p1.next;
        }
        while (p2  != null){
            maxLength2 ++;
            leading2 = p2.val;
            p2 = p2.next;
        }

        if(leading1 == 0){
            val1 = 0;
        }else{
            p1 = node1;
            for (int i = 0; i < maxLength1; i++) {
                val1 += p1.val*(Math.pow(10,i));
                p1 = p1.next;
            }
        }
        if(leading2 == 0){
            val2 = 0;
        }else{
            p2 = node2;
            for (int i = 0; i < maxLength2; i++) {
                val2 += p2.val*(Math.pow(10,i));
                p2 = p2.next;
            }
        }
        if(val1 >= Integer.MAX_VALUE){
            val1 = val1 % Integer.MAX_VALUE;
        }
        if(val2 >= Integer.MAX_VALUE){
            val2 = val2 % Integer.MAX_VALUE;
        }
        int sum = val1 + val2;
        int tem = sum;
        ListNode result = null;
        ListNode header = null;
        while (tem  != 0){
            int tail = tem % 10;
            ListNode listNode = new ListNode(tail);
            if(result == null){
                result = listNode;
                header = listNode;
            }else{
                result.next = listNode;
                result = result.next;
            }
            tem = tem / 10;
        }

        return header;
    }

    /**
     * 上个方法没有考虑大数溢出
     * 这次直接操作两个链表。不转成数相加再转成链表了
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers1(ListNode l1,ListNode l2){
        int maxLength1 = 0;
        int maxLength2 = 0;
        int minLength = 0;
        ListNode p1 = l1;
        ListNode p2 = l2;
        while (p1  != null ){
            maxLength1 ++;

            p1 = p1.next;
        }
        while (p2  != null){
            maxLength2 ++;

            p2 = p2.next;
        }
        minLength = maxLength1 > maxLength2 ? maxLength2 : maxLength1;
        int upValue = 0;
        ListNode p = null;
        ListNode header = null;
        p1 = l1;
        p2 = l2;
        for (int i = 0; i < minLength; i++) {
            int tempVal = p1.val + p2.val + upValue;
            p1 = p1.next;
            p2 = p2.next;
            if(tempVal >= 10){
                upValue = 1;
            }else {
                upValue = 0;
            }
            if(p == null){
                p = new ListNode(tempVal % 10);
                header = p;
            }else {
                p.next = new ListNode(tempVal % 10);
                p = p.next;
            }
        }
        if(maxLength1 > maxLength2){
            p.next = p1;
            while (upValue > 0) {

                int tempVal = (p1 == null ? 0 :p1.val) + upValue;
                p1.val = tempVal % 10;
                if(tempVal >= 10){
                    upValue = 1;
                }else {
                    upValue = 0;
                }
                if(p1.next != null ){
                    p1 = p1.next;
                }else if(upValue > 0 && p1.next == null){
                    p1.next = new ListNode(upValue);
                    break;
                }
            }
        }else if(maxLength2 > maxLength1){
            p.next = p2;
            while (upValue > 0) {

                int tempVal = (p2 == null ? 0 :p2.val) + upValue;
                p2.val = tempVal % 10;
                if(tempVal >= 10){
                    upValue = 1;
                }else {
                    upValue = 0;
                }
                if(p2.next != null ){
                    p2 = p2.next;
                }else if(upValue > 0 && p2.next == null){
                    p2.next = new ListNode(upValue);
                    break;
                }
            }
        }else if( maxLength1 == maxLength2){
            if(upValue > 0){
                p.next = new ListNode(upValue);
            }
        }
        return header;
    }

    @Test
    public void testAddTwoNumbers(){
        ListNode listNode1 = new ListNode(9);

        ListNode listNode2 = new ListNode(1);
        ListNode listNode3 = new ListNode(9);
        ListNode listNode4 = new ListNode(9);
        ListNode listNode5 = new ListNode(9);
        ListNode listNode6 = new ListNode(9);
        ListNode listNode7 = new ListNode(9);
        ListNode listNode8 = new ListNode(9);
        ListNode listNode9 = new ListNode(9);
        ListNode listNode10 = new ListNode(9);
        ListNode listNode11 = new ListNode(9);

        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
        listNode6.next = listNode7;
        listNode7.next = listNode8;
        listNode8.next = listNode9;
        listNode9.next = listNode10;
        listNode10.next = listNode11;

        ListNode listNode = addTwoNumbers1(listNode1, listNode2);
        ListNode p = listNode;
        while (p != null){
            System.out.print(p.val);
            p = p.next;
        }
        System.out.println();
    }
}
