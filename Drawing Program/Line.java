import java.awt.*;

public class Line extends Item {
    private Node start;
    private Node stop;

    Line(Node start,Node stop){
        this.start=start;
        this.stop=stop;
    }

    /** Tests and calculations **/
    public int type(){
        return 2;
    }
    public boolean has(Node node){
        if (node.equals(this.start)||node.equals(this.stop)) return true;
        else return false;
    }
    public double distanceto(Node node){
        Node project = node.project(this.start,this.stop);
        double dot = project.minus(this.start).dot(this.stop.minus(this.start));/////i did project.minus(start)
        if (dot<0) return node.distanceto(this.start);
        else if (dot>this.stop.minus(this.start).dot())return node.distanceto(this.stop);
        else return node.minus(project).distanceto(new Node(0,0));
    }
    public double distancetolite(Node node){
        return distanceto(node);
    }

    /** Editing "this" **/
    public void setStart(Node start){this.start=start;}
    public void setStop(Node stop){
        this.stop=stop;
    }
    public void setnodes(Node[] nodes){
        this.start=nodes[0];
        this.stop=nodes[1];
    }
    public void shift(Node node){
        this.start.shift(node);
        this.stop.shift(node);
    }
    public void shiftxy(double x, double y){
        this.start.shiftxy(x,y);
        this.stop.shiftxy(x,y);
    }

    /**Getting nodes and values **/
    public Node[] nodes(){
        Node[] nodes = new Node[2];
        nodes[0]=this.start;
        nodes[1]=this.stop;
        return nodes;
    }
    public Node[] startstop(){
        return this.nodes();
    }
    public Line copy(){
        return new Line(this.start.copy(),this.stop.copy());
    }
    public Node project(Node node){
        Node project = node.project(this.start,this.stop);
        double dot = project.minus(this.start).dot(this.stop.minus(this.start));/////i did project.minus(start)
        if (dot<0) return this.start;
        else if (dot>this.stop.minus(this.start).dot())return this.stop;
        else return project;
    }

    /** toString methods **/
    public String totxt(){
        return "2 2\n"+ this.start.getX()+" "+this.start.getY()+
                "\n"+ this.stop.getX()+" "+this.stop.getY()+"\n";
    }
    public String toAsy(){
        return "draw(("+this.start.getX()+","+this.start.getY()+")--("+this.stop.getX()+","+this.stop.getY()+"));\n";
    }

    /** Graphics **/
    public void draw(){
        StdDraw.line(start.getX(),start.getY(),stop.getX(),stop.getY());
    }
    public void highlight(boolean selected) {
        if (selected) {
            StdDraw.setPenRadius(0.015);
            StdDraw.setPenColor(Setup.selected);
            this.draw();
            this.draw();//to make highlighting as dark as for splines and ellipses
            // which have overlapping points being drawn
            StdDraw.setPenRadius();
            StdDraw.setPenColor(Color.black);
        } else {
            Node node = new Node(StdDraw.mouseX(), StdDraw.mouseY());
            Node project = node.project(this.start, this.stop);
            Node line = this.stop.minus(this.start);
            double length = line.length();
            if (length==0)length=1;
            double dot = project.minus(start).dot(line);
            Node s = line.scale(0.05 / length);
            double dot1 = 0.05 * length;

            if (dot < dot1) Setup.highlight(this.start.add(s.scale(2)), this.start);
            else if (dot > line.dot() - dot1) Setup.highlight(this.stop.minus(s.scale(2)), this.stop);
            else Setup.highlight(project.add(s), project.minus(s));
        }
        StdDraw.setPenColor(Color.black);
    }

}
