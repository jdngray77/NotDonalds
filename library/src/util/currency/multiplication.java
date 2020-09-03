package util.currency;

import sale.price.ShortPrice;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class multiplication extends JFrame {
    private JButton executeButton;
    private JSpinner spnTimes;
    private JLabel lblExpected;
    private JLabel lblActual;
    private JSpinner spnDecimal;
    private JSpinner spnFraction;
    private JSpinner spnLen;
    private JTextField txtSymbol;
    private JPanel panel;
    private JCheckBox chkRTL;

    public multiplication() {
        executeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                calculate();
            }
        });

        setDefaults();

        setContentPane(panel);
        pack();
        setVisible(true);
        calculate();
        setLocationRelativeTo(null);
    }

    private void setDefaults() {
        spnTimes.setValue(1);
        spnFraction.setValue(99);
        spnDecimal.setValue(10);
        spnLen.setValue(10);
    }

    private void calculate() {
        try {
            // This is acctually horrendous. Unlucky!
            lblExpected.setText(String.valueOf(Float.parseFloat(spnDecimal.getValue() + "." + spnFraction.getValue()) * Float.parseFloat(spnTimes.getValue().toString())));
            ShortPrice price = new ShortPrice(Short.valueOf(spnDecimal.getValue().toString()), Short.valueOf(spnFraction.getValue().toString()),Byte.parseByte(String.valueOf(spnLen.getValue())), txtSymbol.getText().charAt(0), chkRTL.isSelected());
            lblActual.setText(price.times(Byte.parseByte(spnTimes.getValue().toString())).asDisplay());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"Failed to Execute: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new multiplication();
    }

}
