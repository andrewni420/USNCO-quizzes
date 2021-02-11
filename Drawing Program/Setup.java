import java.awt.*;

public class Setup {

    /** Constants and Colors **/
    private static final double Height=0.124375;
    private static final Color background = new Color(225,225,225,200);
    public static final Color highlighter = new Color(75,150,250,100);
    public static final Color selected = new Color(0, 100,250,200);
    public static final Color grayout = new Color(150,150,150,150);
    public static final Color nodecolor = new Color(0, 225, 175);

    /** Setup **/
    public static void setup(){
        StdDraw.enableDoubleBuffering();
        StdDraw.rectangle(-0.15,0.5,0.1,0.5);//toolbar
        StdDraw.rectangle(0.5,0.5,0.5,0.5);//drawing pad
        String[] toolnames = {"Marquee", "Node", "Line", "Ellipse", "Spline", "Save          Open", "Print","Exit"};
        for (int i=0;i<8;i++){
            StdDraw.rectangle(-0.15,Height*i+0.0625+0.0025,0.095,0.06);
            StdDraw.text(-0.15,Height*i+0.0025+0.015,toolnames[7-i]);
        }
        marquee();
        node();
        line();
        ellipse();
        spline();
        file();
        print();
        exit();
    }
    public static void setup(int i){
        setup();
        if (i>-1||i==-5) highlight(i);
        Project.objects.draw();
    }
    public static void highlight(int i){
        if (i==-1) return;
        if (i!=-5&&i<0||i>7) throw new RuntimeException("highlighter index out of bounds");
        i=7-i;
        double xc=-0.15;
        double xs=0.095;
        double d=0.005;
        double h=Height*i+d;
        double ys=0.06;

        StdDraw.rectangle(-0.15,0.5,0.1,0.5);//toolbar
        StdDraw.rectangle(-0.15,Height*i+0.0625+0.0025,0.095,0.06);
        StdDraw.setPenColor(Color.blue);
        if (Project.doubleclick)StdDraw.setPenColor(Color.green);
        if (i==2) {
            double[] x = {xc - xs - d, xc + d/2, xc + d/2, xc - xs - d, xc - xs - d, xc - xs, xc - xs, xc-d/2, xc-d/2, xc - xs};
            double[] y = {h - d, h - d, h + 2 * ys + d, h + 2 * ys + d, h - d, h, h + 2 * ys, h + 2 * ys, h, h};
            StdDraw.filledPolygon(x,y);
        } else if (i==12){
            h=Height*2+d;
            double[] x = {xc - d/2, xc + xs + d, xc + xs + d, xc - d/2, xc - d/2, xc + d/2, xc + d/2, xc + xs, xc + xs, xc + d/2};
            double[] y = {h - d, h - d, h + 2 * ys + d, h + 2 * ys + d, h - d, h, h + 2 * ys, h + 2 * ys, h, h};
            StdDraw.filledPolygon(x, y);
        } else {
            double[] x = {xc - xs - d, xc + xs + d, xc + xs + d, xc - xs - d, xc - xs - d, xc -xs, xc - xs, xc + xs, xc + xs, xc - xs};
            double[] y = {h - d, h - d, h + 2 * ys + d, h + 2 * ys + d, h - d, h, h + 2 * ys, h + 2 * ys, h, h};
            StdDraw.filledPolygon(x, y);
        }
        StdDraw.setPenColor(Color.black);
    }
    public static void setcanvas(){
        StdDraw.setCanvasSize(1215,990);
        StdDraw.setXscale(-0.3,1.05);
        StdDraw.setYscale(-0.05,1.05);
    }
    public static void setcanvas(int i){
        StdDraw.setCanvasSize(171,108);
        StdDraw.setXscale(-0.15-0.095,-0.15+0.095);
        StdDraw.setYscale(Height*i+0.005,Height*i+0.125);
    }

    /** Tool icons **/
    private static void marquee(){
        double[]x = {0.7,0.6,0.35,0.3,0.15,0.65,0.45,0.7};
        double[]y = {0.4,0.3,0.55,0.35,0.85,0.7,0.65,0.4};
        StdDraw.rectangle(0.5,0.5,0.5,0.5);
        for (int i=0;i<8;i++){
            x[i]=x[i]*0.1-0.19;
            y[i]=y[i]*0.1+Height*7+0.02;
        }
        StdDraw.filledPolygon(x,y);
    }
    private static void node(){
        StdDraw.filledCircle(-0.15,Height*6+0.065,0.01);
    }
    private static void line(){
        StdDraw.setPenRadius(0.01);
        StdDraw.line(-0.15-0.05,Height*5+0.04,-0.15+0.05,Height*5+0.09);
        StdDraw.setPenRadius();
    }
    private static void ellipse(){
        StdDraw.setPenRadius(0.01);
        StdDraw.ellipse(-0.15,Height*4+0.07,0.05,0.025);
        StdDraw.setPenRadius();
    }
    private static void spline(){
        StdDraw.setPenRadius(0.01);
        Node[] nodes = new Node[4];
        nodes[0]= new Node(-0.15-0.07,Height*3+0.032);
        nodes[1]= new Node(-0.15,Height*3+0.032);
        nodes[2]= new Node(-0.15,Height*3+0.11);
        nodes[3]= new Node(-0.15+0.07,Height*3+0.11);
        Spline example = new Spline(nodes);
        example.draw();
        StdDraw.setPenRadius();
    }
    private static void file(){
        StdDraw.line(-0.15+0.0025,Height*2+0.005,-0.15+0.0025,Height*2+0.125);
        StdDraw.line(-0.15-0.0025,Height*2+0.005,-0.15-0.0025,Height*2+0.125);
        StdDraw.setPenColor(Color.white);
        StdDraw.filledRectangle(-0.15,Height*2+0.065,0.0023,0.062);
        StdDraw.setPenColor(Color.black);
        save();
        open();
    }
    private static void save(){
        double s=0.06;
        double x=-0.15-0.08;
        double y=Height*2+0.0425;
        double[] xs = {0,1, 1,0.8,0.7,  0.7,0.15,0.15,0,0,  0.1, 0.1, 0.9, 0.9, 0.1};
        double[] ys = {0,0,0.8,1,  1,   0.7,0.7,  1,  1,0,  0.075,0.525,0.525,0.075,0.075};
        for (int i=0;i<15;i++){
            xs[i]=xs[i]*s+x;
            ys[i]=ys[i]*s+y;
        }
        StdDraw.filledPolygon(xs,ys);
        StdDraw.setPenColor(Color.white);
        double s1=s*0.025;
        double s2=s*0.01;
        StdDraw.filledRectangle(s1+x,s-s1+y,s1,s1);
        StdDraw.filledRectangle(s1+x,s1+y,s1,s1);
        StdDraw.filledRectangle(s-s1+x,s1+y,s1,s1);
        StdDraw.setPenColor(Color.black);
        StdDraw.filledCircle(2*s1+x,s-2*s1+y,2*s1);
        StdDraw.filledCircle(2*s1+x,2*s1+y,2*s1);
        StdDraw.filledCircle(s-2*s1+x,2*s1+y,2*s1);
        StdDraw.filledRectangle(69*s2+x,71*s2+y,s2,s2);
        StdDraw.filledRectangle(16*s2+x,71*s2+y,s2,s2);
        StdDraw.filledRectangle(s/2+x,37*s2+y,33*s2,3.5*s2);
        StdDraw.filledRectangle(s/2+x,23*s2+y,33*s2,3.5*s2);
        StdDraw.filledRectangle(55*s2+x,85*s2+y,8*s2,11*s2);
        StdDraw.setPenColor(Color.white);
        StdDraw.filledCircle(0.68*s+x,0.72*s+y,2*s2);
        StdDraw.filledCircle(0.17*s+x,0.72*s+y,2*s2);
        StdDraw.setPenColor(Color.black);
    }
    private static void open(){
        double x=-0.1;
        double y=Height*2+0.075;
        double s0 = 0.05;
        double s=s0*0.01;
        double[] xs = {-0.8, 0.55,0.8, 0.55,0.55,-0.25,-0.25,-0.8, -0.8,-0.75,/*10*/-0.75,-0.3,-0.3,0.5,0.5,0,-0.2,-0.55,-0.75};
        double[] ys = {-0.6, -0.6, 0.2,0.2, 0.45,  0.45, 0.55, 0.55,-0.6,-0.45, /*10*/ 0.5,0.5, 0.4,0.4,0.2,0.2, 0,   0,   -0.45};
        for (int i=0;i<xs.length;i++){
            xs[i]=xs[i]*s0+x;
            ys[i]=ys[i]*s0+y;
        }
        StdDraw.filledPolygon(xs,ys);
        int[]corners = {0,1,4,6,7,12};
        int[]xpm = {1,-1,-1,-1,1,1};
        int[]ypm={1,1,-1,-1,-1,1};
        for (int i=0;i<corners.length;i++) {
            StdDraw.setPenColor(Color.white);
            StdDraw.filledRectangle(xs[corners[i]]+xpm[i]*s, ys[corners[i]]+ypm[i]*s, s, s);
            StdDraw.setPenColor(Color.black);
            StdDraw.filledCircle(xs[corners[i]]+xpm[i]*2*s, ys[corners[i]]+ypm[i]*2*s, 2*s);
        }
        double[] x1={xs[2],xs[2]-2.72*s,xs[2]-0.811*s,xs[2]};
        double[] y1={ys[2],ys[2],ys[2]-2.6*s,ys[2]};
        double[] x2={xs[17],xs[17]+1.3*s,xs[17]-0.525*s,xs[17]};
        double[] y2={ys[17],ys[17],ys[17]-1.188*s,ys[17]};
        double[] x3={xs[15],xs[15]+0.828*s,xs[15]-0.3*s,xs[15]};
        double[] y3={ys[15],ys[15],ys[15]-0.3*s,ys[15]};
        StdDraw.setPenColor(Color.white);
        StdDraw.filledPolygon(x1,y1);
        StdDraw.filledPolygon(x2,y2);
        StdDraw.filledPolygon(x3,y3);
        StdDraw.setPenColor(Color.black);
        StdDraw.filledCircle(xs[2]-2.72*s,ys[2]-2*s,2*s);
        StdDraw.filledCircle(xs[17]+1.3*s,ys[17]-2*s,2*s);
        StdDraw.filledCircle(xs[15]+0.828*s,ys[15]-2*s,2*s);
    }
    private static void print(){
        StdDraw.text(-0.15,Height+0.065,"[asy] ... [/asy]");
        StdDraw.rectangle(-0.15,Height+0.065,0.065,0.02);
    }
    private static void exit(){
        StdDraw.setPenColor(Color.red);
        StdDraw.filledRectangle(-0.15,0.065,0.025,0.025);
        StdDraw.setPenColor(Color.black);
    }

    /** Tooltips **/
    private static void marqueetip(){
        double x=StdDraw.mouseX();
        double y=StdDraw.mouseY();
        StdDraw.setPenColor(background);
        StdDraw.filledRectangle(x+0.308,y-0.075,0.298,0.065);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(x+0.177,y-0.025,"Hover close to an object to highlight");
        StdDraw.text(x+0.292,y-0.05,"Click to select highlighted object. Its nodes will show up in teal");
        StdDraw.text(x+0.308,y-0.075,"Shift click for additive selection. Click a selected object to unselect");
        StdDraw.text(x+0.155,y-0.1,"Click and drag to move objects.");
        StdDraw.text(x+0.2,y-0.125,"NB: Move an object's nodes one at a time.");
        StdDraw.rectangle(x+0.308,y-0.075,0.298,0.065);
    }
    private static void nodetip(){
        double x=StdDraw.mouseX();
        double y=StdDraw.mouseY();
        StdDraw.setPenColor(background);
        StdDraw.filledRectangle(x+0.2,y-0.025,0.19,0.015);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(x+0.2,y-0.025,"Click inside the drawing pad to add a node");
        StdDraw.rectangle(x+0.2,y-0.025,0.19,0.015);
    }
    private static void linetip(){
        double x=StdDraw.mouseX();
        double y=StdDraw.mouseY();
        StdDraw.setPenColor(background);
        StdDraw.filledRectangle(x+0.25,y-0.05,0.24,0.04);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(x+0.205,y-0.025,"Click inside the drawing pad to begin a line");
        StdDraw.text(x+0.138,y-0.05,"Click again to finish the line");
        StdDraw.text(x+0.25,y-0.075,"Clicking outside the box will abort the unfinished line");
        StdDraw.rectangle(x+0.25,y-0.05,0.24,0.04);
    }
    private static void ellipsetip(){
        double x=StdDraw.mouseX();
        double y=StdDraw.mouseY();
        StdDraw.setPenColor(background);
        StdDraw.filledRectangle(x+0.3625,y-0.075,0.3525,0.065);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(x+0.2225,y-0.025,"Click inside the drawing pad to begin an ellipse");
        StdDraw.text(x+0.3625,y-0.05,"Click to set diametrically opposite point, or press enter to set and skip to step 4");
        StdDraw.text(x+0.147,y-0.075,"Click to set final ellipse shape");
        StdDraw.text(x+0.312,y-0.1,"Click to set the beginning of the arc, or press enter to skip and save");
        StdDraw.text(x+0.3025,y-0.125,"Click to set final arc and save. Hold shift to go CW instead of CCW");
        StdDraw.rectangle(x+0.3625,y-0.075,0.3525,0.065);
    }
    private static void splinetip(){
        double x=StdDraw.mouseX();
        double y=StdDraw.mouseY();
        StdDraw.setPenColor(background);
        StdDraw.filledRectangle(x+0.25,y-0.05,0.24,0.04);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(x+0.217,y-0.025,"Click inside the drawing pad to begin a spline");
        StdDraw.text(x+0.251,y-0.05,"Click to add a node, hold Ctrl to add nodes in reverse");
        StdDraw.text(x+0.131,y-0.075,"Press Enter to save spline");
        StdDraw.rectangle(x+0.25,y-0.05,0.24,0.04);
    }
    private static void savetip(){
        double x=StdDraw.mouseX();
        double y=StdDraw.mouseY();
        StdDraw.setPenColor(background);
        StdDraw.filledRectangle(x+0.24,y-0.0375,0.23,0.0275);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(x+0.142,y-0.025,"Click save to save as .txt file");
        StdDraw.text(x+0.24,y-0.05,"Enter filename (without full path) as standard input");
        StdDraw.rectangle(x+0.24,y-0.0375,0.23,0.0275);
    }
    private static void opentip(){
        double x=StdDraw.mouseX();
        double y=StdDraw.mouseY();
        StdDraw.setPenColor(background);
        StdDraw.filledRectangle(x+0.24,y-0.0375,0.23,0.0275);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(x+0.142,y-0.025,"Click open to open a .txt file");
        StdDraw.text(x+0.24,y-0.05,"Enter filename (without full path) as standard input");
        StdDraw.rectangle(x+0.24,y-0.0375,0.23,0.0275);
    }
    public static void fileprompt(int i){
        StdDraw.clear();
        Setup.setup(i);
        StdDraw.setPenColor(Setup.grayout);
        StdDraw.filledRectangle(0.375,0.5,0.675,0.55);
        StdDraw.setPenColor(Color.white);
        StdDraw.filledRectangle(0.375,0.5,0.19,0.025);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(0.375,0.5,"Please enter filename as Standard Input");
        StdDraw.rectangle(0.375,0.5,0.19,0.025);
        StdDraw.show();
    }
    private static void printtip(){
        double x=StdDraw.mouseX();
        double y=StdDraw.mouseY();
        StdDraw.setPenColor(background);
        StdDraw.filledRectangle(x+0.285,y-0.025,0.275,0.02);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(x+0.285,y-0.025,"Prints the asymptote code for the drawing as standard output");
        StdDraw.rectangle(x+0.285,y-0.025,0.275,0.02);

    }
    private static void exittip(){
        double x=StdDraw.mouseX();
        double y=StdDraw.mouseY();
        StdDraw.setPenColor(background);
        StdDraw.filledRectangle(x+0.18,y-0.025,0.17,0.02);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(x+0.18,y-0.025,"Terminates program without printing");
        StdDraw.rectangle(x+0.18,y-0.025,0.17,0.02);

    }
    public static void tooltips(int i){
        if (i==0)marqueetip();
        if (i==1)nodetip();
        if (i==2)linetip();
        if (i==3)ellipsetip();
        if (i==4)splinetip();
        if (i==5)savetip();
        if (i==-5)opentip();
        if (i==6)printtip();
        if (i==7)exittip();
    }
    public static boolean intooltip(Node mouse,Node reference, int i){
        double x=reference.getX();
        double y=reference.getY();
        double x0=mouse.getX();
        double y0=mouse.getY();
        if (i==0&&Math.abs(x+0.308-x0)<0.298&&Math.abs(y-0.075-y0)<0.065)return true;
        if (i==1&&Math.abs(x+0.2-x0)<0.19&&Math.abs(y-0.05-y0)<0.04) return true;
        if (i==2&&Math.abs(x+0.25-x0)<0.24&&Math.abs(y-0.05-y0)<0.04)return true;
        if (i==3&&Math.abs(x+0.3625-x0)<0.3525&&Math.abs(y-0.075-y0)<0.065) return true;
        if (i==4&&Math.abs(x+0.25-x0)<0.24&&Math.abs(y-0.05-y0)<0.04)return true;
        if (i==5&&Math.abs(x+0.24-x0)<0.23&&Math.abs(y-0.0375-y0)<0.0275)return true;
        if (i==-5&&Math.abs(x+0.24-x0)<0.23&&Math.abs(y-0.0375-y0)<0.0275)return true;
        if (i==6&&Math.abs(x+0.285-x0)<0.275&&Math.abs(y-0.025-y0)<0.02)return true;
        if (i==7&&Math.abs(x+0.18-x0)<0.17&&Math.abs(y-0.025-y0)<0.02)return true;
        return false;
    }

    /** Highlighter **/
    public static void highlight(Node one, Node two){
        Node diff = two.minus(one);
        double length=diff.length();
        if (length==0) length=1;
        Node perp = diff.rotate(Math.PI/2).scale(0.025/length);
        Node[] corners = new Node[4];
        corners[0]=one.minus(perp);
        corners[1]=one.add(perp);
        corners[2]=two.add(perp);
        corners[3]=two.minus(perp);
        double[] x = new double[5];
        double[]y = new double[5];
        for (int i=0;i<4;i++){
            x[i]=corners[i].getX();
            y[i]=corners[i].getY();
        }
        x[4]=corners[0].getX();
        y[4]=corners[0].getY();
        StdDraw.setPenColor(highlighter);
        StdDraw.filledPolygon(x,y);
    }
}
