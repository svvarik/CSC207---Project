import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImagefileTest{

    @Test
    public void getTagsTest() throws IOException {
        String path = new File(".").getAbsolutePath();
        ImageFile image = new ImageFile(path + File.separator + "TestImages"+ File.separator + "Scenery.jpg");
        assertEquals(image.getTags().size(), 0);

        TagManager tm = new TagManager();
        image.addTag("tag1", tm);
        assertEquals(image.getTags().size(), 1);
        assertEquals(image.getTags().get(0).toString(), "@tag1");
    }

    @Test
    public void setTagsToObservableListTest() {
        ObservableList<Tag> tags = FXCollections.observableArrayList();
        TagManager tm = new TagManager();
        tags.add(new Tag("tag1", tm));
        tags.add(new Tag("tag2", tm));

        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        assertEquals(image.getTags().size(), 0);
        image.setTagsToObservableList(tags);
        assertEquals(image.getTags().size(), 2);
    }

    @Test
    public void getArrayedTagsTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        assertEquals(image.getArrayedTags().size(), 0);
        TagManager tm = new TagManager();
        image.addTag("tag1", tm);
        assertEquals(image.getArrayedTags().size(), 1);
        assertEquals(image.getArrayedTags().get(0), "@tag1");
    }

    @Test
    public void setTagsToArrayListTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        assertEquals(image.getArrayedTags().size(), 0);
        ArrayList arr = new ArrayList();
        arr.add("@tag1");
        arr.add("@tag2");
        //image.setTagsToArrayList(arr);
        assertEquals(image.getArrayedTags().size(), 2);
    }

    @Test
    public void getImageHistory() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        TagManager tm = new TagManager();
        image.addTag("tag1", tm);
        image.addTag("tag2", tm);
        assertEquals(image.getImageHistory().size(), 2);
        assertEquals(image.getImageHistory().get(0), "@tag1");
        assertEquals(image.getImageHistory().get(1), "@tag2");
        image.removeImageTag(image.getTags().get(0));
        assertEquals(image.getImageHistory().size(), 2);
    }

    @Test
    public void getFilePathTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        assertEquals(image.getFilePath(), "TestImages/scenery.jpg");
    }

    @Test
    public void getTaggedNameTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        TagManager tm = new TagManager();
        assertEquals(image.getTaggedName(), "scenery");
        image.addTag("tag1", tm);
        assertEquals(image.getTaggedName(), "scenery@tag1");
        image.addTag("tag2", tm);
        assertEquals(image.getTaggedName(), "scenery@tag1@tag2");
        //TagManager tm = new TagManager();
        //Tag tag = new Tag("@tag1", tm);
        //image.removeImageTag(tag);
        //assertEquals(image.getTaggedName(), "scenery@tag2");

    }

    @Test
    public void addTagTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        TagManager tm = new TagManager();
        image.addTag("@tag1", tm);
        assertEquals(image.getTags().size(), 1);
        image.addTag("tag1", tm);
        assertEquals(image.getTags().size(), 1);

        image.addTag("tag2", tm);
        assertEquals(image.getTags().size(), 2);
    }

    @Test
    public void hasTagTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        TagManager tm = new TagManager();
        image.addTag("@tag1", tm);
        image.addTag("@tag2", tm);
        image.addTag("tag3", tm);
        assertTrue(image.hasTag("tag1"));
        assertFalse(image.hasTag("@tag4"));

    }

/*
    @Test
    public void newImagePathTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        TagManager tm = new TagManager();
        image.addTag("@tag1", tm);
        image.addTag("@tag2", tm);
        image.addTag("tag3", tm);
        assertEquals(image.newImagePath(), "scenery@tag1@tag2@tag3");
        Tag tag = new Tag("tag1", tm);
        image.removeImageTag(tag);
        assertEquals(image.newImagePath(), "scenery@tag2@tag3");
    }
*/

    @Test
    public void renameTest() {
        TagManager tm = new TagManager();
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        image.addTag("@tag1", tm);
        image.addTag("@tag2", tm);
        image.addTag("tag3", tm);

        image.rename("TestImages/newFolder");   // if the user moves image to a new directory
        assertEquals(image.getFilePath(), "TestImages/newFolder/scenery@tag1@tag2@tag3");

    }

    @Test
    public void removeImageTagTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        TagManager tm = new TagManager();
        image.addTag("@tag1", tm);
        image.addTag("@tag2", tm);
        image.addTag("tag3", tm);
        //TagManager tm = new TagManager();
        //Tag tag = new Tag("tag1", tm);
        image.removeImageTag(image.getTags().get(0));
        assertEquals(image.getTaggedName(), "scenery@tag2@tag3");
    }
}


/*
    @Test
    public void addSetOfTagsTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        TagManager tm = new TagManager();
        image.addTag("@tag2", tm);
        image.addTag("tag3", tm);
        assertEquals(image.newImagePath(), "TestImages/scenery@tag2@tag3");

        ArrayList arr = new ArrayList();
        arr[0] = "tag1";
        arr[1] = "@tag1";
        arr[2] = "@tag4";
        arr[3] = "tag5";

        image.addSetOfTags(arr, tm);
        assertEquals(image.newImagePath(), "TestImages/scenery@tag2@tag3@tag1@tag4@tag5");

    }
*/

/*
    @Test
    public void revertToOlderTagsTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        TagManager tm = new TagManager();
        image.addTag("@tag2", tm);
        assertEquals(image.getFileName(), "scenery/scenery@tag2");
        image.revertToOlderTags();
        assertEquals(image.getFileName(), "scenery");
        assertEquals(image.getTags().get(0), "@tag2");
        assertEquals(image.getTags().size(), 1);
    }
}*/
