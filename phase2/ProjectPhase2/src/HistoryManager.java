import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;


/** A History Manager class that keeps track of all renaming done, and logs data **/

public class HistoryManager {
    /**
     * A formatted array list of all the renaming done in the program,
     */
    private ObservableList<String> renamingList;
    private String saveFilePath;

    /**
     * A constructor for the HistoryManager class.
     */
    public HistoryManager() {
        this.renamingList = FXCollections.observableArrayList();
    }

    ObservableList<String> getRenamingList() {
        return renamingList;
    }

    /**
     * This method adds a new rename event to the renamingList Array.
     *
     * @param oldName The old name of the image in a String format.
     * @param newName The new name added to the image, in a string format.
     */
    public void addRenameEvent(String oldName, String newName) {
        // Add the current timestamp, the Old Name, and the NewName into the
        // ArrayList.
        String timeStamp = new SimpleDateFormat().format(new Date());
        this.renamingList.add(
                timeStamp + "Old Name: " + oldName + "New Name: " + newName + System.lineSeparator());
    }
}
