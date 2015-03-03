package com.algorithmica.ds.map;

import java.util.Stack;

public class TreeMap<K extends Comparable<K>, V> implements SortedMap<K, V> {

	private int size = 0;

	private Entry<K, V> root = null;

	public boolean put(K key, V value) {
		Entry<K, V> entry = getEntry(key);
		boolean isAdded = false;
		if (entry != null) {
			entry.value = value;
			isAdded = true;
		} else {
			entry = new Entry<K, V>(key, value);
			isAdded = addEntry(entry);
			if (isAdded) {
				++size;
			}
		}
		return isAdded;
	}

	private boolean addEntry(Entry<K, V> newEntry) {
		if (root == null) {
			root = newEntry;
			return true;
		}
		Entry<K, V> curr = root;

		while (true) {
			if (newEntry.key.compareTo(curr.key) < 0) {
				if (curr.left == null) {
					curr.left = newEntry;
					return true;
				} else {
					curr = curr.left;
				}
			} else if (newEntry.key.compareTo(curr.key) > 0) {
				if (curr.right == null) {
					curr.right = newEntry;
					return true;
				} else {
					curr = curr.right;
				}
			} else {
				return false;
			}
		}
	}

	public boolean containsKey(K key) {
		Entry<K, V> entry = getEntry(key);
		return (entry != null) ? true : false;
	}

	public V remove(K key) {
		Entry<K, V> entry = getEntry(key);
		if (entry == null) {
			return null;
		}
		Entry<K, V> parent;
		boolean isRoot = false;
		if (entry == root) {
			isRoot = true;
			parent = new Entry<K, V>(null, null, null, root);
		} else {
			parent = getParent(entry);
		}
		Entry<K, V> temp = null;
		if (entry.left == null && entry.right == null) {
		} else if (entry.left == null || entry.right == null) {
			temp = (entry.left != null) ? entry.left : entry.right;
		} else {
			temp = removeMax(entry.left);
			if (entry.left != temp) {
				temp.left = entry.left;
			}
			temp.right = entry.right;
		}
		if (parent.left == entry) {
			parent.left = temp;
		} else {
			parent.right = temp;
		}
		if (isRoot) {
			root = parent.right;
		}
		--size;
		return null;
	}

	private Entry<K, V> removeMax(Entry<K, V> curr) {
		if (curr == null || curr.right == null) {
			return curr;
		}
		Entry<K, V> prev = curr;
		curr = curr.right;
		for (; curr.right != null; prev = curr, curr = curr.right)
			;
		if (curr.left != null) {
			prev.right = curr.left;
		} else {
			prev.right = null;
		}
		return curr;
	}

	private Entry<K, V> removeMin(Entry<K, V> curr) {
		if (curr == null || curr.left == null) {
			return curr;
		}
		Entry<K, V> prev = curr;
		curr = curr.left;
		for (; curr.left != null; prev = curr, curr = curr.left)
			;
		if (curr.right != null) {
			prev.left = curr.right;
		} else {
			prev.left = null;
		}
		return curr;
	}

	public V get(K key) {
		Entry<K, V> curr = getEntry(key);
		return (curr != null) ? curr.value : null;
	}

	private Entry<K, V> getEntry(K key) {
		if (key == null) {
			return null;
		}
		Entry<K, V> curr = root;
		while (curr != null) {
			if (key.compareTo(curr.key) == 0) {
				return curr;
			}
			if (key.compareTo(curr.key) < 0) {
				curr = curr.left;
			} else {
				curr = curr.right;
			}
		}
		return curr;
	}

	private Entry<K, V> getParent(Entry<K, V> entry) {
		if (root == entry || entry == null)
			return null;
		Entry<K, V> curr = root;
		while (curr != null) {
			if (curr.left == entry || curr.right == entry) {
				return curr;
			}
			if (entry.key.compareTo(curr.key) < 0) {
				curr = curr.left;
			} else if (entry.key.compareTo(curr.key) > 0) {
				curr = curr.left;
			} else {
				break;
			}
		}
		return null;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public K findMinKey() {
		if (isEmpty()) {
			return null;
		}
		Entry<K, V> curr = root;
		for (; curr.left != null; curr = curr.left)
			;
		return curr.key;
	}

	public K findMaxKey() {
		if (isEmpty()) {
			return null;
		}
		Entry<K, V> curr = root;
		for (; curr.right != null; curr = curr.right)
			;
		return curr.key;
	}

	public K findKthSmallestKey(int k) {
		if (k > size || k < 1)
			return null;
		Entry<K, V> curr = root;
		Stack<Entry<K, V>> stack = new Stack<Entry<K, V>>();
		while (true) {
			while (curr != null) {
				stack.push(curr);
				curr = curr.left;
			}
			do {
				curr = stack.pop();
				--k;
				if (k == 0) {
					return curr.key;
				}
			} while (curr.right == null && !stack.isEmpty());
			curr = curr.right;
		}
	}

	public void display() {
		auxDisplay(true, true);
	}

	public void displayKeys() {
		auxDisplay(true, false);
	}

	private void auxDisplay(boolean keys, boolean values) {
		if (isEmpty())
			return;
		StringBuilder sb = new StringBuilder();
		Entry<K, V> curr = root;
		Stack<Entry<K, V>> stack = new Stack<Entry<K, V>>();
		while (true) {
			while (curr != null) {
				stack.push(curr);
				curr = curr.left;
			}
			if (stack.isEmpty()) {
				break;
			}
			do {
				curr = stack.pop();
				if (keys && values) {
					sb.append(curr + ", ");
				} else if (keys) {
					sb.append(curr.key + ",");
				} else if (values) {
					sb.append(curr.value + ",");
				}
			} while (curr.right == null && !stack.isEmpty());
			curr = curr.right;
		}
		System.out.println(sb.toString());
	}

	class Entry<K2 extends Comparable<K2>, V2> {
		K2 key;
		V2 value;
		Entry<K2, V2> left, right;

		public Entry(K2 key, V2 value) {
			this.key = key;
			this.value = value;
		}

		public Entry(K2 key, V2 value, Entry<K2, V2> left, Entry<K2, V2> right) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
		}

		public String toString() {
			return "(" + key.toString() + ", " + value.toString() + ")";
		}

	}
}
