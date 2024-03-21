package chip8;

/*
 * This class will define the font which are all the hexadecimal digits - 0 to 9
 * and A to F. This will be stored in an array so that if you
 * wanted the representation for the digit B, you simply need to type font[0xB].
 *
 * In addition, this class will also keep track of what keys are pressed down.
 * This can just be an array of length 16 (the CHIP-8 keypad has sixteen keys).
 *
 * Original layout:
 * 1 2 3 C
 * 4 5 6 D
 * 7 8 9 E
 * A 0 B F
 *
 * Our layout:
 * 1 2 3 4
 * Q W E R
 * A S D F
 * Z X C V
 */

public class Keyboard {
    private static final int[][] FONT = {
            {0xF0, 0x90, 0x90, 0x90, 0xF0}, // 0
            {0x20, 0x60, 0x20, 0x20, 0x70}, // 1
            {0xF0, 0x10, 0xF0, 0x80, 0xF0}, // 2
            {0xF0, 0x10, 0xF0, 0x10, 0xF0}, // 3
            {0x90, 0x90, 0xF0, 0x10, 0x10}, // 4
            {0xF0, 0x80, 0xF0, 0x10, 0xF0}, // 5
            {0xF0, 0x80, 0xF0, 0x90, 0xF0}, // 6
            {0xF0, 0x10, 0x20, 0x40, 0x40}, // 7
            {0xF0, 0x90, 0xF0, 0x90, 0xF0}, // 8
            {0xF0, 0x90, 0xF0, 0x10, 0xF0}, // 9
            {0xF0, 0x90, 0xF0, 0x90, 0x90}, // A
            {0xE0, 0x90, 0xE0, 0x90, 0xE0}, // B
            {0xF0, 0x80, 0x80, 0x80, 0xF0}, // C
            {0xE0, 0x90, 0x90, 0x90, 0xE0}, // D
            {0xF0, 0x80, 0xF0, 0x80, 0xF0}, // E
            {0xF0, 0x80, 0xF0, 0x80, 0x80}  // F
    };

    private boolean[] isKeyDown = new boolean[16];

    public Keyboard() {
        // TODO Write constructor
    }

    public void keyDown(int key) {
        throw new UnsupportedOperationException();
    }

    public void keyUp(int key) {
        throw new UnsupportedOperationException();
    }

}
