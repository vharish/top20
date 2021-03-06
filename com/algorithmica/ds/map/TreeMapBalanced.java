package com.algorithmica.ds.map;


public class TreeMapBalanced<K extends Comparable<K>, V> implements
		SortedMap<K, V> {

	private int size = 0;

	private Entry<K, V> root = null;

	public boolean put(K key, V value) {
		Entry<K, V> entry = getEntry(key);
		if (entry != null) {
			entry.value = value;
			return true;
		}
		entry = new Entry<K, V>(key, value);
		boolean isAdded = addEntry(entry);
		if (isAdded) {
			++size;
		}
		return isAdded;
	}

	private boolean addEntry(Entry<K, V> entry) {
		if (root == null) {
			root = entry;
			return true;
		}
		return auxAddEntry(null, root, entry);
	}

	private boolean auxAddEntry(Entry<K, V> prev, Entry<K, V> curr,
			Entry<K, V> newEntry) {
		if (curr == null) {
			if (newEntry.key.compareTo(prev.key) < 0) {
				prev.left = newEntry;
				return true;
			} else if (newEntry.key.compareTo(prev.key) > 0) {
				prev.right = newEntry;
				return true;
			} else {
				return false;
			}
		}
		if (curr.key.equals(newEntry.key)) {
			return false;
		}
		boolean isAdded = true;
		if (newEntry.key.compareTo(curr.key) < 0) {
			isAdded = auxAddEntry(curr, curr.left, newEntry);
		} else if (newEntry.key.compareTo(curr.key) > 0) {
			isAdded = auxAddEntry(curr, curr.right, newEntry);
		}
		updateHeight(curr);
		if (!isBalanced(curr)) {
			balance(prev, curr);
		}
		return isAdded;
	}

	private void balance(Entry<K, V> parent, Entry<K, V> node) {
		if (isBalanced(node)) {
			return;
		}
		TypeOfImbalance type = findTypeOfImbalance(node);
		switch (type) {
		case LL:
			rotateRight(parent, node);
			break;
		case RR:
			rotateLeft(parent, node);
			break;
		case LR:
			rotateLeft(node, node.left);
			rotateRight(parent, node);
			break;
		case RL:
			rotateRight(node, node.right);
			rotateLeft(parent, node);
			break;
		default:
			break;
		}
	}

	private void rotateRight(Entry<K, V> parent, Entry<K, V> node) {
		Entry<K, V> lChild = node.left;
		if (parent.left == node) {
			parent.left = lChild;
		} else {
			parent.right = lChild;
		}
		node.left = lChild.right;
		lChild.right = node;
		updateHeight(node);
		updateHeight(lChild);
		updateHeight(parent);
	}

	private void rotateLeft(Entry<K, V> parent, Entry<K, V> node) {
		Entry<K, V> rChild = node.right;
		if (parent.left == node) {
			parent.left = rChild;
		} else {
			parent.right = rChild;
		}
		rChild.left = node;
		node.right = rChild.left;
		updateHeight(node);
		updateHeight(rChild);
		updateHeight(parent);
	}

	private TypeOfImbalance findTypeOfImbalance(Entry<K, V> node) {
		if (!isBalanced(node)) {
			return TypeOfImbalance.NONE;
		}
		int hLeft = (node.left == null) ? -1 : node.left.height;
		int hRight = (node.right == null) ? -1 : node.right.height;
		if (hLeft > hRight) {
			if (node.left.left != null) {
				return TypeOfImbalance.LL;
			} else {
				return TypeOfImbalance.LR;
			}
		} else {
			if (node.right.right != null) {
				return TypeOfImbalance.RR;
			} else {
				return TypeOfImbalance.RL;
			}
		}
	}

	private boolean isBalanced(Entry<K, V> curr) {
		if (curr == null) {
			return true;
		}
		int hLeft = (curr.left == null) ? -1 : curr.left.height;
		int hRight = (curr.right == null) ? -1 : curr.right.height;
		int diff = Math.abs(hLeft - hRight);
		return diff <= 1;
	}

	private Entry<K, V> getEntry(K key) {
		if (key == null) {
			return null;
		}
		Entry<K, V> curr = root;
		while (curr != null) {
			if (key.compareTo(curr.key) < 0) {
				curr = curr.left;
			} else if (key.compareTo(curr.key) > 0) {
				curr = curr.right;
			} else {
				break;
			}
		}
		return curr;
	}

	private void updateHeight(Entry<K, V> curr) {
		if (curr == null) {
			return;
		}
		int hLeft = (curr.left == null) ? -1 : curr.left.height;
		int hRight = (curr.right == null) ? -1 : curr.right.height;
		curr.height = Math.max(hLeft + 1, hRight + 1);
	}

	public boolean containsKey(K key) {
		return getEntry(key) != null;
	}

	public V remove(K key) {
		if (containsKey(key)) {
			Entry<K, V> entry = removeEntry(key);
			--size;
			return entry.value;
		}
		return null;
	}

	private Entry<K, V> removeEntry(K key) {
		Entry<K, V> falseRoot = new Entry<K, V>(null, null, null, root);
		Entry<K, V> removed = auxRemoveEntry(falseRoot, root, key);
		if (root == removed) {
			root = falseRoot.right;
		}
		return removed;
	}

	private Entry<K, V> auxRemoveEntry(Entry<K, V> prev, Entry<K, V> curr, K key) {
		if (curr == null) {
			return null;
		}
		Entry<K, V> removed = null;
		if (key.compareTo(curr.key) < 0) {
			removed = auxRemoveEntry(curr, curr.left, key);
		} else if (key.compareTo(curr.key) > 0) {
			removed = auxRemoveEntry(curr, curr.right, key);
		} else {
			Entry<K, V> temp = null;
			if (curr.left == null && curr.right == null) {
			} else if (curr.left == null || curr.right == null) {
				temp = (curr.left != null) ? curr.left : curr.right;
			} else {
				temp = removeMax(curr.left);
				if (curr.left != temp) {
					temp.left = curr.left;
				}
				temp.right = curr.right;
				updateHeight(temp);
			}
			if (prev.left == curr) {
				prev.left = temp;
			} else {
				prev.right = temp;
			}
			return curr;
		}
		if (removed != null) {
			updateHeight(curr);
		}
		return removed;
	}

	private Entry<K, V> removeMax(Entry<K, V> curr) {
		if (curr == null || curr.right == null) {
			return curr;
		}
		Entry<K, V> prev = curr;
		curr = curr.right;
		while (curr.right != null) {
			--prev.height;
			prev = curr;
			curr = curr.right;
		}
		if (curr.left != null) {
			prev.right = curr.left;
		} else {
			prev.right = null;
		}
		--prev.height;
		return curr;
	}

	public V get(K key) {
		Entry<K, V> entry = getEntry(key);
		return entry == null ? null : entry.value;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void display() {
		auxDisplay(root);
		System.out.println();
	}

	private void auxDisplay(Entry<K, V> curr) {
		if (curr == null) {
			return;
		}
		auxDisplay(curr.left);
		System.out.print(curr + ", ");
		auxDisplay(curr.right);
	}

	public void displayKeys() {
		auxDisplayKeys(root);
		System.out.println();
	}

	private void auxDisplayKeys(Entry<K, V> curr) {
		if (curr == null) {
			return;
		}
		auxDisplay(curr.left);
		System.out.println(curr.key + ", ");
		auxDisplay(curr.right);
	}

	public K findMinKey() {
		return findKthSmallestKey(1);
	}

	public K findMaxKey() {
		return findKthSmallestKey(size);
	}

	public K findKthSmallestKey(int k) {
		if (k > size || k <= 0) {
			return null;
		}
		Entry<K, V> entry = auxFindKthSmallestEntry(root, k, new Counter(0));
		return entry.key;
	}

	private Entry<K, V> auxFindKthSmallestEntry(Entry<K, V> curr, int k,
			Counter counter) {
		if (curr == null) {
			return null;
		}
		Entry<K, V> entry = auxFindKthSmallestEntry(curr.left, k, counter);
		if (entry != null) {
			return entry;
		}
		++counter.count;
		if (k == counter.count) {
			return curr;
		}
		return auxFindKthSmallestEntry(curr.right, k, counter);
	}

	static class Entry<K2 extends Comparable<K2>, V2> {
		K2 key;
		V2 value;
		Entry<K2, V2> left, right;
		int height;

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

	enum TypeOfImbalance {
		NONE, LL, RR, LR, RL
	}

	class Counter {
		public Counter(int count) {
			this.count = count;
		}

		int count;
	}
}
