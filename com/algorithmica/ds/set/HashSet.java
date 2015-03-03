package com.algorithmica.ds.set;

import com.algorithmica.ds.map.HashMap;

public class HashSet<T> implements Set<T> {

	private HashMap<T, Object> map = new HashMap<T, Object>();

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

}
