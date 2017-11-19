import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Keeps track of all renaming made*/
public class HistoryManager {
  // List of all renaming done
  private static ArrayList<String> renamingList = new ArrayList<>();

  // Should be instantiated in the main method once the program is exited.
  public HistoryManager() throws SecurityException, IOException {

    // Initialize the logger
    FileHandler fh = new FileHandler("history.txt", true);
    fh.setFormatter(new LogFormatter());
    Logger logger = Logger.getLogger("logger");
    logger.setUseParentHandlers(false);
    logger.setLevel(Level.ALL);
    logger.addHandler(fh);
    for (String renaming : renamingList) {
      logger.info(renaming);
    }
  }

  static void tagAdded(ImageFile image, String tag) {
    String timeStamp = new SimpleDateFormat().format(new Date());
    String oldName = image.getTaggedName();
    String[] nameParts = image.getTaggedName().split("\\.");
    String newName = nameParts[0] + tag + "." + nameParts[1];
    renamingList.add(
        timeStamp
            + "\nTag: "
            + tag
            + " was added to "
            + image.toString()
            + "\nOldName: "
            + oldName
            + "\nNewName: "
            + newName
            + "\n");
  }

  static void tagDeleted(ImageFile image, String tag) {
    String timeStamp = new SimpleDateFormat().format(new Date());
    String newName = image.getTaggedName();
    String[] nameParts = image.getTaggedName().split("\\.");
    String oldName = nameParts[0] + tag + "." + nameParts[1];
    renamingList.add(
        timeStamp
            + "\nTag: "
            + tag
            + " was deleted from "
            + image.toString()
            + "\nOldName: "
            + oldName
            + "\nNewName: "
            + newName
            + "\n");
  }
}
