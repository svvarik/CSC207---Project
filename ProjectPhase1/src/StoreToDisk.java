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

    /**
     * Declares the input stream as a serialized file.
     * Converts the serialized binary data stored in programData.ser, and reads it into a SavedState class
     * as deserialized human-readable information.
     * @throws Exception
     */
    static void deserializeData() throws Exception {
        InputStream file = new FileInputStream("programData.ser");
        InputStream buffer = new BufferedInputStream(file);

        // Load from programData.ser if file is not empty
        if (buffer.available() > 0) {
            ObjectInputStream fileData = new ObjectInputStream(buffer);
            SavedState saveState = (SavedState) fileData.readObject();
            SelectImageViewController.tagManager.setAllTags(saveState.loadTags());
            SelectImageViewController.imageManager.setCreatedImages(saveState.loadImages());

        }
    }

    /**
     * Declares the output stream as the serialized file.
     * Serializes the SavedState class information and writes it to the output stream.
     * @throws Exception
     */
    static void serializeData() throws Exception {
        OutputStream file = new FileOutputStream("programData.ser", false);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        SavedState toSave = StoreToDisk.initSaveState();
        output.writeObject(toSave);
        output.close();
    }

    /**
     * Creates a new SavedState instance, and stores the all new changes in Tags and Images made.
     * @return  SavedState
     */
    private static SavedState initSaveState(){
        SavedState saveState = new SavedState();
        saveState.saveTags(SelectImageViewController.tagManager.getAllTags());
        saveState.saveImages(SelectImageViewController.imageManager.getCreatedImages());
        return saveState;
    }
}