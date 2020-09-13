import controller.pos;
import fxml.Main;
import io.MenuHelper;
import network.Client;
import util.RuntimeHelper;

import java.io.IOException;

public class main extends Main<pos> {

    public main() throws IOException {
        super(new pos());
    }

    /**
     * Events / logic to be executed before attempting to startup.
     */
    @Override
    protected void preLoad() {
        RuntimeHelper.log(this, "[PRELOAD] Validating startup configuration..");
        if (!Client.assertConnection() || !loadMenu()) return;                                                                 // Before loading UI, check that a server is available then fetch the menu from it.
        RuntimeHelper.log(this, "[PRELOAD] Able to start, commence!");
    }

    /**
     * events to be triggered after jfx fxml ux has loaded.
     */
    @Override
    protected void postLoad() {
        controller.renderMenu();
        controller.setOrderFlash();
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

    /**
     * Expected runtime entry point.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
