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
    ArrayList<ImageFile> imagesAssociated;

    private String tag;

    public Tag(String tagName) {
        if (Pattern.matches("^[@][a-zA-Z_0-9]*", tagName)) {
            this.tag = tagName;
            TagManager.add_tag(this);
        }
    }

    public String getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Tag && this.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return this.tag;
    }
}
