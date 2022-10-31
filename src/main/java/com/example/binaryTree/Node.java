package com.example.binaryTree;

public class Node {
    int Data;
    Node Left;
    Node Right;
    boolean rtag;
    boolean ltag;
    double x;
    double y;
    public Node(int Data){
        this.Data = Data;
        this.Left = null;
        this.Right = null;
        this.rtag = true;
    }
}
