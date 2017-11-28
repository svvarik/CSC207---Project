import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;


public class LogViewController extends GeneralController{

    @FXML private ListView<String> listViewHistory;

    @Override
    void initController(TagITModel model) {
        this.tagITModel = model;
        try {
            this.initListView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the ListView to the ObservableList in the TagITModel's
     * HistoryManager class.
     * @throws IOException An InputOutput exception.
     */
    private void initListView() throws IOException {
        this.listViewHistory.setItems(tagITModel.getHistoryManager().getRenamingList());
    }
}
