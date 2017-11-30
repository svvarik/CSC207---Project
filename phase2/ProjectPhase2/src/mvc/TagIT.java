package mvc;
import history.StoreToDisk;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class.
 */
public class TagIT extends Application {
    /**
     * A JavaFX Stage Object that represents the current window displayed
     */
    static Stage primaryStage;
    private TagITModel tagITModel;

    /**
     * The start method is a method from the Java Applicaton class.
     * It is called when the system is ready for the application to begin running.
     * This method deserializes previous data(if any), stored from the last run, creates and displays the first scene.
     *
     * @param primaryStage object to create the first scene with
     * @throws Exception
     * @return void
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.tagITModel = new TagITModel();

        StoreToDisk.initSaveFiles();
        StoreToDisk.readHistory(this.tagITModel);
        StoreToDisk.deserializeData(this.tagITModel);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SelectDirectory.fxml"));
        Parent root = loader.load();
        SelectDirectoryController controller = loader.getController();
        controller.initController(this.tagITModel);
        primaryStage.setTitle("TagIT");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    /**
     * The stop method is a method part of the Java Application Class.
     * The stop method is overidden so that when the program is to be closed, this method will also create a
     * HistoryManager to log all changes, and serialize that data to store it for the next run.
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        StoreToDisk.writeHistory(this.tagITModel);
        StoreToDisk.serializeData(this.tagITModel);
    }

    public static void main(String[] args) {
        launch(args);
    }


}

