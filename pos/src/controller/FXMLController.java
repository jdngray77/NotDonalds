package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public abstract class FXMLController extends Parent {

    /**
     * Path to the FXML definiton this controller commands.
     */
    private String FXML = "";

    /**
     *
     */
    public AnchorPane anchorPane;

    /**
     *
     */
    public FXMLLoader loader;

    /**
     * FXML controller of the jfx scene to which this is a child.
     */

    public FXMLController parentController;

    /**
     * Default constructor, called when created by JavaFX.
     * @apiNote if creating manually use a populated constructor.
     */
    public FXMLController(){
        this("", null);
    }

    /**
     * Creates a new FXML controller.
     * @param _FXML path to the FXML scene this controller commands.
     */
    public FXMLController (String _FXML){
        this(_FXML, null);
    }

    /**
     * Creates a new FXML controller.
     * @param _FXML path to the FXML scene this controller commands.
     */
    public FXMLController (String _FXML, FXMLController _parentController) {
        FXML = _FXML;
        parentController = _parentController;
    }

    /**
     * Creates a new controlled anchorPane from an instance of FXML.
     *
     * - Creates loader
     *      - parses FXML (this.MENU_TILE_FXML)
     *      - creates an instance of controller.menuTile (No argument constructor)
     *      - creates UI elements from parsed FXML,
     *          - injecting elements with matching fx:id's into the controller.
     *      - registers event handlers
     *      - invokes initialise on the controller
     *      - returns UI hierarchy of the tile.
     * - Gets controller
     * - Sets item of representation
     *      - Updates UI elements accordingly
     * - returns resulting instance.
     *
     * @return A new instance of AnchorPane, following the FXML design for a menu tile that represents the provided item.
     */
    public FXMLController _clone() throws IOException {
        return create(FXML, parentController);
    }

    /**
     * Creates a new controlled anchorPane from an instance of FXML.
     *
     * - Creates loader
     *      - parses FXML (this.MENU_TILE_FXML)
     *      - creates an instance of controller.menuTile (No argument constructor)
     *      - creates UI elements from parsed FXML,
     *          - injecting elements with matching fx:id's into the controller.
     *      - registers event handlers
     *      - invokes initialise on the controller
     *      - returns UI hierarchy of the tile.
     * - Gets controller
     * - Sets item of representation
     *      - Updates UI elements accordingly
     * - returns resulting instance.
     *
     * @return A new instance of AnchorPane, following the FXML design for a menu tile that represents the provided item.
     * @throws IOException
     */
    public static FXMLController create(String FXML, FXMLController _parentController) throws IOException {
        FXMLLoader loader = new FXMLLoader(FXMLController.class.getResource("." + FXML));                                           // Create a loader with the FXML reference.
                                                                                                                        // I need to create an instance of this JUST to get the controller of the new pane. This should be statically available.
        AnchorPane ap = loader.load();                                                                                  // parse FXML to create AP. This must be done before getting the controller, otherwise this could be collapsed to contoller.ap = loader.getcontroller

        FXMLController controller = loader.getController();                                                             // Get controller to set the item the pane represents.
        controller.anchorPane = ap;                                                                                     // store the anchor pane and controller for future reference.
        controller.parentController = _parentController;

        return controller;                                                                                              // Return newly created menu tile.
    }

    private static FXMLController create(FXMLController controller) throws IOException {
        return null;
    }
}
