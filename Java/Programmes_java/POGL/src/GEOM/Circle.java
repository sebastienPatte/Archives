package GEOM;

/**
 * A circle is a particular kind of element
 */
public class Circle extends Element {

    /** 
     * Coordinates of the center, and radius
     */
    protected int x, y;
    protected int r;
    
    public Circle(int x, int y, int r, String c) {
        super(c);
        this.x = x;
        this.y = y;
        this.r = r;
    }
    

    
}
