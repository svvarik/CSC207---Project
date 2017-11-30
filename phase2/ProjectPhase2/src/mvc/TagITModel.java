package mvc;

import history.HistoryManager;
import image.ImageFile;
import image.ImageManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tag.TagManager;

import java.awt.image.BufferedImage;
import javafx.beans.value.ObservableStringValue;
import java.util.Observable;
import java.util.Observer;

/**
 * A system that models the information being passed when the program is run.
 * It keeps track of the changes made to TagManager, ImageManager when a user is
 * adding/removing tags, changing the image location in the computer's file system.
 * It also keeps track of the current ImageFile and that ImageFile's directory
 * the user chooses.
 */
public class TagITModel implements Observer {

    /**
     * The HistoryManager instance that will be used throughout the program.
     */
    private HistoryManager historyManager;
    /**
     * The ImageManager instance that will be used throughout the program
     */
    private ImageManager imageManager;
    /**
     * The TagManager instance that will be used throughout the program
     */
    private TagManager tagManager;
    /**
     * The current ImageFile chosen by the user
     */
    private ImageFile currentImage;
    /**
     * The BufferedImage with original colours the user is viewing
     */
    private BufferedImage currentImageWithFilter;
    /**
     * The current directory that the user has opened
     */
    private String currentDirectory;

    /** The observable filepath */
    private String imageFilePath;

    TagITModel() {
        this.historyManager = new HistoryManager();
        this.imageManager = new ImageManager();
        this.tagManager = new TagManager();
    }

    /**
     * A getter method to access the current ImageFile
     *
     * @return The current ImageFile
     */
    ImageFile getCurrentImage() {
        return this.currentImage;
    }

    /**
     * A setter method to set the current ImageFile to a new ImageFile
     *
     * @param newCurImage The new ImageFile to set
     */
    void setCurrentImage(ImageFile newCurImage) {
        this.currentImage = newCurImage;
        this.currentImage.addObserver(this);
        this.currentImage.addObserver(this.historyManager);
        this.setImageFilePath();
    }

    /**
     * A setter method to set the currentImageWithFilter to curImageWithFilter.
     * @param curImageWithFilter the BufferedImage to set currentImageWithFilter to.
     */
    void setCurrentImagewithFilter(BufferedImage curImageWithFilter) {
        this.currentImageWithFilter = curImageWithFilter;
    }

    /**
     * A getter method to access the currentImageWithFilter.
     * @return the BufferedImage stored in currentImageWithFilter.
     */
    BufferedImage getCurrentImagewithFilter() {
        return this.currentImageWithFilter;
    }

    /**
     * A getter method to access the HistoryManager information.
     */
    public HistoryManager getHistoryManager() {
        return this.historyManager;
    }

    /**
     * A method to access the TagManager information
     *
     * @return The TagManager instance used in the program
     */
    public TagManager getTagManager() {
        return this.tagManager;
    }

    /**
     * A method to access the ImageManager information
     *
     * @return The ImageManager instance used in the program
     */
    public ImageManager getImageManager() {
        return this.imageManager;
    }

    /**
     * A getter method to access the current directory chosen by the user
     *
     * @return A string representation of the directory, the file path
     */
    String getCurrentDirectory() {
        return this.currentDirectory;
    }

    /**
     * A setter method to set the current directory to a new one
     *
     * @param dirPath The new file path to set
     */
    void setCurrentDirectory(String dirPath) {
        this.currentDirectory = dirPath;
    }

    private void setImageFilePath() {
        this.imageFilePath = this.currentImage.getFilePath();
    }

    String getImageFilePath() {
        return this.imageFilePath;
    }

    /**
     * A method from the Observer interface that is overridden to update the
     * current directory based on the user's actions.
     */
    @Override
    public void update(Observable o, Object arg) {
        this.setCurrentDirectory(this.currentDirectory);
        this.setImageFilePath();
    }
}
