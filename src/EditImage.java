import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;

public class EditImage {
    public static void main(String[] args) throws IOException{
        BufferedImage img = ImageIO.read(new File("image_2.png"));
        Graphics2D g = img.createGraphics();
        g.drawLine(100,100,130,100);
        ImageIO.write(img,"png",new File("3spd.png"));
    }
}
