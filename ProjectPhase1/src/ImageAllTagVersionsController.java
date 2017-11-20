import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


public class ImageAllTagVersionsController {

    private ImageFile selectedImage;
    @FXML private ListView imageNames;
    @FXML private Button changeTags;
    @FXML private Button backButton;


    /**
     * This method accepts an ImageFile to intialize the view.
     * @param image
     */
    public void initImageFile(ImageFile image) {
        this.selectedImage = image;
        this.imageNames.setItems(FXCollections.observableList(image.getImageHistory()));
    }

    public void listViewSelected(ActionEvent event) {
        String changeToName = (String) this.imageNames.getSelectionModel().getSelectedItem();
        this.selectedImage.revertToOlderTags(changeToName);
    }

    public void goBack(ActionEvent event){

    }


}
