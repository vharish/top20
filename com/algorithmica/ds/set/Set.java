package com.algorithmica.ds.set;

public interface Set<T> {
	public boolean add(T element);

	public boolean remove(T element);

	public boolean isEmpty();

	public int size();

	public void display();
}
