import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

abstract public class GeneralController {
    protected TagITModel tagITModel;
    @FXML protected MenuItem closeButton;
    @FXML protected MenuItem manageAllTagsButton;
    @FXML protected MenuItem viewHistoryButton;
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LogView.fxml"));
        Parent root = loader.load();
        LogViewController controller = loader.getController();
        controller.initController(this.tagITModel);
        Stage stage = new Stage();
        stage.setTitle("Tag History");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * This method opens a new window which allows the user to manage all the
     * Tags they currently have stored.
     */
    void openManageTagWindow() {
    }

    /**
     * This method closes the window.
     */
    @FXML void closeWindow() {
        Platform.exit();
    }
}

