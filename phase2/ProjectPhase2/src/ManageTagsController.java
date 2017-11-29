import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ManageTagsController extends GeneralController {

    @FXML ListView allTagsList;
    @FXML Button addTag;
    @FXML Button removeTag;
    @FXML TextField userEntry;

    /**
     * This method allows a user to add a Tag to the total Tags.
     */
    @FXML void tagAdded(){
        if(userEntry!=null) {
            this.tagITModel.getTagManager().addTag(userEntry.getText());
            userEntry.clear();
        }
    }

    /**
     * This method allows a user to remove a Tag from the total Tags.
     */
    @FXML void tagRemoved(){
        if(userEntry!=null) {
            this.tagITModel.getTagManager().removeTag(allTagsList.
                    getSelectionModel().getSelectedItem().toString());
        }
    }

    /**
     * This method sets the TagITModel's total Tags to the ListView.
     */
    void setUpController(){
        this.allTagsList.setItems(this.tagITModel.getTagManager().getAllTags());
    }

    void setUpController(Object object){
    }


}
