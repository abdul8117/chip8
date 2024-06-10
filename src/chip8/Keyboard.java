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

import javafx.scene.input.KeyCode;

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

    private boolean[] keyStates = new boolean[16];

    public void keyDown(KeyCode key) {
        switch (key) {
            case DIGIT1:
                keyStates[0] = true;
                break;
            case DIGIT2:
                keyStates[1] = true;
                break;
            case DIGIT3:
                keyStates[2] = true;
                break;
            case DIGIT4:
                keyStates[3] = true;
                break;
            case Q:
                keyStates[4] = true;
                break;
            case W:
                keyStates[5] = true;
                break;
            case E:
                keyStates[6] = true;
                break;
            case R:
                keyStates[7] = true;
                break;
            case A:
                keyStates[8] = true;
                break;
            case S:
                keyStates[9] = true;
                break;
            case D:
                keyStates[10] = true;
                break;
            case F:
                keyStates[11] = true;
                break;
            case Z:
                keyStates[12] = true;
                break;
            case X:
                keyStates[13] = true;
                break;
            case C:
                keyStates[14] = true;
                break;
            case V:
                keyStates[15] = true;
                break;
            default:
                break;
        }
    }

    public void keyUp(KeyCode key) {
        switch (key) {
            case DIGIT1:
                keyStates[0] = false;
                break;
            case DIGIT2:
                keyStates[1] = false;
                break;
            case DIGIT3:
                keyStates[2] = false;
                break;
            case DIGIT4:
                keyStates[3] = false;
                break;
            case Q:
                keyStates[4] = false;
                break;
            case W:
                keyStates[5] = false;
                break;
            case E:
                keyStates[6] = false;
                break;
            case R:
                keyStates[7] = false;
                break;
            case A:
                keyStates[8] = false;
                break;
            case S:
                keyStates[9] = false;
                break;
            case D:
                keyStates[10] = false;
                break;
            case F:
                keyStates[11] = false;
                break;
            case Z:
                keyStates[12] = false;
                break;
            case X:
                keyStates[13] = false;
                break;
            case C:
                keyStates[14] = false;
                break;
            case V:
                keyStates[15] = false;
                break;
            default:
                break;
        }
    }

    public boolean isKeyDown(int key) {
        return keyStates[key];
    }

    public static int[][] getFonts() {
        return FONT;
    }
}
