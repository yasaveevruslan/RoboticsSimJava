package com.example.demo2.training;

import com.example.demo2.StartApplication;
import javafx.animation.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import de.jensd.fx.glyphs.fontawesome.*;

import javax.imageio.ImageIO;

public class MainController {
    @FXML
    public AnchorPane Menu, ActivityPanel;
    @FXML
    public ImageView imageArea;
    @FXML
    public JFXButton buttonONandOFF, buttonImageContours, buttonSmartBoard;
    @FXML
    public JFXButton buttonONandOFFSmall, buttonImageContoursSmall, buttonSmartBoardSmall;
    @FXML
    public Button buttonReset, buttonStart, buttonStop;
    public ScrollPane Scroll;
    public StackPane StackActivityPanel;
    public JFXButton buttonChangeMode;
    public AnchorPane parent;
    public ImageView imageRobot;

    public static boolean needDrawContour = true;

    private boolean buttonSmartClicked = false;
    private boolean buttonImageClicked = false;
    public static boolean stopClicked = false;
    public static boolean resetClicked = false;
    public static boolean startClicked = false;

    private boolean isLightMode = true;

    private AnchorPane originalPanel;

    private int initY = 0;
    private int numGroup = 0;
    private final Font font = new Font("Arial", 16);
    private final String darkModeCss = Objects.requireNonNull(getClass().getResource("/com/example/demo2/darkMode.css")).toExternalForm();
    private final String ligModeCss = Objects.requireNonNull(getClass().getResource("/com/example/demo2/lightMode.css")).toExternalForm();
    private List<Group> views = new ArrayList<>();
    private List<String> variablesList = new ArrayList<>();
    private List<Boolean> booleanList = new ArrayList<>();

    private float posX = 290, posY = 290, posZ = 0;

    public static int[][] Array2DCort, Array2DRobot;

    public StateMachine sm = new StateMachine();


    private void initTimelineForSmartBoard(){
        Timeline updateTimeValue = new Timeline(new KeyFrame(Duration.millis(50), e -> updateValue()));
        updateTimeValue.setCycleCount(Animation.INDEFINITE);
        updateTimeValue.play();

        Timeline updateTimeBoolean = new Timeline(new KeyFrame(Duration.millis(50), e -> updateBoolean()));
        updateTimeBoolean.setCycleCount(Animation.INDEFINITE);
        updateTimeBoolean.play();

        Timeline updateTimeRobot = new Timeline(new KeyFrame(Duration.millis(50), e -> updateRobot()));
        updateTimeRobot.setCycleCount(Animation.INDEFINITE);
        updateTimeRobot.play();

        Timeline updateCort = new Timeline(new KeyFrame(Duration.millis(200), e -> initCort()));
        updateCort.setCycleCount(Animation.INDEFINITE);
        updateCort.play();
    }
    public MainController()
    {

        initTimelineForSmartBoard();
    }

    @FXML
    private void initialize()
    {


        if(!(buttonImageClicked && buttonSmartClicked)){
            Scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            Scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }

        initTimelineForSmartBoard();

        buttonONandOFF.setOnAction(actionEvent -> hidePanel(true));
        buttonONandOFFSmall.setOnAction(actionEvent -> hidePanel(false));

        buttonSmartBoard.setOnAction(actionEvent -> actionWithSmartBoard());
        buttonSmartBoardSmall.setOnAction(actionEvent -> actionWithSmartBoard());

        buttonImageContours.setOnAction(actionEvent -> actionWithImageContours());
        buttonImageContoursSmall.setOnAction(actionEvent -> actionWithImageContours());

        buttonChangeMode.setOnAction(actionEvent -> changeMode());

        buttonStart.setOnAction(actionEvent -> buttonStartController());
        buttonReset.setOnAction(actionEvent -> buttonResetController());
        buttonStop.setOnAction(actionEvent -> buttonStopController());

    }
    @FXML
    private void buttonStartController(){
        startClicked = !startClicked;
        repaintRobot();
        resetClicked = false;
        stopClicked = false;
    }
    @FXML
    private void buttonStopController(){
        stopClicked = !stopClicked;
        startClicked = false;
        resetClicked = false;
    }
    @FXML
    private void buttonResetController(){
        resetClicked = !resetClicked;
        startClicked = false;
        stopClicked = false;

    }

    @FXML
    public void setImageUrl(URL imageUrl) {
        Image image = new Image(imageUrl.toExternalForm());
        imageArea.setImage(image);
    }

    @FXML
    public void setImageRobot(URL imageUrl) {
        Image image = new Image(imageUrl.toExternalForm());
        imageRobot.setImage(image);
    }

    @FXML
    private void hidePanel(Boolean notSmallButton) {
        if (Scroll.getOpacity() == 0.88) {
            Scroll.setOpacity(0.0);
        } else if (Boolean.FALSE.equals(notSmallButton)) {
            Scroll.setOpacity(0.88);
        }
    }

    @FXML
    private void actionWithSmartBoard(){
        if(buttonImageClicked){
            buttonImageClicked = false;

            StackActivityPanel.setPrefSize(407, 736);

        }

        if (!buttonSmartClicked) {
            AnchorPane newPanel = new AnchorPane();

            newPanel.setPrefSize(ActivityPanel.getWidth(), ActivityPanel.getHeight());
            AnchorPane.setBottomAnchor(newPanel, 0.0);
            AnchorPane.setTopAnchor(newPanel, 0.0);
            AnchorPane.setRightAnchor(newPanel, 0.0);


            createTextView("posX", Elements.coordinatesX);
            createTextView("posY", Elements.coordinatesY);
            createTextView("posZ", Elements.coordinatesZ);

            createBooleanView("posC");
            createBooleanView("posG");


            addViewsOnPanel(newPanel);

            buttonSmartClicked = true;

            if(originalPanel == null){
                originalPanel = new AnchorPane();
                originalPanel.setStyle(ActivityPanel.getStyle());
                originalPanel.setPrefSize(ActivityPanel.getWidth(), ActivityPanel.getHeight());
                originalPanel.getChildren().addAll(ActivityPanel.getChildren());
            }

            ActivityPanel.getChildren().clear();
            ActivityPanel.getChildren().add(newPanel);


        }else{
            if (originalPanel != null) {
                ActivityPanel.getChildren().clear();
                ActivityPanel.getChildren().add(originalPanel);
                views.clear();

                buttonSmartClicked = false;
                numGroup = 0;
                initY = 0;
            }
        }
    }


    @FXML
    private void actionWithImageContours(){

//        ImageView img = new ImageView();
        ImageView img = new ImageView();

        if(buttonSmartClicked){
            buttonSmartClicked = false;
            numGroup = 0;
            initY = 0;
            views.clear();
        }
        if (!buttonImageClicked) {
            buttonImageClicked = true;

            AnchorPane newPanel = new AnchorPane();

            newPanel.setPrefSize(ActivityPanel.getWidth(), ActivityPanel.getHeight());
            AnchorPane.setBottomAnchor(newPanel, 0.0);
            AnchorPane.setTopAnchor(newPanel, 0.0);
            AnchorPane.setRightAnchor(newPanel, 0.0);
            newPanel.getChildren().clear();

            repaintImageBorders((processedImage) -> {
                Image image = new Image(processedImage);
                img.setImage(image);
                img.setBlendMode(BlendMode.DIFFERENCE);
                img.setFitWidth(600);
                img.setFitHeight(400);

            });



            AnchorPane.setTopAnchor(img, 50.0);
            AnchorPane.setLeftAnchor(img, 50.0);
            AnchorPane.setRightAnchor(img, 140.0);
            newPanel.getChildren().add(img);
            Scroll.setOnKeyPressed(this::handleKeyPressed);

            if(originalPanel == null){
                originalPanel = new AnchorPane();
                originalPanel.setStyle(ActivityPanel.getStyle());
                originalPanel.setPrefSize(ActivityPanel.getWidth(), ActivityPanel.getHeight());
                originalPanel.getChildren().addAll(ActivityPanel.getChildren());
            }

            ActivityPanel.getChildren().clear();
            ActivityPanel.getChildren().add(newPanel);
        }else{
            if (originalPanel != null) {

                ActivityPanel.getChildren().clear();
                StackActivityPanel.setPrefSize(407, 736);
                ActivityPanel.getChildren().add(originalPanel);

                buttonImageClicked = false;
            }
        }
    }

    private void createTextView(String nameLabel, double value){
        Group group = new Group();

        Rectangle rectangle = new Rectangle(120,100);
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
        rectangle.setFill(Color.rgb(182, 163, 155));
        rectangle.setStroke(Color.BLACK);

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setLayoutY(33);
        textArea.setPrefSize(120, 67);
        textArea.setStyle("-fx-border-color: #808080;");
        textArea.setFont(font);
        textArea.setFont(Font.font(font.getFamily(), FontWeight.BOLD, font.getSize()));
        textArea.setMouseTransparent(true);
        textArea.setFocusTraversable(false);
        textArea.setText(String.valueOf(value));

        Label label = new Label(nameLabel);
        label.setAlignment(Pos.CENTER);
        label.setPrefSize(120, 20);
        label.setFont(font);
        label.setFont(Font.font(font.getFamily(), FontWeight.BOLD, font.getSize()));
        group.getChildren().addAll(rectangle, textArea, label);
        setLayoutForGroupOnSmartBoard(group);
    }

    private void createBooleanView(String nameLabel) {
        Group group = new Group();

        Rectangle rectangle = new Rectangle(120, 100);
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
        rectangle.setFill(Color.rgb(182, 163, 155));
        rectangle.setStroke(Color.BLACK);

        Label label = new Label(nameLabel);
        label.setAlignment(Pos.CENTER);
        label.setPrefSize(120, 20);
        label.setFont(font);
        label.setFont(Font.font(font.getFamily(), FontWeight.BOLD, font.getSize()));
        Rectangle rectangle1 = new Rectangle(120, 67);

        rectangle1.setArcHeight(5);
        rectangle1.setArcWidth(5);
        rectangle1.setLayoutY(33);
        rectangle1.setStroke(Color.BLACK);

        group.getChildren().addAll(rectangle, label, rectangle1);
        setLayoutForGroupOnSmartBoard(group);
    }

    private void setLayoutForGroupOnSmartBoard(Group group){
        double[] coordinateY = {0, 120, 240, 360, 480, 600, 720, 840, 960, 1080};
        double x = 15;
        double y = 10;
        numGroup++;
        if(numGroup>2 && (numGroup-1)%2==0){
            y +=coordinateY[initY];
        } else if(numGroup%2 == 0){
            x += 143;
            y +=coordinateY[initY];
            initY++;
        }
        group.setLayoutX(x);
        group.setLayoutY(y);
        views.add(group);
    }

    private void addViewsOnPanel(AnchorPane pane){
        for (Group view : views) {
            pane.getChildren().addAll(view);
        }
    }

    private String matToImage(Mat mat) {
        String fileName = "im.png";

        try {

            Path imagePath = Paths.get(Objects.requireNonNull(getClass().getResource("")).toURI()).resolve(fileName);
            // Преобразование пути в строку
            String url = imagePath.toString();
            // Запись файла
            Imgcodecs.imwrite(url, mat);

            return url;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "Ошибка: " + e.getMessage();
        }

    }

    private void handleKeyPressed(KeyEvent event) {
        if(buttonImageClicked){
            if (event.getCode() == KeyCode.Q && StackActivityPanel.getWidth()<=800) {
                double currentWidth = StackActivityPanel.getPrefWidth() + 100;
                StackActivityPanel.setPrefWidth(currentWidth);
            }else if(event.getCode() == KeyCode.E){
                double currentWidth = StackActivityPanel.getPrefWidth() - 100;
                StackActivityPanel.setPrefWidth(currentWidth);
            }
        }
    }

    @FXML
    public void changeMode(){
        isLightMode = !isLightMode;
        if(isLightMode){
            setLightMode();
        }else{
            setDarkMode();
        }
    }

    private void setLightMode(){
        parent.getStylesheets().remove(darkModeCss);
        parent.getStylesheets().add(ligModeCss);
        FontAwesomeIconView iconView = (FontAwesomeIconView) buttonChangeMode.getGraphic();

        iconView.setGlyphName("MOON_ALT");

    }
    private void setDarkMode(){
        parent.getStylesheets().remove(ligModeCss);
        parent.getStylesheets().add(darkModeCss);
        FontAwesomeIconView iconView = (FontAwesomeIconView) buttonChangeMode.getGraphic();
        iconView.setGlyphName("SUN_ALT");
    }

    @FXML
    private void repaintRobot(){
        if(startClicked){
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> updateImagePosition()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }

    }
    @FXML
    private void repaintImageBorders(Consumer<String> callback) {
        if (buttonImageClicked) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
                LogicBorders borders = new LogicBorders();
                List< MatOfPoint> li = borders.calculateBorders((int)imageRobot.getLayoutX(), (int)imageRobot.getLayoutY());
                String text = matToImage(borders.showFrameContours(li,borders.compareContour(li)));
                callback.accept(text);
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }


    private void updateImagePosition() {
        imageRobot.setLayoutX(posX);
        imageRobot.setLayoutY(posY);
        imageRobot.setRotate(posZ);
    }

    private void updateValue(){
        variablesList.add(String.format("%.04f", Elements.coordinatesX));
        variablesList.add(String.format("%.04f", Elements.coordinatesY));
        variablesList.add(String.format("%.04f", Elements.coordinatesZ));

        for (int i = 0; i < views.size(); i++) {
            for (Node node : views.get(i).getChildren()) {
                if (node instanceof TextArea textArea) {
                    textArea.setText(String.valueOf(variablesList.get(i)));
                }
            }
        }
        variablesList.clear();
    }

    private void updateBoolean() {
        booleanList.add(startClicked);
        booleanList.add(true);

        int rectangleCount = 0;
        for (int i = 0; i < views.size(); i++) {
            for (Node node : views.get(i).getChildren()) {
                if (node instanceof Rectangle rectangle) {
                    if (rectangleCount % 2 != 0) {
                        boolean isTrue = Boolean.TRUE.equals(booleanList.get(i));
                        rectangle.setFill(isTrue ? Color.rgb(35, 255, 31) : Color.rgb(219, 44, 44));
                    }
                    rectangleCount++;
                }else if(node instanceof TextArea){
                    rectangleCount=0;
                }
            }
        }
        variablesList.clear();
    }

    private void updateRobot()
    {
        initRobotElements();

        if (resetClicked)
        {
            posX = 290;
            posY = 290;
            posZ = 0;
            Elements.positionRobotX = 290;
            Elements.positionRobotY = 290;
            Elements.positionRobotZ = 0;
        }
        else
        {
            posX = Elements.positionRobotX;
            posY = Elements.positionRobotY;
            posZ = Elements.positionRobotZ;
        }
    }

    private void initRobotElements()
    {
        new RobotContainer();
        RobotContainer.el.CalculateCoordinates();
        RobotContainer.el.changePosition();
        StateMachine.time += 0.05;
        sm.Update();

        if (resetClicked)
        {
            sm.resetStateMachine();
        }
    }

    private void initCort()
    {
        try
        {
            generation2DArrays();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    /*
        Инициализация работы с симуляцией и генерацией поля
    */
    public void generation2DArrays() throws IOException
    {

        try
        {
            StartApplication.imgCort = ImageIO.read(new File("src/main/resources/com/example/demo2/paint.png"));

            StartApplication.imgRobot = rotateImageByDegrees(SwingFXUtils.fromFXImage(imageRobot.getImage(), null), posZ);

            File outfile = new File("original.png");
            ImageIO.write(StartApplication.imgRobot, "png", outfile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Array2DCort = new int[StartApplication.imgCort.getHeight()][StartApplication.imgCort.getWidth()];
        Array2DRobot = new int[StartApplication.imgRobot.getHeight()][StartApplication.imgRobot.getWidth()];
        assert StartApplication.imgCort != null;
        assert StartApplication.imgRobot != null;
        Array2DCort = get2DPixelArraySlow(StartApplication.imgCort, 1);
        Array2DRobot = get2DPixelArraySlow(StartApplication.imgRobot, -1);

        union2DArray(Array2DCort, Array2DRobot);
        change2DPixelArrayInImage(Array2DCort, "cort.png");
        change2DPixelArrayInImage(Array2DRobot, "robotPaint.png");
    }

    /*
        Вращение изображения для синхронизации с реальным роботом (BufferedImage)
    */
    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle)
    {

        double rads = Math.toRadians(angle);

        int w = img.getWidth();
        int h = img.getHeight();

        int newWidth = w;
        int newHeight = h;

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((double) (newWidth - w) / 2, (double) (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }


    /*
        получение двумерного массива из изображения (BufferedImage)
    */
    public int[][] get2DPixelArraySlow(BufferedImage sampleImage, int mode)
    {
        int width = sampleImage.getWidth();
        int height = sampleImage.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                int color = 0;
                if (sampleImage.getRGB(col, row) < -1)
                {
                    color = 1;
                }
                else if (sampleImage.getRGB(col, row) >= -1)
                {
                    color = 0;
                }
                else
                {
                    color = -1;
                }
                result[row][col] = color  * mode;
            }
        }
        return result;
    }

    /*
        преобразование двумерного массива в картинку с сохранением в файл с именем
    */
    public void change2DPixelArrayInImage(int[][] mas, String name) throws IOException
    {

        int h = mas.length;
        int w = mas[0].length;

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        java.awt.Color color = java.awt.Color.BLUE;
        for (int row = 0; row < h; row++)
        {
            for (int col = 0; col < w; col++)
            {
                if (mas[row][col] == -1)
                {
                    color = java.awt.Color.BLUE;
                }
                else if (mas[row][col] == 1)
                {
                    color = java.awt.Color.BLACK;
                }
                else
                {
                    color = java.awt.Color.WHITE;
                }
                image.setRGB(col, row, color.getRGB());

            }
        }

        File outfile = new File(name);
        ImageIO.write(image, "png", outfile);
    }

    /*
        сохранение массива с указанным именем в текстовый документ
    */
    public void addPixelInTXTDoc(int[][] mas, String name) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(name)));
        for (int[] i : mas)
        {
            String stuffToWrite = Arrays.toString(i);
            writer.write(stuffToWrite + '\n');
        }
        writer.close();
    }


    /*
        объединение массива поля и массива робота в единый массив
    */
    public void union2DArray(int[][] firstMas, int[][] secondMas)
    {
        int first, second = 0;

        for (int i = 0; i < secondMas.length; i++)
        {
            for (int j = 0; j < secondMas[0].length; j++)
            {
                first = (int) (Elements.positionRobotY - 70 + i);
                second = (int) (Elements.positionRobotX - 45 + j);

                if (firstMas[first][second] != secondMas[i][j] && firstMas[first][second] != 1)
//                if (firstMas[first][second] != secondMas[i][j] && secondMas[i][j] == -1)
                {
                    firstMas[first][second] = secondMas[i][j];
                }
            }
        }
    }
}