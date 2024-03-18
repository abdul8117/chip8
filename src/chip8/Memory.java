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

	public decodeAndExecuteInstruction(short instruction) {

		short nnn = instruction & 0xFFF;
		byte n;
		byte x = (instruction & 0xF00) >> 8;
		byte y;
		byte kk;

		switch ((instruction & 0xF000) >> 12):
			case 0x001:
				// ...
				break;
			
			case 0x002:
				// Call subroutine at nnn
				stack.push(pc);
				pc = nnn;
				break;
			
			case 0x003:
				// Skip next instruction if Vx = kk
				if (registers[x] == kk) 
					pc += 2;
				break;
			
			// TODO: 0x004, 0x005, 0x006

			case 0x007:
				// Sets Vx to Vx + kk
				registers[x] += kk;
				break;
			
			case 0x009:
				// Skip next instruction if Vx != Vy
				if (registers[x] != registers[y])
					pc += 2;
				break;
			
			case 0x000A:

			case 0x000B:

			case 0x000C:

			case 0x000D:


		
		switch (instruction & 0xF00F):
			case 0x8000:
				// TODO
				break;
			
			case 0x8001:
				// TODO
				break;

			case 0x8002:
				// TODO
				break;

			case 0x8003:
				// TODO
				break;
			
			case 0x8004:
				// TODO
				break;

			case 0x8005:
				// TODO
				break;
			
			case 0x8006:
				// TODO
				break;

			case 0x8007:
				// TODO
				break;

			case 0x800E:
				// TODO
				break;
		
			}
	



		}
