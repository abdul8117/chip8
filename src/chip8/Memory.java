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
        byte n; // Kathy
        byte x = (instruction & 0xF00) >> 8;
        byte y; // Kathy
        byte kk; // Kathy

        switch (instruction) {
            case 0x00E0:
                // Clear the display
                // TODO when the Screen has been implemented
                break;

            case 0x00EE:
                //  Return from a subroutine.
                // The interpreter sets the program counter to the address
                // at the top of the stack, then subtracts 1 from the stack
                // pointer.

                pc = stack.peek();
                stack.push();
                break;
        }

        switch ((instruction & 0xF000) >> 12) {
            case 0x0001:
                // Kathy
                break;

            case 0x0002:
                // Call subroutine at nnn
                stack.push(pc);
                pc = nnn;
                break;

            case 0x0003:
                // Skip next instruction if Vx = kk
                if (registers[x] == kk)
                    pc += 2;
                break;

            case 0x0004:
                // Kathy
                break;

            case 0x0005:
                if (registers[x] == registers[y])
                    pc += 2;
                break;

            case 0x0006:
                // Kathy
                break;

            case 0x0007:
                // Sets Vx to Vx + kk
                registers[x] += kk;
                break;

            case 0x0009:
                // Skip next instruction if Vx != Vy
                if (registers[x] != registers[y])
                    pc += 2;
                break;

            case 0x000A:
                // Kathy
                break;

            case 0x000B:
                pc = nnn + registers[0];
                break;

            case 0x000C:
                // Kathy
                break;

            case 0x000D:
                // TODO for when we have implemented a screen
                break;
        }


        switch (instruction & 0xF00F) {
            case 0x8000:
                registers[x] = registers[y];
                break;

            case 0x8001:
                // Kathy
                break;

            case 0x8002:
                registers[x] = registers[x] & registers[y];
                break;

            case 0x8003:
                // Kathy
                break;

            case 0x8004:
                // Kathy
                break;

            case 0x8005:
                registers[0xF] = registers[x] > registers[y] ? 1 : 0;
                registers[x] -= registers[y];
                break;

            case 0x8006:
                registers[0xF] = (registers[x] & 0x0F) == 0x01 ? 1 : 0;
                registers[x] = registers[x] >> 1;
                break;

            case 0x8007:
                // Kathy
                break;

            case 0x800E:
                // Kathy
                break;

            case 0x9000:
                // Abdul
                if (registers[x] != registers[y])
                    pc += 2;
                break;
        }

        // Some of the following instructions won't be written until we
        // have implemented the Keyboard class
        switch (instruction & 0xF0FF) {
            case 0xE09E:
                break;

            case 0xE0A1:
                break;

            case 0xF007:
                // Kathy
                break;

            case 0xF00A:
                break;

            case 0xF015:
                delayTimer = registers[x];
                break;

            case 0xF018:
                soundTimer = registers[x];
                break;

            case 0xF01E:
                // Kathy
                break;

            case 0xF029:
                break;

            case 0xF033:
                break;

            case 0xF055:
                // Together?
                break;

            case 0xF065:
                // TODO: figure out what this instruction should be doing
                break;
        }
    }
}
