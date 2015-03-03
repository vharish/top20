package com.algorithmica.ds.map;

public class HashMap<K, V> implements Map<K, V> {

	private static final int INITIAL_CAPACITY = 10;

	private int size = 0;
	private int capacity = INITIAL_CAPACITY;
	protected Entry<K, V>[] buckets;

	public HashMap() {
		this.capacity = INITIAL_CAPACITY;
		init();
	}

	public HashMap(int capacity) {
		this.capacity = capacity;
		init();
	}

	private void init() {
		buckets = new Entry[capacity];
	}

	public boolean put(K key, V value) {
		if (size + 1 > capacity) {
			rehash();
		}
		int index = hash(key) % buckets.length;
		Entry<K, V> head = buckets[index];
		if (head == null) {
			buckets[index] = new Entry<K, V>(key, value);
			++size;
		} else {
			Entry<K, V> curr = head, prev = null;
			for (; curr != null && !curr.key.equals(key); prev = curr, curr = curr.next)
				;
			if (curr == null) {
				prev.next = new Entry<K, V>(key, value);
				++size;
			} else {
				if (curr.value.equals(value)) {
					return false;
				}
				curr.value = value;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private void rehash() {
		int newCapacity = capacity * 2;
		Entry<K, V>[] oldBuckets = buckets;
		buckets = new Entry[newCapacity];
		for (Entry<K, V> e : oldBuckets) {
			for (Entry<K, V> curr = e; curr != null; curr = curr.next) {
				put(curr.key, curr.value);
			}
		}
		capacity = newCapacity;
	}

	private int hash(K key) {
		return Math.abs(key.hashCode());
	}

	public boolean containsKey(K key) {
		int index = hash(key) % buckets.length;
		Entry<K, V> head = buckets[index];
		if (head != null) {
			for (Entry<K, V> curr = head; curr != null; curr = curr.next) {
				if (curr.key.equals(key)) {
					return true;
				}
			}
		}
		return false;
	}

	public V remove(K key) {
		int index = hash(key) % buckets.length;
		Entry<K, V> head = buckets[index];
		if (head != null) {
			Entry<K, V> curr = head, prev = null;
			for (; curr != null; prev = curr, curr = curr.next) {
				if (curr.key.equals(key)) {
					if (prev != null) {
						prev.next = curr.next;
					} else {
						buckets[index] = curr.next;
					}
					curr.next = null;
					--size;
					return curr.value;
				}
			}
		}
		return null;
	}

	public V get(K key) {
		int index = hash(key) % buckets.length;
		Entry<K, V> head = buckets[index];
		if (head != null) {
			for (Entry<K, V> curr = head; curr != null; curr = curr.next) {
				if (curr.key.equals(key)) {
					return curr.value;
				}
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

	public void display() {
		auxDisplay(true, true);
	}

	public void displayKeys() {
		auxDisplay(true, false);
	}

	private void auxDisplay(boolean keys, boolean values) {
		StringBuilder sb = new StringBuilder();
		boolean both = keys && values;
		for (Entry<K, V> e : buckets) {
			for (Entry<K, V> curr = e; curr != null; curr = curr.next) {
				if (both) {
					sb.append("(" + curr.key.toString() + ", "
							+ curr.value.toString() + ")" + ", ");
				} else if (keys) {
					sb.append(curr.key.toString() + ", ");
				} else if (values) {
					sb.append(curr.value.toString() + ", ");
				}
			}
		}
		System.out.println(sb.toString());
	}

	class Entry<K2, V2> {
		K2 key;
		V2 value;
		int hash;
		Entry<K2, V2> next, prev;

		public Entry(K2 key, V2 value) {
			this.key = key;
			this.value = value;
		}
	}

}
