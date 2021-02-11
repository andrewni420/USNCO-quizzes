/*import java.awt.Color;
class Format {
    private Color color;
    private double penradius;
    private double[] dashpattern;//1 on 2 off is {1,2} cycles around and around the array.
    //1 on 2 off 3 on 4 off is {1,2,3,4}
    //will draw StdDraw lines instead of having curves??? prbly not

    public Format(){
        this.color=Color.black;
        this.penradius=0.005;
        double[] temp = {1};
        this.dashpattern=temp;
    }
    public Format(Color color, double penradius, double[] dashpattern){
        this.color=color;
        this.penradius=penradius;
        this.dashpattern=dashpattern;
    }

    public void dotted(){//idk how low the "on" part has to be to have dots...
    }

    public void clear(){
        StdDraw.setPenColor();
        StdDraw.setPenRadius();
    }

    public void setColor(Color color){
        this.color=color;
    }
    public void setPenradius(double Penradius){
        this.penradius=Penradius;
    }
    public void setDashpattern(double[] dashpattern){
        this.dashpattern=dashpattern;
    }

    public Color getColor(){
        return this.color;
    }
    public double getPenradius(){
        return this.penradius;
    }
    public double[] getDashpattern(){
        return this.dashpattern;
    }

    public void apply(){
        StdDraw.setPenColor(this.color);
        StdDraw.setPenRadius(this.penradius);
        // How to do dash pattern?
    }
}*/
