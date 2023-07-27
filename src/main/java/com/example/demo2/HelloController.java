package com.example.demo2;

import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HelloController {
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

    public AnchorPane originalPanel;

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
            AnchorPane.setBottomAnchor(newPanel, 0.0);
            AnchorPane.setTopAnchor(newPanel, 0.0);
            AnchorPane.setRightAnchor(newPanel, 0.0);


//            // Создаем Label для отображения названия
//            Label nameLabel = new Label("Название:");
//            nameLabel.setFont(new Font("Arial", 18));
//            nameLabel.setLayoutX(20);
//            nameLabel.setLayoutY(20);
//
//            // Создаем Text для отображения значения
//            Rectangle border = new Rectangle();
//            Text valueText = new Text("Значение");
//            valueText.setFont(new Font("Arial", 18));
//            valueText.setLayoutX(120);
//            valueText.setLayoutY(20);
//            border.setWidth(valueText.getBoundsInLocal().getWidth()); // Ширина равна ширине текста
//            border.setHeight(valueText.getBoundsInLocal().getHeight()); // Высота равна высоте текста
//            border.setFill(null); // Устанавливаем прозрачный фон для Rectangle
//            border.setStroke(Color.BLACK); // Устанавливаем цвет границы
//            border.setStrokeWidth(1.0); // Устанавливаем толщину границы
//
//            // Добавляем Label и Text в newPanel
//            newPanel.getChildren().addAll(nameLabel, valueText, border);

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
            }
        }
    }

    @FXML
    private void actionWithImageContours(){
        if(buttonSmartClicked){
            buttonSmartClicked = false;
        }
        if (!buttonImageClicked) {
            AnchorPane newPanel = new AnchorPane();
            newPanel.setStyle("-fx-background-color: #E2445C");
            newPanel.setPrefSize(ActivityPanel.getWidth(), ActivityPanel.getHeight());
            AnchorPane.setBottomAnchor(newPanel, 0.0);
            AnchorPane.setTopAnchor(newPanel, 0.0);
            AnchorPane.setRightAnchor(newPanel, 0.0);
            buttonImageClicked = true;

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

                buttonImageClicked = false;
            }
        }
    }

}