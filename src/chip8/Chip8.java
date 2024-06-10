package chip8;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Chip8 extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // create display, initialise keyboard and memory, load ROM

        Screen screen = new Screen();
        StackPane root = new StackPane();
        root.getChildren().add(screen.c);

        Keyboard keyboard = new Keyboard();
        Memory memory = new Memory(screen, keyboard);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        String romName = "test_opcode";
        memory.loadROM(romName);
        memory.loadFonts(Keyboard.getFonts());

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                memory.runNextInstruction();
                screen.renderScreen();
                scene.setOnKeyPressed(event -> keyboard.keyDown(event.getCode()));
                scene.setOnKeyReleased(event -> keyboard.keyUp(event.getCode()));
            }
        }.start();
    }
}
