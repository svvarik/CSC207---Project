package tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A collection of tags used and created in the program
 */
public class TagManager {

	private ObservableList<Tag> allTags;

	/**
	 * Construct a new TagManager object
	 */
	public TagManager() {
		this.allTags = FXCollections.observableArrayList();
	}

	/**
	 * Returns allTags.
	 *
	 * @return the observableList allTags
	 */
	public ObservableList<Tag> getAllTags() {
		return this.allTags;
	}

	/**
	 * Set allTags to list.
	 *
	 * @param list
	 *            the observableList to set allTags to.
	 */
	public void setAllTags(ObservableList<Tag> list) {
		this.allTags = list;
	}

	/**
	 * Add a tag to tagsUsed, a list of all Tags used in the program.
	 *
	 * @param tagToBeAdded
	 *            a Tag that is to be added into tagsUsed
	 */
	void addTag(Tag tagToBeAdded) {
		if (!this.containsTag(tagToBeAdded.toString())) {
			this.allTags.add(tagToBeAdded);
		}
	}

	// This allows users to add their own tags independent of images
	public void addTag(String tagToBeAdded) {
		new Tag(tagToBeAdded, this);
	}

	/**
	 * Remove a tag from the list, tagsUsed. The argument must exist within the
	 * list.
	 *
	 * @param tagToBeRemoved
	 *            The Tag specified to be removed from the list.
	 */
	public void removeTag(String tagToBeRemoved) {
		for (int i = 0; i < this.allTags.size(); i++) {
			if (this.allTags.get(i).toString().equals(tagToBeRemoved)) {
				this.allTags.remove(i);
			}
		}
	}

	/**
	 * Search for a Tag with the given String value, and return it if found. If
	 * not found, return null.
	 *
	 * @param tagToBeFound
	 *            A string object that represents the information in the tag.
	 * @return Tag
	 */
	public Tag findTag(String tagToBeFound) {
		for (int i = 0; i < this.allTags.size(); i = i + 1) {
			if (this.allTags.get(i).toString().equals(tagToBeFound)) {
				return this.allTags.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Checks if the String tag is contained in the TagManager collection
	 * @param tag
	 * @return  boolean value, true if tag exists in the collection, false otherwise
	 */

	boolean containsTag(String tag) {
		for (Tag existingTag : allTags) {
			if (existingTag.toString().equals(tag)) {
				return true;
			}
		}
		return false;
	}
}
