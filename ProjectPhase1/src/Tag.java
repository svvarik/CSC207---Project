import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * This class represents the Tag that can be added to Image filenames, in order
 * to sort through and categorize images.
 */
public class Tag implements Serializable {

    private String tag;

    public Tag(String tagName) {
        if (Pattern.matches("^[@][a-zA-Z0-9]*", tagName)) {
            this.tag = tagName;
            //TagManager.addTag(this);
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
}
