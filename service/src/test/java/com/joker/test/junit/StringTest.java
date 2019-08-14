package com.joker.test.junit;

import lombok.Data;
import org.junit.Test;

import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 * created by Joker on 2019/7/24
 */
public class StringTest {

    /**
     * 字符串替换1
     */
    @Test
    public void test1() {
        String str = "{0}/{1}/{2}";
        String format = MessageFormat.format(str, "a", "b", 3);
        System.out.println(format);
    }

    /**
     * 字符串替换2
     */
    @Test
    public void test2() {
        String format = String.format("%s/%s/%s", 1, 2, 3);
        System.out.println(format);
        System.out.println('\u0000');

    }

    @Test
    public void test5() {
        //获取系统默认的字符编码
        System.out.println(Charset.defaultCharset());
        //获取系统默认语言
        System.out.println(System.getProperty("user.language"));
    }

    @Test
    public void test6() {
        Double result = 1.414 * 1.414 > 2 ? 1.413 : 1.414;
        double dian = 0.001;
        while (result.toString().length() < 12) {
            dian *= 0.1;
            for (int i = 1; i < 10; i++) {
                if ((result + dian * i) * (result + dian * i) > 2) {
                    result = result + dian * (i - 1);
                    System.out.println(result);
                    break;
                }
            }
        }
        System.out.println("计算结果:" + result);
        System.out.println("校验结果");
        System.out.println(result * result);
        System.out.println((result + 0.0000000001) * (result + 0.0000000001));
    }

    @Test
    public void test7() {
        double low = 1.41, high = 1.42;
        double mid = (high + low) / 2;
        double dian = 0.0000000001;
        System.out.println(String.valueOf(mid).length());
        while ((high - low) > dian) {
            System.out.println(mid);
            if (mid * mid < 2) {
                low = mid;
            } else {
                high = mid;
            }
            mid = (high + low) / 2;
        }
        System.out.println(mid);
    }

    @Test
    public void test8() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        node1.next = node2;
        node2.next = node3;

        Node node = node1;
        while (node.next != null) {
            System.out.println(node);
            node = node.next;
        }
        System.out.println(node);

        Node left = null;
        Node right = node1;
        while (right != null) {
            Node temp = right.getNext();

            right.setNext(left);

            left = right;

            right = temp;
        }

        node = left;
        while (node.next != null) {
            System.out.println(node);
            node = node.next;
        }
        System.out.println(node);
    }

}

@Data
class Node<T> {
    T val;
    Node next;

    public Node(T val) {
        this.val = val;
    }
}
