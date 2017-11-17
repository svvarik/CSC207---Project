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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.StandardCopyOption;
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
    private static int num_windows_open = 0;

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

        FileManager fm = new FileManager(FileManager.currentDirectory);
        ImageManager.currentImage = ImageManager.findImage(imagePath, f.getName());
        ImageManager.currentImage.addObserver(fm);

        allTagsForCurrPic.setItems(ImageManager.currentImage.tags);

        allTagsUsed.setItems(TagManager.allTags);
        allTagsUsed.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
            if (!ImageManager.currentImage.hasTag(selectedTag.toString())) {
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

        // get current directory of the image

        String[] words = currentImagePath.split("/");
        String s = "";
        for(int i = 0; i < words.length - 1; i++) {
            s += "/";
            s += words[i];
        }

        System.out.println(s);

        FileManager.updateCurrentDirectory(s);

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
        num_windows_open++;
        if (num_windows_open <= 1) {

            File sourcePath = new File(currentImagePath);

            DirectoryChooser dirChoose = new DirectoryChooser();
            File newPath = dirChoose.showDialog(null);
            Path nextPath = Paths.get(newPath.getAbsolutePath());
            String s = nextPath.toAbsolutePath().toString();

            String[] words = currentImagePath.split("/");
            s += "/";
            s += words[words.length - 1];

            //FileManager.updateCurrentDirectory(newPath.getAbsolutePath());
            //FileManager.imageFilesFilter()
            File destinationPath = new File(s);
            currentImagePath = s;

            System.out.println("Next relative path is: " + s);

            try {
                Files.copy(sourcePath.toPath(), destinationPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copy successful!");
                if(sourcePath.delete()) {
                    System.out.println(sourcePath + " got deleted");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            //num_windows_open = 0;


        }
    }

    public void openFolder(ActionEvent actionEvent) {
        num_windows_open++;
        if (num_windows_open <= 1) {

            File sourcePath = new File(currentImagePath);

            DirectoryChooser dirChoose = new DirectoryChooser();
            sourcePath = dirChoose.showDialog(null);
            num_windows_open = 0;

        }

    }
}

