module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires com.jfoenix;
    requires opencv;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires jdk.jlink;

    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
    exports com.example.demo2.training;
    opens com.example.demo2.training to javafx.fxml;
}