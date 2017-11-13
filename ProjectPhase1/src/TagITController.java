import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TagITController implements Initializable {

    @FXML MenuItem close;

    @FXML
    public Button selectDirectory;

    @FXML
    public ListView<String> dirImages;

    @FXML
    public Button submit;

    @FXML
    public javafx.scene.image.ImageView imageView;

    @FXML
    public Button selectImage;

    @FXML
    public ListView<String> currentTags;

    @FXML
    public ListView<String> allTags;

    @FXML
    TextField tagInput;

    @FXML
    Button tagImage;

    @FXML
    public javafx.scene.image.ImageView imageToTag;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void selectDirAction(ActionEvent event) {
        dirImages.getItems().clear();
        DirectoryChooser fl = new DirectoryChooser();
        File dir = fl.showDialog(null);

        if (dir != null) {
            File[] imageList = FileManager.imageFilesFilter(dir);
            for (File f : imageList) {
                dirImages.getItems().add(f.getAbsolutePath());
            }
        }
        dirImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void previewImageAction(ActionEvent event) {
        currentTags.getItems().clear();
        String imagePath = dirImages.getSelectionModel().getSelectedItem();
        FileManager.selectedImage = imagePath;
        File f = new File(imagePath);
        if (f.isDirectory()) {
            // say something like "Please select an image."
        } else {
            Image image = new Image(f.toURI().toString());
            ImageFile thisImage= ImageManager.findImage(imagePath, f.getName());
            for (Tag t : TagManager.tagsUsed){
                allTags.getItems().add(t.getTag());
            }
            ImageManager.currentImage = thisImage;
            ArrayList<Tag> imageTags = ImageManager.currentImage.getTags();
            for (Tag t : imageTags){
                currentTags.getItems().add(t.getTag());
            }
            currentTags.setOrientation(Orientation.HORIZONTAL);
            imageView.setImage(image);
        }
    }

    @FXML
    public void selectImageAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SelectImage.fxml"));
        Parent selectImageLoad = loader.load();

        Scene selectImageScene = new Scene(selectImageLoad);

        SelectImageViewController controller = loader.getController();
        controller.initImagePath(dirImages.getSelectionModel().getSelectedItem());

        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());

        window.setScene(selectImageScene);
        window.show();
        }


//    @FXML
//    private void handleSelectImageAction(ActionEvent event) throws IOException{
////        fileManager.selectedImage = dirImages.getSelectionModel().getSelectedItem();
////        File f = new File(fileManager.selectedImage);
////        Image image = new Image(f.toURI().toString());
////        imageToTag.setImage(image);
//        Stage stage;
//        Parent root;
//        //get reference to the button's stage
//        stage = (Stage) selectImage.getScene().getWindow();
//        //load up OTHER FXML document
//        root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
//        //create a new scene with root and set the stage
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

    public void enterTagAction(){
        String addedTag = tagInput.getText();
        if (!TagManager.tagsUsed.contains(addedTag)){
            allTags.getItems().add(addedTag);
        }
        if (!ImageManager.currentImage.getTags().contains(addedTag)){
            currentTags.getItems().add(addedTag);
            (ImageManager.currentImage).addTag(addedTag);
        }
        tagInput.clear();
    }

    public void handleMenuClose(ActionEvent event){
        Platform.exit();
    }
}
        // button listener- when the button is clicked, update screen
        // configuration file

    // Go to photo. Start new scene- create imageFile in this step- associate with path?
    // Tags...


