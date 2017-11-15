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

/** Keeps track of all renaming made. Class stores */
public class HistoryManager {

  // List of all renaming done
  ArrayList<String> renamingList = new ArrayList<>();
  // List of all current tags available in the program
  ArrayList<String> tagList = new ArrayList<>();
  // The path to the file containing the renaming history
  File history;
  // The path to the file containing all the tags
  File allTags;

  // Should be instantiated in the main method once the program is exited.
  public HistoryManager() throws IOException {
    File f1 = new File("history.txt");
    File f2 = new File("allTags.txt");
    // Check to see if these file already exist, and if not create them
    if (!f1.exists()) {
      f1.createNewFile();
    }
    if (!f2.exists()) {
      f2.createNewFile();
    }

    this.history = f1;
    this.allTags = f2;
  }

  void newTag(Tag tag) {
    this.tagList.add(tag.toString());
  }

  void tagRemoved(Tag tag) {
    tagList.remove(tag);
  }

  void tagAdded(ImageFile image, String tag) {
    String timeStamp = new SimpleDateFormat().format(new Date());
    this.renamingList.add(timeStamp + "\nTag: " + tag + "was added to " + image.toString());
  }

  void tagDeleted(ImageFile image, String tag) {
    String timeStamp = new SimpleDateFormat().format(new Date());
    this.renamingList.add(timeStamp + "\nTag: " + tag + "was deleted from " + image.toString());
  }

  void imageMoved(ImageFile image) {
    String timeStamp = new SimpleDateFormat().format(new Date());
    this.renamingList.add(timeStamp + "\nImage: " + image.toString() + "was moved");
  }

  void saveToDisk() {}

  public void readEvents() {

    // Initialize the logger
    Logger eventLogger = Logger.getLogger("Logger");
    Handler consoleHandler = new ConsoleHandler();
    LogManager.getLogManager().reset();
    eventLogger.setLevel(Level.ALL);
    consoleHandler.setLevel(Level.ALL);
    eventLogger.addHandler(consoleHandler);

    try {
      FileHandler fhandler = new FileHandler(history.getAbsolutePath(), true);
      fhandler.setLevel(Level.ALL);
      fhandler.setFormatter(new SimpleFormatter());
      eventLogger.addHandler(fhandler);
      for (int i = 0; i < renamingList.size(); i++) {
        eventLogger.info(renamingList.get(i));
      }
      for (int i = 0; i < tagList.size(); i++) {
        eventLogger.info(tagList.get(i));
      }
    } catch (IOException io) {
      eventLogger.info("Cannot create a file handler");
    }
  }
  // final
}
