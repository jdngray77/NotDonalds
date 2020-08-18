import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.RuntimeHelper;

import java.io.IOException;

public class main extends Application {

    /**
     * Controller for this instance.
     */
    private controller.pos controller;

    /**
     * point of sale base fxml document location.
     */
    private static final String POS_FXML = "./fxml/pos.fxml";

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
                                                                                                                        // PREPARE THE LOADER
        FXMLLoader loader = new FXMLLoader();                                                                           // Create a loader to load the form from FXML file


                                                                                                                        // PREPARE THE SCENE
        // NOTE
        // Controller variables annotated with @FXML, that match an fx:id and type of a component within the
        // scene, will be automatically populated with a reference to the scene instance by this load.
        //
        // i.e posController.menuTilePane matches the scene TilePane with fx:id "menuTilePanel"
        Scene scene = new Scene(loader.load(getClass().getResource(POS_FXML)), 1920, 1080);                // Load the scene from FXML

                                                                                                                        // PREPARE THE STAGE
        primaryStage.setTitle(RuntimeHelper.SYSTEM_NAME);                                                               // Set the stage title
        primaryStage.setScene(scene);                                                                                   // Set loaded FXML scene to stage
        primaryStage.show();                                                                                            // Show the stage as a window.
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
