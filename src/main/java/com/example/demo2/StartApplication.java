package com.example.demo2;

import com.example.demo2.training.Elements;
import com.example.demo2.training.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.awt.image.BufferedImage;


public class StartApplication extends Application {
    private final URL PATH_AREA = getClass().getResource("/com/example/demo2/paint.png");
//    private final URL PATH_ROBOT = getClass().getResource("/com/example/demo2/none.png");
    private final URL PATH_ROBOT = getClass().getResource("/com/example/demo2/robot.png");

    public static Mat sourceImage;

    public static final URL url = StartApplication.class.getResource("/com/example/demo2/paint.png");
    public static BufferedImage imgCort = null;
    public static BufferedImage imgRobot = null;


    public static List<MatOfPoint> lastContours = new ArrayList<>();


    @Override
    public void start(Stage stage) throws IOException {
        initMatImage();
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        MainController controller = fxmlLoader.getController();

        controller.setImageUrl(PATH_AREA);
        controller.setImageRobot(PATH_ROBOT);

        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Demo");
        stage.setScene(scene);
        stage.show();
    }

    private void initMatImage(){
        String fileName = "paint.png";

        Path path = Paths.get("src", "main", "resources", "com", "example", "demo2", fileName);
        String area = path.toString();
        sourceImage = Imgcodecs.imread(area);

    }

    public static String compMatImage(){
        String fileName = "im.png";
        Path path = Paths.get("src", "main", "resources", "com", "example", "demo2", fileName);
        return path.toString();
    }



    public static void main(String[] args)
    {

//        System.load("C:\\Users\\Monbe\\IdeaProjects\\Sim\\RoboticsSimJava\\src\\main\\java\\com\\example\\demo2\\opencv_java440.dll");
        System.load("D:\\SimJava\\newVersion\\RoboticsSimJava\\src\\main\\java\\com\\example\\demo2\\opencv_java440.dll");
        launch();

    }

}
