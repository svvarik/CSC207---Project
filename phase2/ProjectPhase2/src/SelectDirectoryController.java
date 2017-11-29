import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;

public class SelectDirectoryController extends GeneralController {

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

    private int numWindowsOpen;

    /**
     * This method sets up the controller prior to switching to this scene, and
     * sets up any needed UI elements.
     */
    void setUpController() {
        listOfImages.setRoot(getChildrenDirectory(tagITModel.getCurrentDirectory()));
        this.numWindowsOpen = 0;
    }

    void setUpController(Object object) {}

    /** Moves to the next screen where the user can add / remove Tags for the
     * selected image.
     *
     * @param event Event when the "Select Image" button is clicked.
     * @throws IOException
     */
    @FXML public void goToSelectImageScreen(ActionEvent event) throws IOException {
        if (!(listOfImages.getRoot().isLeaf())) {
            String path = listOfImages.getSelectionModel().getSelectedItem().getValue();
            ControllerHelper controllerHelper = new ControllerHelper();
            SelectImageViewController controller = new SelectImageViewController();
            controllerHelper.openSameWindow(controller, "SelectImage.fxml", this.tagITModel, event, path);
        }
    }

    /**
     * Displays a preview of the image when the user clicks on it in the
     * list view.
     *
     * @param event Event when the user clicks on an item in the listview.
     * @throws IOException
     */

    @FXML public void displayPreviewImage (MouseEvent event) throws IOException {
        if (!(listOfImages.getRoot() == null || listOfImages.getSelectionModel().getSelectedItem() == null)) {
            File filePath = new File(listOfImages.getSelectionModel().getSelectedItem().getValue());
            if (!filePath.isDirectory()) {
                Image preview = new Image(filePath.toURI().toString());
                imagePreview.setImage(preview);
            }
        }
    }

    /**
     * Method opens a window that allows the user to choose a Directory and
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

    public void goBackDirectory(ActionEvent event) {
        String parentDirectory = FileManager.getParentDirectory(this.tagITModel.getCurrentDirectory());
        if (this.tagITModel.getCurrentDirectory() != null && parentDirectory != null) {
            this.tagITModel.setCurrentDirectory(parentDirectory);
            this.listOfImages.setRoot(getChildrenDirectory(tagITModel.getCurrentDirectory()));
        }
        listOfImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}