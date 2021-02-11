import java.awt.*;

public class Spline extends Item{
    private Node[] nodes;
    private double[] x;
    private double[] y;
    private int divider;
    //ex. divider = 3: nodes={0,1,2,3,4} + click@5 -> nodes={0,1,2,5,3,4}

    public Spline (Node[] nodes, int length){
        this.nodes=nodes;
        this.setxy(length);
        this.divider=nodes.length-1;
    }
    public Spline (Node[] nodes) {
        this(nodes,100);
    }

    /** Tests and calculations **/
    public int type(){
        return 4;
    }
    public boolean has(Node node){
        for (Node s : this.nodes){
            if (node.equals(s)) return true;
        }
        return false;
    }
    public int calclength(){
        double length = 0;
        for (int i=0;i<this.nodes.length-1;i++){
            length+=this.nodes[i].distanceto(this.nodes[i+1]);
        }
        return (int)(2*length*Project.canvassize);
    }
    public int nodetoi(Node node){
        double mindist = Integer.MAX_VALUE;
        int tmin = -1;
        for (int i=0;i<this.x.length;i++){
            double dist=Math.hypot(this.x[i]-node.getX(),this.y[i]-node.getY());
            if (dist<mindist){
                tmin=i;
                mindist=dist;
            }
        }
        return tmin;
    }
    public Node project(Node node){
        return this.itonode(this.nodetoi(node));
    }
    public int projectlite(Node node){
        double mindist = Integer.MAX_VALUE;
        int tmin = -1;
        int d=25;
        for (int i=d/4;i<d;i++){
            double dist=Math.hypot(this.x[i]-node.getX(),this.y[i]-node.getY());
            if (dist<mindist){
                tmin=i;
                mindist=dist;
            }
        }
        for (int i=1;i<this.x.length/d-1;i++){
            double dist=Math.hypot(this.x[i*d]-node.getX(),this.y[i*d]-node.getY());
            if (dist<mindist){
                tmin=i*d;
                mindist=dist;
            }
        }
        for (int i=this.x.length-d;i<this.x.length;i++){
            double dist=Math.hypot(this.x[i]-node.getX(),this.y[i]-node.getY());
            if (dist<mindist){
                tmin=i;
                mindist=dist;
            }
        }
        return tmin;
    }
    public double distanceto(Node node){
        return node.distanceto(this.itonode(this.nodetoi(node)));
    }
    public double distancetolite(Node node){
        return node.distanceto(this.itonode(this.projectlite(node)));
    }
    /*public Node drdt(double t){
        int n=this.nodes.length;
        double x=0;
        double y=0;
        for (int i=0;i<n-1;i++){
            x+=choose(n-1,i)*Math.pow(1-t,n-i-1)*Math.pow(t,i)*(this.nodes[i+1].getX()-this.nodes[i].getX());
            y+=choose(n-1,i)*Math.pow(1-t,n-i-1)*Math.pow(t,i)*(this.nodes[i+1].getY()-this.nodes[i].getY());
        }
        x*=n;
        y*=n;
        return new Node(x,y);
    }*///why does the mathematical derivative not work???
    public Node drdt(int t){
        int i=1;
        Node one,two;
        int n = this.x.length;
        int l=2;
        if (t-i>0) one = new Node(this.x[t-i],this.y[t-i]);
        else {
            one = new Node(this.x[t],this.y[t]);
            l--;
        }
        if (t+i<n) two = new Node(this.x[t+i],this.y[t+i]);
        else {
            two = new Node(this.x[t],this.y[t]);
            l--;
        }
        while (one.distanceto(two)==0) {
            i++;
            l+=2;
            if (t-i>0) one = new Node(this.x[t-i],this.y[t-i]);
            else l--;
            if (t+i<n) two = new Node(this.x[t+i],this.y[t+i]);
            else l--;
        }
        return two.minus(one).scale((double)this.x.length/l);
    }//numerical computation of derivative
    public static int fact(int n){
        if (n>12) throw new RuntimeException("Math.fact overflow error");
        int answer=1;
        for (int i=1;i<=n;i++){
            answer*=i;
        }
        return answer;
    }
    public static int choose(int n, int r){
        int[] temp = new int[n-r];
        for (int i=0;i<n-r;i++){
            temp[i]=i+1;
        }
        int answer=1;
        for (int i=r+1;i<=n;i++){
            answer*=i;
            for (int j=0;j<n-r;j++){
                if (temp[j]!=0&&answer%temp[j]==0) {
                    answer/=temp[j];
                    temp[j]=0;
                }
            }
        }
        return answer;
    }//avoids integer and stack overflow from simple direct and recursive computation methods

    /** Editing "this" **/
    public void setxy(){
        this.setxy(this.calclength());
    }
    public void setxy(int length){
        this.x=new double[length];
        this.y=new double[length];
        int n=this.nodes.length-1;
        for (int i=0;i<length;i++){
            for (int j=0;j<=n;j++){
                double t=(double)i/length;
                this.x[i]+=choose(n,j)*Math.pow(1-t,n-j)*Math.pow(t,j)*this.nodes[j].getX();
                this.y[i]+=choose(n,j)*Math.pow(1-t,n-j)*Math.pow(t,j)*this.nodes[j].getY();
            }
        }
    }
    public void setxy(double[]x, double[]y){
        this.x=x;
        this.y=y;
    }
    public void addNode(Node node){
        int n=this.nodes.length;
        Node[] temp = new Node[n+1];
        for (int i=0;i<divider;i++){
            temp[i]=this.nodes[i];
        }
        temp[divider]=node;
        for (int i=divider+1;i<n+1;i++){
            temp[i]=this.nodes[i-1];
        }
        if (!StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_CONTROL))divider++;
        this.nodes=temp;
        this.setxy();
    }
    /*public void deleteNode(int index){
        int n=this.nodes.length;
        Node[]temp = new Node[n-1];
        for (int i=0;i<index;i++)temp[i]=this.nodes[i];
        for (int i=index+1;i<n;i++)temp[i-1]=this.nodes[i];
        this.nodes=temp;
        this.divider--;
    }*/
    public void setNode(Node node, int index){
        this.nodes[index]=node;
        this.setxy();
    }
    public void shift(Node node){
        for (int i=0;i<this.nodes.length;i++){
            this.nodes[i].shift(node);
        }
        this.setxy();
    }

    /** Getting nodes, items, and values **/
    public Spline copy(){
        Node[] newnodes = new Node[this.nodes.length];
        for (int i=0;i<newnodes.length;i++){
            newnodes[i]=this.nodes[i].copy();
        }
        Spline spline = new Spline(newnodes);
        int n = this.x.length;
        double[]newx = new double[n];
        double[]newy=new double[n];
        for (int i=0;i<n;i++){
            newx[i]=this.x[i];
            newy[i]=this.y[i];
        }
        spline.setxy(newx,newy);
        return spline;
    }
    public double[] getx(){
        return this.x;
    }
    public double[] gety(){
        return this.y;
    }
    public int getDivider(){
        return this.divider;
    }
    public Node[] nodes(){
        return this.nodes;
    }
    public Node[] startstop(){
        Node[] startstop = new Node[2];
        startstop[0]=this.nodes[0];
        startstop[1]=this.nodes[this.nodes.length-1];
        return startstop;
    }
    public Node itonode(int i){
        return new Node(this.x[i],this.y[i]);
    }

    /** toString methods **/
    public String toAsy(){
        int n=this.nodes.length;
        if (n>4) System.err.println("Warning: Asymptote can only handle cubic splines");
        String Asycode = "draw(("+this.nodes[0].getX()+","+this.nodes[0].getY()+")..controls";
        for (int i=1;i<n-1;i++){
            Asycode+="("+this.nodes[i].getX()+","+this.nodes[i].getY()+") ";
            if (i<n-2) Asycode+="and ";
        }
        Asycode+="..(" + this.nodes[n-1].getX()+","+this.nodes[n-1].getY()+"));\n";
        return Asycode;
    }
    public String totxt(){
        String string = "4 "+this.nodes.length+"\n";
        for (int i=0;i<this.nodes.length;i++){
            string+=this.nodes[i].getX()+" "+this.nodes[i].getY()+"\n";
        }
        return string;
    }

    /** Graphics **/
    public void draw(){
        for (int i=0;i<this.x.length;i++){
            StdDraw.point(this.x[i],this.y[i]);
        }
    }
    public void highlight(boolean selected){
        if (selected){
            StdDraw.setPenRadius(0.015);
            StdDraw.setPenColor(Setup.selected);
            this.draw();
            StdDraw.setPenRadius();
        } else {
            Node mouse = new Node(StdDraw.mouseX(),StdDraw.mouseY());
            int i = this.nodetoi(mouse);
            Node node = this.itonode(i);
            Node drdt = this.drdt(i);
            Node s = drdt.scale(0.05/drdt.length());
            Setup.highlight(node.add(s),node.minus(s));
        }
        StdDraw.setPenColor(Color.black);
    }

}