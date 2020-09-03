package swing;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Absolutely not done.
 *
 * TODO P1: DIALOG DOES NOT RETURN WHEN USER CLICKS OK WHILST IN FULLSCREEN!
 * TODO There's litterally 0 javadocs, oh well!
 *
 */
public class numericInput extends Alert {

    private static int value;
    //#region swing
    private JTextField outputField;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;
    private JButton btnCancle;
    private JButton btn0;
    private JButton btnEnter;
    private JPanel panel;
    //#endregion swing

    public numericInput(String text) {
        super(AlertType.INFORMATION, text);
        addListener(btn0, 0);
        addListener(btn1, 1);
        addListener(btn2, 2);
        addListener(btn3, 3);
        addListener(btn4, 4);
        addListener(btn5, 5);
        addListener(btn6, 6);
        addListener(btn7, 7);
        addListener(btn8, 8);
        addListener(btn9, 9);

        btnCancle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                panel.setVisible(false);
                value = -1;
            }
        });

        btnEnter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                panel.setVisible(false);
            }
        });


        //#region REDACTED
        /*
            Displaying a Swing JDialog for a JavaFX  application was too much of a
            pain in the arse that wasn't really working out for us.
            Instead of re-creating the entire dialog in JFX,
            i'm just displaying the swing panel in a JFX stage as a node using jfx.embed
         */

        //panel.setVisible(true);
        //getContentPane().add(panel);
        //pack();
        //setTitle(title);
        //setVisible(true);
        //#endregion

        // Show
        setResizable(true);
        SwingNode sNode = new SwingNode();                                                                              // Create JFX compatible node to contain Swing content,
        sNode.setContent(panel);                                                                                        // and add dialog pane to it.
        getDialogPane().setContent(sNode);                                                                              // Add the content node to the pane of the JFX dialog window this class extends.
        getDialogPane().autosize();
        Platform.runLater(() -> {
            showAndWait();
            close();
        });
    }

    //#region listener
    private void append(int s){
        outputField.setText(outputField.getText() + s);
    }

    private void addListener(JButton button, int number){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                append(number);
            }
        });
    }
    //#endregion

    public static numericInput New(String text){
        return new numericInput(text);
    }

    public int value(){
        return value;
    }
}
