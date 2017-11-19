import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class ImageAllTagVersionsController {

    private ImageFile selectedImage;
    @FXML private ListView imageNames;


    /**
     * This method accepts an ImageFile to intialize the view.
     * @param image
     */
    public void initImageFile(ImageFile image) {
        this.selectedImage = image;
        this.imageNames.getItems().addAll(this.selectedImage.getTags());


    }

}
