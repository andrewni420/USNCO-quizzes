import java.awt.*;
import java.io.File;
public class Also_Test {
    public static void main(String[] args) {
        /*File myfile = new File("new.png");
        StdDraw.enableDoubleBuffering();
        Color color = new Color(0,0,0,50);
        StdDraw.clear(color);
        StdDraw.line(0,0,1,1);
        StdDraw.setPenColor(color);
        StdDraw.filledRectangle(0.5,0.5,0.5,0.5);
        StdDraw.setPenColor(Color.black);
        StdDraw.show();
        StdDraw.save("new.png");
        StdDraw.clear(Color.blue);
        StdDraw.show();
        StdDraw.pause(1000);
        StdDraw.filledCircle(0,0,0.5);
        StdDraw.picture(0.5,0.5,"new.png",0.5,0.5,45);
        StdDraw.show();
        myfile.delete();*///fileplay

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-1,1);
        Node[] foci = new Node[2];
        foci[0]=new Node(-0.4,-0.15);
        foci[1]=new Node(0.6,.35);
        Ellipse ellipse = new Ellipse(foci,0.75);

        Node node = new Node(0,1);
        Node node1 = new Node(0,-1);
        Node projection = ellipse.project(node,false);
        Line line = new Line(node,projection);
        double theta = ellipse.gettheta(node);
        double theta1 = ellipse.gettheta(node1);
        Node origin = new Node(0,0);
        StdDraw.setPenColor(Color.gray);
        origin.draw(0.1);
        StdDraw.setPenColor(Color.pink);
        ellipse.getpoint(theta).draw(0.015);
        StdDraw.setPenColor(Color.gray);
        ellipse.getpoint(theta1).draw(0.015);
        StdDraw.setPenColor(Color.black);
        ellipse.debug();
        Node drdt = ellipse.drdt(theta);
        Line dr = new Line(projection.minus(drdt),drdt.add(projection));
        line.draw();
        node.draw();
        projection.draw();
        drdt.draw();
        dr.draw();
        Line tocenter=new Line(ellipse.getcenter(),node);
        tocenter.draw();
        ellipse.settmin(theta1);
        ellipse.settmax(theta1);
        ellipse.setxy();
        ellipse.draw();
        StdDraw.show();




    }

}
