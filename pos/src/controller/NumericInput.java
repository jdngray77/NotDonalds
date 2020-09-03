package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import util.RuntimeHelper;

import java.io.IOException;

public final class NumericInput extends FXMLController {


    //#region FXML
    public Label lblDisplay;
    public Button btn1;
    public Button btn2;
    public Button btn3;
    public Button btn4;
    public Button btn5;
    public Button btn6;
    public Button btn7;
    public Button btn8;
    public Button btn9;
    public Button btn0;
    public Button btnEnter;
    public Button btnDelete;
    //#endregion

    private Alert alert;

    public static final String NUMERIC_INPUT_FXML = "./fxml/numericInput.fxml";

    public void showAndWait(){
        // CREATE
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setContent(anchorPane);
        alert.getDialogPane().setPrefSize(RuntimeHelper.SCREEN_SIZE.getWidth(), RuntimeHelper.SCREEN_SIZE.getHeight());

        // STYLE
        alert.setHeaderText(null); // These two lines remove the top content, removing the alert header.
        alert.setGraphic(null);

        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("../style/pos.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("global-base");

        // SHOW
        alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
        alert.showAndWait();
    }

    private void append(int i){
        lblDisplay.setText(lblDisplay.getText() + i);
    }


    @FXML
    private void delete(){
        if (lblDisplay.getText().length() < 1)
                return;

            lblDisplay.setText(lblDisplay.getText().substring(0,lblDisplay.getText().length() - 1));
    }

    @FXML
    public void one() {
        append(1);
    }

    @FXML
    public void two() {
        append(2);
    }

    @FXML
    public void three() {
        append(3);
    }

    @FXML
    public void four() {
        append(4);
    }

    @FXML
    public void five() {
        append(5);
    }

    @FXML
    public void six() {
        append(6);
    }

    @FXML
    public void seven() {
        append(7);
    }

    @FXML
    public void eight() {
        append(8);
    }

    @FXML
    public void nine() {
        append(9);
    }

    @FXML
    public void zero() {
        append(0);
    }

    @FXML
    public void enter() {
        alert.close();
    }

    public int value(){
        try {
            return Integer.parseInt(lblDisplay.getText());
        } catch (Exception e){
            RuntimeHelper.alertFailiure("Invalid Numeric Input", e);
        }
            return 0;
    }

    public static int promptNumericInput(int defaultValue){
        try {
            NumericInput inputController = (NumericInput) FXMLController.create(NUMERIC_INPUT_FXML, null);
            inputController.lblDisplay.setText(String.valueOf(defaultValue));
            inputController.showAndWait();
            return inputController.value();
        } catch (IOException e) {
            RuntimeHelper.alertFailiure("Failed to prompt for input", e);
        }
            return 0;
    }
}
