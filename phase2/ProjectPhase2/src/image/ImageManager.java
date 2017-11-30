package image;

import java.io.IOException;
import java.util.ArrayList;

/** A collection of ImageFiles created and used in the program */
public class ImageManager {

    /** An ArrayList of all the ImageFiles in the program */
    private ArrayList<ImageFile> createdImages;

    /** Constructs a new ImageManager Object */
    public ImageManager() {
        this.createdImages = new ArrayList<ImageFile>();
    }
    /**
     * Returns an ArrayList containing all the ImageFiles in the program
     *
     * @return ArrayList of ImageFiles in the program
     */
    public ArrayList<ImageFile> getCreatedImages() {
        return this.createdImages;
    }

    /**
     * Updates the ImageManager's arrayList of ImageFiles to a new list
     *
     * @param list An ArrayList of ImageFiles to be used as the program's list of existing ImageFiles
     */
    public void setCreatedImages(ArrayList<ImageFile> list) {
        this.createdImages = list;
    }
    /**
     * Adds a new ImageFiles to the ArrayList of existing ImageFiles
     *
     * @param image The ImageFiles to be added
     */
    private void addFile(ImageFile image) {
        this.createdImages.add(image);
    }

    /**
     * Checks to see if there exists an ImageFile with a certain path in the list of existing
     * ImageFiles
     *
     * @param imagePath The absolute path of the ImageFile that is being checked for
     * @return true if there exists an ImageFile with the absolute path in the existing ImageFiles
     */
    public boolean containsImage(String imagePath) {
        for (ImageFile i : this.createdImages) {
            if (imagePath.equals(i.getFilePath())) {
                return true;
            }
        }
        return false;
    }
    /**
     * Returns the ImageFile with the desired absolute path if that ImageFile already exists. If it
     * doesn't creates a new ImageFile.
     *
     * @param imagePath The absolute path of the ImageFile being searched for.
     * @return The ImageFile with the desired absolute path
     * @throws IOException
     */
    public ImageFile findImage(String imagePath) throws IOException {
        for (ImageFile img : this.createdImages) {
            if (imagePath.equals(img.getFilePath())) {
                return img;
            }
        }
        ImageFile thisImage = new ImageFile(imagePath);
        this.addFile(thisImage);
        return thisImage;
    }
}