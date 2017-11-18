
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import java.util.Arrays;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * This class contains functions that enable the program to save persistent data
 * to binary files.
*/
public class StoreToDisk implements Serializable {

    // Paths to Save Files for both Lists
    private static FileOutputStream saveFileTagsUsed;
    private static FileOutputStream saveFileImageFilesUsed;

    // Initialize the save file upon startup, create it if it doesn't exist
    // PRECONDITION: Files don't exist prior to starting application for first time
    static public void initSaveFiles() throws IOException {
        File newFile1 = new File("tags.ser");
        File newFile2 = new File("images.ser");

        if(!newFile1.exists()) {
            FileOutputStream newFileTags = new FileOutputStream("tags.ser");
            StoreToDisk.saveFileTagsUsed = newFileTags;
        }
        if (!newFile2.exists()) {
            FileOutputStream newFileImages = new FileOutputStream("images.ser");
            StoreToDisk.saveFileImageFilesUsed = newFileImages;
        }
    }

    static public void loadFiles() throws Exception {
        InputStream file1 = new FileInputStream("tags.ser");
        InputStream file2 = new FileInputStream("images.ser");
        InputStream buffer1 = new BufferedInputStream(file1);
        InputStream buffer2 = new BufferedInputStream(file2);

        // Load from Tags.ser if file is not empty
        if (buffer1.available() > 0) {
            ObjectInputStream fileTags = new ObjectInputStream(buffer1);
            ArrayList<Tag> tagManagerTags = (ArrayList<Tag>) fileTags.readObject();
            // Convert TagManager's Tag back to an observable list and set it
            TagManager.setAllTags(SerializationHelper.arrayListToObservable(tagManagerTags));
            fileTags.close();
        }
        // Load from Images.ser if file is not empty
        if (buffer2.available() > 0) {
            ObjectInputStream fileImages = new ObjectInputStream(buffer2);
            ArrayList<ImageFile> allImages = (ArrayList<ImageFile>) fileImages.readObject();
            // Convert all the ArrayLists in ImageManager's ImageFiles back to ObservableLists
            SerializationHelper.imageFileArrayToObservable(allImages);
            ImageManager.setCreatedImages(allImages);
            fileImages.close();
        }

    }

    static public void saveFiles() throws Exception {
        OutputStream file1 = new FileOutputStream("tags.ser", true);
        OutputStream buffer1 = new BufferedOutputStream(file1);
        ObjectOutput output1 = new ObjectOutputStream(buffer1);
        OutputStream file2 = new FileOutputStream("images.ser", true);
        OutputStream buffer2 = new BufferedOutputStream(file2);
        ObjectOutput output2 = new ObjectOutputStream(buffer2);

        // Convert TagManager's observable list
        ArrayList<Tag> allTags = SerializationHelper.observableToArrayList(TagManager.getAllTags());

        // Convert all the observable lists in ImageFileManager's ImageFiles
        ArrayList<ImageFile> allImages = SerializationHelper.imageFilesObservableToArray(ImageManager.getCreatedImages());

        output1.writeObject(allTags);
        output1.close();
        output2.writeObject(allImages);
        output2.close();

    }


}

