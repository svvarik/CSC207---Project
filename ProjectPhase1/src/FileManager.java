import javax.imageio.ImageIO;
import java.io.FileFilter;
import java.util.*;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/** Manages the image files in the opened directory */
public class FileManager implements Observer {

    /** The current directory that the user has opened */
    static String currentDirectory;

    /** The images and directories in the current directory*/
    static ObservableList<String> currentDirectoryFiles;

    public FileManager(String directory){
        currentDirectory = directory;
        File[] dirFiles = imageFilesFilter(new File(directory));
        currentDirectoryFiles = FXCollections.observableList(filesToString(dirFiles));
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

    private static ArrayList<String> filesToString(File[] fileList) {
        ArrayList<String> dirFilePaths = new ArrayList<String>();
        for (File f : fileList) {
            dirFilePaths.add(f.getAbsolutePath());
        }
        return dirFilePaths;
    }

    static void updateCurrentDirectory(String directoryPath) {
        currentDirectory = directoryPath;
        File[] dirFiles = imageFilesFilter(new File(directoryPath));
        currentDirectoryFiles = FXCollections.observableList(filesToString(dirFiles));
    }

    @Override
    public void update(Observable o, Object arg) {
        updateCurrentDirectory(currentDirectory);
        System.out.println("Updated");
    }

    static String getParentDirectory(String filePath) {
        if (filePath != null) {
            File childFile = new File(filePath);
            return childFile.getParent();
        }
        return null;
    }
}
