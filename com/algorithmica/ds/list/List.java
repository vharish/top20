package com.algorithmica.ds.list;

public interface List<T> {

	static final String INDEX_OUT_OF_BOUNDS = "Specified index is out of range";

	public int size();

	public boolean add(T element);

	public boolean add(int index, T element);

	public boolean contains(T element);

	public T remove(int index);

	public boolean remove(T element);

	public boolean isEmpty();

	public T get(int index);

	public void display();

}
