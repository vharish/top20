package com.algorithmica.ds;

/**
 * A Bit set implementation with index starting from 0 to n-1
 * **/
public class BitSet {

	private byte[] array;

	public BitSet(int nbits) {
		array = new byte[(int) Math.ceil((double) nbits / 8)];
	}

	public boolean get(int i) {
		int b = array[i / 8] & (1 << (i % 8));
		return b == 0 ? false : true;
	}

	public void set(int i) {
		array[i / 8] |= (1 << (i % 8));
	}

	public void clear(int i) {
		array[i / 8] &= ~(1 << (i % 8));
	}

}
