/**
 * This class represents the Tag that can be added to Image filenames, in order
 * to sort through and categorize images.
 */
public class Tag {

    private String tag;

    private Tag(String tagName) {
        this.tag = tagName;
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
