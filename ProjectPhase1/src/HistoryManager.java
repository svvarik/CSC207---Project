package sample;

import java.util.ArrayList;
import java.io.

/**
 * Keeps track of all renaming made. Class stores
 */
public class HistoryManager {

    // List of all renaming done
    ArrayList<String> renamingList = new ArrayList<>();

    public HistoryManager(){
    }

    void addRenaming(String renaming){
        this.renamingList.add(renaming);
    }

    void saveToDisk(){

    }

}
