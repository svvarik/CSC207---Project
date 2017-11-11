import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class TagITController {

    // SelectDirectory scene objects
    @FXML
    public Button selectDirectory;
    @FXML
    public ListView<String> dirImages;
    @FXML
    public javafx.scene.image.ImageView imageView;
    @FXML
    public Button selectImage;
    @FXML
    public javafx.scene.image.ImageView imageToTag;

    public void selectDirAction(ActionEvent event) {
        DirectoryChooser fl = new DirectoryChooser();
        File dir = fl.showDialog(null);

        if (dir != null) {
            FileManager manager = new FileManager(dir.getAbsolutePath());
            manager.manageCurrent();

//        if (dir != null) {
//            File[] jpgF = dir.listFiles(new FileFilter() {
//                @Override
//                public boolean accept(File pathname) {
//                    return (pathname.isDirectory() || pathname.toString().endsWith(".jpg"));
//                }
//            });
//            for (File f : jpgF) {
//                dirImages.getItems().add(f.getAbsolutePath());
//            }
            for (String path : manager.imageFiles) {
                dirImages.getItems().add(path);
            }
        }
        dirImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    public void previewImageAction(ActionEvent event) {
        String imagePath = dirImages.getSelectionModel().getSelectedItem();
        File f = new File(imagePath);
        if (f.isDirectory()) {
            // say something like "Please select an image."
        } else {
            Image image = new Image(f.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void selectImageAction(ActionEvent event) throws IOException {
        String imagePath = dirImages.getSelectionModel().getSelectedItem();
        File f = new File(imagePath);
        if (f.isDirectory()) {
            // say smt like pls select an image.
        } else {
            Parent root2 = FXMLLoader.load(getClass().getResource("scene2.fxml"));
            Image image = new Image(f.toURI().toString());
            imageToTag.setImage(image);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene2 = new Scene(root2);
            window.setScene(scene2);
        }
    }

    @FXML
    private void handleSelectImageAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage = (Stage) selectImage.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("SelectImage.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

    // Go to photo. Start new scene- create imageFile in this step- associate with path?
    // Tags...


