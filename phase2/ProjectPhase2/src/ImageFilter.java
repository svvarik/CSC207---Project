import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class ImageFilter {

    static void recolour(BufferedImage Img, String filePath) {
        try{
            ImageIO.write(Img, "jpg", new File(filePath));
        }catch(IOException e){
        }

    }
    static BufferedImage originalScale(ImageFile image) throws IOException{
        return ImageIO.read(new File(image.getFilePath()));
    }

    static BufferedImage grayScale(ImageFile image)throws IOException{
        File originalImage = new File(image.getFilePath());
        BufferedImage defaultColourImage = ImageIO.read(originalImage);

        //get image width and height
        int width = defaultColourImage.getWidth();
        int height = defaultColourImage.getHeight();

        //convert to grayscale
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = defaultColourImage.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //calculate average
                int avg = (r+g+b)/3;

                //replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                defaultColourImage.setRGB(x, y, p);
            }
        } return defaultColourImage;
    }
}




