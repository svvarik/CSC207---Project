import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * This class represents the Tag that can be added to Image filenames, in order
 * to sort through and categorize images.
 */
public class Tag {

    // List of all images that this tag is used in
    private ArrayList<ImageFile> imagesAssociated = new ArrayList<>();

    private String tag;

    public Tag(String tagName) {
        if (Pattern.matches("^[@][a-zA-Z_0-9]*", tagName)) {
            this.tag = tagName;
            TagManager.addTag(this);
        }
    }

    public String getTag() {
        return this.tag;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Tag && this.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return this.getTag();
    }

    void addImage(ImageFile file) {
        this.imagesAssociated.add(file);
    }

    void removeImage(ImageFile file) {
        this.imagesAssociated.remove(file);
    }
}
