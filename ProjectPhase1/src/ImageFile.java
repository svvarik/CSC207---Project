import javax.swing.text.html.HTML;
import java.io.File;
import java.util.ArrayList;

/** Keeps track of tags on a particular image */
public class ImageFile {

    /** the tags given to the image */
    private ArrayList<Tag> tags;

    /** the original image file's name */
    private String fileName;

    /** the image file's name with the added tags*/
    private String taggedName;

    /** The filepath for this Image **/
    String filePath;

    /** Construct a new ImageFile object*/
    public ImageFile(String filePath) {
        this.tags = new ArrayList<Tag>();
        this.filePath = filePath;
        int start = this.filePath.lastIndexOf("\\") + 1;
        int end = this.filePath.lastIndexOf(".");
        this.fileName = this.filePath.substring(start, end);
        //this.rename();   //when the class is initialized, this adds nothing..im assuming
    }

    /** Add a new tag to the ImageFile and rename the ImageFile to include the tag
     *
     * @param newTag the new Tag to tag the ImageFile with.
     */
    void addTag(String newTag) {
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
        System.out.println(this.fileName);
        //Still trying to figure this portion out with the renaming/changing filepath
        //int name = this.fileName.lastIndexOf('.');
        this.taggedName = this.fileName + tagsName;
        System.out.println(this.taggedName);
        int firstSplit = this.filePath.lastIndexOf("\\");
        int secondSplit = this.filePath.lastIndexOf(".");
        File thisImage = new File(this.filePath);
        System.out.println(thisImage.getAbsolutePath());
        this.filePath = this.filePath.substring(0, firstSplit + 1) + this.taggedName +
                this.filePath.substring(secondSplit, this.filePath.length());
        System.out.println(this.filePath);
        thisImage.renameTo(new File(this.filePath));
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

    boolean hasTag(String tag){
        for (Tag t : this.tags) {
            if (t.toString().equals(tag)){
                return true;
            }
        } return false;
    }
}
