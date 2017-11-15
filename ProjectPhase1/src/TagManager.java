import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TagManager {

    //static ArrayList<Tag> tagsUsed = new ArrayList<Tag>();

    static ObservableList<Tag> allTags = FXCollections.observableArrayList();

    public TagManager() {
    }

    /**
     * Take in a user inputted string and split it using String regex based on
     * spaces and commas. Create a Tag for each object, add it to the list of
     * all tags, and to the specified image.
     * 
     * @param userInputtedTags A user inputted String with one or more Tags, 
     *                         separated by commas. 
     */
    static void inputTags(String userInputtedTags, ImageFile thisImage) {
        String[] arrayOfTags = userInputtedTags.split(" ,");
        
        for(int i = 0; i < arrayOfTags.length; i = i + 1) {
            Tag newTag = new Tag(arrayOfTags[i]);
            allTags.add(newTag);
            //thisImage.addTag(newTag);
        }
    }

    // GETTER FOR LIST OF ALL TAGS
    static ObservableList<Tag> getAllTags(){
        return TagManager.allTags;
    }

    // SETTER FOR LIST OF ALL AGS
    static void setAllTags(ObservableList<Tag> list){
        TagManager.allTags = list;
    }
    /**
     * Add a tag to tagsUsed, a list of all Tags used in the program.
     *
     * @param tagToBeAdded a Tag that is to be added into tagsUsed
     */
    static void addTag(Tag tagToBeAdded) {
        TagManager.allTags.add(tagToBeAdded);
        //TagManager.allTheTags.add(tagToBeAdded.getTag());
    }

    /**
     * Remove a tag from the list, tagsUsed. The argument must exist within the
     * list.
     *
     * @param tagToBeRemoved The Tag specified to be removed from the list.
     */
    static void removeTag(Tag tagToBeRemoved) {
        for (int i = 0; i < TagManager.allTags.size(); i = i + 1) {
            if (TagManager.allTags.get(i).equals(tagToBeRemoved)) {
                TagManager.allTags.remove(i);
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
        for (int i = 0; i < TagManager.allTags.size(); i = i + 1) {
            if (TagManager.allTags.get(i).toString().equals(tagToBeFound)) {
                return TagManager.allTags.get(i);
            }
        }
        return null;
    }
}
