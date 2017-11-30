package image;

import java.io.IOException;
import java.util.ArrayList;

public class ImageManager {

    private ArrayList<ImageFile> createdImages;

    public ImageManager() {
        this.createdImages = new ArrayList<ImageFile>();
    }

    public ArrayList<ImageFile> getCreatedImages() {
        return this.createdImages;
    }

    public void setCreatedImages(ArrayList<ImageFile> list) {
        this.createdImages = list;
    }

    private void addFile(ImageFile image) {
        this.createdImages.add(image);
    }

    public boolean containsImage(String imagePath) {
        for (ImageFile i : this.createdImages) {
            if (imagePath.equals(i.getFilePath())) {
                return true;
            }
        }
        return false;
    }

    public ImageFile findImage(String imagePath) throws IOException {
        for (ImageFile img : this.createdImages) {
            if (imagePath.equals(img.getFilePath())) {
                return img;
            }
        }
        ImageFile thisImage = new ImageFile(imagePath);
        this.addFile(thisImage);
        return thisImage;
    }
}
