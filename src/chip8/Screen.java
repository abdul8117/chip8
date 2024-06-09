package chip8;

import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

public class Screen {
    private static final int LENGTH = 64;
    private static final int HEIGHT = 32;
    private static final int SCALE = 1;

    // true = white, false = black
    private boolean[][] pixelArray =
            new boolean[LENGTH * SCALE][HEIGHT * SCALE];

    private GraphicsContext gc;
    public Canvas c;

    public Screen() {
        c = new Canvas(LENGTH * SCALE, HEIGHT * SCALE);
        gc = c.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, c.getWidth(), c.getHeight());
    }

    /**
     * Sets every value in pixelArray to false/0/off.
     */
    public void clearScreen() {
        for (int i = 0; i < HEIGHT * SCALE; i++)
            for (int j = 0; j < LENGTH * SCALE; j++)
                pixelArray[i][j] = false;
    }

    public void renderScreen() {
        for (int i = 0; i < HEIGHT * SCALE; i++) {
            for (int j = 0; j < LENGTH * SCALE; j++) {
                if (pixelArray[i][j])
                    gc.setFill(Color.WHITE);
                else
                    gc.setFill(Color.BLACK);

                gc.fillRect(i * SCALE, j * SCALE, SCALE, SCALE);
            }
        }
    }

    public boolean getPixel(int x, int y) {
        return pixelArray[y * SCALE][x * SCALE];
    }

    public void setPixel(int x, int y) {
        pixelArray[y * SCALE][x * SCALE] ^= true;
    }
}
