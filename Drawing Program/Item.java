public class Item {

    /** Tests and calculations **/
    public double distanceto(Node node){/*****NEEDS IMPLEMENTATION****/
        return Integer.MAX_VALUE;
    }
    public double distancetolite(Node node){/*****NEEDS IMPLEMENTATION****/
        return Integer.MAX_VALUE;
    }//maybe puts less strain on computer?
    public int type(){
        return 0;
    }//1 = node, 2 = line, 3 = ellipse, 4 = spline. My workaround for instanceOf because I read online that it's bad practice to use it in programming
    public int nodenum(Item other){
        Node[] nodes = other.nodes();
        for (int i=0;i<nodes.length;i++){
            if (this==nodes[i]) return i;
        }
        return -1;
    }
    public boolean has(Node node){
        return false;
    }

    /** Editing "this" **/
    public void shift(Node node){
    }
    public void setxy(){

    }//for splines and ellipses

    /** Getting nodes and items **/
    public Item copy(){
        return new Item();
    }
    public Node[] nodes(){
        return new Node[0];
    }
    public Node[] startstop(){
        return null;
    }
    public Item transform(Node node){
        Item temp=this.getClass().cast(this.copy());
        temp.shift(node);
        return temp;
    }//shift but creates a new object instead of modifying "this"
    public Node project(Node node){
        return null;
    }
    public Node[] intersectionpoints(Item one, Item two){
        Node[] intersections = new Node[0];
        return intersections;
    }

    /** toString methods **/
    public void print(){}
    public String toAsy(){
        return null;
    }//asymptote code
    public String totxt(){
        return null;
    }//for saving files

    /** Graphics **/
    public void draw(){

    }
    public void drawnodes(){
        for (int i=0;i<this.nodes().length;i++){
            this.nodes()[i].draw(0.0025);
        }
    }
    public void highlight(boolean selected){/*****NEEDS IMPLEMENTATION****/

    }

}
