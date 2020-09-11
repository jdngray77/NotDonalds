package util.packettest;

import javafx.scene.control.Alert;
import network.Client;
import network.packet.Packet;
import network.packet.PacketType;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class tester {

    private JComboBox comboPacketType;
    private JButton btnSendPacket;

    private DefaultListModel model = new DefaultListModel<String>();
    private JList lstResponse;

    private JTextField txtPacketMeta;
    private JPanel tester;

    public tester(){
        new Alert(Alert.AlertType.WARNING, "Packet Tester Tool is intended for technical use only. It constructs and sends packets to the local server, and documents the response.").showAndWait();

        lstResponse.setModel(model);
        JFrame frame = new JFrame("Packet Test");
        frame.setContentPane(tester);

        /*
            populate and configure
         */
        for (PacketType e : PacketType.values())                                                                        // Populate combo box of packet types
            comboPacketType.addItem(e.toString());

        btnSendPacket.addMouseListener(new MouseAdapter() {                                                             // Send button listener
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    Packet response = Client.sendToServer(
                            new Packet(
                                    PacketType.values()[comboPacketType.getSelectedIndex()],
                                    txtPacketMeta.getText()
                            )
                    );
                    model.addElement(
                            "[SENT] " +
                                    PacketType.values()[comboPacketType.getSelectedIndex()] +
                                    " [GOT] " + response.type() + "; "
                                    + response.metaMessage
                                    );
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, ioException.getMessage(), "Packet failed!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        new tester();
    }
}