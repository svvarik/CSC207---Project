import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TagManager {

    private ObservableList<Tag> allTags;

    /** Construct a new TagManager object
     *
     */
    public TagManager() {
        this.allTags = FXCollections.observableArrayList();
    }

    /** Returns allTags.
     *
     * @return the observableList allTags
     */
    ObservableList<Tag> getAllTags(){
        return this.allTags;
    }

    /** Set allTags to list.
     *
     * @param list the observableList to set allTags to.
     */
    void setAllTags(ObservableList<Tag> list){
        this.allTags = list;
    }
    /**
     * Add a tag to tagsUsed, a list of all Tags used in the program.
     *
     * @param tagToBeAdded a Tag that is to be added into tagsUsed
     */
    void addTag(Tag tagToBeAdded) {
        this.allTags.add(tagToBeAdded);
    }

    /**
     * Remove a tag from the list, tagsUsed. The argument must exist within the
     * list.
     *
     * @param tagToBeRemoved The Tag specified to be removed from the list.
     */
    void removeTag(Tag tagToBeRemoved) {
        for (int i = 0; i < this.allTags.size(); i = i + 1) {
            if (this.allTags.get(i).equals(tagToBeRemoved)) {
                this.allTags.remove(i);
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
    Tag findTag(String tagToBeFound) {
        for (int i = 0; i < this.allTags.size(); i = i + 1) {
            if (this.allTags.get(i).toString().equals(tagToBeFound)) {
                return this.allTags.get(i);
            }
        }
        return null;
    }
}
