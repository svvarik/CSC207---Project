import java.util.ArrayList;

public class ImageManager {

    static ArrayList<ImageFile> createdImages = new ArrayList<ImageFile>();
    static ImageFile currentImage;
//
//    public ImageManager() {
//        this.createdImages = new ArrayList<ImageFile>();
//    }

    static ArrayList<ImageFile> getCreatedImages(){
        return ImageManager.createdImages;
    }

    static void setCreatedImages(ArrayList<ImageFile> list){
        ImageManager.createdImages = list;
    }

    static void addFile(ImageFile image) {
        createdImages.add(image);
    }

    static boolean containsImage(String imagePath) {
        for (ImageFile i : createdImages) {
            if (imagePath.equals(i.getFilePath())) {
                return true;
            }
        }
        return false;
    }

    static ImageFile findImage(String imagePath) {
        for (ImageFile i : createdImages) {
            if (imagePath.equals(i.getFilePath())) {
                return i;
            }
        }
        ImageFile thisImage = new ImageFile(imagePath);
        ImageManager.addFile(thisImage);
        return thisImage;
    }
}
