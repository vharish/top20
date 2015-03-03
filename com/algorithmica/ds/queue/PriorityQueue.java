package com.algorithmica.ds.queue;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private int     initialCapacity = 10;
    private List<E> heap;

    public PriorityQueue() {
        init();
    }

    public PriorityQueue(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        init();
    }

    private void init() {
        heap = new ArrayList<E>(initialCapacity);
    }

    public boolean enqueue(E e) {
        heap.add(e);
        int current = heap.size() - 1;
        while (current > 0) {
            int res = heap.get(current).compareTo(heap.get((current - 1) / 2));
            if (res > 0) {
                swap(heap, current, (current - 1) / 2);
                current = (current - 1) / 2;
            } else {
                break;
            }
        }
        return true;
    }

    public E dequeue() {
        E t = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        int current = 0;
        while (2 * current + 1 < heap.size()) {
            int res1 = heap.get(current).compareTo(heap.get(2 * current + 1));
            int res2 = 0;
            if (2 * current + 2 < heap.size()) {
                res2 = heap.get(current).compareTo(heap.get(2 * current + 2));
            }
            if (res1 < 0) {
                if (res2 != 0
                        && heap.get(2 * current + 1).compareTo(
                                heap.get(2 * current + 2)) < 0) {
                    swap(heap, current, 2 * current + 2);
                    current = 2 * current + 2;
                } else {
                    swap(heap, current, 2 * current + 1);
                    current = 2 * current + 1;
                }
            } else if (res2 < 0) {
                swap(heap, current, 2 * current + 2);
                current = 2 * current + 2;
            } else {
                break;
            }
        }
        return t;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public boolean contains(Object o) {
        return heap.contains(o);
    }

    private static <E> void swap(List<E> heap, int i, int j) {
        E t = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, t);
    }

    public void display() {
        for (E e : heap) {
            System.out.print(e + ",");
        }
        System.out.println();
    }
}
