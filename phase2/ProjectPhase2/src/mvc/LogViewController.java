package mvc;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;


public class LogViewController extends GeneralController{

    @FXML private ListView<String> listViewHistory;

    /**
     * This method sets the ListView to the ObservableList in the TagITModel's
     * HistoryManager class.
     * @throws IOException An InputOutput exception.
     */
    void setUpController() {
        this.listViewHistory.setItems(tagITModel.getHistoryManager().getRenamingList());
    }

    void setUpController(Object object){}

}
