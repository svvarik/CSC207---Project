import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class FilterImage {

    public static void recolour(BufferedImage Img, File f){
        try{
            ImageIO.write(Img, "jpg", f);
        }catch(IOException e){
        }

    }
    public static void Original(ImageFile image) throws IOException{
        recolour(image.original, image.f);
    }

    public static void grayScale(ImageFile image)throws IOException{

        //get image width and height
        int width = image.original.getWidth();
        int height = image.original.getHeight();

        //convert to grayscale
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = image.original.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //calculate average
                int avg = (r+g+b)/3;

                //replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                image.current.setRGB(x, y, p);
            }
        }
        recolour(image.current, image.f);
    }
}




