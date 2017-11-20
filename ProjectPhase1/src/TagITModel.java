import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class TagITModel implements Observer{
    private ImageManager imageManager = new ImageManager();
    private TagManager tagManager = new TagManager();
    private ImageFile currentImage;

    /** The current directory that the user has opened */
    private String currentDirectory;

    /** The images and directories in the current directory*/
    private ObservableList<String> currentDirectoryFiles;


    ImageFile getCurrentImage() {
        return this.currentImage;
    }

    void setCurrentImage(ImageFile newCurImage) {
        this.currentImage = newCurImage;
    }

    TagManager getTagManager() {
        return this.tagManager;
    }

    ImageManager getImageManager() {
        return this.imageManager;
    }

    String getCurrentDirectory() {
        return this.currentDirectory;
    }

    void setCurrentDirectory(String dirPath) {
        this.currentDirectory = dirPath;
        File[] dirFiles = FileManager.imageFilesFilter(new File(this.currentDirectory));
        currentDirectoryFiles = FXCollections.observableList(FileManager.filesToString(dirFiles));
    }

    ObservableList<String> getCurrentDirectoryFiles() {
        return this.currentDirectoryFiles;
    }

    @Override
    public void update(Observable o, Object arg) {
        currentDirectory = this.currentDirectory;
        this.setCurrentDirectory(this.currentDirectory);
    }
}
