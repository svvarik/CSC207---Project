import java.util.ArrayList;
import java.io.File;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogManager;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

/**
 * Keeps track of all renaming made. Class stores
 */
public class HistoryManager {

    public Logger logger;
    FileHandler fh;

    // List of all renaming done
    static ArrayList<String> renamingList = new ArrayList<>();
    // The path to the file containing the renaming history
    File history;


    // Should be instantiated in the main method once the program is exited.
    public HistoryManager(String file) throws SecurityException,IOException {
        File f1 = new File(file);
        // Check to see if these file already exist, and if not create them
        if (!f1.exists()) {
            f1.createNewFile();
        }
    }

    static void tagAdded(ImageFile image, String tag) {
        String timeStamp = new SimpleDateFormat().format(new Date());
        renamingList.add(timeStamp + "\nTag: " + tag + " was added to " + image.toString());
    }

    static void tagDeleted(ImageFile image, String tag) {
        String timeStamp = new SimpleDateFormat().format(new Date());
        renamingList.add(timeStamp + "\nTag: " + tag + " was deleted from " + image.toString());
    }

    static void imageMoved(ImageFile image) {
        String timeStamp = new SimpleDateFormat().format(new Date());
        renamingList.add(timeStamp + "\nImage: " + image.toString() + "was moved");
    }

    public void readEvents(String file) throws SecurityException, IOException {

        // Initialize the logger

        fh = new FileHandler(file, true);
        logger = Logger.getLogger("logger");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        this.logger.setLevel(Level.ALL);
    }
}
