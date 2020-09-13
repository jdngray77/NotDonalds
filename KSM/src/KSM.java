import fxml.Main;
import network.Client;

/**
 * Main entry point class for a kitchen screen monitor window.
 *
 * @author gordie
 * @version 1
 */
public class KSM extends Main<controller.KSM> {

    public KSM() {
        super(new controller.KSM());
    }

    @Override
    protected void preLoad() {
        if (!Client.assertConnection()) return;                                                                         // Check for a connection to the server.
    }

    @Override
    protected void postLoad() {

    }

    /**
     * Expected runtime entry point.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
