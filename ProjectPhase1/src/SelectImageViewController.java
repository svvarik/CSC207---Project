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

public class SelectImageViewController {

    @FXML public ListView<Tag> allTagsUsed;
    @FXML public ListView<Tag> allTagsForCurrPic;
    @FXML TextField userInputtedTag;
    @FXML Button addTag;
    @FXML Button removeTag;
    @FXML public MenuItem close;
    @FXML ImageView imageToBeTagged;
    @FXML Button backButton;
    @FXML Button changeLocation;
    @FXML Button openEnclosingFolder;

    // Data from previous screen
    List<String> prevScreenList;
    String selectedImagePath;

    static final ImageManager imageManager = new ImageManager();
    static final TagManager tagManager = new TagManager();

    // Current Path for image we are viewing
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
        System.out.println("in here?");

        FileManager fm = new FileManager(FileManager.currentDirectory);
        imageManager.setCurrentImage(imageManager.findImage(imagePath));
        imageManager.getCurrentImage().addObserver(fm);

        allTagsForCurrPic.setItems(imageManager.getCurrentImage().tags);

        allTagsUsed.setItems(tagManager.getAllTags());
        allTagsUsed.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Add a tag to the current image being modified in the screen.
     *
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

    // TODO: Function currently goes back to SelectDirectory Screen but doesn't
    // TODO: go back to the previous "state". If I was viewing a certain directory
    // TODO: that is not there when I hit back.
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

