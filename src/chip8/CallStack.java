package chip8;

import java.util.NoSuchElementException;

public class CallStack {
	private int[] stack; // the call stack
	private short pointer; // points to the topmost value on the stack
	
	/*
	 * Constructor initialising a call stack of size 16
	 */
	public CallStack(int size) {
		stack = new int[size];
		pointer = -1; // will not point to an address if stack is empty
	}
	
	public boolean isEmpty() {
		return pointer == -1;
	}
	
	public int peek() {
		if (isEmpty())
			throw new NoSuchElementException("Stack is empty");
		
		return stack[pointer];
	}
	
	public boolean push(short address) {
		if (pointer >= 16) return false; // stack is full
		
		stack[++pointer] = address;
		
		return true;
	}
	
	public void pop() {
		if (isEmpty()) return;
		
		stack[pointer] = 0;
		pointer -= 1;
	}
}
