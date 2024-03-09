package chip8;

public abstract class AbstractStack {
	abstract boolean isEmpty();
	
	abstract short peek();
	
	abstract boolean add(short address);
	
	abstract void pop();
}
