package com.algorithmica.ds.map;

public interface SortedMap<K, V> extends Map<K, V> {

	public K findMinKey();

	public K findMaxKey();

	public K findKthSmallestKey(int k);
}
