import javax.swing.text.html.HTML;
import java.util.ArrayList;

/** Keeps track of tags on a particular image */
public class ImageFile {

    /** the tags given to the image */
    private ArrayList<Tag> tags;

    /** the original image file's name */
    private String fileName;

    /** the image file's name with the added tags*/
    private String taggedName;

    /** Construct a new ImageFile object*/
    public ImageFile(String fileName){
        this.tags = new ArrayList<Tag>();
        this.fileName = fileName;
        this.rename();
    }

    /** Add a new tag to the ImageFile and rename the ImageFile to include the tag
     *
     * @param newTag the new Tag to tag the ImageFile with.
     */
    private void addTag(String newTag) {
        //Find tag in TagManager?
        Tag imageTag = TagManager.findTag(newTag);
        if (imageTag == null) {
            imageTag = new Tag(newTag);
        }
        this.tags.add(imageTag);
        imageTag.addImage(this);
        this.rename();
    }

    /** Remove an existing tag from ImageFile.
     *
     * @param deletedTag the Tag to remove from the ImageFile.
     */
    void removeImageTag(Tag deletedTag) {
        this.tags.remove(deletedTag);
        deletedTag.removeImage(this);
        this.rename();
    }

    /** Update the taggedName of the ImageFile to include all its current tags.*/
    private void rename(){
        StringBuilder tagsName = new StringBuilder();
        for (Tag tag : this.tags) {
            tagsName.append(tag.toString());
        }
        this.taggedName = this.fileName + tagsName;
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
    ArrayList<Tag> getTags() {
        return this.tags;
    }
}
