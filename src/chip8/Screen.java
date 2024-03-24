package chip8;

import javafx.scene.canvas.*;

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
     * This method simply sets every value in pixelArray to false/0/off.
     */
    public void clearScreen() {
        for (int i = 0; i < HEIGHT * SCALE; i++) {
            for (int j = 0; j < WIDTH * SCALE; j++) {
                pixelArray[i][j] = false;
            }
        }
    }

    public boolean getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    public void setPixel(int x, int y) {
        pixelArray[x][y] ^= true;
    }
}
