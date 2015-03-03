package com.algorithmica.ds.queue;

public interface Queue<E> {

    public boolean enqueue(E e);

    public E dequeue();

    public int size();

    public boolean isEmpty();

    public boolean contains(Object o);

    public void display();

}
