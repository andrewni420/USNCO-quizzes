import java.awt.*;

public class Node extends Item{
    private double x;
    private double y;

    public Node(double x, double y){
        this.x=x;
        this.y=y;
    }
    public Node(){
        this(0,0);
    }

    /** Tests and calculations **/
    public int type(){
        return 1;
    }
    public boolean equalto(Node other){
        return this.x==other.x&&this.y==other.y;
    }
    public boolean has(Node node){
        return this.equals(node);
    }
    public boolean outofbounds(){
        return Math.abs(this.x-0.5)>0.5||Math.abs(this.y-0.5)>0.5;
    }

        /** Geometric methods **/
        //arithmetic operations
        public Node add(Node other){
            return new Node(this.x+other.x,this.y+other.y);
        }
        public Node minus(Node other){return this.add(other.negative());}
        public Node negative(){
        return new Node(-this.x,-this.y);
    }
        public Node avg(Node other,double t){
        return new Node((1-t)*this.x+t*other.x,(1-t)*this.y+t*other.y);
    }
        //metric operations
        public double length(){return this.distanceto(new Node(0,0));}
        public  double dot(Node other){
            return this.x*other.x+this.y*other.y;
        }
        public  double dot(){
        return this.x*this.x+this.y*this.y;
    }
        public double distanceto(Node other){
        return Math.sqrt(Math.pow(this.x-other.x,2)+Math.pow(this.y-other.y,2));
    }
        public double distancetolite(Node other){
        return this.distanceto(other);
    }
        //general geometric operations
        public Node rotate(double theta){
        return new Node(this.x*Math.cos(theta)-this.y*Math.sin(theta),this.x*Math.sin(theta)+this.y*Math.cos(theta));
    }
        public Node rotate(Node pivot, double theta){
        return this.minus(pivot).rotate(theta).add(pivot);
    }
        public Node scale(double c){
        return new Node(c*this.getX(),c*this.getY());
    }
        public Node scale(Node center, double c){
        return this.add(center.negative()).scale(c).add(center);
    }
        public Node scale(Node one, Node two,double c){//one=two
        Node projection=this.project(one,two);
        Node scaleddistance = this.add(projection.negative()).scale(c);
        return projection.add(scaleddistance);
    }
        public Node project(Node one, Node two){//one=two
        Node temp = two.minus(one);//temp=0
        Node temp1=this.minus(one);
        double dot = 1;
        if (temp.dot()!=0)dot=temp1.dot(temp)/temp.dot();
        return temp.scale(dot).add(one);
    }
        public Node changebasis(Node one, Node two,boolean inverse){
        double a = one.getX();
        double b = two.getX();
        double c = one.getY();
        double d = two.getY();
        double x = this.getX();
        double y = this.getY();
        if (!inverse) return new Node(a*x+b*y,c*x+d*y);
        else return new Node(d * x - b * y, -c * x + a * y).scale(a * d - b * c);
    }

    /** Editing "this" **/
    public void shift(Node node){
        this.shiftxy(node.x,node.y);
    }
    public void shiftxy(double x, double y){
        this.x+=x;
        this.y+=y;
    }
    public void copyxy(Node other){
        this.x=other.x;
        this.y=other.y;
    }
    public void setxy(double x, double y){
        this.x=x;
        this.y=y;

    }

    /** Getting nodes and values **/
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public Node copy(){
        return new Node(this.x,this.y);
    }
    public Node[] copy(int length){
        Node[] copied = new Node[length];
        for (int i=0;i<length;i++){
            copied[i]=new Node(this.x,this.y);
        }
        return copied;
    }
    public double theta(){
        return Math.atan2(this.getY(),this.getX());
    }
    public Node project(Node node){
        return new Node(this.x,this.y);
    }
    public Node[] startstop(){
        Node[] node = new Node[1];
        node[0]=this;
        return node;
    }

    /** toString methods **/
    public String toAsy(){
        return "dot(("+this.x+","+this.y+"));\n";
    }
    public String totxt(){
        return "1 1\n" +this.x+" "+this.y+"\n";
    }

    /** Graphics **/
    public void draw(){
        StdDraw.filledCircle(this.x,this.y,0.005);
    }
    public void draw(double radius){
        StdDraw.filledCircle(this.x,this.y,radius);
    }
    public void highlight(boolean selected){
        if (selected) {
            StdDraw.setPenColor(Setup.selected);
            this.draw(0.01);
        } else {
            StdDraw.setPenColor(Setup.highlighter);
            this.draw(0.03);
        }
        StdDraw.setPenColor(Color.black);
    }

}