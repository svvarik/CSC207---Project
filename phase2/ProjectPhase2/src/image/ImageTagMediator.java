package image;
import tag.TagManager;
import tag.Tag;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * This static class maintains the relationships between a Tag and an ImageFile.
 * It allows Tags to be added and removed to ImageFiles.
 */
public class ImageTagMediator {

    /**
     * This method adds a given Tag to an ImageFile.
     *
     * @param image The ImageFile the user wishes to add a Tag to.
     * @param newTag The given Tag being attached to the image.
     */
    static public void addTagToImage(ImageFile image, String newTag, TagManager manager){

        if (Pattern.matches("^[@]?[a-zA-Z0-9]+", newTag)) {
            String modifiedNewTag = newTag;
            //Checks to see if the user is typing in the tag, or were picking a tag with @sign already from sidebar
            if (!newTag.contains("@")) {
                modifiedNewTag = "@" + modifiedNewTag;
            }
            if ((!image.hasTag(modifiedNewTag))) {
                Tag imageTag = manager.findTag(modifiedNewTag);
                if (imageTag == null) {
                    imageTag = new Tag(modifiedNewTag, manager);
                }
                image.tags.add(imageTag);
                String imagePath = image.getImagePath();
                image.rename(imagePath);
                if (!image.getImageHistory().contains(image.getTaggedName())) {
                    image.getImageHistory().add(image.getTaggedName());
                }
            }
        }

    }

    /**
     * This method removes a Tag from an ImageFile.
     *
     * @param image The ImageFile that the user wishes to remove a Tag from.
     * @param deletedTag The Tag being removed from the image.
     */
    static public void removeTagFromImage(ImageFile image, Tag deletedTag) {
        image.tags.remove(deletedTag);
        String imagePath = image.getImagePath();
        image.rename(imagePath);
        if (!image.getImageHistory().contains(image.getTaggedName())) {
            image.getImageHistory().add(image.getTaggedName());
        }

    }

    /**
     * Revert an image's Tags from an older set which was previously used.
     *
     * @param tagList The array of Tags being reverted to.
     * @param image The image which is being reverted.
     */
    static public void revertToOldName(ArrayList<Tag> tagList, ImageFile image, String oldFileName, TagManager tm) {
        // Retrieve tags from an older name, retrieveTags(String fileName)
        // Remove the set of current tags, removeAllTags(image)
        // Add the set of tags to this image

        int end = oldFileName.lastIndexOf(".");
        String[] oldFileParts = oldFileName.substring(0, end).split("@");
        ArrayList<String> oldDesiredTags = new ArrayList<>();
        for (int i = 1; i < oldFileParts.length; i++) {
            oldDesiredTags.add("@" + oldFileParts[i]);
        }
        image.tags.clear();
        image.addSetOfTags(oldDesiredTags, tm);
    }
}

/**
 * Retrieve Tags from a given String by looking for words that
 * are in "@tag" format.
 *
 * @param fileName The file name the method is collecting Tags from.
 * @return The ArrayList of all Tags found in the file name.
 */

//static public ArrayList<Tag> retrieveTags(String fileName){
//return null;
//}

/**
 * This method removes all Tags from a current image. j
 * @param image The image where all Tags are being removed.
 */
//static public void removeAllTags(ImageFile image) {

//}

/**
 * Add a set of Tags in an ArrayList to an ImageFile.
 * @param tags The ArrayList of Tags being added.
 * @param image The image being added to.
 */
//static public void addTags(ArrayList<Tag> tags, ImageFile image) {

//}
//}
