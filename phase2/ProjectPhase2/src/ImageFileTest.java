import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class ImageFileTest {
	
	@Test
	public void getFilePathTest() {
		ImageFile image = new ImageFile("/h/u13/c5/00/narasi15/Desktop/dog.jpg");
		assertEquals(image.getFilePath(), "/h/u13/c5/00/narasi15/Desktop/dog.jpg");
	}
	
	@Test
	public void getTaggedNameTest() {
		ImageFile image = new ImageFile("/h/u13/c5/00/narasi15/Desktop/dog.jpg");
		assertEquals(image.getTaggedName(), "dog.jpg");
	}
	
	@Test
	public void addTagTest() throws SecurityException, IOException {
		ImageFile image = new ImageFile("/h/u13/c5/00/narasi15/Desktop");
		assertEquals(image.getTags().size(), 0);
		
		TagManager tm = new TagManager();
		HistoryManager history = new HistoryManager();
		image.addTag("cute", tm);
		assertEquals(image.getTags().size(), 1);
		
	}
	
	@Test
	public void hasTagTest() {
		
	}
	
	

}
