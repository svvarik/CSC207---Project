import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main class.
 */
public class TagIT extends Application {
    /**
     * A JavaFX Stage Object that represents the current window displayed
     */
    static Stage primaryStage;

    /**
     * The start method is a method from the Java Applicaton class.
     * It is called when the system is ready for the application to begin running.
     * This method deserializes previous data(if any), stored from the last run, creates and displays the first scene.
     *
     * @param primary  A Stage object to create the first scene with
     * @throws Exception
     * @return void
     */
    @Override
    public void start(Stage primary) throws Exception {

        StoreToDisk.initSaveFile();
        StoreToDisk.deserializeData();

        this.primaryStage = primary;
        Parent root = FXMLLoader.load(getClass().getResource("SelectDirectory.fxml"));
        primaryStage.setTitle("TagIT");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * The stop method is a method part of the Java Application Class.
     * The stop method is overidden so that when the program is to be closed, this method will also create a
     * HistoryManager to log all changes, and serialize that data to store it for the next run.
     * @throws Exception
     */
    @Override
    public void stop() throws Exception{
        new HistoryManager();
        StoreToDisk.serializeData();
    }

    public static void main(String[] args) {
        launch(args);
    }


}

