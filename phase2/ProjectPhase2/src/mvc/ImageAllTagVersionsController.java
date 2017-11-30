package mvc;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ImageAllTagVersionsController extends GeneralController {

    @FXML
    ListView<String> imageNames;
    @FXML Button changeTags;
    @FXML Button backButton;

    /**
     * This method sets up the controller prior to switching to this scene, and
     * sets up any needed UI elements.
     */
    void setUpController(){
        this.imageNames.setItems(FXCollections.observableList(this.tagITModel.getCurrentImage().getImageHistory()));
    }

    /**
     * This method reverts the ImageFile's name & Tags to a previous version
     * selected in the ListView.
     */
    public void listViewSelected() {
        String changeToName = this.imageNames.getSelectionModel().getSelectedItem();
        this.tagITModel.getCurrentImage().revertToOlderTags(changeToName, this.tagITModel.getTagManager());
    }

    /**
     * This method allows the user to go back to the previous screen.
     *
     * @param event The event when the user clicks the back button.
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException{
        ControllerHelper controllerHelper = new ControllerHelper();
        SelectImageViewController controller = new SelectImageViewController();
        String path = this.tagITModel.getCurrentImage().getFilePath();
        controllerHelper.openSameWindow(controller, "SelectImage.fxml", this.tagITModel, event, path);
    }

    void setUpController(Object object){}
}
