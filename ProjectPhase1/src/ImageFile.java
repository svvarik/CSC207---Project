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
    ObservableList<Tag> tags;

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



    /** Construct a new ImageFile object*/
    public ImageFile(String filePath) {
        this.tags = FXCollections.observableArrayList();
        this.filePath = filePath;
        int start = this.filePath.lastIndexOf("\\") + 1;
        int end = this.filePath.lastIndexOf(".");
        this.fileName = this.filePath.substring(start, end);
        //this.rename();   //when the class is initialized, this adds nothing..im assuming
        this.thisImageHistory.add(this.toString());
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
            this.tags.add(imageTag);
            imageTag.addImage(this);
            this.rename();
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
        deletedTag.removeImage(this);
        this.rename();
        thisImageHistory.add(this.toString());
        this.nameVersion += 1;
    }

    /** Update the taggedName of the ImageFile to include all its current tags.*/
    private void rename(){
        StringBuilder tagsName = new StringBuilder();
        for (Tag tag : this.tags) {
            tagsName.append(tag.toString());
        }
        this.taggedName = this.fileName + tagsName;
        int firstSplit = this.filePath.lastIndexOf("\\");
        int secondSplit = this.filePath.lastIndexOf(".");
        File thisImage = new File(this.filePath);
        this.filePath = this.filePath.substring(0, firstSplit + 1) + this.taggedName +
                this.filePath.substring(secondSplit, this.filePath.length());
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

    /** Return all the Tags of the ImageFile.
     *
     * @return all the current tags of the ImageFile.
     */
    ObservableList<Tag> getTags() {
        return this.tags;
    }

    boolean hasTag(String tag){
        for (Tag t : this.tags) {
            if (t.toString().equals(tag)){
                return true;
            }
        } return false;
    }
    public String toString(){
        String[] names = this.fileName.split("/");
        return this.fileName;
    }
}
