import controller.pos;
import io.MenuHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import network.Client;
import network.packet.Packet;
import network.packet.PacketType;
import sale.menu.Menu;
import util.RuntimeHelper;

import java.io.IOException;

public class main extends Application {

    /**
     * Static reference to the main application window controller
     */
    public static pos controller;

    /**
     * Starts a pos ui instance.
     * Creates a JFX stage from the pos.fxml scene.
     *
     * Overrides JavaFX Application Start()
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        if (!assertConnection() || !loadMenu()) return;                                                                 // Before loading UI, check that a server is available then fetch the menu from it.

                                                                                                                        // PREPARE THE LOADER
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pos.POS_FXML));                                       // Create a loader to load the form from FXML file

                                                                                                                        // PREPARE THE SCENE FOR STAGE
        // FXML INJECTION NOTE
        // Controller variables annotated with @FXML, that match an fx:id and type of a component within the
        // scene, will be automatically populated with a reference to the scene instance by this load.
        //
        // i.e posController.menuTilePane matches the scene TilePane with fx:id "menuTilePanel"
        Scene scene = new Scene(loader.load(), 1920, 1080);                                                 // Load the scene from FXML
        controller = loader.getController();
                                                                                                                        // PREPARE THE STAGE TO SHOW SCENE
        primaryStage.setTitle(RuntimeHelper.SYSTEM_NAME);                                                               // Set the stage title
        primaryStage.setScene(scene);                                                                                   // place loaded FXML scene on stage
        primaryStage.show();                                                                                            // Show the stage as a window.
        controller.renderMenu();
    }

    /**
     * Tests connection between between this client and a network server.
     *
     * Broadcasts a 'PING', expecting to connect and receive an 'ACKNOWLEDGE' in response.
     *
     * @throws IOException If this client fails to connnect, send a packet, and recieve the expected response in a compatible form.
     * @apiNote serialization versions for the class 'network.packet.Packet' between the client and server implementations
     * must match for serialization to be valid.
     */
    private boolean assertConnection() {
       try{
           if (Client.sendToServer(new Packet(PacketType.PING)).type() != PacketType.ACKNOWLEDGE) throw new IOException("Did not receive a valid ping response.");
           else return true;
       } catch (Exception e) {
           e.printStackTrace();
           alertStartFailiure("Failed to connect to the server, " + e.getMessage());
           return false;
       }
    }

    /**
     * Gets the menu from the server
     */
    private boolean loadMenu(){
        try {
            MenuHelper.menu = (Menu) Client.sendToServer(new Packet(PacketType.MENU_REQUEST)).getPacketData();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            alertStartFailiure("Failed to load menu from server, " + e.getMessage());
            return false;
        }
    }

    private void alertStartFailiure(String message){
        new Alert(Alert.AlertType.ERROR, "Cannot start (" + message + ")", ButtonType.OK).showAndWait();
    }

    /**
     * JRE entry point.
     * Uses JFX Application Launch to manage start.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
