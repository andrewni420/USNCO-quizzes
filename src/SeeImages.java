import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SeeImages {
    public static final int year = 2010;
    public static final String USNCO = "N";
    public static int pause=250;
    public static int slow = 1;

    public static void main(String[] args) throws IOException{
        pause*=slow;
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(900,900);
        StdDraw.setScale(-0.6,0.6);

        /*picture(0,0,"src/"+year+USNCO+"/q41-4.png");
        StdDraw.show();
        StdDraw.pause(10000);/**/

        for (int i=5;i<61;i++){
            picture(0,0,"src/"+year+USNCO+"/q"+i+".png");
            StdDraw.show();
            StdDraw.clear();
            StdDraw.pause(pause);
            if (StdDraw.isMousePressed()) {
                System.out.println(i);
                while (StdDraw.isMousePressed()){}
            }
            picture(0,0,"src/"+year+USNCO+"/q"+i+"-1.png");
            StdDraw.show();
            StdDraw.clear();
            StdDraw.pause(pause);
            if (StdDraw.isMousePressed()) {
                System.out.println(i);
                while (StdDraw.isMousePressed()){}
            }
            picture(0,0,"src/"+year+USNCO+"/q"+i+"-2.png");
            StdDraw.show();
            StdDraw.clear();
            StdDraw.pause(pause);
            if (StdDraw.isMousePressed()) {
                System.out.println(i);
                while (StdDraw.isMousePressed()){}
            }
            picture(0,0,"src/"+year+USNCO+"/q"+i+"-3.png");
            StdDraw.show();
            StdDraw.clear();
            StdDraw.pause(pause);
            if (StdDraw.isMousePressed()) {
                System.out.println(i);
                while (StdDraw.isMousePressed()){}
            }
            picture(0,0,"src/"+year+USNCO+"/q"+i+"-4.png");
            StdDraw.show();
            StdDraw.clear();
            StdDraw.pause(pause);
            if (StdDraw.isMousePressed()) {
                System.out.println(i);
                while (StdDraw.isMousePressed()){}
            }
        }
    }
    public static void picture (double one, double two, String filename) throws IOException{
        BufferedImage image = ImageIO.read(new File(filename));
        double x=image.getWidth();
        double y=image.getHeight();
        double max = Math.max(x,y);
        x/=max;
        y/=max;
        StdDraw.picture(one,two,filename,x,y);
    }
}
