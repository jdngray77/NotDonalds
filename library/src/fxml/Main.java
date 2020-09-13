package fxml;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.RuntimeHelper;

import java.io.IOException;

public abstract class Main<T extends FXMLController> extends Application {

    /**
     * Template used to create
     */
    private T dummyController;

    /**
     * Reference to the main application window controller
     */
    public T controller;

    /**
     *
     * @param _dummyController Template controller to copy when creating the application.
     */
    public Main(T _dummyController){
        dummyController = _dummyController;
    }

    protected abstract void preLoad();

    protected abstract void postLoad();

    protected void createFXML(Stage primaryStage){
        try {                                                                                                           // PREPARE THE LOADER
            controller = (T) FXMLController.create(dummyController.getFXML(), null);

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
            primaryStage.show();                                                                                        // Show the stage as a window.
        } catch (IOException e) {
            alertStartFailure("Failed to create ui: ",e);
        }
    }

    protected final void alertStartFailure(String message, Exception e){
        RuntimeHelper.alertFailiure("[STARTUP] Cannot start!",e);
    }


    /**
     *
     *  JFX Application begins here.
     * Starts a FXML application instance.
     * Creates a JFX stage from the pos.fxml scene.
     *
     * Overrides JavaFX Application Start()
     * @param primaryStage to project ui onto
     * @throws IOException if failed to read fxml, or other resources.
     * @version 1.1
     **/
    @Override
    public final void start(Stage primaryStage)  {
        preLoad();
        createFXML(primaryStage);
        postLoad();
    }

}
