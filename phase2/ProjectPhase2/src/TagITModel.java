import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
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
<<<<<<< HEAD
    private ImageManager imageManager;
    private TagManager tagManager;
=======
	/** The ImageManager instance that will be used throughout the program */
    private ImageManager imageManager = new ImageManager();
    /** The TagManager instance that will be used throughout the program */
    private TagManager tagManager = new TagManager();
    /** The current ImageFile chosen by the user */
>>>>>>> 5f43ed945fc84d29b9a6fcaf0d7b1a20fc941eea
    private ImageFile currentImage;

    /** The current directory that the user has opened */
    private String currentDirectory;

    /** The images and directories in the current directory*/
    private ObservableList<String> currentDirectoryFiles;

    public TagITModel() {
        imageManager = new ImageManager();
        tagManager = new TagManager();
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
     * Returns the list of files/folders represented by their file paths
     * @return
     * 		An observable list of file paths in the current directory
     */
    ObservableList<String> getCurrentDirectoryFiles() {
        return this.currentDirectoryFiles;
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
