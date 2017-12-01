package mvc;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import java.io.IOException;

abstract public class GeneralController {

    protected TagITModel tagITModel;
    @FXML protected MenuItem closeButton;
    @FXML protected MenuItem viewHistoryButton;
    @FXML protected MenuItem manageAllTagsButton;
    @FXML protected MenuItem helpButton;
    @FXML protected MenuItem aboutButton;

    void initController(TagITModel model) {
        this.tagITModel = model;
    }

    /**
     * This method opens a new window which allows the user to view all renaming
     * history.
     */
    @FXML void openHistoryWindow() throws IOException {
        ControllerHelper controllerHelper = new ControllerHelper();
        LogViewController controller = new LogViewController();
        controllerHelper.openNewWindow(controller, "LogView.fxml",
                this.tagITModel, "TagIT History");
    }

    /**
     * This method opens a new window which allows the user to manage all the
     * Tags they currently have stored.
     */
    @FXML void openManageTagWindow() {
        ControllerHelper controllerHelper = new ControllerHelper();
        ManageTagsController controller = new ManageTagsController();
        controllerHelper.openNewWindow(controller, "ManageTags.fxml", this.tagITModel, "Manage Tags", 500, 350);
    }

    @FXML void openHelp(){
        ControllerHelper controllerHelper = new ControllerHelper();
        HelpController controller = new HelpController();
        controllerHelper.openNewWindow(controller, "Help.fxml", this.tagITModel, "Help", 500, 350);
    }

    @FXML void openAbout(){
        ControllerHelper controllerHelper = new ControllerHelper();
        AboutController controller = new AboutController();
        controllerHelper.openNewWindow(controller,"About.fxml", this.tagITModel, "About TagIT", 600, 400);
    }

    /**
     * This method closes the window.
     */
    @FXML void closeWindow() {
        Platform.exit();
    }

    /**
     * This method sets up the controller prior to switching scenes. It takes in
     * an object
     * @param object
     */
    abstract void setUpController(Object object);

    abstract void setUpController();
}

