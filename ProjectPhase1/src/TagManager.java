import java.util.ArrayList;

public class TagManager {

    private static ArrayList<Tag> tagsUsed;

    public TagManager() {
    }

    /**
     * Add a tag to tagsUsed, a list of all Tags used in the program.
     *
     * @param tagToBeAdded a Tag that is to be added into tagsUsed
     */
    static void addTag(Tag tagToBeAdded) {
        TagManager.tagsUsed.add(tagToBeAdded);
    }

    /**
     * Remove a tag from the list, tagsUsed. The argument must exist within the
     * list.
     *
     * @param tagToBeRemoved The Tag specified to be removed from the list.
     */
    static void removeTag(Tag tagToBeRemoved) {
        for (int i = 0; i < TagManager.tagsUsed.size(); i = i + 1) {
            if (TagManager.tagsUsed.get(i).equals(tagToBeRemoved)) {
                TagManager.tagsUsed.remove(i);
            }
        }
    }

    /**
     * Search for a Tag with the given String value, and return it if found.
     * If not found, return null.
     *
     * @param tagToBeFound A string object that represents the information in
     *                     the tag.
     * @return Tag
     */
    static Tag findTag(String tagToBeFound) {
        for (int i = 0; i < TagManager.tagsUsed.size(); i = i + 1) {
            if (TagManager.tagsUsed.get(i).toString().equals(tagToBeFound)) {
                return TagManager.tagsUsed.get(i);
            }
        }
        return null;
    }
}
