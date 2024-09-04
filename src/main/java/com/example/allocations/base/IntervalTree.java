package com.example.allocations.base;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IntervalTree<T extends TimeInterval> {
    private class Node {
        T interval;
        LocalDateTime maxEnd;
        Node left, right;

        Node(T interval) {
            this.interval = interval;
            this.maxEnd = interval.getEnd();
        }
    }

    private Node root;

    public IntervalTree(List<T> intervals) {
        if (intervals != null && !intervals.isEmpty()) {
            for (T interval : intervals) {
                insert(interval);
            }
        }
    }

    public void insert(T interval) {
        root = insert(root, interval);
    }

    private Node insert(Node node, T interval) {
        if (node == null) {
            return new Node(interval);
        }

        // Check for overlap before inserting
        if (isOverlap(interval, node.interval)) {
            throw new IllegalArgumentException("Cannot insert overlapping interval: " + interval + " " +node.interval);
        }

        // Insert the interval in the correct subtree
        if (interval.getStart().isBefore(node.interval.getStart())) {
            node.left = insert(node.left, interval);
        } else {
            node.right = insert(node.right, interval);
        }

        // Update the maxEnd value
        if (node.maxEnd.isBefore(interval.getEnd())) {
            node.maxEnd = interval.getEnd();
        }

        return node;
    }

    private boolean isOverlap(T interval1, T interval2) {
        return interval1.getStart().isBefore(interval2.getEnd()) && interval1.getEnd().isAfter(interval2.getStart());
    }

    public List<T> query(LocalDateTime start, LocalDateTime end) {
        List<T> result = new ArrayList<>();
        query(root, start, end, result);
        return result;
    }

    private void query(Node node, LocalDateTime start, LocalDateTime end, List<T> result) {
        if (node == null) {
            return;
        }

        // Check if the interval of this node overlaps with the query interval
        if (node.interval.getStart().isBefore(end) && node.interval.getEnd().isAfter(start)) {
            result.add(node.interval);
        }

        // If there's a left child and its maxEnd is greater than start, we might have overlapping intervals on the left
        if (node.left != null && node.left.maxEnd.isAfter(start)) {
            query(node.left, start, end, result);
        }

        // Always query the right subtree
        query(node.right, start, end, result);
    }
}
