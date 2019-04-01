package GEOM;
/**
 * A line segment is a particular kind of element
 */
public class Line extends Element {

    /**
     * Coordinates of the two ends of the segment
     */
    protected int x1, y1;
    protected int x2, y2;
    
    public Line(int x1, int y1, int x2, int y2, String c) {
        super(c);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    @Override
    public void zoom(int z){
    	this.x1 *= z;
        this.y1 *= z;
        this.x2 *= z;
        this.y2 *= z;
    }
    
    @Override
    public String print(){
    	return "Line";
    }
}
