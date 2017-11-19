import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TagIT extends Application {
    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        StoreToDisk.initSaveFile();
        StoreToDisk.deserializeData();

        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("SelectDirectory.fxml"));
        primaryStage.setTitle("TagIT");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception{
        new HistoryManager();
        StoreToDisk.serializeData();
    }

    public static void main(String[] args) {
        launch(args);
    }


}

