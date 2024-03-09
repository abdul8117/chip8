package chip8;

/*
 * This class represents the memory / RAM of a CHIP-8 virtual machine.
 */

public class Memory {
	
	byte[] memory = new byte[4096]; // 4 KiB of memory
	byte[] registers = new byte[16]; // sixteen 8-bit registers
	byte soundTimer, delayTimer; // two, special-purpose 8-bit registers
	
	short pc; // program counter
	CallStack stack; // call stack containing up to sixteen 16-bit values
	
	/*
	 * Constructor
	 */
	public Memory() {
		// ...
	}
	
}
