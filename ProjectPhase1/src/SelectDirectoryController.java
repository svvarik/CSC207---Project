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
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectDirectoryController implements Initializable {

    @FXML MenuItem close;
    @FXML public Button selectDirectory;
    @FXML public ListView<String> dirImages;
    @FXML public javafx.scene.image.ImageView imagePreview;
    @FXML public Button selectImage;

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

    @FXML
    public void displayPreviewImage(MouseEvent event) throws IOException {
        File imagePath = new File(dirImages.getSelectionModel().getSelectedItem());
        Image preview = new Image(imagePath.toURI().toString());
        imagePreview.setImage(preview);
    }

    public void handleMenuClose(ActionEvent event){
        Platform.exit();
    }
}
        // button listener- when the button is clicked, update screen
        // configuration file

    // Go to photo. Start new scene- create imageFile in this step- associate with path?
    // Tags...


