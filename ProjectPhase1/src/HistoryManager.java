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
import java.util.logging.*;

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
    public HistoryManager(String file) throws SecurityException, IOException {
        File f1 = new File(file);
        // Check to see if these file already exist, and if not create them
        if (!f1.exists()) {
            f1.createNewFile();
        }
    }

    static void tagAdded(ImageFile image, String tag) {
        String timeStamp = new SimpleDateFormat().format(new Date());
        String oldName = image.getTaggedName();
        String[] nameParts = image.getTaggedName().split("\\.");
        String newName = nameParts[0] + tag + "." + nameParts[1];
        renamingList.add("\n" + timeStamp + "\nTag: " + tag + " was added to " +
                image.toString() + "\nOldName: " + oldName + "\nNewName: " + newName);
    }

    static void tagDeleted(ImageFile image, String tag) {
        String timeStamp = new SimpleDateFormat().format(new Date());
        String newName = image.getTaggedName();
        String[] nameParts = image.getTaggedName().split("\\.");
        String oldName = nameParts[0] + tag + "." + nameParts[1];
        renamingList.add("\n" + timeStamp + "\nTag: " + tag + " was deleted from " + image.toString()+ "\nOldName: " + oldName  + "\nNewName: " + newName);
    }

    static void imageMoved(ImageFile image) {
        String timeStamp = new SimpleDateFormat().format(new Date());
        renamingList.add("\n" + timeStamp + "\nImage: " + image.toString() + "was moved");
    }

    public void readEvents(String file) throws SecurityException, IOException {

        // Initialize the logger
        fh = new FileHandler(file, true);
        fh.setFormatter(new LogFormatter());
        logger = Logger.getLogger("logger");
        logger.setUseParentHandlers(false);
        this.logger.setLevel(Level.ALL);
        logger.addHandler(fh);
        for (int j = 0; j < renamingList.size(); j++) {
            logger.info(renamingList.get(j));
        }
    }
}

