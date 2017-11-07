import javax.swing.text.html.HTML;
import java.util.ArrayList;

public class ImageFile {
    private ArrayList<Tag> tags;
    private String fileName;
    private String taggedName;

    public ImageFile(String fileName){
        this.tags = new ArrayList<Tag>();
        this.fileName = fileName;
        this.rename();
    }

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

    void removeImageTag(Tag deletedTag) {
        this.tags.remove(deletedTag);
        deletedTag.removeImage(this);
        this.rename();
    }

    private void rename(){
        StringBuilder tagsName = new StringBuilder();
        for (Tag tag : this.tags) {
            tagsName.append(tag.toString());
        }
        this.taggedName = this.fileName + tagsName;
    }

    String getTaggedName(){
        return this.taggedName;
    }

    String getFileName() {
        return this.fileName;
    }

    ArrayList<Tag> getTags() {
        return this.tags;
    }
}
