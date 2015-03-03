package com.algorithmica.ds.list;

// An implementation of Circular Doubly Linked List
public class DoublyLinkedList<T> implements List<T> {

	private int size = 0;
	private final DoublyLinkedNode<T> header = new DoublyLinkedNode<T>();

	public DoublyLinkedList() {
		init();
	}

	private void init() {
		header.prev = header;
		header.next = header;
	}

	public int size() {
		return size;
	}

	public boolean add(T element) {
		DoublyLinkedNode<T> newNode = new DoublyLinkedNode<T>(element,
				header.prev, header);
		header.prev.next = newNode;
		header.prev = newNode;
		++size;
		return true;
	}

	public boolean add(int index, T element) {
		checkRange(index, 0, size);
		DoublyLinkedNode<T> tmpNode = getNode(index - 1);
		DoublyLinkedNode<T> newNode = new DoublyLinkedNode<T>(element, tmpNode,
				tmpNode.next);
		tmpNode.next.prev = newNode;
		tmpNode.next = newNode;
		++size;
		return true;
	}

	public boolean contains(T element) {
		for (DoublyLinkedNode<T> current = header.next; current != header; current = current.next) {
			if (current.data.equals(element)) {
				return true;
			}
		}
		return false;
	}

	public T remove(int index) {
		checkRange(index, 0, size - 1);
		DoublyLinkedNode<T> current = getNode(index);
		current.prev.next = current.next;
		current.next.prev = current.prev;
		current.prev = null;
		current.next = null;
		--size;
		return current.data;
	}

	public boolean remove(T element) {
		for (DoublyLinkedNode<T> current = header.next; current != header; current = current.next) {
			if (current.data.equals(element)) {
				// Element found. Do the removal
				current.prev.next = current.next;
				current.next.prev = current.prev;
				current.prev = null;
				current.next = null;
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
		for (DoublyLinkedNode<T> current = header.next; current != header; current = current.next) {
			sb.append(current.data.toString());
			if (current.next != header) {
				sb.append(", ");
			}
		}
		sb.append("]");
		System.out.println(sb.toString());
	}

	private DoublyLinkedNode<T> getNode(int index) {
		DoublyLinkedNode<T> current = header;
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

class DoublyLinkedNode<T> {
	DoublyLinkedNode<T> prev, next;
	T data;

	public DoublyLinkedNode() {
	}

	public DoublyLinkedNode(T data) {
		this.data = data;
	}

	public DoublyLinkedNode(T data, DoublyLinkedNode<T> prev,
			DoublyLinkedNode<T> next) {
		this.data = data;
		this.prev = prev;
		this.next = next;
	}

}