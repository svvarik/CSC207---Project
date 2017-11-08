package test4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Button viewImage;

    @FXML
    private Button manageTags;

    @FXML
    private Button chooseDirectory;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        if(event.getSource()== viewImage) {
            //get reference to the button's stage
            stage = (Stage) viewImage.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("ViewImage.fxml"));
        } else if (event.getSource() == manageTags){
            stage = (Stage) manageTags.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ManageTags.fxml"));
        } else {
            stage = (Stage) chooseDirectory.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ChooseDirectory.fxml"));
        }
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
