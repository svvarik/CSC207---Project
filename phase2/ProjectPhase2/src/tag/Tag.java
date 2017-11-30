package tag;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * A Tag object that can be added to, or removed from image file names.
 */
public class Tag implements Serializable {

    private static final long serialVersionUID = 546799;

    /**
     * The String name of the tag.
     */
    private String tag;

    /**
     * Initializes a new Tag object with the name 'tagName'
     * @param tagName The name of the tag
     */
    public Tag(String tagName, TagManager tagManager) {
        if (Pattern.matches("^[@]?[a-zA-Z0-9 ]+", tagName) && !tagName.trim().equals("") &&
                !tagName.trim().equals("@")){
            if (tagName.contains("@")) {
                this.tag = tagName;
            }
            else{
                this.tag = "@" + tagName;
            }
            tagManager.addTag(this);
        }
    }

    /**
     * A getter method that returns the name of the Tag
     * @return String
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * Returns true if this Tag is equal to obj, false otherwise
     * @param obj Another Tag object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Tag && this.toString().equals(obj.toString());
    }

    /**
     * Returns the string representation of the Tag.
     * @return String
     */
    @Override
    public String toString() {
        return this.getTag();
    }
}
