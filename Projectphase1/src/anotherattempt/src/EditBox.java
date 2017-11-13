import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class EditBox {

    public static ImageView imageToTag;

    public void display() throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        Stage window = new Stage();
        Scene scene2 = new Scene(root2);
        File f = new File(fileManager.selectedImage);
        Image image = new Image(f.toURI().toString());
        imageToTag.setImage(image);
        window.setScene(scene2);
        window.show();
    }
}
