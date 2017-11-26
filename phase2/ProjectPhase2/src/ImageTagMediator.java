import java.util.ArrayList;

/**
 * This static class maintains the relationships between a Tag and an ImageFile.
 * It allows Tags to be added and removed to ImageFiles.
 */
public class ImageTagMediator {

    /**
     * This method adds a given Tag to an ImageFile.
     *
     * @param image The ImageFile the user wishes to add a Tag to.
     * @param tag The given Tag being attached to the image.
     */
    static public void addTagToImage(ImageFile image, Tag tag){

    }

    /**
     * This method removes a Tag from an ImageFile.
     *
     * @param image The ImageFile that the user wishes to remove a Tag from.
     * @param tag The Tag being removed from the image.
     */
    static public void removeTagFromImage(ImageFile image, Tag tag) {

    }

    /**
     * Revert an image's Tags from an older set which was previously used.
     *
     * @param tagList The array of Tags being reverted to.
     * @param image The image which is being reverted.
     */
    static public void revertToOldName(ArrayList<Tag> tagList, ImageFile image) {
        // Retrieve tags from an older name, retrieveTags(String fileName)
        // Remove the set of current tags, removeAllTags(image)
        // Add the set of tags to this image

    }

    /**
     * Retrieve Tags from a given String by looking for words that
     * are in "@tag" format.
     *
     * @param fileName The file name the method is collecting Tags from.
     * @return The ArrayList of all Tags found in the file name.
     */
    static public ArrayList<Tag> retrieveTags(String fileName){
        return null;
    }

    /**
     * This method removes all Tags from a current image. j
     * @param image The image where all Tags are being removed.
     */
    static public void removeAllTags(ImageFile image) {

    }

    /**
     * Add a set of Tags in an ArrayList to an ImageFile.
     * @param tags The ArrayList of Tags being added.
     * @param image The image being added to.
     */
    static public void addTags(ArrayList<Tag> tags, ImageFile image) {

    }
}
