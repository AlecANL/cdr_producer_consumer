package org.example.projectcdr;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;

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
            this.readFile(selectedFile);
        }
    }

    private void readFile(File file) {
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            String[] lines = content.split("\n");

            for (String line : lines) {
                System.out.println("LINEA >>>> " + line);
            }
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al leer archivo: " + file.getName());
        }
    }
}
