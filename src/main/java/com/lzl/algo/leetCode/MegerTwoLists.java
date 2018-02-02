package com.lzl.algo.leetCode;

import org.junit.Test;

/**
 * Created by Lizanle on 2018/2/2.
 */
public class MegerTwoLists {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode megerTwoLists(ListNode l1,ListNode l2){
        ListNode listNode =  null,head = null;
        while(l1 != null || l2 != null){
            if(head == null){
                if(l1 != null && l2 != null){
                    if(l1.val > l2.val){
                       head =  new ListNode(l2.val);
                        l2 = l2.next;
                    }else {
                        head =  new ListNode(l1.val);
                        l1 = l1.next;
                    }
                }
                else if(l1 == null){
                    head = new ListNode(l2.val);
                    l2 = l2.next;
                }
                else if(l2 == null){
                    head = new ListNode(l1.val);
                    l1 = l1.next;
                }
                listNode = head ;
            }else {
                if(l1 != null && l2 != null){
                    if(l1.val < l2.val){
                        listNode.next = new ListNode(l1.val);
                        l1 = l1.next;
                    }else{
                        listNode.next = new ListNode(l2.val);
                        l2 = l2.next;
                    }
                    listNode = listNode.next;
                }
                else if(l1 == null){
                    listNode.next = new ListNode(l2.val);
                    listNode = listNode.next;
                    l2 = l2.next;
                }
                else if(l2 == null){
                    listNode.next = new ListNode(l1.val);
                    listNode = listNode.next;
                    l1 = l1.next;
                }
            }
        }
        return head;
    }

    @Test
    public void test(){
            ListNode l1 = new ListNode(1);
            ListNode l2 = new ListNode(3);
            ListNode l3 = new ListNode(5);
            l1.next = l2;
            l2.next = l3;

        ListNode l4 = new ListNode(1);
        ListNode l5 = new ListNode(3);
        ListNode l6 = new ListNode(6);
        l4.next = l5;
        l5.next = l6;

        ListNode listNode = this.megerTwoLists(l1, l4);
        while (listNode != null){
            System.out.println(listNode.val);
            listNode = listNode.next;
        }

    }
}
