package mvc;
import tag.Tag;
import graphics.ImageFilter;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

import java.awt.*;
import java.awt.image.BufferedImage;
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
public class SelectImageViewController extends GeneralController {
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

    /** Displays a preview of the current image */
    @FXML ImageView imageToBeTagged;

    /** An action to move to the previous scene */
    @FXML Button backButton;

    /** An action to change the directory of the current image */
    @FXML Button changeLocation;

    /** Allows the user to apply a Gray Scale filter over an image. */
    @FXML Button grayScale;

    @FXML Button sepia;

    @FXML Button inverse;

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

    @FXML Button defaultColour;

    @FXML Button setFilterButton;

    /**
     * Tracks the number of windows to the user's computer's FileSystemViewer
     * open in the current scene
     * */
    private static int numWindowsOpen = 0;

    /**
     * Set the ImageView object on this screen to display an image given a
     * string file path to the desired image.
     *
     * @param path A string that is the file path for image that is to be
     *                  displayed.
     */
    @Override
    void setUpController(Object path) {
        String imagePath = (String) path;
        File f = new File(imagePath);
        Image imageNeedsToBeTagged = new Image(f.toURI().toString());
        imageToBeTagged.setImage(imageNeedsToBeTagged);
        try {
            this.tagITModel.setCurrentImage(this.tagITModel.getImageManager().findImage(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(parentDirectory);
                }
            } catch (IOException ignored) {
            }

//            try {
//                Desktop.getDesktop().open(parentDirectory);
//            } catch (IOException ignored) {
//                //I don't know what to put in the catch block...
//            }
            numWindowsOpen = 0;
        }
    }


    /**
     * Applies the given Filter to the image displayed in imageToBeTagged.
     * It does not modify the original file the image is stored in.
     *
     * @param event the action of the button being clicked.
     * @throws IOException IOException from creating BufferedImage
     */
    public void filterImage(ActionEvent event) throws IOException{
        Button clickedButton = (Button) event.getSource();
        BufferedImage img = ImageFilter.originalScale(tagITModel.getCurrentImage());

        if (clickedButton.equals(grayScale)) {
            img = ImageFilter.grayScale(tagITModel.getCurrentImage());
        }
        if (clickedButton.equals(defaultColour)) {
            img = ImageFilter.originalScale(tagITModel.getCurrentImage());
        }

        if (clickedButton.equals(sepia)){
            img = ImageFilter.sepia(tagITModel.getCurrentImage());
        }

        if (clickedButton.equals(inverse)){
            img = ImageFilter.inverse(tagITModel.getCurrentImage());
        }
        this.tagITModel.setCurrentImagewithFilter(img);
        Image newImage = SwingFXUtils.toFXImage(img, null);
        imageToBeTagged.setImage(newImage);
    }


    /**
     * Modifies the image being edited to include the filter.
     * The image with the filter replaces the original image.
     */
    public void setFilter() {
        if (this.tagITModel.getCurrentImagewithFilter() != null) {
            ImageFilter.recolour(this.tagITModel.getCurrentImagewithFilter(),
                    this.tagITModel.getCurrentImage().getFilePath());
            this.tagITModel.setCurrentImagewithFilter(null);
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
        ControllerHelper controllerHelper = new ControllerHelper();
        SelectDirectoryController controller = new SelectDirectoryController();
        controllerHelper.openSameWindow(controller, "SelectDirectory.fxml", this.tagITModel, event);
    }

    /**
     * Moves to a new scene to display a history of previous file names for the current image
     *
     * @param event An event to display older filenames and tags
     * @throws IOException
     */
    public void goToAllVersionsView(ActionEvent event) throws IOException {
        ControllerHelper controllerHelper = new ControllerHelper();
        ManageTagsController controller = new ManageTagsController();
        controllerHelper.openSameWindow(controller, "ImageAllTagVersions.fxml", this.tagITModel, event);
    }

    void setUpController(){}
}

