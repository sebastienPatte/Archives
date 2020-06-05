package GEOM;

/**
 * Template class for geometric elements
 */
public abstract class Element implements Visitable{

    /**
     * Each element has a unique id, defined using a global counter.
     */
    private static int counter = 0;
    protected final int id;
    
    /**
     * Each element also has a colour.
     */
    protected String colour;
    
    public Element(String c) {
        this.id = counter++;
        this.colour = c;
    }
    
    
    abstract public void visit(Visitor v);
    
    public void accept(Visitor v){
    	v.visit(this);
    }
}
