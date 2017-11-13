import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Scene2Controller implements Initializable {
    @FXML
    public static javafx.scene.image.ImageView imageToTag;

    public void display() throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        Stage window = Main.primaryStage;
        Scene scene2 = new Scene(root2);
        File f = new File(fileManager.selectedImage);
        Image image = new Image(f.toURI().toString());
        imageToTag.setImage(image);
        window.setScene(scene2);
        window.show();
    }

    public void initialize(URL url, ResourceBundle rb) {
    }
}
