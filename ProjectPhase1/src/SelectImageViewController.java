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
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectImageViewController {

    @FXML
    public ListView<String> allTagsUsed;

    @FXML
    public ListView<String> allTagsForCurrPic;

    @FXML
    TextField userInputtedTag;

    @FXML
    Button addTag;

    @FXML
    Button removeTag;

    @FXML
    public MenuItem close;

    @FXML ImageView imageToBeTagged;

    void initImagePath(String imagePath) {

        File f = new File(imagePath);
        Image imageNeedsToBeTagged = new Image(f.toURI().toString());
        imageToBeTagged.setImage(imageNeedsToBeTagged);

        ImageManager.currentImage = ImageManager.findImage(imagePath, f.getName());

        ArrayList<Tag> imageTags = ImageManager.currentImage.getTags();
        for (Tag t : imageTags){
            allTagsForCurrPic.getItems().add(t.getTag());
        }

        allTagsUsed.setItems(TagManager.allTheTags);
    }

    public void enterTagAction(){
        String addedTag = userInputtedTag.getText();
//        if (!TagManager.allTheTags.contains(addedTag)){
//            allTagsUsed.getItems().add(addedTag);
//        }
        if (!ImageManager.currentImage.hasTag(addedTag)){
            allTagsForCurrPic.getItems().add(addedTag);
            (ImageManager.currentImage).addTag(addedTag);
        }
        System.out.println(TagManager.tagsUsed);
        userInputtedTag.clear();
    }

    public void handleMenuClose(ActionEvent event) {
        Platform.exit();
    }

}

