package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import util.RuntimeHelper;

import java.io.IOException;

/**
 * FXML Numeric input dialog controller
 * For graphical user input, especially on touchscreens.
 * @author gordie
 * @version 1
 */
public final class NumericInput extends FXMLController {

    //#region constants
    /**
     * relative location to the FXML definition of an numeric input window.
     */
    public static final String NUMERIC_INPUT_FXML = "./fxml/numericInput.fxml";

    /**
     * The string to display when the user requests to remove an item.
     */
    private static final String ITEM_REMOVE_TEXT = "REMOVE ITEM";

    /**
     * The value returned when the user confirms a request to remove an item.
     */
    public static final int ITEM_REMOVE_INDICATOR = -42069;
    //#endregion

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

    //#region properties
    /**
     * The alert window that is used to parent and display the popup dialog
     * for this dialog.
     */
    private Alert alert;
    //#endregion

    /**
     * Shows this FXML controller's content in an Alert window,
     * and waits for the user to confirm their input.
     */
    public void showAndWait(){
        // CREATE
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setContent(getAnchorPane());
        alert.getDialogPane().setPrefSize(400, 600);

        // REMOVE HEADER
        alert.setHeaderText(null); // These two lines remove the top content, removing the alert header.
        alert.setGraphic(null);

        // CSS
        alert.getDialogPane().getStylesheets().add(getClass().getResource("../style/pos.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("global-base");

        // SHOW
        alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
        alert.showAndWait();
    }

    /**
     * Appends a numeric to the end of the display's content.
     * @param i the numeric to append.
     */
    private void append(int i){
        if (lblDisplay.getText() == ITEM_REMOVE_TEXT)                   // If there is a remove request,
            lblDisplay.setText("");                                     // clear it. Don't append to it.

        lblDisplay.setText(lblDisplay.getText() + i);                   // Append numeric as text.
    }

    //#region FXML UI events
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
    //#endregion

    //#region methods
    /**
     * Gets the resulting value of this numeric dialog.
     * @return integer representation of the byte range value selected
     * OR
     * @return this#ITEM_REMOVE_INDICATOR if user requested the item to be removed.
     */
    public int value(){
        try {                                                               // TRY:    'case it's somehow possible that the content of the display cannot cast to an integer.
            if (lblDisplay.getText() == ITEM_REMOVE_TEXT)                   //         If the user has requested item removal,
                return ITEM_REMOVE_INDICATOR;                               // RETURN: ITEM_REMOVE_INDICATOR
            else                                                            //         the user had not requested item removal,
                return Integer.parseInt(lblDisplay.getText());              // RETURN: integer representation of the value selected.
        } catch (ClassCastException e){
            RuntimeHelper.alertFailiure("Invalid Numeric Input", e);     // Handle cast exception.
        }
        return 0;
    }

    /**
     *
     */
    @FXML
    private void min() {
        lblDisplay.setText("1");
    }

    @FXML
    private void remove() {
        lblDisplay.setText(ITEM_REMOVE_TEXT);
    }

    @FXML
    private void max() {
        lblDisplay.setText(String.valueOf(Byte.MAX_VALUE));
    }
    //#endregion

    //#region static utility
    /**
     * Prompts the user for a numeric input, and returns the resulting value.
     * @param defaultValue the default value to display.
     */
    public static int promptNumericInput(int defaultValue){
        try {                                                                                                             // TRY:    FXML load failure
            NumericInput inputController = (NumericInput) FXMLController.create(NUMERIC_INPUT_FXML, null); //         create controller
            inputController.lblDisplay.setText(String.valueOf(defaultValue));                                             //         set default
            inputController.showAndWait();                                                                                //         Show to user
            return inputController.value();                                                                               // RETURN: result from prompt.
        } catch (IOException e) {
            RuntimeHelper.alertFailiure("Failed to prompt for input", e);                                              // Handle FXML failed to load.
        }
        return defaultValue;                                                                                              // RETURN: default, did not get input.
    }
    //#endregion
}
