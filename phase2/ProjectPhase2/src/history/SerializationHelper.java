package history;

import image.ImageFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tag.Tag;

import java.util.ArrayList;

/**
 * This class provides methods that aid in serializing the data that is not
 * inherently serializable. They work by either converting and returning
 * a serializable type, or shifting the data within the object from one variable
 * to another.
 */
class SerializationHelper {

    /**
     * Modifies an ArrayList of ImageFiles such that the information in each
     * object's ObservableList is copied into each object's serializable
     * ArrayList.
     *
     * @param list An ArrayList of ImageFiles that need to be serialized.
     * @return An ArrayList of ImageFiles that have had their respective Arraylist
     *         objects filled with their ObservableLists.
     */
    static ArrayList<ImageFile> imageFilesObservableToArray(ArrayList<ImageFile> list) {
        for (ImageFile i : list) {
            i.setTagsToArrayList(observableToArrayList(i.getTags()));
            i.setTagsToObservableList(null);
        }
        return list;
    }

    /**
     * Modifies an ArrayList of ImageFiles such that the information in each
     * object's ObservableList is populated with the items from the object's
     * Arraylist. ArrayList is then set to null.
     *
     * @param list An ArrayList of ImageFiles that needs to be deserialized.
     * @return An ArrayList of ImageFiles that have had their respective
     *         ObservableLists populated and their ArrayLists wiped.
     */
    static ArrayList<ImageFile> imageFileArrayToObservable(ArrayList<ImageFile> list){
        for (ImageFile i: list) {
            i.setTagsToObservableList(arrayListToObservable(i.getArrayedTags()));
            i.setTagsToArrayList(null);
        }
        return list;
    }

    /**
     * Converts an ObservableList to an ArrayList and returns it.
     *
     * @param list An ObservableList that needs to be serialized.
     * @return An ArrayList.
     */
    static ArrayList<Tag> observableToArrayList(ObservableList<Tag> list) {
        return new ArrayList<>(list);
    }

    /**
     * Converts an ArrayList to an ObservableList, and returns it.
     *
     * @param list An ArrayList that has been deserialized.
     * @return An ObservableList.
     */
    static ObservableList<Tag> arrayListToObservable(ArrayList<Tag> list) {
        return FXCollections.observableArrayList(list);
    }
}
