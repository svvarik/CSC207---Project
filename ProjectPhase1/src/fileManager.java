import java.util.*;
import java.io.File;



/** Manages the image files in the opened directory */
public class fileManager {


    /** The current directory that the user has opened */
    private String currentDirectory = "Desktop";
    /** The next directory the user wishes to open */
    private String nextDirectory;
    /** A list of all the image file names contained in files within the current directory */
    public ArrayList<String> imageFiles;
    /** A list of all the image names in the current directory */
    public ArrayList<String> images;


    public fileManager(String directory){
        this.currentDirectory = directory;
    }

    /** Recursively looks through and finds all the image and image files in the selected current directory */
    public void manageCurrent(){
        File[] listOfFiles = new File(currentDirectory).listFiles();
        findFiles(listOfFiles);
    }

    public void findFiles(File[] files){
        for (File file : files) {
            String fileName = file.getAbsolutePath();
            if (file.isDirectory() && containsImages(file)) {
                imageFiles.add(fileName);
            } else if (fileName.endsWith(".jpeg")){
                imageFiles.add(file.getAbsolutePath());
            }
        }
    }

    public boolean containsImages(File file){
        boolean status = false;
        File[] Files = new File(currentDirectory).listFiles();
        for (File file2 : Files ){
            if (file2.getAbsolutePath().endsWith(".jpeg"));
            status = true;
            break;
        }
        return status;
    }



    /** Switches directories to the next directory the user has inputted. */
    public void Open(String userInput){
        this.nextDirectory = userInput;
        switchDirectories();
    }

    /** Moves back (out) one directory */
    public void Back(){
        int i = this.currentDirectory.lastIndexOf('/');
        this.nextDirectory = this.currentDirectory.substring(0,(i+1));
        switchDirectories();
    }

    /** Sets the current directory to the next directory */
    private void switchDirectories(){
        this.currentDirectory = this.nextDirectory;
        this.nextDirectory = null;
    }

}
