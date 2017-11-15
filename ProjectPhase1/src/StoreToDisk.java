import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;


/**
 * This class contains functions that enable the program to save persistent data
 * to binary files.
 */
public class StoreToDisk implements Serializable {

    // Paths to Save Files for both Lists
    private static File saveFileTagsUsed;
    private static File saveFileImageFilesUsed;

    // Initialize the save file upon startup, create it if it doesn't exist
    // PRECONDITION: tagsOrImages can only equal "tags" or "images"
    public void initSaveFile(String path, String tagsOrImages) throws IOException {
        File fileToBeCreated = new File(path);
        if (!fileToBeCreated.exists()) {
            fileToBeCreated.createNewFile();
        }
        if(tagsOrImages.equals("tags")) {
            StoreToDisk.saveFileTagsUsed = fileToBeCreated;
        } else if(tagsOrImages.equals("images")) {
            StoreToDisk.saveFileImageFilesUsed = fileToBeCreated;
        }
    }


    // Save all the tags used into a saveFile
    static public void saveTagsUsed() throws IOException{

        OutputStream file = new FileOutputStream(StoreToDisk.saveFileTagsUsed);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(TagManager.getAllTags());
        output.close();
    }


    static public void saveImagesUsed() throws IOException {
        OutputStream file = new FileOutputStream(StoreToDisk.saveFileImageFilesUsed);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(ImageManager.getCreatedImages());
        output.close();
    }


    static public void loadTagsUsed() throws ClassNotFoundException, IOException{

        InputStream file = new FileInputStream(StoreToDisk.saveFileTagsUsed);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        //deserialize the ObservableList of Tags
        TagManager.setAllTags((ObservableList<Tag>) input.readObject());
        input.close();

    }

    static public void loadImagesUsed() throws ClassNotFoundException, IOException{

        InputStream file = new FileInputStream(StoreToDisk.saveFileTagsUsed);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        //deserialize the ArrayList ofImages
        ImageManager.setCreatedImages((ArrayList<ImageFile>) input.readObject());
        input.close();

    }
}
