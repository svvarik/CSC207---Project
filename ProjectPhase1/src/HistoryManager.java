import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Keeps track of all renaming made */
public class HistoryManager {
  /** A formatted array list of all the renaming done in the program, */
  private static ArrayList<String> renamingList = new ArrayList<>();

  /**
   * Keeps an array list of all the renaming done, tags added and deleted, in the program and
   * updates history.txt with the changes once the program is exited.
   */
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
  /**
   * Creates a formatted String to be stored in renamingList with the time, old name and new name of
   * the ImageFile and is called when a tag is added to an ImageFile
   *
   * @param image The ImageFile that is being tagged
   * @param tag The String tag that was added to the ImageFile
   */
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
  /**
   * Creates a formatted String to be stored in renamingList with the time, old name and new name of
   * the ImageFile, and is called when a tag is deleted from an ImageFile
   *
   * @param image The ImageFile that is being tagged
   * @param tag The String tag that was added to the ImageFile
   */
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
