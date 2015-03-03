package com.algorithmica.ds.map;

public interface Map<K, V> {

	public boolean put(K key, V value);

	public boolean containsKey(K key);

	public V remove(K key);

	public V get(K key);

	public int size();

	public boolean isEmpty();

	public void display();
	
	public void displayKeys();
}