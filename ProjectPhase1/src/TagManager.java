import java.util.ArrayList;

public class TagManager {

    public static ArrayList<Tag> tagsUsed;

    public TagManager(){
    }

    public static void addTag(Tag tagToBeAdded) {
        TagManager.tagsUsed.add(tagToBeAdded);
    }

    // This might be messy,
    public static void removeTag(Tag tagToBeRemoved) {
        tagsUsed.remove(tagToBeRemoved);
    }

}
