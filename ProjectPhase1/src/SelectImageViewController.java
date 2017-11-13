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

    void initImagePath(String string) {

        File f = new File(string);
        Image imageNeedsToBeTagged = new Image(f.toURI().toString());
        imageToBeTagged.setImage(imageNeedsToBeTagged);
    }

    public void enterTagAction(){
        String addedTag = userInputtedTag.getText();
        if (!TagManager.tagsUsed.contains(addedTag)){
            allTagsUsed.getItems().add(addedTag);
        }
        if (!ImageManager.currentImage.getTags().contains(addedTag)){
            allTagsForCurrPic.getItems().add(addedTag);
            (ImageManager.currentImage).addTag(addedTag);
        }
        userInputtedTag.clear();
    }

    public void handleMenuClose(ActionEvent event) {
        Platform.exit();
    }

}

