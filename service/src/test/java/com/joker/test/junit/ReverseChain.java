//package com.joker.test.junit;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//public class ReverseChain {
//    private static Node chain;
//
//    static {
//        Node last = null;
//        for (int i = 'd'; i >= 'a'; i--) {
//            chain = new Node(String.valueOf((char) i), last);
//            last = chain;
//        }
//    }
//
//    public static void main(String[] args) {
//        System.out.println(chain);
//        Node left = null;
//        Node right = chain;
//        while (right != null) {
//            Node temp = right.getNext();
//            right.setNext(left);
//            left = right;
//            right = temp;
//            if (right == null) {
//                chain = left;
//            }
//            System.out.println(left);
//        }
//        System.out.println(chain);
//    }
//}
//
//@Data
//@AllArgsConstructor
//class Node {
//    private String val;
//    private Node next;
//}