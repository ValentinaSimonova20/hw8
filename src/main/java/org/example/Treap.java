package org.example;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * Теория<br/>
 * <a href="http://e-maxx.ru/algo/treap">http://e-maxx.ru/algo/treap</a><br/>
 * <a href="https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/">https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/</a><br/>
 * <a href="https://www.geeksforgeeks.org/implementation-of-search-insert-and-delete-in-treap/">https://www.geeksforgeeks.org/implementation-of-search-insert-and-delete-in-treap/</a><br/>
 * <a href="http://faculty.washington.edu/aragon/pubs/rst89.pdf">http://faculty.washington.edu/aragon/pubs/rst89.pdf</a><br/>
 * <a href="https://habr.com/ru/articles/101818/">https://habr.com/ru/articles/101818/</a><br/>
 * Примеение в linux kernel<br/>
 * <a href="https://www.kernel.org/doc/mirror/ols2005v2.pdf">https://www.kernel.org/doc/mirror/ols2005v2.pdf</a>
 *
 */
public class Treap {

    Node root;

    public Treap() {
    }

    public Double getProfit(int amount, Double price) {
        List<Double> result = new ArrayList<>();
        calculateProfit(root, amount, price,result);
        currentAmount = 0;
        return result.stream().mapToDouble(i -> i).sum();
    }


    static Integer currentAmount = 0;
    private void calculateProfit(Node cur, Integer amount, Double price, List<Double> result) {
        if (cur == null || currentAmount >= amount) {
            return;
        }
        calculateProfit(cur.left, amount, price, result);
        if(cur.price >= price && currentAmount < amount) {
            result.add(0.01);
            currentAmount++;
        }
        calculateProfit(cur.right, amount, price, result);
    }

    public void add(Integer key, Double prices) {
        root = insert(root, key, prices);
    }


    public void remove(Integer key) {
        root = deleteNode(root, key);
    }

    private Node deleteNode(Node cur, Integer key) {
        if (cur == null)
            return cur;

        if (key.compareTo(cur.key) < 0)
            cur.left = deleteNode(cur.left, key);
        else if (key.compareTo(cur.key) > 0)
            cur.right = deleteNode(cur.right, key);

            // IF KEY IS AT ROOT

            // If left is NULL
        else if (cur.left == null) {
            Node temp = cur.right;
            cur = temp;  // Make right child as root
        }
        // If Right is NULL
        else if (cur.right == null) {
            Node temp = cur.left;
            cur = temp;  // Make left child as root
        }
        // If key is at root and both left and right are not NULL
        else if (cur.left.priority < cur.right.priority) {
            cur = leftRotation(cur);
            cur.left = deleteNode(cur.left, key);
        } else {
            cur = rightRotation(cur);
            cur.right = deleteNode(cur.right, key);
        }

        return cur;
    }


    public Integer getKeyByPrice(Double price) {
        int[] result = new int[1];
        calculateIndexNumber(root, price, result);
        return result[0];
    }


    private void calculateIndexNumber(Node cur, Double price, int[] result) {
        if (cur == null) {
            return;
        }
        calculateIndexNumber(cur.left, price, result);
        if(cur.price.equals(price)) {
            result[0] = cur.key;
            return;
        }
        calculateIndexNumber(cur.right, price, result);
    }



    private Node searchNode(Node cur, Integer key) {
        if (cur == null || key.compareTo(cur.key) == 0) {
            return cur;
        }
        if (key.compareTo(cur.key) > 0) {
            return searchNode(cur.right, key);
        }
        return searchNode(cur.left, key);
    }


    private Node insert(Node cur, Integer key, Double price) {
        if (cur == null) {
            return new Node(key, price);
        }
        if (key.compareTo(cur.key) > 0) {
            cur.right = insert(cur.right, key, price);
            if (cur.right.priority < cur.priority) {
                cur = leftRotation(cur);
            }

        } else {
            cur.left = insert(cur.left, key, price);
            if (cur.left.priority < cur.priority) {
                cur = rightRotation(cur);
            }

        }
        return cur;
    }

    /* T1, T2 and T3 are subtrees of the tree rooted with y
  (on left side) or x (on right side)
                y                               x
               / \     Right Rotation          /  \
              x   T3   – – – – – – – >        T1   y
             / \       < - - - - - - -            / \
            T1  T2     Left Rotation            T2  T3 */

    private Node leftRotation(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    private Node rightRotation(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }


    @Getter
    public static class Node {
        static Random RND = new Random();
        Integer key;
        int priority;
        Double price;
        Node left;
        Node right;

        public Node(Integer key, Double price) {
            this(key, RND.nextInt(10), price);
        }

        public Node(Integer key, int priority, Double price) {
            this(key, priority, null, null, price);
        }

        public Node(Integer key, int priority, Node left, Node right, Double price) {
            this.key = key;
            this.priority = priority;
            this.left = left;
            this.right = right;
            this.price = price;
        }

        @Override
        public String toString() {
            return String.format("(%d,%d,%f)", key, priority, price);
        }
    }
}
