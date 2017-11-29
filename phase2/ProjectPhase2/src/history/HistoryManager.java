package history;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;


/** A History Manager class that keeps track of all renaming done, and logs data **/

public class HistoryManager implements Observer{
    /**
     * A formatted array list of all the renaming done in the program,
     */
    private ObservableList<String> renamingList;

    /**
     * A constructor for the HistoryManager class.
     */
    public HistoryManager() {
        this.renamingList = FXCollections.observableArrayList();
    }

    public ObservableList<String> getRenamingList() {
        return renamingList;
    }

    /**
     * This method adds a new rename event to the renamingList Array.
     *
     * @param oldName The old name of the image in a String format.
     * @param newName The new name added to the image, in a string format.
     */
    private void addRenameEvent(String oldName, String newName) {
        // Add the current timestamp, the Old Name, and the NewName into the
        // ArrayList.
        String timeStamp = new SimpleDateFormat().format(new Date());
        this.renamingList.add(
                "Time: " + timeStamp + ", " + "Old Name: " + oldName + ", " +
                        "New Name: " + newName + System.lineSeparator());
    }

    @Override
    public void update(Observable o, Object arg) {
        String[] namesToLog = (String[]) arg;
        String oldName = namesToLog[0];
        String newName = namesToLog[1];
        this.addRenameEvent(oldName, newName);
    }

    public void testMethod() {
        for (String s : this.renamingList ) {
            System.out.println(s);

        }
    }
}
