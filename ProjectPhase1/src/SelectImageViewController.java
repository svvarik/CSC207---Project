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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This SelectImageViewController class manages and defines all the application's actions in the second scene.
 *
 * The second scene occurs when an image file has been selected and the user is able to perform different operations
 * including adding/removing tags, changing the directory and so on.
 */
public class SelectImageViewController {

    /** The following variables declare the annotation '@FXML', which means they use an FXMLLoader to read values
     of Button/TextField/ImageView objects from the associated .fxml file
     **/

    // Displays a list view of all tags used for the current image
    @FXML public ListView<Tag> allTagsUsed;

    // Displays a list view of all the tags used in the current image's file name
    @FXML public ListView<Tag> allTagsForCurrPic;

    // Takes in keyboard input from the user, when user adds a tag
    @FXML TextField userInputtedTag;

    // An action to add a tag to an image
    @FXML Button addTag;

    // An action to remove a tag from an image
    @FXML Button removeTag;

    // An action to close a menu
    @FXML public MenuItem close;

    // Displays a preview of the current image
    @FXML ImageView imageToBeTagged;

    // An action to move to the previous scene
    @FXML Button backButton;

    // An action to change the directory of the current image
    @FXML Button changeLocation;

    // An action to open a File System Viewer to view the directory where the current image is located
    @FXML Button openEnclosingFolder;

    // An action to change the file name to one of its previous names, with the set of tags
    @FXML Button changeToPastTags;

    // Data from previous screen
    List<String> prevScreenList;

    // represents the string filepath for an image
    String selectedImagePath;

    // An ImageManager to keep track of changes to image files
    static final ImageManager imageManager = new ImageManager();

    // A TagManager to keep track of changes with tags
    static final TagManager tagManager = new TagManager();

    // Tracks the number of windows to the user's computer's FileSystemViewer open in the current scene
    private static int numWindowsOpen = 0;

    /**
     * Set the ImageView object on this screen to display an image given a
     * string file path to the desired image.
     *
     * @param imagePath A string that is the file path for image that is to be
     *                  displayed.
     */
    void initImagePath(String imagePath) {

        //currentImagePath = imagePath;
        this.selectedImagePath = imagePath;
        File f = new File(imagePath);
        Image imageNeedsToBeTagged = new Image(f.toURI().toString());
        imageToBeTagged.setImage(imageNeedsToBeTagged);

        FileManager fm = new FileManager(FileManager.currentDirectory);
        imageManager.setCurrentImage(imageManager.findImage(imagePath));
        imageManager.getCurrentImage().addObserver(fm);

        allTagsForCurrPic.setItems(imageManager.getCurrentImage().tags);

        allTagsUsed.setItems(tagManager.getAllTags());
        allTagsUsed.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Adds a tag to the current image being modified on the screen.
     */
    public void enterTagAction(){
        if (!userInputtedTag.getText().trim().isEmpty()) {
            String addedTag = userInputtedTag.getText();
            imageManager.getCurrentImage().addTag(addedTag);

            System.out.println(tagManager.getAllTags());
            userInputtedTag.clear();
        }
        if (!allTagsUsed.getSelectionModel().getSelectedItems().isEmpty()) {
            Tag selectedTag = allTagsUsed.getSelectionModel().getSelectedItem();
            if (!imageManager.getCurrentImage().hasTag(selectedTag.toString())) {
                imageManager.getCurrentImage().addTag(selectedTag.toString());
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
        if(tagToRemove != null) {
            imageManager.getCurrentImage().removeImageTag(tagToRemove);
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
        Parent selectDirectoryLoad = loader.load();

        SelectDirectoryController controller = loader.getController();

        controller.initRetrievingListView();

        Scene selectDirectoryScene = new Scene(selectDirectoryLoad);

        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());

        window.setScene(selectDirectoryScene);
        window.show();
    }


    /**
     * Exits the application upon the user clicking close in the Menu bar.
     *
     * @param event Event when the user clicks the "close" menu option.
     */
    public void handleMenuClose(ActionEvent event) {
        //TODO Figure out how to close ;
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
            if(newPath != null) {
                String path = newPath.getAbsolutePath() + "/" + imageManager.getCurrentImage().getTaggedName();
                imageManager.getCurrentImage().rename(path);
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
            File sourcePath = new File(imageManager.getCurrentImage().getFilePath());
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
        controller.initImageFile(imageManager.findImage(this.selectedImagePath));

        Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());

        window.setScene(allTagsViewScene);
        window.show();
    }

}

