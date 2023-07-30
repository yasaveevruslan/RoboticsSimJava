package com.example.demo2;

import com.example.demo2.training.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StartApplication extends Application {
    private URL PATH_AREA = getClass().getResource("/com/example/demo2/paint.png");


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        MainController controller = fxmlLoader.getController();

        controller.setImageUrl(PATH_AREA);
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Demo");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
