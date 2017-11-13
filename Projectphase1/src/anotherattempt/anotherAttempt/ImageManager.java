import java.io.File;
import java.util.ArrayList;

public class ImageManager {

    static ArrayList<ImageFile> createdImages = new ArrayList<ImageFile>();
    static ImageFile currentImage;

    static void addFile(ImageFile image) {
        createdImages.add(image);
    }

    static boolean containsImage(String imagePath) {
        for (ImageFile i : createdImages) {
            if (imagePath.equals(i.filePath)) {
                return true;
            }
        }
        return false;
    }

    static ImageFile findImage(String imagePath, String fileName) {
        for (ImageFile i : createdImages) {
            if (imagePath.equals(i.filePath)) {
                return i;
            }
        }
        ImageFile thisImage = new ImageFile(imagePath, fileName);
        ImageManager.addFile(thisImage);
        return thisImage;
    }
}
