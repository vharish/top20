package com.algorithmica.ds.list;

import java.util.Arrays;

public class ArrayList<T> implements List<T> {

	private Object[] list;

	private final int MAX_CAPACITY = Integer.MAX_VALUE;

	private final int INIT_CAPACITY = 10;

	private final int INC_FACTOR = 2;

	private int capacity = INIT_CAPACITY;

	private int size = 0;

	public ArrayList() {
		init();
	}

	public ArrayList(int capacity) {
		this.capacity = capacity;
		init();
	}

	private void init() {
		list = new Object[capacity];
	}

	public int size() {
		return size;
	}

	public boolean add(T element) {
		ensureCapacity(size + 1);
		list[size++] = element;
		return true;
	}

	public boolean add(int index, T element) {
		checkRange(index, 0, size);
		ensureCapacity(size + 1);
		shiftToRight(index, 1);
		list[index] = element;
		++size;
		return true;
	}

	private void ensureCapacity(int reqCapacity) {
		if (reqCapacity > capacity) {
			increaseCapacity();
		}
	}

	private void increaseCapacity() {
		int newCapacity = capacity * INC_FACTOR;
		list = Arrays.copyOf(list, newCapacity);
		capacity = newCapacity;
	}

	public boolean contains(T element) {
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public T remove(int index) {
		checkRange(index, 0, size - 1);
		Object element = list[index];
		shiftToLeft(index + 1, 1);
		--size;
		return (T) element;
	}

	public boolean remove(T element) {
		int index = getFirstIndex(list, element);
		if (index < 0) {
			return false;
		}
		remove(index);
		return true;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void display() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < size - 1; i++) {
			sb.append(list[i].toString() + ", ");
		}
		sb.append(list[size - 1].toString()).append("]");
		System.out.println(sb.toString());
	}

	@SuppressWarnings("unchecked")
	public T get(int index) {
		checkRange(index, 0, size - 1);
		return (T) list[index];
	}

	private int getFirstIndex(Object[] list2, T element) {
		for (int i = 0; i < size; i++) {
			if (list2[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}

	private void shiftToRight(int fromIndex, int shiftBy) {
		for (int i = size; i > fromIndex; i--) {
			list[i] = list[i - 1];
		}
	}

	private void shiftToLeft(int fromIndex, int shiftBy) {
		for (int i = fromIndex; i < size; i++) {
			list[i - 1] = list[i];
		}
	}

	private void checkRange(int index, int min, int max) {
		if (index < min || index > size) {
			throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
		}
	}
}
