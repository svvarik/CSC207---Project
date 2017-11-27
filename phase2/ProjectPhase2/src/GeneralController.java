import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

abstract public class GeneralController {


    /**
     * This method opens a new window which allows the user to view all renaming
     * history.
     */
    @FXML void openHistoryWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LogView.fxml"));
        Parent root = loader.load();
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

