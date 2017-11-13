import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Button selectDir;

    @FXML
    public ListView<String> dirImages;

    //Preview? then another button with "Select Image", open new scene with tagging
    @FXML
    public Button submit;

    @FXML
    public javafx.scene.image.ImageView imageView;

    @FXML
    public Button selectImage;

    @FXML
    public ListView<String> currentTags;

    @FXML
    public ListView<String> allTags;

    @FXML
    TextField tagInput;

    @FXML
    Button tagImage;

    @FXML
    public javafx.scene.image.ImageView imageToTag;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void selectDirAction(ActionEvent event) {
        dirImages.getItems().clear();
        DirectoryChooser fl = new DirectoryChooser();
        File dir = fl.showDialog(null);

        if (dir != null) {
            File[] jpgF = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return (pathname.isDirectory() || pathname.toString().endsWith(".jpg"));
                }
            });
            for (File f : jpgF) {
                dirImages.getItems().add(f.getAbsolutePath());
            }
        }
        dirImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void previewImageAction(ActionEvent event) {
        currentTags.getItems().clear();
        String imagePath = dirImages.getSelectionModel().getSelectedItem();
        FileManager.selectedImage = imagePath;
        File f = new File(imagePath);
        if (f.isDirectory()) {
            // say something like "Please select an image."
        } else {
            Image image = new Image(f.toURI().toString());
            ImageFile thisImage= ImageManager.findImage(imagePath, f.getName());
            System.out.println(ImageManager.findImage(imagePath, f.getName()).filePath);
            ImageManager.currentImage = thisImage;
            System.out.println(ImageManager.currentImage);
            ArrayList<Tag> imageTags = ImageManager.currentImage.getTags();
            for (Tag t : imageTags){
                currentTags.getItems().add(t.getTag());
            }
            currentTags.setOrientation(Orientation.HORIZONTAL);
            imageView.setImage(image);
        }
    }

    @FXML
    public void selectImageAction(ActionEvent event) throws IOException {
        FileManager.selectedImage = dirImages.getSelectionModel().getSelectedItem();
        File f = new File(FileManager.selectedImage);
        if (f.isDirectory()) {
             //say smt like pls select an image.
        } else {
            Parent root2 = FXMLLoader.load(getClass().getResource("scene2.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene2 = new Scene(root2);
            Image image = new Image(f.toURI().toString());
            window.setScene(scene2);
            window.show();
            imageToTag.setImage(image);
//        EditBox ctr = new EditBox();
//        ctr.display();
        }}


//    @FXML
//    private void handleSelectImageAction(ActionEvent event) throws IOException{
////        fileManager.selectedImage = dirImages.getSelectionModel().getSelectedItem();
////        File f = new File(fileManager.selectedImage);
////        Image image = new Image(f.toURI().toString());
////        imageToTag.setImage(image);
//        Stage stage;
//        Parent root;
//        //get reference to the button's stage
//        stage = (Stage) selectImage.getScene().getWindow();
//        //load up OTHER FXML document
//        root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
//        //create a new scene with root and set the stage
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

    public void enterTagAction(){
        System.out.println(tagInput.getText());
        String addedTag = tagInput.getText();
        currentTags.getItems().add(addedTag);
        System.out.println(ImageManager.currentImage);
        (ImageManager.currentImage).addTag(addedTag);
        System.out.println(ImageManager.currentImage.getTags());
        tagInput.clear();


    }
}