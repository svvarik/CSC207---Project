import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.WritableRaster;
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


    static BufferedImage sepia(ImageFile image) throws IOException {
        File originalImage = new File(image.getFilePath());
        BufferedImage defaultColourImage = ImageIO.read(originalImage);

        //get image width and height
        int width = defaultColourImage.getWidth();
        int height = defaultColourImage.getHeight();

        int sepiaDepth = 20;
        int sepiaIntensity = 20;
        // We need 3 integers (for R,G,B color values) per pixel.
        int[] pixels = new int[width * height * 3];
        defaultColourImage.getRaster().getPixels(0, 0, width, height, pixels);

        for (int x = 0; x < defaultColourImage.getWidth(); x++) {
            for (int y = 0; y < defaultColourImage.getHeight(); y++) {

                int rgb = defaultColourImage.getRGB(x, y);
                Color color = new Color(rgb, true);
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int gry = (r + g + b) / 3;

                r = g = b = gry;
                r = r + (sepiaDepth * 2);
                g = g + sepiaDepth;

                if (r > 255) {
                    r = 255;
                }
                if (g > 255) {
                    g = 255;
                }
                if (b > 255) {
                    b = 255;
                }

                // Darken blue color to increase sepia effect
                b -= sepiaIntensity;

                // normalize if out of bounds
                if (b < 0) {
                    b = 0;
                }
                if (b > 255) {
                    b = 255;
                }

                color = new Color(r, g, b, color.getAlpha());
                defaultColourImage.setRGB(x, y, color.getRGB());

            }
        }
        return defaultColourImage;
    }
}




