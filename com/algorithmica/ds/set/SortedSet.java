package com.algorithmica.ds.set;

public interface SortedSet<T extends Comparable<T>> extends Set<T> {

	public T findMin();

	public T findMax();

	public T findKthSmallest(int k);

}
