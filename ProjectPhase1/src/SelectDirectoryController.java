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
import java.util.List;
import java.util.ResourceBundle;

public class SelectDirectoryController implements Initializable {
    
    @FXML MenuItem close;
    @FXML public Button selectDirectory;
    @FXML public ListView<String> listOfImages;
    @FXML public javafx.scene.image.ImageView imagePreview;
    @FXML public Button selectImage;
    @FXML public Button backButtonDirectory;
    private static int num_windows_open = 0;
    private TagITModel tagITModel;

    public SelectDirectoryController(TagITModel model){
        this.tagITModel = model;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    /**
     * Function opens a window that allows the user to choose a Directory and
     * open it in the program. The application then displays all image files in
     * it along with directories.
     *
     * @param event When the button is clicked on the SelectDirectory button.
     */
    @FXML public void selectDirectoryAction(ActionEvent event) {
        listOfImages.getItems().clear();
        num_windows_open++;
        if (num_windows_open <= 1) {
            DirectoryChooser dirChoose = new DirectoryChooser();
            File openedDirectory = dirChoose.showDialog(null);

            if (openedDirectory != null) {
                this.tagITModel.setCurrentDirectory(openedDirectory.getAbsolutePath());

                listOfImages.setItems(this.tagITModel.getCurrentDirectoryFiles());
            }
            listOfImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            num_windows_open = 0;

        }
    }
    
    /**
     * Moves to the next screen where the user can add / remove Tags for the
     * selected image.
     *
     * @param event Event when the "Select Image" button is clicked.
     * @throws IOException
     */
    
    @FXML public void selectImageAction (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SelectImage.fxml"));
        Parent selectImageLoad = loader.load();

        Scene selectImageScene = new Scene(selectImageLoad);

        SelectImageViewController controller = loader.getController();
        if (!(listOfImages.getItems().isEmpty())) {

            controller.initImagePath(listOfImages.getSelectionModel().getSelectedItem(), this.tagITModel);


            Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());

            window.setScene(selectImageScene);
            window.show();

        }
    }
    
    /**
     * Displays a preview of the image when the user clicks on it in the
     * listview.
     *
     * @param event Event when the user clicks on an item in the listview.
     * @throws IOException
     */
    @FXML public void displayPreviewImage (MouseEvent event) throws IOException {
        if (!(listOfImages.getItems().isEmpty() || listOfImages.getSelectionModel().getSelectedItem() == null)){
            File filePath = new File(listOfImages.getSelectionModel().getSelectedItem());
            if (filePath.isDirectory()) {
                listOfImages.getItems().clear();
                this.tagITModel.setCurrentDirectory(filePath.getAbsolutePath());
                File[] imageList = FileManager.imageFilesFilter(filePath);
                for (File f : imageList) {
                    listOfImages.getItems().add(f.getAbsolutePath());
                }
            } else {
                Image preview = new Image(filePath.toURI().toString());
                imagePreview.setImage(preview);
            }
        }
    }

    public void goBackDirectory(ActionEvent event) {
        listOfImages.getItems().clear();
        String parentDirectory = FileManager.getParentDirectory(this.tagITModel.getCurrentDirectory());
        if (this.tagITModel.getCurrentDirectory() != null && parentDirectory != null) {
            this.tagITModel.setCurrentDirectory(parentDirectory);
            listOfImages.setItems(this.tagITModel.getCurrentDirectoryFiles());
        }
        listOfImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    void initRetrievingListView(){
        listOfImages.setItems(this.tagITModel.getCurrentDirectoryFiles());
    }
    
    /**
     * Exits the application upon the user clicking close in the Menu bar.
     *
     * @param event Event when the user clicks the "close" menu option.
     */
    @FXML public void handleMenuClose(ActionEvent event){
        Platform.exit();
    }
}