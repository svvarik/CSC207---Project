import javax.swing.text.html.HTML;
import java.util.ArrayList;

public class ImageFile {
    private ArrayList<Tag> tags;
    private String fileName;
    private String name;

    public ImageFile(String fileName){
        this.tags = new ArrayList<Tag>();
        this.fileName = fileName;
        this.rename();
    }

    private void addTag(String newTag) {
        //Find tag in TagManager?
        Tag imageTag = TagManager.tagsUsed.findTag(newTag);
        if (TagManager.tagsUsed.contains(imageTag)) {
            int tagIndex = TagManager.tagsUsed.indexOf(imageTag);
            Tag existingTag = TagManager.tagsUsed.get(tagIndex);
            existingTag.addImage(this.name???);
            this.tags.add(existingTag);
        }
        else{
            Tag imageTag = new Tag(newTag);
        }
        this.tags.add(imageTag);
        imageTag.add_image(this);
        this.rename();
    }

    private void removeTag(Tag deletedTag) {
        this.tags.remove(deletedTag);
        this.rename();
    }

    private void rename(){
        StringBuilder tagsName = new StringBuilder();
        for (Tag tag : this.tags) {
            tagsName.append(tag.toString());
        }
        this.name = this.fileName + tagsName;
    }


}
