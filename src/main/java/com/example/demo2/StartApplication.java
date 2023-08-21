package com.example.demo2;

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
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.awt.image.BufferedImage;

import static jdk.tools.jlink.internal.JlinkTask.createImage;


public class StartApplication extends Application {
    private final URL PATH_AREA = getClass().getResource("/com/example/demo2/paint.png");
    private final URL PATH_ROBOT = getClass().getResource("/com/example/demo2/none.png");

    public static Mat sourceImage;

    public static final URL url = StartApplication.class.getResource("/com/example/demo2/paint.png");
    public static BufferedImage img = null;


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

    public static int[][] mas = new int[998][500];

    public static void main(String[] args) throws IOException {

        generation2DArrayForCort();
        get2DPixelArraySlow(img);
        addPixelInTXTDoc(mas);
        change2DPixelArrayInImage(mas);
//        System.load("C:\\Users\\Monbe\\IdeaProjects\\Sim\\RoboticsSimJava\\src\\main\\java\\com\\example\\demo2\\opencv_java440.dll");
        System.load("D:\\SimJava\\newVersion\\RoboticsSimJava\\src\\main\\java\\com\\example\\demo2\\opencv_java440.dll");
        launch();

    }


    public static void generation2DArrayForCort(){
        try {
            img = ImageIO.read(new File("src/main/resources/com/example/demo2/paint.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        assert img != null;
        mas = get2DPixelArraySlow(img);
    }


    public static int[][] get2DPixelArraySlow(BufferedImage sampleImage) {
        int width = sampleImage.getWidth();
        int height = sampleImage.getHeight();
        int[][] result = new int[height][width];

        int elements = 0;
        System.out.println(width + " " + height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int color = 0;
                if (sampleImage.getRGB(col, row) == -1)
                {
                    color = 1;
                }
                else
                {
                    color = 2;
                }
                result[row][col] = color;
                elements ++;
            }
        }
        System.out.println(elements);

        return result;
    }

    public static void change2DPixelArrayInImage(int[][] mas) throws IOException {
        int h = mas.length;
        int w = mas[0].length;


        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();

        Color color = Color.BLUE;
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (mas[row][col] == 1){
                    color = Color.WHITE;
                }else{
                    color = Color.BLACK;


                }
                image.setRGB(col, row, color.getRGB());

            }
        }

        File outputfile = new File("image.png");
        ImageIO.write(image, "png", outputfile);
    }

    public static void addPixelInTXTDoc(int[][] mas) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("note.txt")));
        for (int[] i : mas){
            String stuffToWrite = Arrays.toString(i);
            writer.write(stuffToWrite + '\n');
        }
        writer.close();
    }


}
