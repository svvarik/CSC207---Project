import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;


public class ImageAllTagVersionsController {

    private TagITModel tagITModel;
    @FXML MenuItem close;
    @FXML ListView imageNames;
    @FXML Button changeTags;
    @FXML Button backButton;


    /**
     * This method initializes the controller.
     *
     * @param tagITModel The model
     */
    public void initImageFile(TagITModel tagITModel) {
        this.tagITModel = tagITModel;
        this.imageNames.setItems(FXCollections.observableList(this.tagITModel.getCurrentImage().getImageHistory()));
    }

    public void listViewSelected(ActionEvent event) {
        String changeToName = (String) this.imageNames.getSelectionModel().getSelectedItem();
        this.tagITModel.getCurrentImage().revertToOlderTags(changeToName, this.tagITModel.getTagManager());
    }

    public void goBack(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SelectImage.fxml"));
        Parent allTagsViewParent = loader.load();
        Scene allTagsViewScene = new Scene(allTagsViewParent);

        // Access the controller and call a method.
        SelectImageViewController controller = loader.getController();
        controller.initImagePath(this.tagITModel.getCurrentImage().getFilePath());

        Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());

        window.setScene(allTagsViewScene);
        window.show();
    }

    @FXML public void handleMenuClose(ActionEvent event){
        Platform.exit();
    }

}
