package com.example.demo2.training;

import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

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

    private boolean buttonSmartClicked = false;
    private boolean buttonImageClicked = false;

    private AnchorPane originalPanel;

    private int initY = 0;
    private int numGroup = 0;
    private final Font font = new Font("Arial", 16);

    private List<Group> views = new ArrayList<>();


    @FXML
    private void initialize() {
        buttonONandOFF.setOnAction(actionEvent -> hidePanel(true));
        buttonONandOFFSmall.setOnAction(actionEvent -> hidePanel(false));


        buttonSmartBoard.setOnAction(actionEvent -> actionWithSmartBoard());
        buttonSmartBoardSmall.setOnAction(actionEvent -> actionWithSmartBoard());

        buttonImageContours.setOnAction(actionEvent -> actionWithImageContours());
        buttonImageContoursSmall.setOnAction(actionEvent -> actionWithImageContours());

    }
    @FXML
    public void setImageUrl(String imageUrl) {
        Image image = new Image(imageUrl);
        imageArea.setImage(image);
    }
    @FXML
    private void hidePanel(Boolean notSmallButton) {
        if (ActivityPanel.getOpacity() == 1.0) {
            ActivityPanel.setOpacity(0.0);
        } else if (Boolean.FALSE.equals(notSmallButton)) {
            ActivityPanel.setOpacity(1.0);
        }
    }
    @FXML
    private void actionWithSmartBoard(){
        if(buttonImageClicked){
            buttonImageClicked = false;
        }

        if (!buttonSmartClicked) {
            AnchorPane newPanel = new AnchorPane();

            newPanel.setPrefSize(ActivityPanel.getWidth(), ActivityPanel.getHeight());
            newPanel.setBlendMode(BlendMode.SRC_ATOP);

            AnchorPane.setBottomAnchor(newPanel, 0.0);
            AnchorPane.setTopAnchor(newPanel, 0.0);
            AnchorPane.setRightAnchor(newPanel, 0.0);

            createBooleanView("posA");
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
                originalPanel.setBlendMode(BlendMode.SRC_ATOP);

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

    private LogicBorders borders = new LogicBorders();


    @FXML
    private void actionWithImageContours(){
        if(buttonSmartClicked){
            buttonSmartClicked = false;
            numGroup = 0;
            initY = 0;
            views.clear();
        }
        if (!buttonImageClicked) {
            AnchorPane newPanel = new AnchorPane();
            newPanel.setStyle("-fx-background-color: #E2445C");
            newPanel.setBlendMode(BlendMode.SRC_ATOP);
            newPanel.setPrefSize(ActivityPanel.getWidth(), ActivityPanel.getHeight());
            AnchorPane.setBottomAnchor(newPanel, 0.0);
            AnchorPane.setTopAnchor(newPanel, 0.0);
            AnchorPane.setRightAnchor(newPanel, 0.0);

            borders.calculateBorders(290,290);
            buttonImageClicked = true;

            if(originalPanel == null){
                originalPanel = new AnchorPane();
                originalPanel.setStyle(ActivityPanel.getStyle());
                originalPanel.setBlendMode(BlendMode.SRC_ATOP);

                originalPanel.setPrefSize(ActivityPanel.getWidth(), ActivityPanel.getHeight());
                originalPanel.getChildren().addAll(ActivityPanel.getChildren());
            }

            ActivityPanel.getChildren().clear();
            ActivityPanel.getChildren().add(newPanel);
        }else{
            if (originalPanel != null) {
                ActivityPanel.getChildren().clear();
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
        rectangle.setFill(Color.rgb(146, 195, 255));
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

        group.getChildren().addAll(rectangle, textArea, label);
        setLayoutForGroupOnSmartBoard(group);
    }

    private void createBooleanView(String nameLabel) {
        Group group = new Group();

        Rectangle rectangle = new Rectangle(120, 100);
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
        rectangle.setFill(Color.rgb(146, 195, 255));
        rectangle.setStroke(Color.BLACK);

        Label label = new Label(nameLabel);
        label.setAlignment(Pos.CENTER);
        label.setPrefSize(120, 20);
        label.setFont(font);

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

}