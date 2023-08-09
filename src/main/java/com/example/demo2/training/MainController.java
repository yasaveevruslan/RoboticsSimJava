package com.example.demo2.training;

import javafx.animation.*;
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

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import de.jensd.fx.glyphs.fontawesome.*;

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
    private boolean stopClicked = false;
    private boolean resetClicked = false;
    private boolean startClicked = false;

    private boolean isLightMode = true;

    private AnchorPane originalPanel;

    private int initY = 0;
    private int numGroup = 0;
    private final Font font = new Font("Arial", 16);
    private final String darkModeCss = Objects.requireNonNull(getClass().getResource("/com/example/demo2/darkMode.css")).toExternalForm();
    private final String ligModeCss = Objects.requireNonNull(getClass().getResource("/com/example/demo2/lightMode.css")).toExternalForm();
    private List<Group> views = new ArrayList<>();
    private List<Double> variablesList = new ArrayList<>();
    private List<Boolean> booleanList = new ArrayList<>();


    private void initTimelineForSmartBoard(){
        Timeline updateTimeValue = new Timeline(new KeyFrame(Duration.millis(300), e -> updateValue()));
        updateTimeValue.setCycleCount(Animation.INDEFINITE);
        updateTimeValue.play();

        Timeline updateTimeBoolean = new Timeline(new KeyFrame(Duration.millis(300), e -> updateBoolean()));
        updateTimeBoolean.setCycleCount(Animation.INDEFINITE);
        updateTimeBoolean.play();
    }
    public MainController() {
        initTimelineForSmartBoard();
    }

    @FXML
    private void initialize() {


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
    }
    @FXML
    private void buttonStopController(){
        stopClicked = !stopClicked;


    }
    @FXML
    private void buttonResetController(){
        resetClicked = !resetClicked;


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


            createTextView("posX", imageRobot.getLayoutX());

            createTextView("posY",imageRobot.getLayoutY());


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
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> updateImagePosition()));
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
        imageRobot.setLayoutX(290);
        imageRobot.setLayoutY(290);
        imageRobot.setRotate(90);
    }

    private void updateValue(){
        variablesList.add(imageRobot.getLayoutX());
        variablesList.add(imageRobot.getLayoutY());

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

}