import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectDirectoryController {

    @FXML
    MenuItem close;
    @FXML
    public Button selectDirectory;
    @FXML
    public TreeView<String> listOfImages;
    @FXML
    public javafx.scene.image.ImageView imagePreview;
    @FXML
    public Button selectImage;
    @FXML
    public Button backButtonDirectory;
    private static int numWindowsOpen = 0;
    //To Fix: Don't hardcord numWindowsOpen.

    private TagITModel tagITModel;

    void initModel(TagITModel model) {
        this.tagITModel = model;
    }

    /**
     * Function opens a window that allows the user to choose a Directory and
     * open it in the program. The application then displays all image files in
     * it along with directories.
     *
     * @param event When the button is clicked on the SelectDirectory button.
     */
    @FXML
    public void selectDirectoryAction(ActionEvent event) {
        numWindowsOpen++;
        if (numWindowsOpen <= 1) {
            DirectoryChooser dirChoose = new DirectoryChooser();
            File openedDirectory = dirChoose.showDialog(null);
            if (openedDirectory != null) {
                this.tagITModel.setCurrentDirectory(openedDirectory.getAbsolutePath());
                listOfImages.setRoot(getChildrenDirectory(openedDirectory.getAbsolutePath()));
            }
            listOfImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            numWindowsOpen = 0;
        }
    }

    private TreeItem<String> getChildrenDirectory(String directory) {
        TreeItem<String> rootDirectory = new TreeItem<String>(directory);
        for (File item : FileManager.imageFilesFilter(new File(directory))) {
            if (item.isDirectory()) {
                rootDirectory.getChildren().add(getChildrenDirectory(item.getAbsolutePath()));
            } else {
                rootDirectory.getChildren().add(new TreeItem<String>(item.getAbsolutePath()));
                if (item.getName().contains("@")) {
                    int end = item.getName().lastIndexOf(".");
                    int start = item.getName().indexOf("@");
                    String[] tagsInFileName = item.getName().substring(start + 1, end).split("@");
                    for (String tag : tagsInFileName) {
                        new Tag("@" + tag, this.tagITModel.getTagManager());
                    }
                }
            }
        }
        return rootDirectory;
    }

    /** Moves to the next screen where the user can add / remove Tags for the
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
        if (!(listOfImages.getRoot().isLeaf())) {
            controller.initImagePath(listOfImages.getSelectionModel().getSelectedItem().getValue(), this.tagITModel);

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

        @FXML
        public void displayPreviewImage (MouseEvent event) throws IOException {
            if (!(listOfImages.getRoot() == null || listOfImages.getSelectionModel().getSelectedItem() == null)) {
                File filePath = new File(listOfImages.getSelectionModel().getSelectedItem().getValue());

                if (!filePath.isDirectory()) {
                    Image preview = new Image(filePath.toURI().toString());
                    imagePreview.setImage(preview);
                }
            }
        }

    public void goBackDirectory(ActionEvent event){
        String parentDirectory = FileManager.getParentDirectory(this.tagITModel.getCurrentDirectory());
        if (this.tagITModel.getCurrentDirectory()!= null && parentDirectory != null){
            this.tagITModel.setCurrentDirectory(parentDirectory);
            this.listOfImages.setRoot(getChildrenDirectory(tagITModel.getCurrentDirectory()));
        }
        listOfImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }

    void initRetrievingTreeView() {
        //listOfImages.setItems(this.tagITModel.getCurrentDirectoryFiles());
        listOfImages.setRoot(getChildrenDirectory(tagITModel.getCurrentDirectory()));
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