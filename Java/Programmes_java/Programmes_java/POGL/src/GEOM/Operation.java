package GEOM;
/**
 * Template class for the operations
 */
public abstract class Operation implements Visitor{

    /**
     * Operation on an element whose kind is not statically known
     */
    abstract public void operate(Element e);
    
    /**
     * Operations specific to lines and circle elements
     */
    public void operate(Line l) {}
    public void operate(Circle c) {}
    
    abstract public void visitLine(Line l);
    abstract public void visitCircle(Circle c);
	
}
