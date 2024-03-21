package chip8;

public class Screen {
    private static final int WIDTH = 64;
    private static final int HEIGHT = 32;

    // true = white, false = black
    private boolean[][] pixelArray = new boolean[WIDTH][HEIGHT];

    public Screen() {
        // TODO Write constructor
    }

    public boolean getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    public void setPixel(int x, int y) {
        /*
        This method is called when a pixel in the sprite (which is being
        iterated through) is 1/true/on. If the screen pixel in x and y are
        also turned on, then they should be turned off, and vice versa. This
        represents an XOR operation.
         */

        pixelArray[x][y] ^= true;
    }


}
