import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.text.html.HTML;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Keeps track of tags on a particular image
 */
public class ImageFile extends Observable implements Serializable {

    /**
     * the tags given to the image
     */
    transient ObservableList<Tag> tags;

    /**
     * the original image file's name
     */
    private String fileName;

    /**
     * the image file's name with the added tags
     */
    private String taggedName;

    /**
     * The filepath for this Image
     */
    private String filePath;

    /**
     * Every name this image has had
     */
    private ArrayList<String> imageHistory = new ArrayList<>();

    /**
     * An ArrayList of the same Tags as attribute tags. For serialization purposes only.
     */
    private ArrayList<Tag> arrayedTags;


    /**
     * Construct a new ImageFile object
     *
     * @param filePath the file path of the associated image.
     */
    public ImageFile(String filePath) {
        this.tags = FXCollections.observableArrayList();
        this.filePath = filePath;
        //int start = this.filePath.lastIndexOf("\\") + 1;

        File userFile = new File(this.filePath);
        this.fileName = userFile.getName();
        this.taggedName = this.fileName;
        if (!this.imageHistory.contains(this.taggedName)) {
            this.imageHistory.add(this.taggedName);
        }
    }

    /**
     * Returns this ImageFile object's filepath.
     *
     * @return this ImageFile object's filepath.
     */
    String getFilePath() {
        return this.filePath;
    }

    /**
     * Add a new tag to the ImageFile and rename the ImageFile to include the tag
     *
     * @param newTag  the new Tag to tag the ImageFile with.
     * @param manager the TagManager currently used to manage all Tags.
     */
    void addTag(String newTag, TagManager manager) {

        if (!this.hasTag(newTag)) {
            Tag imageTag = manager.findTag(newTag);
            if (imageTag == null) {
                imageTag = new Tag(newTag, manager);
            }
            HistoryManager.tagAdded(this, newTag);
            this.tags.add(imageTag);
            String imagePath = this.newImagePath();
            this.rename(imagePath);
            if (!this.imageHistory.contains(this.taggedName)) {
                this.imageHistory.add(this.taggedName);
            }
        }
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
        HistoryManager.tagDeleted(this, deletedTag.toString());
        if (!this.imageHistory.contains(this.taggedName)) {
            this.imageHistory.add(this.taggedName);
        }
    }

    /**
     * Return the updated file path the image and this ImageFile object should have according to the added or removed
     * Tags.
     *
     * @return the new file path.
     */
    private String newImagePath() {
        StringBuilder tagsName = new StringBuilder();
        for (Tag tag : this.tags) {
            tagsName.append(tag.toString());
        }
        int end = this.fileName.lastIndexOf(".");
        int firstSplit = this.filePath.lastIndexOf("\\");
        int secondSplit = this.filePath.lastIndexOf(".");
        System.out.println(this.filePath.substring(0, firstSplit + 1) + this.fileName.substring(0, end) + tagsName +
                this.filePath.substring(secondSplit, this.filePath.length()));
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
     * Return the taggedName of the ImageFile.
     *
     * @return the taggedName of the ImageFile.
     */
    String getTaggedName() {
        return this.taggedName;
    }

    /**
     * Return the fileName of the ImageFile.
     *
     * @return the fileName of the ImageFile
     */
    String getFileName() {
        return this.fileName;
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
     * Return a String representation of the ImageFile
     *
     * @return the String representation of the ImageFile.
     */
    public String toString() {
        return this.getFilePath();
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
        HistoryManager.nameReverted(this, oldFileName);

        int end = oldFileName.lastIndexOf(".");
        String[] oldFileParts = oldFileName.substring(0, end).split("@");
        ArrayList<String> oldDesiredTags = new ArrayList<>();
        for (int i = 1; i < oldFileParts.length; i++) {
            oldDesiredTags.add("@" + oldFileParts[i]);
        }
        this.tags.clear();
        this.addSetOfTags(oldDesiredTags, manager);
    }

    /**
     * Return all the Tags of the ImageFile.
     *
     * @return all the current tags of the ImageFile.
     */
    ObservableList<Tag> getTags() {
        return this.tags;
    }

    /**
     * Return the ImageHistory of the ImageFile.
     *
     * @return the ImageHistory of the ImageFile.
     */
    ArrayList<String> getImageHistory() {
        return this.imageHistory;
    }

    /**
     * Return the ArrayedTags of this ImageFile.
     *
     * @return the arrayedTags of this ImageFile.
     */
    ArrayList<Tag> getArrayedTags() {
        return this.arrayedTags;
    }

    /**
     * Sets tags to an arrayList.
     *
     * @param tags the tags to convert to arraylist.
     */
    void setTagsToArrayList(ArrayList<Tag> tags) {
        this.arrayedTags = tags;
    }

    /**
     * Sets tags to an observable list.
     *
     * @param tags the tags to convert to observable list.
     */
    void setTagsToObservableList(ObservableList<Tag> tags) {
        this.tags = tags;
    }
}