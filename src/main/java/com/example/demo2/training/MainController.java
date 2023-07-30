package com.example.demo2.training;

import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    private boolean buttonSmartClicked = false;
    private boolean buttonImageClicked = false;

    private AnchorPane originalPanel;

    private int initY = 0;
    private int numGroup = 0;
    private final Font font = new Font("Arial", 16);
    private String darkModeCss = getClass().getResource("/com/example/demo2/darkMode.css").toExternalForm();
    private String ligModeCss = getClass().getResource("/com/example/demo2/lightMode.css").toExternalForm();
    private List<Group> views = new ArrayList<>();


    @FXML
    private void initialize() {
        if(!(buttonImageClicked && buttonSmartClicked)){
            Scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            Scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }

        buttonONandOFF.setOnAction(actionEvent -> hidePanel(true));
        buttonONandOFFSmall.setOnAction(actionEvent -> hidePanel(false));

        buttonSmartBoard.setOnAction(actionEvent -> actionWithSmartBoard());
        buttonSmartBoardSmall.setOnAction(actionEvent -> actionWithSmartBoard());

        buttonImageContours.setOnAction(actionEvent -> actionWithImageContours());
        buttonImageContoursSmall.setOnAction(actionEvent -> actionWithImageContours());

        buttonChangeMode.setOnAction(actionEvent -> changeMode());

    }
    private boolean isLightMode = true;

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
    public void setImageUrl(URL imageUrl) {
        Image image = new Image(imageUrl.toExternalForm());
        imageArea.setImage(image);
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

            createTextView("posA");
            createBooleanView("posB");
            createBooleanView("posC");
            createBooleanView("posD");
            createBooleanView("posE");
            createBooleanView("posF");
            createBooleanView("posG");
            createBooleanView("posI");
            createBooleanView("posY");
            createBooleanView("posU");
            createBooleanView("posO");
            createBooleanView("posPG");
            createBooleanView("posL");

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

                buttonSmartClicked = false;
                numGroup = 0;
                initY = 0;
                views.clear();
            }
        }
    }


    @FXML
    private void actionWithImageContours(){
        LogicBorders borders = new LogicBorders();
        String text = matToImage(borders.showFrameContours(borders.calculateBorders(290,290)));
        ImageView img = new ImageView();

        if(buttonSmartClicked){
            buttonSmartClicked = false;
            numGroup = 0;
            initY = 0;
            views.clear();
        }
        if (!buttonImageClicked) {

            AnchorPane newPanel = new AnchorPane();

            newPanel.setPrefSize(ActivityPanel.getWidth(), ActivityPanel.getHeight());
            AnchorPane.setBottomAnchor(newPanel, 0.0);
            AnchorPane.setTopAnchor(newPanel, 0.0);
            AnchorPane.setRightAnchor(newPanel, 0.0);
            newPanel.getChildren().clear();

            Image image = new Image(text);
            img.setImage(image);
            img.setBlendMode(BlendMode.DIFFERENCE);
            img.setFitWidth(600);
            img.setFitHeight(400);

            AnchorPane.setTopAnchor(img, 50.0);
            AnchorPane.setLeftAnchor(img, 50.0);
            AnchorPane.setRightAnchor(img, 140.0);
            newPanel.getChildren().add(img);
            buttonImageClicked = true;
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

                String newText = matToImage(borders.showFrameContours(borders.calculateBorders(290, 290)));
                Image newImage = new Image(newText);
                img.setImage(newImage);

                ActivityPanel.getChildren().clear();
                StackActivityPanel.setPrefSize(407, 736);
                ActivityPanel.getChildren().add(originalPanel);

                buttonImageClicked = false;
            }
        }
    }

    private void createTextView(String nameLabel){
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
        textArea.setMouseTransparent(true);
        textArea.setFocusTraversable(false);

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
        rectangle1.setFill(Color.rgb(35, 255, 31));
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
        String url ="C:\\Users\\Monbe\\IdeaProjects\\Sim\\RoboticsSimJava\\src\\main\\resources\\com\\example\\demo2\\im.png";
        System.load("C:\\Users\\Monbe\\IdeaProjects\\Sim\\RoboticsSimJava\\src\\main\\java\\com\\example\\demo2\\opencv_java440.dll");
        Imgcodecs.imwrite(url, mat);
        return url;

    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.I && StackActivityPanel.getWidth()<=1000) {
            double currentWidth = StackActivityPanel.getPrefWidth() + 100;
            StackActivityPanel.setPrefWidth(currentWidth);
        }else if(event.getCode() == KeyCode.K){
            double currentWidth = StackActivityPanel.getPrefWidth() - 100;
            StackActivityPanel.setPrefWidth(currentWidth);
        }
    }


}