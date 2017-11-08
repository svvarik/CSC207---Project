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

/**
 * Keeps track of all renaming made. Class stores
 */
public class HistoryManager {

    // List of all renaming done
    ArrayList<String> renamingList = new ArrayList<>();

    public HistoryManager(){

        readEvents();
    }

    //Uncomment these when more code has been written to add and delete tags
    /**
     void tagAdded(ImageFile image, String tag){
     String timeStamp = new SimpleDateFormat().format( new Date() );
     this.renamingList.add(timeStamp + "\nTag: " + tag + "was added to " + image.toString());
     }

     void tagDeleted(ImageFile image, String tag) {
     String timeStamp = new SimpleDateFormat().format( new Date() );
     this.renamingList.add(timeStamp + "\nTag: " + tag + "was deleted from " + image.toString());
     }

     void imageMoved(ImageFile image) {
     String timeStamp = new SimpleDateFormat().format( new Date() );
     this.renamingList.add(timeStamp + "\nImage: " + image.toString() + "was moved");
     }**/

    void saveToDisk(){

    }

    public void readEvents() {


        // Initialize the logger
        Logger eventLogger = Logger.getLogger("Logger");
        Handler consoleHandler = new ConsoleHandler();
        LogManager.getLogManager().reset();
        eventLogger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        eventLogger.addHandler(consoleHandler);

        try {
            FileHandler fhandler = new FileHandler("history.txt");
            fhandler.setLevel(Level.ALL);
            fhandler.setFormatter(new SimpleFormatter());
            eventLogger.addHandler(fhandler);
            for (int i = 0; i < renamingList.size(); i++) {
                eventLogger.info(renamingList.get(i));

            }
        } catch (IOException io) {
            eventLogger.info("Cannot create a file handler");
        }

    }


}
