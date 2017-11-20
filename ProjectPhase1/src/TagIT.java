import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TagIT extends Application {
    static Stage primaryStage;
    private TagITModel tagITModel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.tagITModel = new TagITModel();

        StoreToDisk.initSaveFile();
        StoreToDisk.deserializeData(this.tagITModel);

        primaryStage = primaryStage;
//        Parent root = FXMLLoader.load(getClass().getResource("SelectDirectory.fxml"));
//        SelectDirectoryController dirController = new SelectDirectoryController(tagITModel);
//        root.setController
//        primaryStage.setTitle("TagIT");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectDirectory.fxml"));

        // Create a controller instance
        SelectDirectoryController dirController = new SelectDirectoryController(tagITModel);
        // Set it in the FXMLLoader
        loader.setController(dirController);
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        primaryStage.setTitle("TagIT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception{
        new HistoryManager();
        StoreToDisk.serializeData(this.tagITModel);
    }

    public static void main(String[] args) {
        launch(args);
    }


}

