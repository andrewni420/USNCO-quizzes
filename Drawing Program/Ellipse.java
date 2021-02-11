import java.awt.*;

public class Ellipse extends Item{
    private Node[] nodes;
    //{foci[0], foci[1], tminnode, tmaxnode, other}
    //"other" = some point on ellipse = determines "a" value
    private double a;
    private double tmin;
    private double tmax;
    private double[]x;
    private double[]y;
    //private boolean outofbounds;
    public static double tau = 2*Math.PI;

    /******* Main constructors *******/
    public Ellipse (Node[] foci, double a, double tmin, double tmax){
        if (foci.length!=2) throw new RuntimeException("Wrong number of foci");
        this.a=a;
        this.nodes = new Node[5];
        this.nodes[0]=foci[0];
        this.nodes[1]=foci[1];
        this.nodes[4]=this.getcenter().add(new Node(this.a,0));
        if (this.bc()[1]>this.a) throw new RuntimeException("a is too small");
        this.tmin=tmin;
        this.tmax=tmax;
        this.nodes[2]=this.getpoint(tmin);
        this.nodes[3]=this.getpoint(tmax);
        this.setxy();
    }
    public Ellipse(Node one, Node two, Node three){
        this.nodes = new Node[5];
        this.nodes[4]=three;
        Node temp = three.project(one,two);
        double radius= one.distanceto(two)/2;
        double scalefactor = three.distanceto(temp)/Math.sqrt(Math.pow(radius,2)-Math.pow(temp.distanceto(one.avg(two,0.5)),2));
        double c=radius*Math.sqrt(Math.abs(Math.pow(scalefactor,2)-1));
        if (scalefactor<1) {
            this.a=radius;
            this.nodes[0]=one.avg(two,(this.a-c)/(2*this.a));
            this.nodes[1]=one.avg(two,(this.a+c)/(2*this.a));
        }
        else{//scalefactor>=1
            this.a=scalefactor*radius;
            Node center = one.avg(two,0.5);
            this.nodes[0]=one.rotate(center,Math.PI/2).scale(center,c/radius);
            this.nodes[1]=this.nodes[0].scale(center,-1);
        }
        this.tmin=0;
        this.tmax=2*Math.PI;
        this.nodes[2]=this.getpoint(0);
        this.nodes[3]=this.getpoint(2*Math.PI);
        this.setxy();
    }
    /** Special Cases **/
    public Ellipse(Node[] foci, double a){
        this(foci,a,0,2*Math.PI);
    }
    public Ellipse (Node center, double r){
        this(center.copy(2),r);
    }
    public Ellipse(Node one, Node two){
        this(one.avg(two,0.5),one.distanceto(two)/2);
    }

    /******* Tests and calculations *******/
    public int type(){
        return 3;
    }
    public boolean inarc(double theta){
        double[]t=this.thetas();
        double min = Math.min(t[0],t[1]);
        double max = Math.max(t[0],t[1]);
        boolean answer = theta>=min&&theta<=max;
        if (t[1]>t[0]) return answer;
        else return !answer;
    }
    public boolean has(Node node){
        boolean answer=false;
        if (node.equals(this.nodes[0])||node.equals(this.nodes[1])) answer=true;
        if (node.equals(this.nodes[2])||node.equals(this.nodes[3])) answer=true;
        if (node.equals(this.basis()[0])||node.equals(this.basis()[1])) answer=true;
        return answer;
    }
    public boolean hasxy(Node node){
        if (this.distanceto(node)<0.01) return true;
        else return false;
    }
    public double distanceto(Node node){
        Node project = this.project(node,true);
        return project.distanceto(node);
    }
    public double distancetolite(Node node){
        return this.projectlite(node,true).distanceto(node);
    }
    public double disttofoci(Node node){
        return node.distanceto(this.nodes[0])+node.distanceto(this.nodes[1]);
    }
    public Node drdt(double theta){
        Node[] basis = this.basis();
        Node x = basis[0].scale(-Math.sin(theta));
        Node y = basis[1].scale(Math.cos(theta));
        return x.add(y);
    }
    public double calclength(){
        double[] t = this.thetas();
        if (t[1]<=t[0]) t[1]+=2*Math.PI;
        return 2*Math.abs(t[1]-t[0])*a*Project.canvassize;
    }
    /*public boolean outofbounds(){
        return this.outofbounds;
    }*/

    /** Editing "this" **/
    public void shift(Node node){
        for (int i=0;i<5;i++){
            this.nodes[i].shift(node);
        }
        this.setxy();
    }
    public void setnode(double theta, int i){
        double t = 0;
        if (i==2) t=this.tmin;
        if (i==3) t=this.tmax;
        if (i==4) t=theta;
        this.nodes[i].copyxy(this.getpoint(t));
    }
    public void seta(){
        this.a=(this.nodes[4].distanceto(this.nodes[0])+this.nodes[4].distanceto(this.nodes[1]))/2;
    }
    public void settmin(double tmin){
        this.nodes[2]=this.getpoint(tmin);
    }
    public void settmax(double tmax){
        this.nodes[3]=this.getpoint(tmax);
    }
    public void setxy(){
        int length=(int)(this.calclength());
        this.x=new double[length];
        this.y=new double[length];
        double[] t=this.thetas();
        this.tmin=t[0];
        this.tmax=t[1];
        if (t[1]<=t[0]) t[1]+=2*Math.PI;
        for (int i = 0; i < length; i++) {
            Node temp = this.getzero().rotate(this.getcenter(), t[0]+i * (t[1] - t[0]) / length)
                    .scale(this.nodes[0], this.nodes[1], this.bc()[0] / this.a);
            this.x[i] = temp.getX();
            this.y[i] = temp.getY();
            //if (!this.outofbounds&&temp.outofbounds()) this.outofbounds=true;
        }
    }
    public void setxy(double[]x,double[]y){
        this.x=x;
        this.y=y;
    }
    public void recalctnodes() {
        if (!(this.disttofoci(this.nodes[2]) == 2 * this.a)) {
            this.nodes[2].copyxy(this.project(this.nodes[2], false));
        }
        if (!(this.disttofoci(this.nodes[3]) == 2 * this.a)) {
            this.nodes[3].copyxy(this.project(this.nodes[3], false));
        }
    }
    public void bound(){
    }//maybe have bound() keep track of when the ellipse exits/reenters screen and then deletes those parts of the array

    /** Getting nodes, items, and values **/
    //non-calculation intensive getting items
    public Ellipse copy(){
        double[]t=this.thetas();
        Node[] newfoci = new Node[2];
        newfoci[0]=this.nodes[0].copy();
        newfoci[1]=this.nodes[1].copy();
        Ellipse newellipse = new Ellipse(newfoci,this.a,t[0],t[1]);
        int n=this.x.length;
        double[] newx=new double[n];
        double[]newy=new double[n];
        for (int i=0;i<n;i++){
            newx[i]=this.x[i];
            newy[i]=this.y[i];
        }
        newellipse.setxy(newx,newy);
        return newellipse;
    }
    public Node[] basis(){
        Node[] orthogonalbasis = new Node[2];
        Node center = this.getcenter();
        Node zero=this.getzero();
        orthogonalbasis[0]=zero.minus(center);
        orthogonalbasis[1]=zero.minus(center).rotate(Math.PI/2).scale(this.bc()[0]/a);
        return orthogonalbasis;
    }
    public Node[] nodes(){
        return this.nodes;
    }
    public Node[] startstop(){
        this.recalctnodes();
        Node[] startstop = new Node[2];
        startstop[0]=this.nodes[2];
        startstop[1]=this.nodes[3];
        return startstop;
    }
    public Node getzero(){
        if (this.bc()[1]==0) return this.getcenter().add(new Node(this.a,0));
        else return this.nodes[0].scale(this.getcenter(),a/this.bc()[1]);
    }
    public Node getcenter(){
        return this.nodes[0].avg(this.nodes[1],0.5);
    }
    public Node getpoint(double theta){
        Node[] basis = this.basis();
        Node x = basis[0].scale(Math.cos(theta));
        Node y = basis[1].scale(Math.sin(theta));
        return this.getcenter().add(x).add(y);
    }
    //getting doubles
    public double[] getABC(){
        double[] temp = new double[3];
        temp[0]=this.a;
        temp[1]=this.bc()[0];
        temp[2]=this.bc()[1];
        return temp;
    }
    public double[] thetas(){
        double[] thetas = new double[2];
        thetas[0]=this.gettheta(this.nodes[2]);
        thetas[1]=this.gettheta(this.nodes[3]);
        return thetas;
    }
    public double[] bc(){
        double[] bc = new double[2];
        bc[1]=this.nodes[0].distanceto(this.nodes[1])/2;//c
        bc[0]=Math.sqrt(Math.pow(this.a,2)-Math.pow(bc[1],2));//b
        return bc;
    }
    public double[]getx(){
        return this.x;
    }
    public double[]gety(){
        return this.y;
    }
    public double gettheta(Node node) {
        Node temp = node.minus(this.getcenter()).changebasis(this.basis()[0], this.basis()[1], true);
        return (Math.atan2(temp.getY(), temp.getX())+tau)%tau;
    }
    //calculation-intensive item getting
    public Node project(Node node){
        return this.project(node,false);
    }
    public Node project(Node node,boolean isarc){
        double theta0=this.gettheta(node);
        int length=(int)(Math.PI*2*a*Project.canvassize);
        double mindist=Integer.MAX_VALUE;
        double minthet=0;
        boolean decreasing = false;
        for (int i=-length/16;i<15*length/16;i++){
            double theta=theta0+Math.PI*2*i/length;
            double dist=node.distanceto(this.getpoint(theta));
            if (dist<mindist) {
                mindist=dist;
                minthet=theta;
                //breaks when it starts increasing after decreasing
                decreasing=true;
            }else if (decreasing) break;
        }
        //returns an endpoint if the closest point isn't on the arc
        if (!isarc||this.inarc((minthet+2*Math.PI)%(2*Math.PI))) return this.getpoint(minthet);
        else {
            if (this.nodes[2].distanceto(node)>this.nodes[3].distanceto(node)) return this.nodes[3];
            else return this.nodes[2];
        }
    }//array-based   /**** Which is ****/
    public Node project2(Node node,boolean isarc){
        double theta0=this.gettheta(node);
        int length=(int)(this.calclength());
        Node closest = new Node();

        for (int i=-length/16;i<15*length/16;i++){
            double theta=theta0+Math.PI*2*i/length;
            Node point = this.getpoint(theta);
            if (node.minus(point).dot(this.drdt(theta))<1e-13) {
                closest=point;
                if (!isarc) return closest;
                else break;
            }
        }
        double minthet = this.gettheta(closest);
        if (this.inarc((minthet+2*Math.PI)%(2*Math.PI))) return this.getpoint(minthet);
        else {
            if (this.nodes[2].distanceto(node)>this.nodes[3].distanceto(node)) return this.nodes[3];
            else return this.nodes[2];
        }
    }//node-based   /**** better?  ****/
    public Node projectlite(Node node,boolean isarc){
        double theta0=this.gettheta(node);
        double length=Math.PI*2*a*Project.canvassize/25;
        double mindist=Integer.MAX_VALUE;
        double minthet=0;
        boolean decreasing = false;
        for (double i=-length/16;i<15*length/16;i++){
            double theta=theta0+Math.PI*2*i/length;
            double dist=node.distanceto(this.getpoint(theta));
            if (dist<mindist) {
                mindist=dist;
                minthet=theta;
                decreasing=true;
            }else if (decreasing) break;
        }
        if (!isarc||this.inarc((minthet+tau)%tau)) return this.getpoint(minthet);
        else {
            if (this.nodes[2].distanceto(node)>this.nodes[3].distanceto(node)) return this.nodes[3];
            else return this.nodes[2];
        }
    }//~25x less computations than project. Used for distance calculations

    /** toString methods **/
    public String toAsy(){
        String string;
        double[]t=this.thetas();
        if ((t[1]-t[0])%(2*Math.PI)==0){
            string = "path p = ellipse(("+this.getcenter().getX()+","+this.getcenter().getY()+")"
                    + ","+this.a+","+this.bc()[0]+");\n"
                    + "draw(rotate("+this.nodes[0].theta()*180/Math.PI+")*p);\n";
        } else {
            string = "pair c = (" + this.getcenter().getX() + "," + this.getcenter().getY() + ");\n"
                    + "path p = arc(c," + this.a + ", " + t[0] + ", " + t[1] + ");\n"
                    + "draw(rotate(" + this.nodes[0].theta() * 180 / Math.PI + ")*yscale(" + this.bc()[0] / this.a + ")*p);\n";
        }
        return string;
    }
    public String totxt(){
        return "3 2\n" + this.nodes[0].getX() +" "+this.nodes[0].getY()+
                "\n"+ this.nodes[1].getX()+" "+this.nodes[1].getY()+
                "\n"+this.a+" "+this.thetas()[0]+" "+this.thetas()[1]+"\n";
    }

    /** Graphics **/
    public void draw(){
        for (int i=0;i<this.x.length;i++){
            StdDraw.point(this.x[i],this.y[i]);
        }
    }
    public void highlight(boolean selected){
        if (selected) {
            StdDraw.setPenRadius(0.015);
            StdDraw.setPenColor(Setup.selected);
            this.draw();
            StdDraw.setPenRadius();
            StdDraw.setPenColor(Color.black);
        } else {
            Node node = new Node(StdDraw.mouseX(), StdDraw.mouseY());
            Node project = this.project(node,true);/**Use project not projectlite**/
            double theta=this.gettheta(project);
            Node drdt=this.drdt(theta);
            Node s = drdt.scale(0.05/drdt.length());
            Setup.highlight(project.add(s),project.minus(s));
        }
        StdDraw.setPenColor(Color.black);
    }//Consider using Elliptic Integrals
    public void debug(){
        Line line = new Line(this.nodes[0],this.nodes[1]);
        line.draw();
        Node center = this.getcenter();
        center.draw(0.01);
        Line other = new Line(center.add(this.basis()[1]),center.minus(this.basis()[1]));
        other.draw();
        StdDraw.setPenColor(Color.red);
        this.nodes[0].draw(0.015);
        this.nodes[1].draw(0.01);
        StdDraw.setPenColor(Color.blue);
        this.getzero().draw(0.015);
        this.getzero().scale(center,-1).draw(0.01);
        StdDraw.setPenColor(Color.green);
        this.basis()[1].draw(0.01);
        StdDraw.setPenColor(Color.black);
    }
}
