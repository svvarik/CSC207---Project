package filemanager;
import javax.imageio.ImageIO;
import java.io.FileFilter;
import java.util.*;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Manages the image files in the opened directory */
public class FileManager {
	//
	// /** The current directory that the user has opened */
	// static String currentDirectory;
	//
	// /** The images and directories in the current directory*/
	// static ObservableList<String> currentDirectoryFiles;

	// public FileManager(String directory){
	// currentDirectory = directory;
	// File[] dirFiles = imageFilesFilter(new File(directory));
	// currentDirectoryFiles =
	// FXCollections.observableList(filesToString(dirFiles));
	// }

	/**
	 * Filters the contents in a directory, only returning a list of files whose full path is to folders
	 * and/or image files in that directory.
	 * @param directory
	 * 			The current directory chosen by the user
	 * @return a list of files, whose file paths are to an image and/or folder in the current directory
	 */
	public static File[] imageFilesFilter(File directory) {
		String[] imageSuffix = ImageIO.getReaderFileSuffixes();
		FileFilter imageFilter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				for (String suffix : imageSuffix) {
					// returns true if the pathname is an image
					if (pathname.toString().endsWith(suffix)) {
						return true;
					}
				}
				// returns true if pathname is a folder
				return (pathname.isDirectory());
			}
		};
		return directory.listFiles(imageFilter);
	}
	

	/**
	 * Returns an array list of string file paths.
	 * @param fileList
	 * 			A list of Files where the String file path of every File element in the list will be stored
	 * 			in an array list
	 * 			
	 * @return the array list of file paths
	 */
	public static ArrayList<String> filesToString(File[] fileList) {
		ArrayList<String> dirFilePaths = new ArrayList<String>();
		for (File f : fileList) {
			dirFilePaths.add(f.getAbsolutePath());
		}
		return dirFilePaths;
	}

	// static void updateCurrentDirectory(String directoryPath) {
	// currentDirectory = directoryPath;
	// File[] dirFiles = imageFilesFilter(new File(directoryPath));
	// currentDirectoryFiles =
	// FXCollections.observableList(filesToString(dirFiles));
	// }
	//
	// @Override
	// public void update(Observable o, Object arg) {
	// updateCurrentDirectory(currentDirectory);
	// System.out.println("Updated");
	// }

	/**
	 * Determines the parent directory of the current directory.
	 * 
	 * @param filePath
	 * 			The file path whose parent directory will be determined
	 * @return  The parent directory's file path
	 */
	public static String getParentDirectory(String filePath) {
		if (filePath != null) {
			File childFile = new File(filePath);
			return childFile.getParent();
		}
		return null;
	}
}
