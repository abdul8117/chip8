package chip8;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.lang.ArrayIndexOutOfBoundsException;

/**
 * This class represents the memory / RAM of a CHIP-8 virtual machine.
 * <br>
 * CHIP-8 does not support two's complement so using the byte data type for
 * these fields may cause issues because 8 bits in two's complement has a
 * maximum value of 127 whereas a CHIP-8 virtual machine expects to
 * represent numbers as big as 255.
 */

public class Memory {
    private int[] memory;
    private short[] registers;
    private short soundTimer, delayTimer;
    private int I, pc;
    private CallStack stack;
    private Screen screen;
    private Keyboard keyboard;

    /**
     * Initialises the registers and runtime stack, and loads the ROM into
     * memory.
     */
    public Memory(Screen screen, Keyboard keyboard) {
        memory = new int[4096];           // 4 KiB of memory
        registers = new short[16];        // sixteen 8-bit registers
        soundTimer = delayTimer = 0;      // special purpose registers
        I = 0;
        pc = 0x200;                       // program counter
        stack = new CallStack(16);   // call/runtime stack
        this.screen = screen;
        this.keyboard = keyboard;
    }

    /**
     * This method loads a ROM file onto memory starting at address 0x200.
     * <br>
     * A ROM is a set of instructions that the CHIP-8 virtual machine will
     * execute. It is essentially the program that will get executed.
     */
    public void loadROM(String romName) {
        int[] program = getAllInstructions(romName);

        for (int i = 0; i < program.length; i++) {
            try {
                memory[i + 0x200] = program[i];
//                System.out.println("Just stored " + Integer.toHexString(memory[i + 0x200]) + " into memory.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Program is too large.");
                System.exit(200); // input program is too large for it to be stored in the available amount of memory
            }
        }
    }

    /**
     * Loads the fontset into memory starting at memory address 0x50.
     *
     * @param fonts the fontset
     */
    public void loadFonts(int[][] fonts) {
        for (int i = 0; i < fonts.length; i++)
            for (int j = 0; j < fonts[0].length; j++)
                memory[0x50 + i + j] = fonts[i][j];
    }

    public void run() {
        int instruction = (memory[pc] << 8) | memory[pc + 1];
        pc += 2;

        // for debugging purposes:
        if (instruction == 0)
            throw new RuntimeException();

        System.out.println("Currently executing: " + Integer.toHexString(instruction));

        decodeAndExecuteInstruction(instruction);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finished executing: " + Integer.toHexString(instruction));

        if (soundTimer > 0) --soundTimer;
        if (delayTimer > 0) --delayTimer;
    }

    public void decodeAndExecuteInstruction(int instruction) {

        int nnn = instruction & 0xFFF;
        short n = (short) (instruction & 0xF);
        short x = (short) ((instruction & 0xF00) >> 8);
        short y = (short) ((instruction & 0xF0) >> 4);
        short kk = (short) (instruction & 0xFF);

        switch (instruction) {
            case 0x00E0:
                // Clear the display
                screen.clearScreen();
                break;

            case 0x00EE:
                // Return from a subroutine.
                pc = stack.peek();

                try {
                    stack.pop();
                } catch (Exception e) {
                    System.exit(101); // stack is already empty
                }

                break;
        }

        switch ((instruction & 0xF000) >> 12) {
            case 0x0001:
                // Jump to location nnn.
                pc = nnn;
                break;

            case 0x0002:
                // Call subroutine at nnn
                try {
                    stack.push(pc);
                } catch (Exception e) {
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
                // Skip next instruction if Vx != kk.
                if (registers[x] != kk)
                    pc += 2;
                break;

            case 0x0005:
                // Skip next instruction if Vx = Vy.
                if (registers[x] == registers[y])
                    pc += 2;
                break;

            case 0x0006:
                //Set Vx = kk.
                registers[x] = kk;
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
                // Set I = nnn.
                I = nnn;
                break;

            case 0x000B:
                // Jump to location nnn + V0.
                pc = nnn + registers[0];
                break;

            case 0x000C:
                // Set Vx = random byte AND kk.
                Random random = new Random();
                short randomNum = (short) random.nextInt(256);
                registers[x] = (short) (randomNum & kk);
                break;

            case 0x000D:
                int xCoord = registers[x] % 64;
                int yCoord = registers[y] % 32;
                registers[0xF] = 0;

                for (int i = 0; i < n; i++) {
                    if (yCoord == 32) break;

                    int mask = 0x80;
                    for (int j = 0; j < 8; j++) {
                        if (xCoord == 64) break;

                        int pixel = memory[I + i] & mask;
                        mask = mask >> 1;

                        if (pixel != 0) {
                            if (screen.getPixel(xCoord, yCoord))
                                registers[0xF] = 1;

                            screen.setPixel(xCoord, yCoord);
                        }

                        System.out.println("Set pixel at coordinates (" + xCoord + ", " + yCoord + ")");

                        xCoord += 1;
                    }

                    yCoord += 1;
                    xCoord = registers[x];
                }

                break;
        }

        switch (instruction & 0xF00F) {
            case 0x8000:
                // Set Vx = Vy.
                registers[x] = registers[y];
                break;

            case 0x8001:
                // Set Vx = Vx OR Vy.
                registers[x] = (short) (registers[x] | registers[y]);
                break;

            case 0x8002:
                // Set Vx = Vx AND Vy.
                registers[x] = (short) (registers[x] & registers[y]);
                break;

            case 0x8003:
                // Set Vx = Vx XOR Vy.
                registers[x] = (short) (registers[x] ^ registers[y]);
                break;

            case 0x8004:
                // Set Vx = Vx + Vy, set VF = carry.
                registers[0xF] = (short) ((registers[x] + registers[y]) > 255 ? 1 : 0);
                registers[x] = (short) ((registers[x] + registers[y]) & 0xFF);
                break;

            case 0x8005:
                // Set Vx = Vx - Vy, set VF = NOT borrow.
                registers[0xF] = (short) (registers[x] > registers[y] ? 1 : 0);
                registers[x] -= registers[y];
                break;

            case 0x8006:
                // Set Vx = Vx SHR 1.
                registers[0xF] = (short) ((registers[x] & 1) == 1 ? 1 : 0);
                registers[x] = (short) (registers[x] >> 1);
                break;

            case 0x8007:
                // Set Vx = Vy - Vx, set VF = NOT borrow.
                registers[0xF] = (short) ((registers[y] > registers[x] ? 1 : 0));
                registers[x] = (short) (registers[y] - registers[x]);
                break;

            case 0x800E:
                // Set Vx = Vx SHL 1.
                registers[0xF] = (short) (((registers[x] & 0x80) >> 7) == 1 ? 1 : 0);
                registers[x] <<= 1;
                break;

            case 0x9000:
                // Skip next instruction if Vx != Vy.
                if (registers[x] != registers[y])
                    pc += 2;
                break;
        }

        switch (instruction & 0xF0FF) {
            case 0xE09E:
                if (keyboard.isKeyDown(registers[x])) pc += 2;
                break;

            case 0xE0A1:
                if (!keyboard.isKeyDown(registers[x])) pc += 2;
                break;

            case 0xF007:
                delayTimer = x;
                break;

            case 0xF00A:
                if (keyboard.isKeyDown(0))
                    registers[x] = 0;
                else if (keyboard.isKeyDown(1))
                    registers[x] = 1;
                else if (keyboard.isKeyDown(2))
                    registers[x] = 2;
                else if (keyboard.isKeyDown(3))
                    registers[x] = 3;
                else if (keyboard.isKeyDown(4))
                    registers[x] = 4;
                else if (keyboard.isKeyDown(5))
                    registers[x] = 5;
                else if (keyboard.isKeyDown(6))
                    registers[x] = 6;
                else if (keyboard.isKeyDown(7))
                    registers[x] = 7;
                else if (keyboard.isKeyDown(8))
                    registers[x] = 8;
                else if (keyboard.isKeyDown(9))
                    registers[x] = 9;
                else if (keyboard.isKeyDown(10))
                    registers[x] = 10;
                else if (keyboard.isKeyDown(11))
                    registers[x] = 11;
                else if (keyboard.isKeyDown(12))
                    registers[x] = 12;
                else if (keyboard.isKeyDown(13))
                    registers[x] = 13;
                else if (keyboard.isKeyDown(14))
                    registers[x] = 14;
                else if (keyboard.isKeyDown(15))
                    registers[x] = 15;
                else
                    pc -= 2;

                break;

            case 0xF015:
                delayTimer = registers[x];
                break;

            case 0xF018:
                soundTimer = registers[x];
                break;

            case 0xF01E:
                I += registers[x];
                break;

            case 0xF029:
                I = 0x50 + (5 * registers[x]);
                break;

            case 0xF033:
                int hundreds = Math.floorDiv(registers[x], 100);
                int tens = Math.floorDiv(registers[x], 10) % 10;
                int ones = registers[x] % 10;

                memory[I] = hundreds;
                memory[I + 1] = tens;
                memory[I + 2] = ones;

                break;

            case 0xF055:
                // Store registers V0 through Vx in memory starting at location I.
                for (int i = I; i < I + 16; i++)
                    memory[i] = registers[x];
                break;

            case 0xF065:
                // Reads values from memory starting at location I into
                // registers V0 through Vx
                for (int i = 0; i < 16; i++)
                    registers[i] = (short) memory[I + i];
                break;
        }
    }

    /**
     * Fetches every instruction present in a provided ROM, storing them in
     * an array.
     *
     * @param fileName name of the ROM, without the .ch8 file extension
     * @return an array of type int[]
     */
    private static int[] getAllInstructions(String fileName) {
        int[] romContent = new int[4096 - 512];
        String path = "roms/" + fileName + ".ch8";

        try {
            DataInputStream input = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(path)
                    )
            );

            byte b;
            boolean eof = false;
            int i = 0;

            while (!eof) {
                try {
                    b = input.readByte();
                    System.out.println("Current byte is: " + Integer.toHexString(Byte.toUnsignedInt(b)));
                    romContent[i] = Byte.toUnsignedInt(b);
                    System.out.println("Loading byte " + Integer.toBinaryString(romContent[i]) + " into memory.");
                    i += 1;
                } catch (IOException e) {
                    eof = true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return romContent;
    }
}
