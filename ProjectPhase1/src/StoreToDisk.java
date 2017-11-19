import java.io.*;


/**
 * This class contains functions that enable the program to save persistent data
 * to binary.
 */
public class StoreToDisk implements Serializable {



    // Initialize the save file upon startup, create it if it doesn't exist
    // PRECONDITION: Files don't exist prior to starting application for first time
    static void initSaveFile() throws IOException {
        File newFile1 = new File("programData.ser");

        if (!newFile1.exists()) {
            new FileOutputStream("programData.ser");
        }
    }

    static void deserializeData() throws Exception {
        InputStream file = new FileInputStream("programData.ser");
        InputStream buffer = new BufferedInputStream(file);

        // Load from Tags.ser if file is not empty
        if (buffer.available() > 0) {
            ObjectInputStream fileData = new ObjectInputStream(buffer);
            SavedState saveState = (SavedState) fileData.readObject();
            SelectImageViewController.tagManager.setAllTags(saveState.loadTags());
            SelectImageViewController.imageManager.setCreatedImages(saveState.loadImages());

        }
    }

    static void serializeData() throws Exception {
        OutputStream file = new FileOutputStream("programData.ser", false);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        SavedState toSave = StoreToDisk.initSaveState();
        output.writeObject(toSave);
        output.close();
    }

    private static SavedState initSaveState(){
        SavedState saveState = new SavedState();
        saveState.saveTags(SelectImageViewController.tagManager.getAllTags());
        saveState.saveImages(SelectImageViewController.imageManager.getCreatedImages());
        return saveState;
    }
}