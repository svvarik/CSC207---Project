import java.util.ArrayList;

public class ImageManager {

    private ArrayList<ImageFile> createdImages;

    public ImageManager() {
        this.createdImages = new ArrayList<ImageFile>();
    }

    ArrayList<ImageFile> getCreatedImages() {
        return this.createdImages;
    }

    void setCreatedImages(ArrayList<ImageFile> list) {
        this.createdImages = list;
    }

    private void addFile(ImageFile image) {
        this.createdImages.add(image);
    }

    boolean containsImage(String imagePath) {
        for (ImageFile i : this.createdImages) {
            if (imagePath.equals(i.getFilePath())) {
                return true;
            }
        }
        return false;
    }

    ImageFile findImage(String imagePath) {
        for (ImageFile i : this.createdImages) {
            if (imagePath.equals(i.getFilePath())) {
                return i;
            }
        }
        ImageFile thisImage = new ImageFile(imagePath);
        this.addFile(thisImage);
        return thisImage;
    }
}
