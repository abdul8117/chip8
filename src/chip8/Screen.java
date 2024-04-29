package chip8;

import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

public class Screen {
    private static final int WIDTH = 64;
    private static final int HEIGHT = 32;
    private static final int SCALE = 10;

    // true = white, false = black
    private boolean[][] pixelArray = new boolean[WIDTH * SCALE][HEIGHT * SCALE];

    private GraphicsContext gc;

    public Screen() {
        Canvas c = new Canvas(WIDTH * SCALE, HEIGHT * SCALE);
        gc = c.getGraphicsContext2D();
        gc.fillRect(0, 0, c.getWidth(), c.getHeight()); // black screen
        clearScreen();
    }

    /*
     * This method sets every value in pixelArray to false/0/off.
     */
    public void clearScreen() {
        for (int i = 0; i < HEIGHT * SCALE; i++) {
            for (int j = 0; j < WIDTH * SCALE; j++) {
                pixelArray[i][j] = false;
            }
        }
    }

    public void renderScreen() {
        for (int i = 0; i < HEIGHT * SCALE; i++) {
            for (int j = 0; j < WIDTH * SCALE; j++) {
                if (pixelArray[i][j])
                    gc.setFill(Color.WHITE);
                else
                    gc.setFill(Color.BLACK);

                gc.fillRect(i, j, SCALE, SCALE);
            }
        }
    }

    public boolean getPixel(int x, int y) {
        return pixelArray[x * SCALE][y * SCALE];
    }

    public void setPixel(int x, int y) {
        pixelArray[x * SCALE][y * SCALE] ^= true;
    }
}
