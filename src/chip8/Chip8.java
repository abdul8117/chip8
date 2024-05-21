package chip8;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Chip8 extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // create display, initialise keyboard and memory, load ROM

        Screen screen = new Screen();
        Keyboard keyboard = new Keyboard();

        Memory memory = new Memory(screen, keyboard);
        memory.loadROM("test_opcode");
        memory.loadFonts(Keyboard.getFonts());

    }
}
