import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.text.html.HTML;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/** Keeps track of tags on a particular image */
public class ImageFile extends Observable implements Serializable {

    /** the tags given to the image */
    transient ObservableList<Tag> tags;

    /** the original image file's name */
    private String fileName;

    /** the image file's name with the added tags*/
    private String taggedName;

    /** The filepath for this Image **/
    private String filePath;

    /** Every name this image has had */
    private ArrayList<String> thisImageHistory = new ArrayList<>();

    /** The current version of the name being used */
    private int nameVersion = 0;

    // ArrayList of all Tags for serialization
    ArrayList<Tag> arrayedTags;


    /** Construct a new ImageFile object*/
    public ImageFile(String filePath) {
        this.tags = FXCollections.observableArrayList();
        this.filePath = filePath;
        //int start = this.filePath.lastIndexOf("\\") + 1;

        File userFile = new File(this.filePath);
        this.fileName = userFile.getName();
        this.thisImageHistory.add(this.toString());
        this.taggedName = this.fileName;
    }

    String getFilePath(){
        return this.filePath;
    }

    /** Add a new tag to the ImageFile and rename the ImageFile to include the tag
     *
     * @param newTag the new Tag to tag the ImageFile with.
     */
    void addTag(String newTag) {

        if (!this.hasTag(newTag)) {
            Tag imageTag = TagManager.findTag(newTag);
            if (imageTag == null) {
                imageTag = new Tag(newTag);
            }
            HistoryManager.tagAdded(this,newTag);
            this.tags.add(imageTag);
            String imagePath = this.newImagePath();
            this.rename(imagePath);
            thisImageHistory.add(this.toString());
            this.nameVersion += 1;
        }
    }

    /** Remove an existing tag from ImageFile.
     *
     * @param deletedTag the Tag to remove from the ImageFile.
     */
    void removeImageTag(Tag deletedTag) {
        this.tags.remove(deletedTag);
        String imagePath = this.newImagePath();
        this.rename(imagePath);
        HistoryManager.tagDeleted(this, deletedTag.toString());
        thisImageHistory.add(this.toString());
        this.nameVersion += 1;
    }

    private String newImagePath(){
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
    /** Update the taggedName of the ImageFile to include all its current tags.*/
    void rename(String imagePath) {
        File thisImage = new File(filePath);
        this.filePath = imagePath;

        // to update the taggedName, in case there were any changes
        File file = new File(this.filePath);
        this.taggedName = file.getName();
        System.out.println("substring: " + this.taggedName);
        thisImage.renameTo(new File(this.filePath));
        setChanged();
        notifyObservers();
    }

    /** Return the taggedName of the ImageFile.
     *
     * @return the taggedName of the ImageFile.
     */
    String getTaggedName(){
        return this.taggedName;
    }

    /** Return the fileName of the ImageFile.
     *
     * @return the fileName of the ImageFile
     */
    String getFileName() {
        return this.fileName;
    }


    boolean hasTag(String tag){
        for (Tag t : this.tags) {
            if (t.toString().equals(tag)){
                return true;
            }
        } return false;
    }


    public String toString(){
        return this.getFilePath();
    }


    /** Return all the Tags of the ImageFile.
     *
     * @return all the current tags of the ImageFile.
     */
    ObservableList<Tag> getTags() {
        return this.tags;
    }

    ArrayList<Tag> getArrayedTags(){
        return this.arrayedTags;
    }

    // SETS TAGS TO ARRAYLISTS
    void setTagsToArrayList(ArrayList<Tag> tags) {
        this.arrayedTags = tags;
    }

    // SETS TAGS BACK TO OBSERVABLE LIST
    void setTagsToObservableList(ObservableList<Tag> tags) {
        this.tags = tags;
    }
}
