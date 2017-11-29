package history;
import tag.Tag;
import image.ImageFile;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a saved state of the Program. It is instantiated and
 * serialized prior to closing the program. All the persistent data will be
 * saved to this object prior to serialization, through the save methods.
 */
class SavedState implements Serializable{
    private ArrayList<Tag> savedTags;
    private ArrayList<ImageFile> savedImages;

    /**
     * Stores all the program's Tags, by calling a SerializationHelper method to
     * convert the ObservableList to an ArrayList so serialization of this
     * SavedState instance can occur.
     *
     * @param allTags The ObservableList of TagManager.tags used throughout the
     *                application's runtime.
     */
    void saveTags(ObservableList<Tag> allTags){
        this.savedTags = SerializationHelper.observableToArrayList(allTags);
    }

    /**
     * Stores all the program's images, by calling a SerializationHelper method
     * to shift all the data in ImageFile's ObservableList to it's ArrayLists,
     * so that serialization can occur.
     *
     * @param allImages The ArrayList of ImageManager.Images used throughout the
     *                  application's runtime.
     */
    void saveImages(ArrayList<ImageFile> allImages){
        this.savedImages = SerializationHelper.imageFilesObservableToArray(allImages);
    }

    /**
     * Converts an ArrayList from SavedState and returns an ObservableList of
     * Tags, so that the program can run on this persistent data.
     *
     * @return An ObservableList of Tags that is from the last time the
     *         application ran.
     */
    ObservableList<Tag> loadTags(){
        return SerializationHelper.arrayListToObservable(this.savedTags);
    }

    /**
     * Modifies the ArrayList so that each ImageFile in this ArrayList has its
     * ObservableList populated with the contents of its ArrayList, and sets the
     * ArrayList to null.
     *
     * @return An ArrayList of Images from the last time the application ran.
     */
    ArrayList<ImageFile> loadImages(){
        return SerializationHelper.imageFileArrayToObservable(this.savedImages);
    }
}
