package com.example.elastic.test;

public class ListNode {

    public int val;
    public ListNode next;
    public ListNode head;

    public ListNode(){

    }

    public ListNode(int val){
        this.val=val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public void first(int data){
        ListNode listNode=new ListNode(data);
        listNode.next=this.head;
        this.head=listNode;
    }

}
