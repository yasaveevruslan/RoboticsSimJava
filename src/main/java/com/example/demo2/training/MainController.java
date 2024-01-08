package com.example.demo2.training;

import com.example.demo2.StartApplication;
import com.example.demo2.logic.InitLogic;
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

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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

    public static float irFront, irBack, usFront, usRight;

//    private ConnectionHelper connectionHelper = new ConnectionHelper();


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

        Timeline updateCreateImage = new Timeline(new KeyFrame(Duration.millis(200), e -> initCort()));
        updateCreateImage.setCycleCount(Animation.INDEFINITE);
        updateCreateImage.play();
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
            createTextView("index", StateMachine.currentIndex);
            createTextView("array", StateMachine.currentArray);

            createTextView("IR_Front", irFront);
            createTextView("IR_Back", irBack);
            createTextView("US_Front", usFront);
            createTextView("US_Right", usRight);

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

                Image image = new Image("D:\\SimJava\\RoboticsSimJava\\cortPaint.png");
                img.setImage(image);
                img.setBlendMode(BlendMode.DIFFERENCE);
                img.setFitWidth((998+110) * 0.4);
                img.setFitHeight((500+110) * 0.4);
            });



            AnchorPane.setTopAnchor(img, 25.0);
            AnchorPane.setLeftAnchor(img, 25.0);
            AnchorPane.setRightAnchor(img, 25.0);
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
        String fileName = "cort.png";

        try {

            Path imagePath = Paths.get(Objects.requireNonNull(getClass().getResource("")).toURI()).resolve(fileName);
            // Преобразование пути в строку
            String url = imagePath.toString();
            // Запись файла
//            Imgcodecs.imwrite(url, mat);

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
        variablesList.add(String.valueOf(StateMachine.currentIndex));
        variablesList.add(String.valueOf(StateMachine.currentArray));

        variablesList.add(String.format("%.04f", irFront));
        variablesList.add(String.format("%.04f", irBack));
        variablesList.add(String.format("%.04f", usFront));
        variablesList.add(String.format("%.04f", usRight));

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
//        RobotContainer.el.CalculateSensors();
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
            generations2DArrays();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void generations2DArrays() throws IOException {
        try
        {
            StartApplication.imgCort = ImageIO.read(new File("src/main/resources/com/example/demo2/поле№3симулятор.png"));

            StartApplication.imgRobot = rotateImageByDegrees(SwingFXUtils.fromFXImage(imageRobot.getImage(), null), posZ);

            ImageIO.write(StartApplication.imgRobot, "png", new File("original.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Array2DCort = new int[StartApplication.imgCort.getHeight()+ 110][StartApplication.imgCort.getWidth() + 110];
        Array2DCort = new int[StartApplication.imgCort.getHeight()][StartApplication.imgCort.getWidth()];

        assert StartApplication.imgCort != null;
        assert StartApplication.imgRobot != null;

        Array2DCort = get2DPixelArraySlow(StartApplication.imgCort, true);
        Array2DRobot = get2DPixelArraySlow(StartApplication.imgRobot, false);

//        addPixelInTXTDoc(Array2DCort, "CortColor.txt");
//        addPixelInTXTDoc(Array2DRobot, "RobotColor.txt");

        union2DArray(Array2DCort, Array2DRobot);

        change2DPixelArrayInImage(Array2DCort, "cortPaint.png");
        change2DPixelArrayInImage(Array2DRobot, "robotPaint.png");

        generationContours();
    }

    private BufferedImage rotateImageByDegrees(BufferedImage img, double angle)
    {

        double rads = Math.toRadians(angle);

        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((double) (0) / 2, (double) (0) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

    private int[][] get2DPixelArraySlow(BufferedImage sampleImage, boolean isCort)
    {
        int width = sampleImage.getWidth() + (isCort ? 110 : 0);
        int height = sampleImage.getHeight() + (isCort ? 110 : 0);
        int[][] result = new int[height][width];


        if (isCort)
        {
            for (int i = 0; i < height; i ++)
            {
                for (int j = 0; j < width; j++)
                {
                    result[i][j] = 3;
                }
            }
        }


        for (int row = isCort ? 55 : 0; row < (isCort ? height - 55 : height); row++)
        {
            for (int col = isCort ? 55 : 0; col < (isCort ? width - 55 : width); col++)
            {

                int colorMode = 15;
                int colorPixel = sampleImage.getRGB(isCort ? col - 55 : col, isCort ? row - 55 : row);

                int blue = colorPixel & 0xff;
                int green = (colorPixel & 0xff00) >> 8;
                int red = (colorPixel & 0xff0000) >> 16;
                int alpha = (colorPixel & 0xff000000) >>> 24;

                boolean IsWhite = Function.InRangeBool(blue, 90, 255)
                        && Function.InRangeBool(green, 90, 255)
                        && Function.InRangeBool(red, 90, 255)
                        && Function.InRangeBool(alpha, 100, 255);

                // color red = blue: 20-90; green: 20-90; red: 140-255
                boolean IsRed = Function.InRangeBool(blue, 0, 90)
                        && Function.InRangeBool(green, 0, 90)
                        && Function.InRangeBool(red, 140, 255);

                boolean IsBlack = Function.InRangeBool(blue, 0, 200)
                        && Function.InRangeBool(green, 0, 200)
                        && Function.InRangeBool(red, 0, 200);

                if (isCort)
                {
                    if (IsBlack)
                    {
                        colorMode = 3; // BLACK
                    }
                    else
                    {
                        colorMode = 0; // WHITE
                    }
                }
                else
                {
                    if (alpha <= 200 || IsWhite)
                    {
                        colorMode = 0; // WHITE
                    }
                    else
                    {
                        if (IsRed)
                        {
                            colorMode = 1; // RED
                        }
                        else
                        {
                            colorMode = 2; // BLUE
                        }
                    }
                }
                result[row][col] = colorMode;
            }
        }
        return result;
    }

    private void addPixelInTXTDoc(int[][] mas, String name) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(name)));
        for (int[] i : mas)
        {
            String stuffToWrite = Arrays.toString(i);
            writer.write(stuffToWrite + '\n');
        }
        writer.close();
    }

    private void change2DPixelArrayInImage(int[][] mas, String name) throws IOException
    {

        int h = mas.length;
        int w = mas[0].length;

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        java.awt.Color color = java.awt.Color.WHITE;
        for (int row = 0; row < h; row++)
        {
            for (int col = 0; col < w; col++)
            {
                if (mas[row][col] == 2)
                {
                    color = java.awt.Color.BLUE;
                }
                else if (mas[row][col] == 1)
                {
                    color = java.awt.Color.RED;
                }
                else if (mas[row][col] == 3)
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

    private void union2DArray(int[][] firstMas, int[][] secondMas)
    {
        int first, second = 0;

        for (int i = 0; i < secondMas.length; i++)
        {
            for (int j = 0; j < secondMas[0].length; j++)
            {
                first = ((int)(Elements.positionRobotY) - 70 + i);
                second = ((int)(Elements.positionRobotX) - 45 + j);

                /*
                 * добавление массива с роботом под стенки
                 */
//                if (firstMas[first + 55][second + 55] != secondMas[i][j] && firstMas[first + 55][second + 55] != 3)
//                {
//                    firstMas[first + 55][second + 55] = secondMas[i][j];
//                }
                if (firstMas[first + 55][second + 55] != secondMas[i][j] && (secondMas[i][j] == 1 || secondMas[i][j] == 2))
                {
                    firstMas[first + 55][second + 55] = secondMas[i][j];
                }
            }
        }
    }

    private void generationContours() throws IOException {
        int firstX = (int) (posX - 50), firstY = (int) (posY - 50), firstHeight = 205, firstWidth = 205;

        float limitUp, limitDown, limitLeft, limitRight;

        float blackUp, blackDown, blackLeft, blackRight;

        boolean isUp, isDown, isLeft, isRight;

//        for (int i = firstX; i < firstWidth; i ++)
//        {
//            for (int j = firstY; j < firstHeight; j ++)
//            {
//
//            }
//        }

//        limitLeft = leftContour();
//        System.out.println(limitLeft);

//        int[][] masRight = rightContour();
//        int[][] masUp = upContour();
//        int[][] masDown = downContour();

        int[][] generationImageContour = generationImageContour();
        int[][] masLeft = leftContour(generationImageContour);

        change2DPixelArrayInImage(generationImageContour, "generationImageContour.png");
        change2DPixelArrayInImage(masLeft, "LeftContour.png");
//        change2DPixelArrayInImage(masRight, "RightContour.png");
//        change2DPixelArrayInImage(masUp, "UpContour.png");
//        change2DPixelArrayInImage(masDown, "DownContour.png");
    }

    private void divisionInto4Zones()
    {
        float firstX, firstY, firstHeight = 100, firstWidth = 100;
        float secondX, secondY, secondHeight = 100, secondWidth = 100;
        float thirdX, thirdY, thirdHeight = 100, thirdWidth = 100;
        float fourthX, fourthY, fourthHeight = 100, fourthWidth = 100;
    }

//    private int[][] leftContour()
//    {
//        float limitLeft = 999999;
//        float thisLimitLeft;
//
//        int startX = (int) (posX - 40), endX = (int) (posX + 60), startY = (int) (posY - 65), endY = (int) (posY + 140);
//
//        int[][] mas = new int[300][300];
//        for (int y = startY; y < endY; y ++)
//        {
//
//            float blackPosition = 45;
//            for (int x = startX; x < endX; x ++)
//            {
//                mas[y - startY][x - startX] = Array2DCort[y][x];
//
//                if (Array2DCort[y][x] == 3)
//                {
//                    blackPosition = x;
//                }
//
//                if (Array2DCort[y][x] == 1 || Array2DCort[y][x] == 2)
//                {
//                    thisLimitLeft = blackPosition;
//                    if (thisLimitLeft < limitLeft)
//                    {
//                        limitLeft = thisLimitLeft;
//                    }
//                    break;
//                }
//
//            }
//
//        }
//        Elements.frontierLeft = (int) limitLeft;
//        return mas;
//    }

    private int[][] rightContour()
    {
        float limitRight = 1043;
        boolean robotOut = true;

        int startX = (int) (posX + 60), endX = (int) (posX + 165), startY = (int) (posY - 65), endY = (int) (posY + 140);

        int[][] mas = new int[300][300];
        for (int y = startY; y < endY; y ++)
        {

            float blackPosition = 1043;
            for (int x = startX; x < endX; x ++)
            {
                mas[y - startY][x - startX] = Array2DCort[y][x];

                if (Array2DCort[y][x] == 1 || Array2DCort[y][x] == 2)
                {
                    robotOut = false;
                }


                if (Array2DCort[y][x] == 3)
                {
                    if (!robotOut)
                    {
                        blackPosition = x;
                        if (blackPosition < limitRight)
                        {
                            limitRight = blackPosition;
                        }
                        break;
                    }
                }
            }

        }
        Elements.frontierRight = (int) limitRight;
        return mas;
    }

    private int[][] upContour()
    {
        float limitUp = 999999;
        float thisLimitUp;

        int startX = (int) (posX - 40), endX = (int) (posX + 165), startY = (int) (posY - 65), endY = (int) (posY + 35);

        int[][] mas = new int[300][300];
        for (int x = startX; x < endX; x ++)
        {

            float blackPosition = 70;
            for (int y = startY; y < endY; y ++)
            {
                mas[y - startY][x - startX] = Array2DCort[y][x];

                if (Array2DCort[y][x] == 3)
                {
                    blackPosition = x;
                }

                if (Array2DCort[y][x] == 1 || Array2DCort[y][x] == 2)
                {
                    thisLimitUp = blackPosition;
                    if (thisLimitUp < limitUp)
                    {
                        limitUp = thisLimitUp;
                    }
                    break;
                }

            }

        }
        Elements.frontierUp = (int)(limitUp);
        return mas;
    }

    private int[][] downContour()
    {
        float limitDown = 570;
        boolean robotOut = true;

        int startX = (int) (posX - 40), endX = (int) (posX + 165), startY = (int) (posY + 35), endY = (int) (posY + 140);

        int[][] mas = new int[300][300];
        for (int x = startX; x < endX; x ++)
        {

            float blackPosition = 570;
            for (int y = startY; y < endY; y ++)
            {
                mas[y - startY][x - startX] = Array2DCort[y][x];

                if (Array2DCort[y][x] == 1 || Array2DCort[y][x] == 2)
                {
                    robotOut = false;
                }

                if (Array2DCort[y][x] == 3)
                {
                    if (!robotOut)
                    {
                        blackPosition = x;
                        if (blackPosition < limitDown)
                        {
                            limitDown = blackPosition;
                        }
                        break;
                    }
                }
            }

        }
        Elements.frontierDown = (int)(limitDown);
        return mas;
    }

    private int[][] generationImageContour()
    {
        int[][] mas = new int[205][205];
        int first, second = 0;

        first = ((int)(Elements.positionRobotY) - 70);
        second = ((int)(Elements.positionRobotX) - 45);

        for (int i = 0; i < mas.length; i++)
        {
            for (int j = 0; j < mas[0].length; j++)
            {
                mas[i][j] = Array2DCort[first+i][second+j];
            }
        }
        return mas;
    }

    private int[][] leftContour(int[][] mas)
    {
        int first, second = 0;
        int[][] masLeft = new int[205][205];
        first = ((int)(Elements.positionRobotY) - 70);
        second = ((int)(Elements.positionRobotX) - 45);

        int positionBlack = 0;
        int thisLimitLeft, limitLeft = 9999;

        for (int i = 0; i < mas.length; i++)
        {
            for (int j = 0; j < mas[0].length; j++)
            {
                masLeft[i][j] = Array2DCort[first+i][second+j];

                if (Array2DCort[first+i][second+j] == 3)
                {
                    positionBlack = second+j;
                }

                if (Array2DCort[first+i][second+j] == 1 || Array2DCort[first+i][second+j] == 2)
                {
                    thisLimitLeft = positionBlack;
                    if (thisLimitLeft < limitLeft)
                    {
                        limitLeft = thisLimitLeft;
                    }
                    break;
                }
            }
        }
        Elements.frontierLeft = limitLeft;
//        System.out.println(limitLeft);
        return masLeft;
    }
}