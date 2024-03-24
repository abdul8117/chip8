package chip8;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.*;

public class Screen {
    private static final int WIDTH = 64;
    private static final int HEIGHT = 32;
    private static final int SCALE = 10;

    // true = white, false = black
    private boolean[][] pixelArray = new boolean[WIDTH * SCALE][HEIGHT * SCALE];

    private GraphicsContext gc;

    public Screen() {
        Canvas c = new Canvas(WIDTH, HEIGHT);
        gc = c.getGraphicsContext2D();
        gc.fillRect(0, 0, c.getWidth(), c.getHeight()); // black screen
        clearScreen();
    }

    /*
     * This method simply sets every value in pixelArray to false/0/off.
     */
    public void clearScreen() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                pixelArray[i][j] = false;
            }
        }
    }

    public boolean getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /*
     * This method is called when a pixel in the sprite (which is being
     * iterated through) is 1/true/on. If the screen pixel in x and y are
     *  also turned on, then they should be turned off, and vice versa. This
     *  represents an XOR operation.
     */
    public void setPixel(int x, int y) {
        pixelArray[x][y] ^= true;
    }
}
