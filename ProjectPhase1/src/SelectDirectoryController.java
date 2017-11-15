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
        DirectoryChooser dirChoose = new DirectoryChooser();
        File openedDirectory = dirChoose.showDialog(null);
        
        if (openedDirectory != null) {
            File[] imageList = FileManager.imageFilesFilter(openedDirectory);
            for (File f : imageList) {
                listOfImages.getItems().add(f.getAbsolutePath());
            }
        }
        listOfImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    
    /**
     * Moves to the next screen where the user can add / remove Tags for the
     * selected image.
     *
     * @param event Event when the "Select Image" button is clicked.
     * @throws IOException
     */
    
    @FXML public void selectImageAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SelectImage.fxml"));
        Parent selectImageLoad = loader.load();
        
        Scene selectImageScene = new Scene(selectImageLoad);
        
        SelectImageViewController controller = loader.getController();
        if(!(listOfImages.getItems().isEmpty())) {

            // Send listView over to SelectImageViewController
            controller.initPrevListView(listOfImages);

            controller.initImagePath(listOfImages.getSelectionModel().getSelectedItem());
            
            Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
            
            window.setScene(selectImageScene);
            window.show();
        }
    }
    
    /**
     * Displays a preview of the image when the user clicks on it in the
     * listView.
     *
     * @param event Event when the user clicks on an item in the listView.
     * @throws IOException
     */
    @FXML public void displayPreviewImage(MouseEvent event) throws IOException {
        if (!listOfImages.getSelectionModel().getSelectedItem().isEmpty()) {
            File filePath = new File(listOfImages.getSelectionModel().getSelectedItem());
            if (filePath.isDirectory()) {
                listOfImages.getItems().clear();
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



    // TODO: WRITE JAVADOC AFTER, QUICK DESCRIP: TAKES IN LIST FROM OTHER
    // TODO: CONTROLLER AND POPULATES THE LISTVIEW HERE WITH ITS ELEMENTS
    void initRetrievingListView(List<String> list){
        for(String i: list){
            listOfImages.getItems().add(i);
        }
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
// button listener- when the button is clicked, update screen
// configuration file

// Go to photo. Start new scene- create imageFile in this step- associate with path?
// Tags..