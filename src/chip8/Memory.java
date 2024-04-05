package chip8;

import java.util.Set;
import java.util.Random;

/**
 * This class represents the memory / RAM of a CHIP-8 virtual machine.
*/

/**
 * <p>This class represents the memory / RAM of a CHIP-8 virtual machine.</p>
 * <br>
 * <p>CHIP-8 does not support two's complement so using the byte data type for
 * these fields may cause issues because 8 bits in two's complement has a
 * maximum value of 127 whereas a CHIP-8 virtual machine expects to
 * represent numbers as big as 255.</p>
 * <br>
 * <p>We are not using ints instead because that would use a lot (a lot) more
 * memory.</p>
*/

public class Memory {

    short[] memory = new short[4096]; // 4 KiB of memory
    short[] registers = new short[16]; // sixteen 8-bit registers
    short soundTimer, delayTimer; // two, special-purpose 8-bit registers
    int I; //16 bit registers 

    int pc = 0x200; // program counter
    CallStack stack; // call stack containing up to sixteen 16-bit values

    /**
     * Constructor
     */
    public Memory() {
        // ...
    }

     * <p>This method loads a ROM file onto memory starting at address 0x200
     * .</p>
     * <br>
     * <p>A ROM is a set of instructions that the CHIP-8 virtual machine will
     * execute. It is essentially the program that will get executed.</p>
     */
    public void loadROM() {

    }

    public void decodeAndExecuteInstruction(int instruction) {

        int nnn = (short) (instruction & 0xFFF);
        short n = (byte) (instruction & 0x00F);
        short x = (byte) ((instruction & 0xF00) >> 8);
        short y = (byte) ((instruction & 0x0F0) >> 4);
        short kk = (byte) (instruction & 0x0FF); 

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

                try {
                    stack.pop();
                }
                catch (Exception e) {
                    System.exit(101); // stack is already empty
                }
                break;
        }

        switch ((instruction & 0xF000) << 12) {
            case 0x0001:
                // Kathy
                //Jump to location nnn.
                pc = nnn;
                break;

            case 0x0002:
                // Call subroutine at nnn
                try {
                    stack.push(pc);
                }
                catch (Exception e) {
                    System.exit(100); // stack is already full
                }

                pc = nnn;
                break;

            case 0x0003:
                // Skip next instruction if Vx = kk
                if (registers[x] == kk)
                    pc += 2;
                break;

            case 0x0004:
                // Kathy
                // Skip next instruction if Vx != kk.
                if(registers[x]!=kk)
                    pc += 2;
                break;

            case 0x0005:
                //Skip next instruction if Vx = Vy.
                if (registers[x] == registers[y])
                    pc += 2;
                break;

            case 0x0006:
                // Kathy
                //Set Vx = kk.
                registers[x]=kk;
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
                //Set I = nnn.
                I = nnn;
                break;

            case 0x000B:
                //Jump to location nnn + V0.
                pc = (short) (nnn + registers[0]);

                pc = nnn + registers[0];
                break;

            case 0x000C:
                // Kathy
                //Set Vx = random byte AND kk.
                Random random = new Random();
                byte randomNum = (byte) random.nextInt(256);

                registers[x] = (byte) (randomNum & kk);
                break;

            case 0x000D:
                // TODO for when we have implemented a screen
                break;
        }

        switch (instruction & 0xF00F) {
            case 0x8000:
            //Set Vx = Vy.
                registers[x] = registers[y];
                break;

            case 0x8001:
                // Kathy
                // Set Vx = Vx OR Vy.
                registers[x] = (byte) (registers[x] | registers[y]);
                break;

            case 0x8002:
                //Set Vx = Vx AND Vy.
                registers[x] = (byte) (registers[x] & registers[y]);

                registers[x] = (short) (registers[x] & registers[y]);
                break;

            case 0x8003:
                // Kathy
                //Set Vx = Vx XOR Vy.
                registers[x] = (byte) (registers[x] ^ registers[y]);
                break;

            case 0x8004:
                // Kathy -> fixed
                //Set Vx = Vx + Vy, set VF = carry.
                registers[0xF] = (short) ((registers[x] + registers[y])> 255 ? 1: 0);
                registers[x]= (short) ((registers[x] + registers[y]) & 0x0FF);

                break;

            case 0x8005:
                //Set Vx = Vx - Vy, set VF = NOT borrow.
                registers[0xF] = (byte) (registers[x] > registers[y] ? 1 : 0);
                registers[0xF] = (short) (registers[x] > registers[y] ? 1 : 0);
                registers[x] -= registers[y];
                break;

            case 0x8006:
                //Set Vx = Vx SHR 1.
                registers[0xF] = (byte) ((registers[x] & 0x0F) == 0x01 ? 1 : 0);
                registers[x] = (byte) (registers[x] >> 1);
                registers[0xF] = (short) ((registers[x] & 1) == 1 ? 1 : 0);
                registers[x] = (short) (registers[x] >> 1);
                break;

            case 0x8007:
                // Kathy
                //Set Vx = Vy - Vx, set VF = NOT borrow.
                registers[0xF] = (byte) ((registers[y]>registers[x]? 1: 0));
                registers[x]= (byte) (registers[y] - registers[x]);
                break;

            case 0x800E:
                // Kathy
                //Set Vx = Vx SHL 1.
                registers[0xF] = (short)(((registers[x] & 0x80)>>7)== 1? 1:0);
                registers[x] <<= 1;
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
