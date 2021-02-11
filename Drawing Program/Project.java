import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Project {

    /******* Settings *******/
    public static boolean debug = false;
    /** Constants **/
    public static final int canvassize=900;
    public static final int ENTER = java.awt.event.KeyEvent.VK_ENTER;
    public static final int BACKSPACE = java.awt.event.KeyEvent.VK_BACK_SPACE;
    public static final int SPACE = java.awt.event.KeyEvent.VK_SPACE;
    public static final int SHIFT = java.awt.event.KeyEvent.VK_SHIFT;
    public static final int CTRL = java.awt.event.KeyEvent.VK_CONTROL;
    /** Objects **/
    public static Scanner scan = new Scanner(System.in);
    public static Node mousefirstpos = new Node(0,0);
    public static Group objects = new Group();
    public static Group select = new Group();
    /** Variables **/
    public static boolean doubleclick=false;
    public static int delay=40;
    public static int tool=0;
    public static int clicktimer=0;
    /* Ideas:
    * Ctrl+C/Ctrl+V
    * Snap to grid / perpendicular line, etc.
    * Shift while dragging to only move in cardinal directions.
    * Draw from all selected places at the same time
    * public static String currentfile?
    * how to snap to / obtain intersections...?
    * hold key down to do multiple arcs of an ellipse
    * double clicking begins a variable sized text box
    * double clicking on tools brings up variants like polyline / freehand?
    * highlighting a node and pressing 1 sprouts a line?
    * */

    /******* Tests *******/
    public static int toolselected(double x, double y){
        int tool=(int)((y-0.0025)/0.124375);
            tool=7-tool;
        if (tool!=5||x<-0.15) return tool;
        else return -tool;
    }
    public static void dclickcheck(double x,double y){
        if (clicktimer<10) doubleclick=true;
        else if (intoolbar(x,y))doubleclick=false;
        clicktimer=0;
    }
    public static boolean inscreen(double x, double y){
        return Math.abs(x-0.5)<0.5&&Math.abs(y-0.5)<0.5;
    }
    public static boolean intoolbar(double x, double y){
        return Math.abs(x + 0.15) < 0.1 && Math.abs(y - 0.5) < 0.5;
    }
    public static boolean click(){
        boolean answer = false;
        if (StdDraw.isMousePressed()){
            while (true){
                if (!StdDraw.isMousePressed()) break;
            }
            answer = true;
        }
        return answer;
    }
    public static boolean Keypress(int event){
        boolean answer = false;
        if (StdDraw.isKeyPressed(event)){
            while (true){
                if (!StdDraw.isKeyPressed(event)) break;
            }
            answer = true;
        }
        return answer;
    }
    public static int placeclicked(double x, double y){
        if (intoolbar(x,y)) {
            tool = toolselected(x, y);
            return 1;
        }
        if (inscreen(x,y)) return 2;
        else return 1;
    }
    public static boolean hotkeys(){
        if (Keypress(SPACE)) {
            tool=0;
            return true;
        }
        if (Keypress(java.awt.event.KeyEvent.VK_1)) {
            tool=1;
            return true;
        }
        if (Keypress(java.awt.event.KeyEvent.VK_2)) {
            tool=2;
            return true;
        }
        if (Keypress(java.awt.event.KeyEvent.VK_3)) {
            tool=3;
            return true;
        }
        if (Keypress(java.awt.event.KeyEvent.VK_4)) {
            tool=4;
            return true;
        }
        if (StdDraw.isKeyPressed(CTRL)){
            if (Keypress(java.awt.event.KeyEvent.VK_P)) {
                tool = 6;
                return true;
            }
            if (Keypress(java.awt.event.KeyEvent.VK_S)) {
                if (StdDraw.isKeyPressed(SHIFT)) doubleclick = true;//something to do with currentfile
                tool = 5;
                return true;
            }
            if (Keypress(java.awt.event.KeyEvent.VK_O)){
                tool=-5;
                return true;
            }
            if (Keypress(java.awt.event.KeyEvent.VK_A)){
                boolean toolzero = tool==0;
                tool=0;
                select.clear();
                objects.copyinto(select);

                if (toolzero) return false;
                else return true;
            }
        }
        if (Keypress(java.awt.event.KeyEvent.VK_ESCAPE)){
            tool=7;
            return true;
        } return false;
    }
    public static int highlighteditem(Group highlight,Node mouse){
        double mindist = 0.05;//closest object to the cursor within 0.05 is highlighted
        for (int i=0;i<objects.size();i++){
            double dist=objects.getItems()[i].distancetolite(mouse);
            if (dist<mindist) {
                highlight.deleteItem(highlight.size()-1);
                highlight.addItem(objects.getItems()[i]);
                mindist=dist;
            }
        }
        int keepobjstill=-1;//When editing an object's nodes, keeps that object still
        double nodemindist = 0.005;//node highlighting overrides object highlighting
        for (int i=0;i<select.size();i++){
            Node[] nodes = select.getItems()[i].nodes();
            for (int j=0;j<nodes.length;j++){
                double dist=nodes[j].distancetolite(mouse);
                if (dist<nodemindist) {
                    highlight.deleteItem(highlight.size()-1);
                    highlight.addItem(nodes[j]);
                    nodemindist=dist;
                    keepobjstill=i;
                }
            }
        }
        return keepobjstill;
    }

    /** Tools **/
    public static void marquee(){//also i probably need to move some of the stuff in marquee() into new methods.
        /** Overarching while loop **/
        while(true) {
            /** Initialization **/
            Node mouse = new Node(StdDraw.mouseX(),StdDraw.mouseY());
            Group highlight = new Group();//highlighted objects
            highlight.addItem(new Item());//dummy item that will be deleted later
            boolean deleteitem=false;//if an object is selected, clicking it will delete rather than select it
            int keepobjstill = highlighteditem(highlight,mouse);//gets highlighted objects and stillitem.
                Item stillitem=null;//object whose node is selected.
                if (keepobjstill>-1) stillitem = select.getItems()[keepobjstill];
                Ellipse ellipse = null;//for casting stillitem to (Ellipse)
            if (delay==40) mousefirstpos=mouse;//tracking mouse position for tooltip

            /** Click and Click+drag functionalities **/
            if (StdDraw.isMousePressed()){
                //clicking outside the box aborts tool
                if (placeclicked(mouse.getX(), mouse.getY()) == 1) {
                    select.clear();
                    return;
                }
                //click to deselect
                if (highlight.size()==1&&select.has(highlight.getItems()[0])) {
                    deleteitem=true;
                } else {
                    //additive selection
                    if (!StdDraw.isKeyPressed(SHIFT)) select.clear();
                    //clicking selects highlighted objects
                    highlight.copyinto(select);
                    //selecting an object's node selects the object too
                    if (keepobjstill>-1&&stillitem!=null) select.addItem(stillitem);
                }

                /** Click+drag functionality **/
                    /** Initializing variables and objects **/
                    //distinguishes between click and click+drag
                    boolean moved = false;
                    //mouse tracking
                    Node prev = mouse;
                    //constant angle for nodes()[4] if stillitem is an ellipse
                    double othertheta = 0;
                        if (stillitem!=null&&stillitem.type()==3) {
                            othertheta = ((Ellipse) stillitem).gettheta(stillitem.nodes()[4]);
                            ellipse = (Ellipse) stillitem;
                        }

                    /** While loop **/
                while(StdDraw.isMousePressed()){

                    /** Setting nodes and values **/
                    Node newpos =new Node(StdDraw.mouseX(),StdDraw.mouseY());
                    Node shift=newpos.minus(prev);//translates mouse movement into node
                    if (newpos.minus(mouse).length()>0.01) moved=true;//see Project.191

                    /** Drag functionality **/
                    if (moved) {
                        //translates mouse movement into object movement (see 202)
                        select.shift(shift);

                        /** Node editing **/
                        if (stillitem!=null) {
                            //keeps object still when editing nodes
                            stillitem.shift(shift.negative());
                            /** Special case for ellipse: "nice" node movement **/
                            if (stillitem.type()==3) {
                                for (Item item:select.getItems()){
                                    //gets the identity of the node being moved
                                    int n = item.nodenum(stillitem);
                                    //keeps the angle of nodes()[4] constant when moving foci
                                    if (n==0||n==1) ellipse.setnode(othertheta,4);
                                    if (n==2||n==3) {
                                        //keeps nodes()[2] and nodes()[3] on the ellipse when moving them
                                        item.shift(stillitem.project(newpos).minus((Node) item));
                                    } else if (n>-1) {
                                        //keeps tmin and tmax constant when editing non-tmin/tmax nodes
                                        ellipse.setnode(0,2);
                                        ellipse.setnode(0,3);
                                    }
                                    //changes a to match the position of nodes()[4]
                                    if (n==4) ellipse.seta();
                                }
                            }
                        }
                        //avoids deleting click+dragged object
                        deleteitem=false;
                    } prev=newpos;//See Project.193

                    /** Graphics **/
                    select.highlight(true);//special highlight for selected objects
                    select.drawnodes();
                    select.draw();
                    Setup.setup(0);
                    StdDraw.show();
                    StdDraw.clear();
                }
            }
            //this way tooltip still works when marquee is selected
            else tooltip(mouse);
            clicktimer++;

            /** Click to deselect **/
            if (deleteitem) {
                if (stillitem!=null) select.delete(new Group(stillitem.nodes()));
                select.delete(highlight.getItems()[0]);
                select.delete(new Group(highlight.getItems()[0].nodes()));
                highlight.clear();
            }

            /** Keyboard functions **/
            if (Keypress(SPACE)) select.clear();
            if (Keypress(BACKSPACE)) {//press delete to delete selected objects
                objects.delete(select);
                select.clear();
            }
            if (hotkeys()) {
                select.clear();
                return;
            }

            /** Graphics **/
            select.highlight(true);//special highlight for selected objects
            highlight.highlight(false);//special highlight for highlighted objects
            select.drawnodes();//draws nodes of selected objects
            Setup.setup(0);
            StdDraw.show();
            StdDraw.clear();
        }
    }
    public static void node(Node node){//"Node" tool will snap to the nearest intersection point.
        objects.addItem(node);
    }
    public static void line(Node start){
        Line line= new Line(start, start);
        while(true) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Node mouse = new Node(x,y);
            boolean click = click();

            Item closest = snapto(mouse);
            if (closest!=null) mouse=closest.project(mouse);

            line.setStop(mouse);
            Setup.setup(2);
            line.draw();
            StdDraw.show();
            StdDraw.clear();
            if (click) {
                if (placeclicked(x,y) == 1) return;
                else break;
            } if (hotkeys()) return;
        }
        objects.addItem(line);
    }
    public static void ellipse(Node one){
        /** Initialization **/
        Node two = new Node();
        Node three;
        Ellipse ellipse = new Ellipse(one,one);
        //tracks the stage of construction
        int step = 1;
        double tmin=0;

        /** Ellipse construction **/
        while(true){
            /** Initialization **/
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Node mouse = new Node(x,y);
            boolean click = click();
                /** Snap to node/item **/
                Item closest = snapto(mouse);
                if (closest!=null) mouse=closest.project(mouse);

            /** Setting Ellipse parameters **/
            if (step==1) {
                two = mouse;
                ellipse = new Ellipse(one, two);
            }
            else if (step==2){
                three = mouse;
                ellipse = new Ellipse(one,two,three);
            }
            else if (step==3){
                double theta = ellipse.gettheta(ellipse.project(mouse,false));
                ellipse.settmin(theta);
                ellipse.settmax(theta);
                ellipse.setxy();
                tmin=theta;
            }
            else if (step==4){
                double theta = ellipse.gettheta(ellipse.project(mouse,false));
                //CCW arc. Shift for CW
                if (StdDraw.isKeyPressed(SHIFT)) {
                    ellipse.settmin(theta);
                    ellipse.settmax(tmin);
                } else {
                    ellipse.settmax(theta);
                    ellipse.settmin(tmin);
                }
                ellipse.setxy();
            }

            /** Graphics **/
            Setup.setup(3);
            ellipse.draw();
            StdDraw.show();
            StdDraw.clear();

            /** Hotkeys and "Step" incrementation **/
            if (Keypress(ENTER)) {
                if (step==1) step=3;
                else {
                    objects.addItem(ellipse);
                    return;
                }
            }
            if (click) {
                int placeclicked = placeclicked(x, y);
                if (placeclicked == 1) return;
                if (placeclicked == 2) {
                    if (step < 4) step++;
                    else break;
                    continue;
                }
            }
            if (hotkeys()) return;
        }

        /** Adds ellipse to "objects" **/
        objects.addItem(ellipse);
    }
    public static void spline(Node start){
        /** Initialization **/
        Node[] initial = start.copy(2);
        Spline spline= new Spline(initial);

        /** Spline construction **/
        while(true) {

            /** Initialization **/
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            boolean click = click();
            Node node=new Node(x,y);

            /** Snap to node/item **/
            Item closest = snapto(node);
            if (closest!=null) node=closest.project(node);

            /** Set Spline parameters **/
            spline.setNode(node,spline.getDivider());

            /** Graphics **/
            Setup.setup(4);
            spline.draw();
            StdDraw.show();
            StdDraw.clear();

            /** Hotkeys and mouse clicking **/
            if (Keypress(ENTER)){
                if (inscreen(x,y)) objects.addItem(spline);
                return;
            }
            if (click) {
                if (placeclicked(x, y) == 1) return;
                else {
                    spline.addNode(node);
                    continue;
                }
            }
            if (hotkeys()) return;
        }
    }
    public static void save(){
        Setup.fileprompt(5);
        String name = scan.nextLine();

        try {
            PrintStream out = new PrintStream(new FileOutputStream(
                    "src/"+name));
            out.println(objects.totxt());
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tool=0;
    }
    public static void open(){
        /** Graphics **/
        Setup.fileprompt(-5);

        /** Initialization **/
        String name = scan.nextLine();
        Scanner scanfile = openFile("src/"+name);
        int numobjects = scanfile.nextInt();
        Group scanned = new Group(numobjects);

        /** Data scanning **/
        for (int i=0;i<numobjects;i++){
            //type and number of nodes
            int type = scanfile.nextInt();
            int n=scanfile.nextInt();
            //Node data collection
            Node[] nodes = new Node[n];
            for (int j=0;j<n;j++){
                double x = scanfile.nextDouble();
                double y = scanfile.nextDouble();
                nodes[j]=new Node(x,y);
            }

            /** Subclass-specific data interpretation **/
            if (type==1){
                scanned.getItems()[i]=nodes[0];
            }
            if (type==2){
                scanned.getItems()[i]=new Line(nodes[0],nodes[1]);
            }
            if (type==3){
                double a = scanfile.nextDouble();
                double tmin = scanfile.nextDouble();
                double tmax = scanfile.nextDouble();
                Ellipse ellipse=new Ellipse(nodes,a,tmin,tmax);
                ellipse.setxy();
                scanned.getItems()[i]=ellipse;
            }
            if (type==4){
                Spline spline =new Spline(nodes);
                spline.setxy(spline.calclength());
                scanned.getItems()[i]=spline;
            }
        }

        /** Exit conditions**/
        scanfile.close();
        scanned.copyinto(objects);
        tool=0;
    }
    public static void print(){
        StdDraw.clear();
        Setup.setup(6);
        StdDraw.show();
        StdDraw.pause(100);
        System.out.println("[asy]");
        for (int i=0;i<objects.size();i++){
            System.out.print(objects.getItems()[i].toAsy());
        }
        System.out.println("[/asy]");
        tool=0;
    }

    /** Graphics **/
    public static void tooltip(Node mouse){
        boolean close=mousefirstpos.distanceto(mouse) < 0.001;
        if (delay > 0) {
            if (close) {
                delay--;//why is this decrementing so slow?
            }
            else delay=40;
        } else if (close&&intoolbar(mouse.getX(),mouse.getY())){
            showtooltip(mouse);
            delay = 40;
        } else delay=40;
    }
    public static void showtooltip(Node temp){
        Setup.setup(tool);
        Setup.tooltips(toolselected(temp.getX(),temp.getY()));
        int delay=20;
        while(true){
            if (click())return;
            Node mousepos = new Node(StdDraw.mouseX(),StdDraw.mouseY());
            if (temp.distanceto(mousepos)<0.001 ||
                    Setup.intooltip(mousepos,temp,toolselected(temp.getX(),temp.getY()))) {
                StdDraw.show();
            } else if (delay>0){
                delay--;
                StdDraw.pause(5);
            } else return;
        }
    }

    /** Aux methods **/
    private static Scanner openFile(String s) {
        try {
            Scanner file = new Scanner(new java.io.FileReader(s));
            return file;
        }
        catch (java.io.FileNotFoundException e) {
            System.out.println ("Error opening file");
            System.exit(1);
            return null;
        }
    }
    public static Item snapto(Node mouse){//very similar to highlighteditem()
        Item closest = null;
        if (StdDraw.isKeyPressed(CTRL)) return null;
        double mindist = 0.01;//closest object to the cursor within 0.05 is highlighted
        for (int i=0;i<objects.size();i++){
            double dist=objects.getItems()[i].distancetolite(mouse);
            if (dist<mindist) {
                closest=objects.getItems()[i];
                mindist=dist;
            }
        }
        double nodemindist = 0.005;
        for (int i=0;i<objects.size();i++){
            Node[] nodes = objects.getstartstop();
            for (int j=0;j<nodes.length;j++){
                double dist=nodes[j].distancetolite(mouse);
                if (dist<nodemindist) {
                    closest= nodes[j];
                    nodemindist=dist;
                }
            }
        }
        if (closest!=null) closest.highlight(false);
        return closest;//and then do mouse.project(closest)
    }

    /******* Main Method *******/
    public static void main(String[] args) {

        /** Prelimiary operation **/
        if (args.length>0&&args[0].equals("debug")) debug=true;//debugging implementation coming soon (probably not)
        StdDraw.enableDoubleBuffering();
        Setup.setcanvas();//sets canvas to have same coordinate to pixel ratio as the default canvas
        Setup.setup();
        StdDraw.show();

        /** Core while loop **/
        while (true){
            /** Setting variables and objects **/
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Node mouse = new Node(x,y);
            boolean clicked = click();//A press + release sequence = 1 click
            if (clicked) {
                dclickcheck(x, y);//checks for double clicking. Not really implemented yet
                if (intoolbar(x, y)) tool = toolselected(x, y); //finds which tool has been selected
            } clicktimer++;//for tracking double clicks.
            hotkeys();

            /** Snap to object **/
            Item closest = snapto(mouse);
            if (closest!=null) mouse=closest.project(mouse);

            /** Tools **/
            if (tool==0) marquee();
            if (clicked&&inscreen(x,y)) {
                if (tool==1){
                    node(mouse);
                    continue;
                }
                if (tool==2){
                    line(mouse);
                    continue;
                    /*avoids a situation where pressing 3/4 returns from line() and
                    immediately goes to ellipse()/spline() without clearing the current node parameters*/
                }
                if (tool==3){
                    ellipse(mouse);
                    continue;
                }
                if (tool==4){
                    spline(mouse);
                    continue;
                }
            }
            if (tool==5)save();
            if (tool==-5)open();
            if (tool==6) print();
            if (tool == 7) {
                StdDraw.clear();
                Setup.setup(7);
                StdDraw.show();
                StdDraw.pause(100);
                System.exit(0);
            }

            /** Graphics **/
            //Setup, highlighting tool, and drawing objects
            Setup.setup(tool);
            //tooltip
            if (delay==40) mousefirstpos=mouse;
            if (!clicked) tooltip(mouse);
            StdDraw.show();
            StdDraw.clear();
        }
    }

}
