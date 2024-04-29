package chip8;

import java.util.NoSuchElementException;
import java.util.EmptyStackException;
import exceptions.StackOverflowException;

public class CallStack {
    private int[] stack; // the call stack
    private short pointer; // points to the topmost value on the stack
    private int size;

    /**
     * Constructor initialising a call stack and stack pointer.
     */
    public CallStack(int size) {
        this.size = size;
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

    public void push(int address) throws StackOverflowException {
        if (pointer >= size) throw new StackOverflowException();

        stack[++pointer] = address;
    }

    public void pop() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();

        stack[pointer] = 0;
        pointer -= 1;
    }
}
