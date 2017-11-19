import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Observable;

public class TagManager {

    private ObservableList<Tag> allTags;

    public TagManager() {
        this.allTags = FXCollections.observableArrayList();
    }

    /**
     * Take in a user inputted string and split it using String regex based on
     * spaces and commas. Create a Tag for each object, add it to the list of
     * all tags, and to the specified image.
     * 
     * @param userInputtedTags A user inputted String with one or more Tags, 
     *                         separated by commas. 
     */
    void inputTags(String userInputtedTags, ImageFile thisImage) {
        String[] arrayOfTags = userInputtedTags.split(" ,");
        
        for(int i = 0; i < arrayOfTags.length; i = i + 1) {
            Tag newTag = new Tag(arrayOfTags[i]);
            this.getAllTags().add(newTag);
            //thisImage.addTag(newTag);
        }
    }

    // GETTER FOR LIST OF ALL TAGS
    ObservableList<Tag> getAllTags(){
        return this.allTags;
    }

    // SETTER FOR LIST OF ALL AGS
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
        //TagManager.allTheTags.add(tagToBeAdded.getTag());
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
