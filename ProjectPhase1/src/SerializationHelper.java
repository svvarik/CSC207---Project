import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.util.ArrayList;

public class SerializationHelper {

    // Change each ImageFile in ImageManager's ArrayList of ImageFiles, from
    // storing an ObservableList to storing an ArrayList. This is for serialization.
    static ArrayList<ImageFile> imageFilesObservableToArray(ArrayList<ImageFile> list) {
        for (ImageFile i : list) {
            i.setTagsToArrayList(observableToArrayList(i.getTags()));
        }

        return list;
    }

    // When retrieving data from system, set each ImageFile's observableList to
    // the data in the ImageFile's ArrayList that was stored upon serialization.
    static ArrayList<ImageFile> imageFileArrayToObservable(ArrayList<ImageFile> list){
        for (ImageFile i: list) {
            i.setTagsToObservableList(arrayListToObservable(i.getArrayedTags()));
        }

        return list;
    }

    static ArrayList<Tag> observableToArrayList(ObservableList<Tag> list) {
        return new ArrayList<>(list);
    }

    static ObservableList<Tag> arrayListToObservable(ArrayList<Tag> list) {
        return FXCollections.observableList(list);
    }

    static ArrayList<String> observableListtoArray(ObservableList<String> list) {
        return new ArrayList<>(list);
    }
}
