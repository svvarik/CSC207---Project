import javafx.collections.ObservableList;

import java.io.*;


/**
 * The StoreToDisk class contains functions that enable the program to save persistent data
 * to binary.
 */
public class StoreToDisk implements Serializable {

    /**
     * Initializes a file to save data to, when program is run. Creates a new 'save' file if it does not exist.
     * PRECONDITION: Save file does not exist prior to starting application for first time
     *
     * @throws IOException
     */
    static void initSaveFile() throws IOException {
        File newFile1 = new File("programData.ser");

        if (!newFile1.exists()) {
            new FileOutputStream("programData.ser");
        }
    }

    static void deserializeData(TagITModel tagITModel) throws Exception {
    /**
     * Declares the input stream as a serialized file.
     * Converts the serialized binary data stored in programData.ser, and reads it into a SavedState class
     * as deserialized human-readable information.
     * @throws Exception
     */
        InputStream file = new FileInputStream("programData.ser");
        InputStream buffer = new BufferedInputStream(file);

        // Load from programData.ser if file is not empty
        if (buffer.available() > 0) {
            ObjectInputStream fileData = new ObjectInputStream(buffer);
            SavedState saveState = (SavedState) fileData.readObject();
            tagITModel.getTagManager().setAllTags(saveState.loadTags());
            tagITModel.getImageManager().setCreatedImages(saveState.loadImages());

        }
    }

    static void serializeData(TagITModel tagITModel) throws Exception {
    /**
     * Declares the output stream as the serialized file.
     * Serializes the SavedState class information and writes it to the output stream.
     * @throws Exception
     */
        OutputStream file = new FileOutputStream("programData.ser", false);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        SavedState toSave = StoreToDisk.initSaveState(tagITModel);
        output.writeObject(toSave);
        output.close();
    }

    private static SavedState initSaveState(TagITModel tagITModel){
    /**
     * Creates a new SavedState instance, and stores the all new changes in Tags and Images made.
     * @return  SavedState
     */
        SavedState saveState = new SavedState();
        saveState.saveTags(tagITModel.getTagManager().getAllTags());
        saveState.saveImages(tagITModel.getImageManager().getCreatedImages());
        return saveState;
    }
}