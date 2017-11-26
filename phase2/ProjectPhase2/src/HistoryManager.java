import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;


/** A History Manager class that keeps track of all renaming done, and logs data **/

public class HistoryManager {
    /** A formatted array list of all the renaming done in the program, */
    private ArrayList<String> renamingList;
    private String saveFilePath;

    /**
     * A constructor for the HistoryManager class.
     */
    public HistoryManager(){
        this.renamingList = new ArrayList<>();
    }

    /**
     * Keeps an array list of all the renaming done, tags added and deleted, in the program and
     * updates history.txt with the changes once the program is exited.
     */
    public void createSaveFile() throws SecurityException, IOException {
        // Create a new File object and set the saveFilePath
        File newFile = new File(".history.txt");
        this.saveFilePath = newFile.getAbsolutePath();

        // Create a new writer object to create / and write to the file.
        OutputStreamWriter writer = new FileWriter(newFile, true);
        BufferedWriter buffer = new BufferedWriter(writer);

        for (String s : this.renamingList) {
            buffer.write(s);
            buffer.newLine();
        }
        buffer.close();
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
