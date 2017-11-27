import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This SelectImageViewController class manages and defines all the
 * application's actions in the second scene.
 * <p>
 * The second scene occurs when an image file has been selected and the user
 * is able to perform different operations
 * including adding/removing tags, changing the directory and so on.
 */
public class SelectImageViewController {

    /**
     * The following variables declare the annotation '@FXML', which means they
     * use an FXMLLoader to read values
     * of Button/TextField/ImageView objects from the associated .fxml file
     **/

    /** Displays a list view of all tags used*/
    @FXML public ListView<Tag> allTagsUsed;

    /** Displays a list view of all the tags used in the current image's file name */
    @FXML public ListView<Tag> allTagsForCurrPic;

    /** Takes in keyboard input from the user, when user adds a tag */
    @FXML TextField userInputtedTag;

    /** An action to add a tag to an image */
    @FXML Button addTag;

    /** An action to remove a tag from an image */
    @FXML Button removeTag;

    /** An action to close a menu */
    @FXML public MenuItem close;

    /** Displays a preview of the current image */
    @FXML ImageView imageToBeTagged;

    /** An action to move to the previous scene */
    @FXML Button backButton;

    /** An action to change the directory of the current image */
    @FXML Button changeLocation;

    @FXML Button grayScale;

    /**
     * An action to open a File System Viewer to view the directory where the
     * current image is located
     */
    @FXML Button openEnclosingFolder;

    /**
     * An action to change the file name to one of its previous names,
     * with the set of tags
     * */
    @FXML Button changeToPastTags;

    /** The Model of the program */
    private TagITModel tagITModel;

    /**
     * Tracks the number of windows to the user's computer's FileSystemViewer
     * open in the current scene
     * */
    private static int numWindowsOpen = 0;

    /**
     * Set the ImageView object on this screen to display an image given a
     * string file path to the desired image.
     *
     * @param imagePath A string that is the file path for image that is to be
     *                  displayed.
     */
    void initImagePath(String imagePath, TagITModel model) {
        File f = new File(imagePath);
        Image imageNeedsToBeTagged = new Image(f.toURI().toString());
        imageToBeTagged.setImage(imageNeedsToBeTagged);

        this.tagITModel = model;

        this.tagITModel.setCurrentImage(this.tagITModel.getImageManager().findImage(imagePath));
        this.tagITModel.getCurrentImage().addObserver(tagITModel);

        this.allTagsForCurrPic.setItems(this.tagITModel.getCurrentImage().getTags());

        this.allTagsUsed.setItems(this.tagITModel.getTagManager().getAllTags());
        this.allTagsUsed.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    /**
     * Adds a tag to the current image being modified on the screen.
     */
    public void enterTagAction() {
        if (!userInputtedTag.getText().trim().isEmpty()) {
            String addedTag = userInputtedTag.getText();
            this.tagITModel.getCurrentImage().addTag(addedTag, this.tagITModel.getTagManager());
            userInputtedTag.clear();
        }
        if (!allTagsUsed.getSelectionModel().getSelectedItems().isEmpty()) {
            ObservableList<Tag> selectedTags = allTagsUsed.getSelectionModel().getSelectedItems();
            for (Tag selectedTag : selectedTags) {
                if (!this.tagITModel.getCurrentImage().hasTag(selectedTag.toString())) {
                    this.tagITModel.getCurrentImage().addTag(selectedTag.toString(), this.tagITModel.getTagManager());
                }
            }
            allTagsUsed.getSelectionModel().clearSelection();
        }
    }

    /**
     * Remove a tag from the current Image.
     *
     * @param event An event when the Remove Tag button is clicked.
     */
    public void removeTagAction(ActionEvent event) {
        // Get the tag from the listview
        Tag tagToRemove = allTagsForCurrPic.getSelectionModel().getSelectedItem();
        if (tagToRemove != null) {
            this.tagITModel.getCurrentImage().removeImageTag(tagToRemove);
        }
    }

    /**
     * This method allows a user to return to the previous screen when the button
     * is clicked.
     *
     * @param event An event when the Back button is clicked.
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SelectDirectory.fxml"));
        Parent selectDirectoryView = loader.load();

        Scene selectDirectoryScene = new Scene(selectDirectoryView);

        // Access the controller and call a method.
        SelectDirectoryController controller = loader.getController();
        controller.initModel(this.tagITModel);
        controller.initRetrievingTreeView();

        Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
        window.setScene(selectDirectoryScene);
        window.show();
    }


    /**
     * Exits the application upon the user clicking close in the Menu bar.
     *
     * @param event Event when the user clicks the "close" menu option.
     */
    public void handleMenuClose(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Changes the directory of the current image to the newly selected directory
     *
     * @param actionEvent The event when Change Image Location Button is clicked
     */
    public void changeImageLocation(ActionEvent actionEvent) {
        numWindowsOpen++;
        if (numWindowsOpen <= 1) {

            DirectoryChooser dirChoose = new DirectoryChooser();
            File newPath = dirChoose.showDialog(null);
            if (newPath != null) {
                String path = newPath.getAbsolutePath() + "/" + this.tagITModel.getCurrentImage().getTaggedName();
                this.tagITModel.getCurrentImage().rename(path);
            }
            numWindowsOpen = 0;
        }
    }

    /**
     * Opens the directory with the user's FileSystemViewer in which the current image is located in.
     *
     * @param actionEvent The event in which the user wants to open the directory containing the image file
     */
    public void openFolder(ActionEvent actionEvent) {
        numWindowsOpen++;
        if (numWindowsOpen <= 1) {
            File sourcePath = new File(this.tagITModel.getCurrentImage().getFilePath());
            File parentDirectory = sourcePath.getParentFile();
            try {
                Desktop.getDesktop().open(parentDirectory);
            } catch (IOException ignored) {
                //I don't know what to put in the catch block...
            }
            numWindowsOpen = 0;
        }
    }

    /**
     * Moves to a new scene to display a history of previous file names for the current image
     *
     * @param event An event to display older filenames and tags
     * @throws IOException
     */
    public void changeToAllTagsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ImageAllTagVersions.fxml"));
        Parent allTagsViewParent = loader.load();
        Scene allTagsViewScene = new Scene(allTagsViewParent);

        // Access the controller and call a method.
        ImageAllTagVersionsController controller = loader.getController();
        controller.initImageFile(this.tagITModel);
        Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
        window.setScene(allTagsViewScene);
        window.show();
    }

    public void grayScaleImage() throws IOException {
        FilterImage.grayScale(tagITModel.getCurrentImage());
    }
}

