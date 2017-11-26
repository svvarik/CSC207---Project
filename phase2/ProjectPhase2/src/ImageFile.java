import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.text.html.HTML;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.regex.Pattern;

/**
 * Keeps track of tags on a particular image
 */
public class ImageFile extends Observable implements Serializable {

    /** The tags given to the image */
    transient ObservableList<Tag> tags;
    /** An ArrayList all the tags, for serialization purposes. */
    private ArrayList<Tag> arrayedTags;
    /** The original image file's name */
    private String fileName;
    /** The image file's name with the added tags */
    private String taggedName;
    /** The absolute filepath for this Image */
    private String filePath;
    /** Every name this image has had */
    private ArrayList<String> imageHistory = new ArrayList<>();

    ObservableList<Tag> getTags() {
        return this.tags;
    }

    void setTagsToObservableList(ObservableList<Tag> tags) {
        this.tags = tags;
    }

    ArrayList<Tag> getArrayedTags() {
        return this.arrayedTags;
    }

    void setTagsToArrayList(ArrayList<Tag> tags) {
        this.arrayedTags = tags;
    }

    ArrayList<String> getImageHistory() {
        return this.imageHistory;
    }

    String getFilePath() {
        return this.filePath;
    }

    String getTaggedName() {
        return this.taggedName;
    }

    /**
     * Construct a new ImageFile object.
     *
     * @param filePath The file path of the image that we are associating
     *                 this ImageFile with.
     */
    public ImageFile(String filePath) {
        this.tags = FXCollections.observableArrayList();
        this.filePath = filePath;
        File userFile = new File(this.filePath);
        this.fileName = userFile.getName();
        this.taggedName = this.fileName;
        // TODO: What is this???? Add this to JavaDoc??
        if (!this.imageHistory.contains(this.taggedName)) {
            this.imageHistory.add(this.taggedName);
        }
    }

    /**
     * Add a new tag to the ImageFile and rename the ImageFile to include the tag
     *
     * @param newTag  the new Tag to tag the ImageFile with.
     * @param manager the TagManager currently used to manage all Tags.
     */
    void addTag(String newTag, TagManager manager) {

        if (!Pattern.matches("^[@]?[a-zA-Z0-9]+", newTag)) {
            return;
        }
        //Checks to see if the user is typing in the tag, or were picking a tag with @sign already from sidebar
        String modifiedNewTag = newTag;
        if (!newTag.contains("@")){
            modifiedNewTag = "@" + modifiedNewTag;
        }

        if ((!this.hasTag(modifiedNewTag))){
            Tag imageTag = manager.findTag(modifiedNewTag);
            if (imageTag == null) {
                imageTag = new Tag(newTag, manager);
            }
            HistoryManager.tagAdded(this, modifiedNewTag);
            this.tags.add(imageTag);
            String imagePath = this.newImagePath();
            this.rename(imagePath);
            if (!this.imageHistory.contains(this.taggedName)) {
                this.imageHistory.add(this.taggedName);
            }
        }
    }

    /**
     * Return true iff this ImageFile does not have the given tag.
     *
     * @param tag the tag to check for.
     * @return true iff this ImageFile does not have the given tag.
     */
    boolean hasTag(String tag) {
        for (Tag t : this.tags) {
            if (tag.equals(t.toString())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Return the updated file path, with Tags added or removed.
     *
     * @return The new absolute file path to this image.
     */
    private String newImagePath() {
        StringBuilder tagsName = new StringBuilder();
        for (Tag tag : this.tags) {
            tagsName.append(tag.toString());
        }
        int end = this.fileName.lastIndexOf(".");
        int firstSplit = this.filePath.lastIndexOf(File.separator);
        int secondSplit = this.filePath.lastIndexOf(".");
        return this.filePath.substring(0, firstSplit + 1) + this.fileName.substring(0, end) + tagsName +
                this.filePath.substring(secondSplit, this.filePath.length());
    }

    /**
     * Update the taggedName of the ImageFile to include all its current tags and adjust the associated image's
     * filepath according to the tags.
     *
     * @param imagePath the new file path to change the associated image's filepath to.
     */
    void rename(String imagePath) {
        File thisImage = new File(filePath);
        this.filePath = imagePath;

        // to update the taggedName, in case there were any changes
        File file = new File(this.filePath);
        this.taggedName = file.getName();
        thisImage.renameTo(new File(this.filePath));
        setChanged();
        notifyObservers();
    }

    /**
     * Remove an existing tag from ImageFile.
     *
     * @param deletedTag the Tag to remove from the ImageFile.
     */
    void removeImageTag(Tag deletedTag) {
        this.tags.remove(deletedTag);
        String imagePath = this.newImagePath();
        this.rename(imagePath);
        if (!this.imageHistory.contains(this.taggedName)) {
            this.imageHistory.add(this.taggedName);
        }
    }

    /**
     * Add all tags in the ArrayList setOfTags to this ImageFile's tags.
     *
     * @param setOfTags an ArrayList of tags to add to this ImageFile's tags.
     * @param manager   the TagManager currently used to manage all the tags.
     */
    private void addSetOfTags(ArrayList<String> setOfTags, TagManager manager) {
        for (String tag : setOfTags) {
            if (!this.hasTag(tag)) {
                Tag imageTag = manager.findTag(tag);
                if (imageTag == null) {
                    imageTag = new Tag(tag, manager);
                }
                this.tags.add(imageTag);
                System.out.println(imageTag.toString());
            }
        }
        String imagePath = this.newImagePath();
        this.rename(imagePath);
    }

    /**
     * Change this ImageFile back to an old state, with the oldFileName the user chooses. Also updates the filepath and
     * name of the associated image.
     *
     * @param oldFileName the FileName the user wants to revert back to.
     * @param manager     the TagManager currently used to manage all the tags.
     */
    void revertToOlderTags(String oldFileName, TagManager manager) {

        int end = oldFileName.lastIndexOf(".");
        String[] oldFileParts = oldFileName.substring(0, end).split("@");
        ArrayList<String> oldDesiredTags = new ArrayList<>();
        for (int i = 1; i < oldFileParts.length; i++) {
            oldDesiredTags.add("@" + oldFileParts[i]);
        }
        this.tags.clear();
        this.addSetOfTags(oldDesiredTags, manager);
    }
}