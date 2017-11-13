import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileFilter;
import java.lang.reflect.Array;
import java.util.*;
import java.io.File;



/** Manages the image files in the opened directory */
public class FileManager {


    /** The current directory that the user has opened */
    private String currentDirectory;
    /** The next directory the user wishes to open */
    private String nextDirectory;
    /** A list of all the image file names contained in files within the current directory */
    public ArrayList<String> imageFiles;
    /** A list of all the image names in the current directory */
    public ArrayList<String> images;

    public static String selectedImage;


    public FileManager(String directory){
        this.currentDirectory = directory;
    }

    /** Recursively looks through and finds all the image and image files in the selected current directory */
    public void manageCurrent(){
            File[] listOfFiles = new File(currentDirectory).listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return (pathname.isDirectory() || pathname.toString().endsWith(".jpg"));
                }
            });
            for (File f : listOfFiles) {
                imageFiles.add(f.getAbsolutePath());
            }
            //findFiles(listOfFiles);
    }

//    public void findFiles(File[] files){
//        for (File file : files) {
//            //String fileName = file.getAbsolutePath();
//            if (file.isDirectory()) {
//                imageFiles.add(file.getAbsolutePath());
//            } else if (file.getAbsolutePath().endsWith(".jpg")){
//                imageFiles.add(file.getAbsolutePath());
//            }
//        }
//    }

    public boolean containsImages(File file){
        File[] files = file.listFiles();
        for (File file2 : files ){
            if (file2.getAbsolutePath().endsWith(".jpg")){
                return true;}
            else {
                containsImages(file);
            }
        }
        return false;
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

    static File[] imageFilesFilter(File directory) {
        String[] imageSuffix = ImageIO.getReaderFileSuffixes();
        FileFilter imageFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                for (String suffix : imageSuffix){
                    if (pathname.toString().endsWith(suffix)) {
                        return true;
                    }
                }
                return (pathname.isDirectory());
            }};
        return directory.listFiles(imageFilter);
    }


}
