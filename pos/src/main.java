import controller.FXMLController;
import controller.pos;
import io.MenuHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.Client;
import network.packet.Packet;
import network.packet.PacketType;
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
     * @version 1.1
     */
    @Override
    public void start(Stage primaryStage)  {
        preLoad();

        try {                                                                                                           // PREPARE THE LOADER
            controller = (pos) FXMLController.create(pos.POS_FXML, null);

            // PREPARE THE SCENE FOR STAGE
            // FXML INJECTION NOTE
            // Controller variables annotated with @FXML, that match an fx:id and type of a component within the
            // scene, will be automatically populated with a reference to the scene instance by this load.
            //
            // i.e posController.menuTilePane matches the scene TilePane with fx:id "menuTilePanel"
            Scene scene = new Scene(controller.getAnchorPane(), RuntimeHelper.SCREEN_SIZE.getWidth(), RuntimeHelper.SCREEN_SIZE.getHeight());                                             // Load the scene from FXML

            // PREPARE THE STAGE TO SHOW SCENE
            primaryStage.setTitle(RuntimeHelper.SYSTEM_NAME);                                                           // Set the stage title
            primaryStage.setScene(scene);                                                                               // place loaded FXML scene on stage
            primaryStage.setFullScreen(false);                                                                           // Fullscreen stage

            controller.renderMenu();
            primaryStage.show();                                                                                        // Show the stage as a window.
        } catch (IOException e) {
            alertStartFailure("Failed to create ui: ",e);
        }

        postLoad();
    }

    /**
     * Events / logic to be executed before attempting to startup.
     */
    private void preLoad() {
        RuntimeHelper.log(this, "[PRELOAD] Validating startup configuration..");
        if (!assertConnection() || !loadMenu()) return;                                                                 // Before loading UI, check that a server is available then fetch the menu from it.
        RuntimeHelper.log(this, "[PRELOAD] Able to start, commence!");
    }

    /**
     * Manually added list of events to be triggered after pos has loaded.
     */
    private void postLoad() {
        controller.setOrderFlash();
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
        RuntimeHelper.log(this, "[CONFIG] Validating server connection..");
       try{
           if (Client.sendToServer(new Packet(PacketType.PING)).type() != PacketType.ACKNOWLEDGE) throw new IOException("Did not receive a valid ping response.");
           else {
               RuntimeHelper.log(this, "[CONFIG] Valid!");
               return true;
           }
       } catch (Exception e) {
           RuntimeHelper.log(this, "[CONFIG] Connection not valid!");
           alertStartFailure("[CONFIG] Failed to connect to the server, ",e);
           return false;
       }
    }

    /**
     * Gets the menu from the server
     */
    private boolean loadMenu(){
        RuntimeHelper.log(this, "[CONFIG] Grabbing menu from server..");
        try {
            MenuHelper.loadMenuFromServer();
            RuntimeHelper.log(this, "[CONFIG] Got it!");
            return true;
        } catch (IOException e) {
            RuntimeHelper.log(this, "[CONFIG] Failed to get menu from server!");
            alertStartFailure("[CONFIG] Failed to load menu from server, ", e);
            return false;
        }
    }

    private void alertStartFailure(String message, Exception e){
        RuntimeHelper.alertFailiure("[STARTUP] Cannot start (" + message + ": " + e.getMessage() + ")",e);
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
