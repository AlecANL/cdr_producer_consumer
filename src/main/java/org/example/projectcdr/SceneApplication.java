package org.example.projectcdr;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneApplication extends Application {

    @Override
    public void start(Stage state) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        state.setTitle("Hello!");
        state.setScene(scene);
        state.show();
    }
}
