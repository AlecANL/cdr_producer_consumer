module org.example.projectcdr {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens org.example.projectcdr to javafx.fxml;
    exports org.example.projectcdr;
}