import java.util.ArrayList;

public class TagManager {

    public static ArrayList<Tag> tagsUsed;

    public TagManager(){
    }

    public void add_tag(Tag tagToBeAdded) {
        TagManager.tagsUsed.add(tagToBeAdded);
    }

    // This might be messy,
    public void remove_tag(Tag tagToBeRemoved) {
        tagsUsed.remove(tagToBeRemoved);
    }

}
