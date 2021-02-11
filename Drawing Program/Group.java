import java.awt.*;

public class Group {
    private Item[] items;

    public Group(int n){
        Item[] items = new Item[n];
        this.items=items;
    }
    public Group(){
        this(0);
    }
    public Group(Item[] items){
        this.items=items;
    }

    /** Tests and calculations **/
    public boolean has(Item item){
        for (Item s : this.items){
            if (s.equals(item)) return true;
        }
        return false;
    }

    /** Editing "this" **/
    public void shift(Node node){
        for (int i=0;i<this.size();i++){
            this.items[i].shift(node);
        }
    }
    public void setItems(Item[] items){
        this.items=items;
    }
    public void addItem(Item item){
        int n=this.items.length;
        Item[] temp = new Item[n+1];
        for (int i=0;i<n;i++){
            temp[i]=this.items[i];
        }
        temp[n]=item;
        this.items=temp;
    }
    public void deleteItem(int index){
        int n=this.items.length;
        if (n<1) throw new RuntimeException("No items to delete");
        if (index>=n) throw new RuntimeException("index too high");
        Item[] temp = new Item[n-1];
        for (int i=0;i<index;i++)temp[i]=this.items[i];
        for (int i=index+1;i<n;i++)temp[i-1]=this.items[i];
        this.items=temp;
    }
    public void delete(Item item){
        for (int i=0;i<this.items.length;i++){
            if (item.equals(this.items[i])) this.deleteItem(i);
        }
    }
    public void delete(Group group){
        for (int i=0;i<group.size();i++){
            this.delete(group.items[i]);
        }
    }
    public void clear(){
        Item[] temp = new Item[0];
        this.items=temp;
    }

    /** Getting nodes, items, and values **/
    public int size(){
        return this.items.length;
    }
    public Item[] getItems(){
        return this.items;
    }
    public void copyinto(Group other){
        int n=this.items.length;
        int m=other.items.length;
        Item[] temp = new Item[n+m];
        for (int i=0;i<m;i++){
            temp[i]=other.items[i];
        }
        for (int i=0;i<n;i++){
            temp[i+m]=this.items[i];
        }
        other.items=temp;
    }
    public Node[] getstartstop(){
        Node[][] temp = new Node[this.size()][];
        int size = 0;
        for (int i=0;i<this.size();i++){
            temp[i]=this.items[i].startstop();
            size+=temp[i].length;
        }
        Node[] startstop = new Node[size];
        size=0;
        for (int i=0;i<this.size();i++){
            for (Node j : temp[i]){
                startstop[size]=j;
                size++;
            }
        }
        return startstop;
    }
    public Group transform(Node node){
        int n = this.size();
        Item[] newitems = new Item[n];
        for (int i=0;i<this.items.length;i++){ this.items[i].transform(node);
            newitems[i]=this.items[i].transform(node);
        }
        return new Group(newitems);
    }

    /** toString methods **/
    public void print(){
        for (int i=0;i<this.items.length;i++){
            this.items[i].print();
        }
    }
    public String totxt(){
        String txt = this.size()+"\n";
        for (Item item : this.items){
            txt+=item.totxt();
        }
        return txt;
    }

    /** Graphics **/
    public void draw(){
        for (int i=0;i<this.size();i++){
            this.items[i].draw();
        }
    }
    public void drawnodes(){
        StdDraw.setPenColor(Setup.nodecolor);
        for (int i=0;i<this.items.length;i++){
            this.items[i].drawnodes();
        }
        StdDraw.setPenColor(Color.black);
    }
    public void highlight(boolean selected){
        for (int i=0;i<this.items.length;i++){
            this.items[i].highlight(selected);
        }
    }
}
