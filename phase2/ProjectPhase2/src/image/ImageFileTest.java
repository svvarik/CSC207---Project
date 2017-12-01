package image;

import tag.Tag;
import tag.TagManager;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImageFileTest {

    // Set up Common to all tests
    private File file =
            new File(
                    "."
                            + File.separator
                            + "src"
                            + File.separator
                            + "TestImages"
                            + File.separator
                            + "Scenery.jpg");
    private ImageFile image = new ImageFile(file.getAbsolutePath());
    private TagManager tm = new TagManager();

    @Test
    public void getTagsTest() throws IOException {

        assertEquals(image.getTags().size(), 0);
        image.addTag("tag1", tm);
        assertEquals(image.getTags().size(), 1);
        assertEquals(image.getTags().get(0).toString(), "@tag1");

        // TearDown
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
        assertEquals(image.getImageHistory().get(2), "Scenery@tag1 @tag2.jpg");
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
        assertEquals(image.getTaggedName(), "Scenery@tag1 @tag2.jpg");

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

    @Test
    public void removeImageTagTest() {
        image.addTag("@tag1", tm);
        image.addTag("@tag2", tm);
        image.addTag("tag3", tm);
        image.removeImageTag(image.getTags().get(0));
        assertEquals(image.getTaggedName(), "Scenery@tag2 @tag3.jpg");

        image.removeImageTag(image.getTags().get(0));
        image.removeImageTag(image.getTags().get(0));
        image.getImageHistory().clear();
    }

    @Test
    public void revertToOlderTagsTest() {
        image.addTag("@tag2", tm);
        assertEquals(image.getTaggedName(), "Scenery@tag2.jpg");
        image.revertToOlderTags(image.getImageHistory().get(0), tm);
        assertEquals(image.getTaggedName(), "Scenery.jpg");
        assertEquals(image.getTags().size(), 0);

        image.getImageHistory().clear();
    }

    @Test
    public void getImageHistoryTest() {
        image.addTag("Test", tm);
        assertEquals(image.getImageHistory().get(0), "Scenery.jpg");
        assertEquals(image.getImageHistory().get(1), "Scenery@Test.jpg");

        image.removeImageTag(image.getTags().get(0));
        image.getImageHistory().clear();
    }

    @Test
    public void setFileNameTest() {
        assertEquals(image.fileName, "Scenery.jpg");
        image.setFileName("SceneryTest.jpg");
        assertEquals(image.fileName, "SceneryTest.jpg");

        image.setFileName("Scenery.jpg");
    }
}
