import java.util.ArrayList;

public class TagManager {

    public static ArrayList<Tag> tagsUsed;

    public TagManager() {
    }

    public static void addTag(Tag tagToBeAdded) {
        TagManager.tagsUsed.add(tagToBeAdded);
    }

    // This might be messy,
    public static void removeTag(Tag tagToBeRemoved) {
        TagManager.tagsUsed.remove(tagToBeRemoved);
    }

    public static Tag findTag(String tagToBeFound) {
        for (int i = 0; i < TagManager.tagsUsed.size(); i = i + 1) {
            if (TagManager.tagsUsed.get(i).toString().equals(tagToBeFound)) {
                return TagManager.tagsUsed.get(i);
            }
        }
        return null;
    }
}
