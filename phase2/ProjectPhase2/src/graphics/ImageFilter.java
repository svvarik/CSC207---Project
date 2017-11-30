package graphics;

import image.ImageFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageFilter {

    /*
    *Title: Converting a colour image into grayscale using RGB algorithm
    *Author: Yusuf Shakeel
    *Date: 26-01-2014
    *Code version: 1.0
    *Availability: https://github.com/yusufshakeel/Java-Image-Processing-Project
    *
    *Title: Algorithm for calculating inverse RGB colour
    *Author: Mark Ransom
    *Date: 05-08-2011
    *Code version: 1.0
    *Availability: https://stackoverflow.com/questions/6961725/algorithm-for-calculating-inverse-color
    *
    *Title: Sepia tone image filter for java
    *Author: Macky G
    *Date: 30-06-2009
    *Code version: 1.0
    *Availability: https://groups.google.com/forum/#!topic/comp.lang.java.programmer/nSCnLECxGdA*/
    
    /**
     * Replaced the image at the given filePath by the BufferedImage img.
     * @param bufferedImg the BufferedImage to replace the image at the
     *                    given file path.
     * @param filePath the file path of the image to replace with bufferedImg.
     */
    public static void recolour(BufferedImage bufferedImg, String filePath) {
        try{
            ImageIO.write(bufferedImg, "jpg", new File(filePath));
        }catch(IOException e){
        }
    }

    /**
     * Returns a BufferedImage of the Image associated with the ImageFile img.
     *
     * @param img the ImageFile associated with the image to return a BufferedImage of.
     * @return a BufferedImage of the image associated with ImageFile img.
     * @throws IOException
     */
    public static BufferedImage originalScale(ImageFile img) throws IOException{
        return ImageIO.read(new File(img.getFilePath()));
    }

    /**
     * Returns a BufferedImage of the Image associated with the ImageFile img
     * with colours changed to the grayScale filter.
     *
     * @param img the ImageFile associated with the image to apply the grayScale filter to.
     * @return a BufferedImage of the image with the grayScale filter applied
     * @throws IOException
     */
    public static BufferedImage grayScale(ImageFile img)throws IOException{
        File originalImage = new File(img.getFilePath());
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


    /**
     * Returns a BufferedImage of the Image associated with the ImageFile img
     * with colours changed to the sepia filter.
     *
     * @param img the ImageFile associated with the image to apply the sepia filter to.
     * @return a BufferedImage of the image with the sepia filter applied
     * @throws IOException
     */
    public static BufferedImage sepia(ImageFile img) throws IOException {
        File originalImage = new File(img.getFilePath());
        BufferedImage defaultColourImage = ImageIO.read(originalImage);

        //get image width and height
        int width = defaultColourImage.getWidth();
        int height = defaultColourImage.getHeight();

        int sepiaDepth = 20;
        int sepiaIntensity = 20;
        // We need 3 integers (for R,G,B color values) per pixel.

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

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

    public static BufferedImage inverse(ImageFile image) throws IOException {
        File originalImage = new File(image.getFilePath());
        BufferedImage defaultColourImage = ImageIO.read(originalImage);

        //get image width and height
        int width = defaultColourImage.getWidth();
        int height = defaultColourImage.getHeight();

        for (int x = 0; x < defaultColourImage.getWidth(); x++) {
            for (int y = 0; y < defaultColourImage.getHeight(); y++) {

                int p = defaultColourImage.getRGB(x, y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //calculate average
                int r1 = 255 -r;
                int g1 = 255 -g;
                int b1 = 255 -b;


                //replace RGB value with avg
                p = (a<<24) | (r1<<16) | (g1<<8) | b1;

                defaultColourImage.setRGB(x, y, p);
                
            }
        }
        return defaultColourImage;
    }
}




