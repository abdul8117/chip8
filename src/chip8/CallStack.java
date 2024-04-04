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
	
	public void push(int address) throws Exception {
		if (pointer >= 16) throw new Exception("Stack is full");
		stack[++pointer] = address;
	}
	
	public void pop() throws Exception {
		if (isEmpty()) throw new Exception("Stack is already empty");
		
		stack[pointer] = 0;
		pointer -= 1;
	}
}
