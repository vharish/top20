package com.algorithmica.ds.list;

public class SinglyLinkedList<T> implements List<T> {

	private int size = 0;

	private final SinglyLinkedNode<T> header = new SinglyLinkedNode<T>();

	public int size() {
		return size;
	}

	public boolean add(T element) {
		SinglyLinkedNode<T> newNode = new SinglyLinkedNode<T>(element);
		SinglyLinkedNode<T> last = getLastNode();
		last.next = newNode;
		++size;
		return true;
	}

	public boolean add(int index, T element) {
		checkRange(index, 0, size);
		SinglyLinkedNode<T> tmp = getNode(index - 1);
		SinglyLinkedNode<T> newNode = new SinglyLinkedNode<T>(element, tmp.next);
		tmp.next = newNode;
		++size;
		return true;
	}

	public boolean contains(T element) {
		for (SinglyLinkedNode<T> current = header.next; current != null; current = current.next) {
			if (current.data.equals(element)) {
				return true;
			}
		}
		return false;
	}

	public T remove(int index) {
		checkRange(index, 0, size - 1);
		SinglyLinkedNode<T> previous = getNode(index - 1);
		SinglyLinkedNode<T> current = previous.next;

		// Do the removal
		previous.next = current.next;
		current.next = null;

		--size;
		return current.data;
	}

	public boolean remove(T element) {
		for (SinglyLinkedNode<T> current = header; current.next != null; current = current.next) {
			if (current.next.data.equals(element)) {
				// Element found. Do the removal
				SinglyLinkedNode<T> tmp = current.next;
				current.next = tmp.next;
				tmp.next = null;
				--size;
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public T get(int index) {
		checkRange(index, 0, size - 1);
		return getNode(index).data;
	}

	public void display() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (SinglyLinkedNode<T> current = header.next; current != null; current = current.next) {
			sb.append(current.data.toString());
			if (current.next != null) {
				sb.append(", ");
			}
		}
		sb.append("]");
		System.out.println(sb.toString());
	}

	private SinglyLinkedNode<T> getLastNode() {
		SinglyLinkedNode<T> current;
		for (current = header; current.next != null; current = current.next)
			;
		return current;
	}

	private SinglyLinkedNode<T> getNode(int index) {
		SinglyLinkedNode<T> current = header;
		while (index-- != -1) {
			current = current.next;
		}
		return current;
	}

	private void checkRange(int index, int min, int max) {
		if (index < min || index > size) {
			throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
		}
	}
}

class SinglyLinkedNode<T> {
	SinglyLinkedNode<T> next;
	T data;

	public SinglyLinkedNode() {
	}

	public SinglyLinkedNode(T data) {
		this.data = data;
	}

	public SinglyLinkedNode(T data, SinglyLinkedNode<T> next) {
		this.data = data;
		this.next = next;
	}

}