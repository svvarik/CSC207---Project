package tag;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TagmanagerTest {

	@Test
	public void addTagTest() {
		TagManager tm = new TagManager();
		Tag tag = new Tag("tag1", tm);

		// add a tag
		tm.addTag(tag);
		assertEquals(tm.getAllTags().size(), 1);

		//add the same tag name again, it should not add it again
		tm.addTag(tag);
		assertEquals(tm.getAllTags().size(), 1);

		//add another different tag
		Tag tag2 = new Tag("tag2", tm);
		tm.addTag(tag2);
		assertEquals(tm.getAllTags().size(), 2);

		//add a name of a tag, type String
		tm.addTag("tag1");
		assertEquals(tm.getAllTags().size(), 2); // it should not add the same name tag

		tm.addTag("tag3");
		assertEquals(tm.getAllTags().size(), 3);  //it can add a different String
	}

	@Test
	public void removeTagTest() {
		TagManager tm = new TagManager();
		tm.addTag("tag1");
		tm.addTag("tag2");
		tm.addTag("tag3");

		assertEquals(tm.getAllTags().size(), 3);
		tm.removeTag("@tag3");
		//figure out why the size is still 3, even after removing tag
		assertEquals(tm.getAllTags().size(), 2);

	}

	@Test
	public void findTagTest() {
		TagManager tm = new TagManager();
		Tag tag = new Tag("tag1", tm);
		tm.addTag("tag2");

		Tag t = tm.findTag("tag3");
		assertNull(t);

		Tag t2 = tm.findTag("tag2");
		assertNull(t2);


	}

	@Test
	public void containsTagTest() {
		TagManager tm = new TagManager();
		Tag tag = new Tag("tag1", tm);
		tm.addTag("tag2");
		tm.addTag("tag3");

		//figure out why assert true is not working
		assertTrue(tm.containsTag("@tag2"));
		assertFalse(tm.containsTag("@tag4"));

	}

	@Test
	public void getAllTagsTest() {
		TagManager tm = new TagManager();
		Tag tag = new Tag("tag1", tm);
		tm.addTag("tag2");
		tm.addTag("tag3");

		ObservableList<Tag> obv = FXCollections.observableArrayList();
		obv = tm.getAllTags();
		assertEquals(obv.get(0).toString(), "@tag1");
		assertEquals(obv.get(1).toString(), "@tag2");
		assertEquals(obv.get(2).toString(), "@tag3");

	}

	@Test
	public void setAllTagsTest() {
		TagManager tm = new TagManager();
		ObservableList<Tag> tags = FXCollections.observableArrayList();
		tags.add(new Tag("tag1", tm));
		tags.add(new Tag("tag2", tm));

		TagManager tm2 = new TagManager();
		tm2.setAllTags(tags);

		assertEquals(tm2.getAllTags(), tags);

	}





}
