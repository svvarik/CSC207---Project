import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * A system that models the information being passed when the program is run. 
 * It keeps track of the changes made to TagManager, ImageManager when a user is 
 * adding/removing tags, changing the image location in the computer's file system. 
 * It also keeps track of the current ImageFile and that ImageFile's directory 
 * the user chooses. 
 *
 */
public class TagITModel implements Observer{

    /** The HistoryManager instance that will be used throughout the program. */
    private HistoryManager historyManager;
    /** The ImageManager instance that will be used throughout the program */
    private ImageManager imageManager;
    /** The TagManager instance that will be used throughout the program */
    private TagManager tagManager;
    /** The current ImageFile chosen by the user */
    private ImageFile currentImage;
    /** The BufferedImage with original colours the user is viewing */
    private BufferedImage currentImagewithFilter;
    /** The current directory that the user has opened */
    private String currentDirectory;
    /** The images and directories in the current directory*/
    private ObservableList<String> currentDirectoryFiles;

    public TagITModel() {
        this.historyManager = new HistoryManager();
        this.imageManager = new ImageManager();
        this.tagManager = new TagManager();
    }

    /**
     * A getter method to access the current ImageFile
     * @return The current ImageFile
     */
    ImageFile getCurrentImage() {
        return this.currentImage;
    }

    /**
     * A setter method to set the current ImageFile to a new ImageFile
     * @param newCurImage
     * 			The new ImageFile to set
     */
    void setCurrentImage(ImageFile newCurImage) {
        this.currentImage = newCurImage;
        this.currentImage.addObserver(this);
        this.currentImage.addObserver(this.historyManager);
    }

    void setCurrentImagewithFilter(BufferedImage curImageWithFilter) {
        this.currentImagewithFilter = curImageWithFilter;
    }

    BufferedImage getCurrentImagewithFilter() {
        return this.currentImagewithFilter;
    }

    /**
     * A getter method to access the HistoryManager information.
     */
    HistoryManager getHistoryManager(){
        return this.historyManager;
    }

    /**
     * A method to access the TagManager information
     * @return
     * 		The TagManager instance used in the program
     */
    TagManager getTagManager() {
        return this.tagManager;
    }

    /**
     * A method to access the ImageManager information
     * @return
     * 		The ImageManager instance used in the program
     */
    ImageManager getImageManager() {
        return this.imageManager;
    }

    /**
     * A getter method to access the current directory chosen by the user
     * @return
     * 		A string representation of the directory, the file path
     */
    String getCurrentDirectory() {
        return this.currentDirectory;
    }

    /**
     * A setter method to set the current directory to a new one
     * @param dirPath
     * 			The new file path to set
     */
    void setCurrentDirectory(String dirPath) {
        this.currentDirectory = dirPath;
        File[] dirFiles = FileManager.imageFilesFilter(new File(this.currentDirectory));
        currentDirectoryFiles = FXCollections.observableList(FileManager.filesToString(dirFiles));
    }

    /**
     * A method from the Observer interface that is overridden to update the
     * current directory based on the user's actions. 
     */
    @Override
    public void update(Observable o, Object arg) {
        this.setCurrentDirectory(this.currentDirectory);
    }
}
