module org.example.actividad1_ut5_davidcarreno {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.actividad1_ut5_davidcarreno to javafx.fxml;
    exports org.example.actividad1_ut5_davidcarreno;
}