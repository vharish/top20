package com.algorithmica.ds.set;

import com.algorithmica.ds.map.TreeMapBalanced;

public class TreeSet<T extends Comparable<T>> implements SortedSet<T> {

	private TreeMapBalanced<T, Object> map = new TreeMapBalanced<T, Object>();

	private final Object VALUE_OBJECT = new Object();

	public boolean add(T element) {
		return map.put(element, VALUE_OBJECT);
	}

	public boolean remove(T element) {
		return map.remove(element) == VALUE_OBJECT;
	}

	public boolean isEmpty() {
		return map.size() == 0;
	}

	public int size() {
		return map.size();
	}

	public void display() {
		map.displayKeys();
	}

	public T findMin() {
		return map.findMinKey();
	}

	public T findMax() {
		return map.findMaxKey();
	}

	public T findKthSmallest(int k) {
		return map.findKthSmallestKey(k);
	}
}
