public class TagITModel {
    private ImageManager imageManager = new ImageManager();
    private TagManager tagManager = new TagManager();
    private ImageFile currentImage;


    ImageFile getCurrentImage() {
        return this.currentImage;
    }

    void setCurrentImage(ImageFile newCurImage) {
        this.currentImage = newCurImage;
    }

    TagManager getTagManager() {
        return this.tagManager;
    }

    ImageManager getImageManager() {
        return this.imageManager;
    }
}
