package GEOM;
public class Geom {

    public static void main(String[] args) {
        // TODO
    	Print p = new Print();
    	Line e = new Line(1,1,2,2,"");
    	Translate t = new Translate(1,1);
    	p.operate(e);
    	apply(p,e);
    	apply(t,e);
    	apply(p,e);
    	
    }
    
    /**
     * Applies an operation to an element.
     * @param o The operation to be applied
     * @param e The element to which the operation is applied
     */
    public static void apply(Operation o, Element e) {
        o.operate(e);
    }
    
}
