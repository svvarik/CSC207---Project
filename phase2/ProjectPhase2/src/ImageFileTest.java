import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImageFileTest {

    //Set up Common to all tests
    TagManager tm = new TagManager();
    String path = new File(".").getAbsolutePath();
    ImageFile image = new ImageFile(path +
            File.separator + "ProjectPhase2" +
            File.separator + "src" +
            File.separator + "TestImages" +
            File.separator + "Scenery.jpg");

    @Test
    public void getTagsTest() throws IOException {

        assertEquals(image.getTags().size(), 0);
        image.addTag("tag1", tm);
        assertEquals(image.getTags().size(), 1);
        assertEquals(image.getTags().get(0).toString(), "@tag1");

        //TearDown
        image.removeImageTag(image.getTags().get(0));
        image.getImageHistory().clear();
    }

    @Test
    public void setTagsToObservableListTest() {
        ObservableList<Tag> tags = FXCollections.observableArrayList();
        tags.add(new Tag("tag1", tm));
        tags.add(new Tag("tag2", tm));
        assertEquals(image.getTags().size(), 0);
        image.setTagsToObservableList(tags);
        assertEquals(image.getTags().size(), 2);

        image.tags.clear();
        image.getImageHistory().clear();
    }

    @Test
    public void getImageHistory() {
        image.addTag("tag1", tm);
        image.addTag("tag2", tm);
        assertEquals(image.getImageHistory().size(), 3);
        assertEquals(image.getImageHistory().get(0), "Scenery.jpg");
        assertEquals(image.getImageHistory().get(1), "Scenery@tag1.jpg");
        assertEquals(image.getImageHistory().get(2),"Scenery@tag1@tag2.jpg");
        image.removeImageTag(image.getTags().get(0));
        assertEquals(image.getImageHistory().size(), 4);

        image.removeImageTag(image.getTags().get(0));
        image.getImageHistory().clear();
    }

    @Test
    public void getFilePathTest() {
        ImageFile image = new ImageFile("TestImages/scenery.jpg");
        assertEquals(image.getFilePath(), "TestImages/scenery.jpg");
    }

    @Test
    public void getTaggedNameTest() {

        assertEquals(image.getTaggedName(), "Scenery.jpg");
        image.addTag("tag1", tm);
        assertEquals(image.getTaggedName(), "Scenery@tag1.jpg");
        image.addTag("tag2", tm);
        assertEquals(image.getTaggedName(), "Scenery@tag1@tag2.jpg");

        image.removeImageTag(image.getTags().get(0));
        image.removeImageTag(image.getTags().get(0));
        image.getImageHistory().clear();
    }

    @Test
    public void addTagTest() {
        image.addTag("@tag1", tm);
        assertEquals(image.getTags().size(), 1);
        image.addTag("tag1", tm);
        assertEquals(image.getTags().size(), 1);

        image.addTag("tag2", tm);
        assertEquals(image.getTags().size(), 2);

        image.removeImageTag(image.getTags().get(0));
        image.removeImageTag(image.getTags().get(0));
        image.getImageHistory().clear();
    }

    @Test
    public void hasTagTest() {
        image.addTag("@tag1", tm);
        image.addTag("@tag2", tm);
        image.addTag("tag3", tm);

        assertTrue(image.hasTag("@tag1"));
        assertFalse(image.hasTag("@tag4"));

        image.removeImageTag(image.getTags().get(0));
        image.removeImageTag(image.getTags().get(0));
        image.removeImageTag(image.getTags().get(0));
        image.getImageHistory().clear();
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

/*    @Test
    public void renameTest() {
        image.addTag("@tag1", tm);
        image.addTag("@tag2", tm);
        image.addTag("tag3", tm);

        image.rename("TestImages/Notes");   // if the user moves image to a new directory
        assertEquals(image.getFilePath(), "TestImages/newFolder/scenery@tag1@tag2@tag3");

    }*/

    @Test
    public void removeImageTagTest() {
        image.addTag("@tag1", tm);
        image.addTag("@tag2", tm);
        image.addTag("tag3", tm);
        //TagManager tm = new TagManager();
        //Tag tag = new Tag("tag1", tm);
        image.removeImageTag(image.getTags().get(0));
        assertEquals(image.getTaggedName(), "Scenery@tag2@tag3.jpg");

        image.removeImageTag(image.getTags().get(0));
        image.removeImageTag(image.getTags().get(0));
        image.getImageHistory().clear();
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