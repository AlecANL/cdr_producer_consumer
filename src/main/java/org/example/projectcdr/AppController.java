package org.example.projectcdr;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class AppController {

    @FXML
    protected void handleOpenFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.csv")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println("Archivo cargado: " + selectedFile.getAbsolutePath());
        }
    }
}
