import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

public class SingleQuestion {
    private static void addImage(BufferedImage buff1, BufferedImage buff2,
                          int x, int y) {
        Graphics2D g2d = buff1.createGraphics();
        g2d.drawImage(buff2, x, y, null);
        g2d.dispose();
    }
    public static void main(String[] args) throws IOException{
        String fileName = "/Users/andrewni/IdeaProjects/USNCO test reader/Locals/2014 local.pdf";
        PDDocument document=null;
        try {
            document = PDDocument.load(new File(fileName));
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI( 4, 500);

            int y0=320;
            int y1=370;
            image=image.getSubimage(315*500/72,y0*500/72,265*500/72,(y1-y0)*500/72);

            ImageIO.write(image,"png",new File("src/2014Lq31-32.png"));

            BufferedImage question1 = ImageIO.read(new File("/Users/andrewni/IdeaProjects/USNCO test reader/src/2014L/q31.png"));
            BufferedImage question2 = ImageIO.read(new File("/Users/andrewni/IdeaProjects/USNCO test reader/src/2014L/q32.png"));

            int width1 = question1.getWidth();
            int height1 = question1.getHeight()+image.getHeight();
            int width2 = question2.getWidth();
            int height2 = question2.getHeight()+image.getHeight();
            BufferedImage finalimage1 = new BufferedImage(width1,height1,BufferedImage.TYPE_INT_ARGB);
            BufferedImage finalimage2 = new BufferedImage(width2,height2,BufferedImage.TYPE_INT_ARGB);
            addImage(finalimage1,image,0,0);
            addImage(finalimage1,question1,0,image.getHeight());
            addImage(finalimage2,image,0,0);
            addImage(finalimage2,question2,0,image.getHeight());/**/

            ImageIO.write(finalimage1,"png",new File("src/q31.png"));
            ImageIO.write(finalimage2,"png",new File("src/q32.png"));
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }
}
