import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectImageViewController {

    @FXML public ListView<Tag> allTagsUsed;
    @FXML public ListView<Tag> allTagsForCurrPic;
    @FXML TextField userInputtedTag;
    @FXML Button addTag;
    @FXML Button removeTag;
    @FXML public MenuItem close;
    @FXML ImageView imageToBeTagged;
    @FXML Button backButton;

    // Data from previous screen
    List<String> prevScreenList;

    // Current Path for image we are viewing
    private String currentImagePath;

    /**
     * Set the ImageView object on this screen to display an image given a
     * string file path to the desired image.
     *
     * @param imagePath A string that is the file path for image that is to be
     *                  displayed.
     */
    void initImagePath(String imagePath) {

        currentImagePath = imagePath;
        File f = new File(imagePath);
        Image imageNeedsToBeTagged = new Image(f.toURI().toString());
        imageToBeTagged.setImage(imageNeedsToBeTagged);

        ImageManager.currentImage = ImageManager.findImage(imagePath, f.getName());

        allTagsForCurrPic.setItems(ImageManager.currentImage.tags);

        allTagsUsed.setItems(TagManager.allTags);
        allTagsUsed.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    // TODO: ADD JAVADOC, QUICK DESCRIP: TAKES IN A LISTVIEW AND STORES EACH
    // TODO: EACH OBJECT AS A LIST ITEM, SO WE CAN SEND IT BACK IF WE GO BACK
    void initPrevListView(ListView<String> list) {
        List<String> convertedList;
        convertedList = list.getItems();
        prevScreenList = convertedList;
    }

    /**
     * Add a tag to the current image being modified in the screen.
     *
     */
    public void enterTagAction(){
        if (!userInputtedTag.getText().trim().isEmpty()) {
            String addedTag = userInputtedTag.getText();
            ImageManager.currentImage.addTag(addedTag);

            System.out.println(TagManager.allTags);
            userInputtedTag.clear();
        }
        if (!allTagsUsed.getSelectionModel().getSelectedItems().isEmpty()) {
            Tag selectedTag = allTagsUsed.getSelectionModel().getSelectedItem();
            if (!ImageManager.currentImage.hasTag(selectedTag.toString())){
                allTagsForCurrPic.getItems().add(selectedTag);
                ImageManager.currentImage.addTag(selectedTag.toString());
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

        // Access tags list for current ImageFile
        File f = new File(currentImagePath);
        ImageManager.currentImage = ImageManager.findImage(currentImagePath, f.getName());
        ImageManager.currentImage.removeImageTag(new Tag(tagToRemove.toString()));
        allTagsForCurrPic.getItems().remove(tagToRemove);
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

        controller.initRetrievingListView(prevScreenList);

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
}

