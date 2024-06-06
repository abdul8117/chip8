package chip8;

import javafx.application.Application;
import javafx.scene.Group;
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
        Group group = new Group();
        group.getChildren().add(screen.c);

        Keyboard keyboard = new Keyboard();

        Memory memory = new Memory(screen, keyboard);

        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();

        memory.loadROM("test_opcode");
        memory.loadFonts(Keyboard.getFonts());

        while (true) {
            memory.run();
        }




    }
}
