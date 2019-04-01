package GEOM;

/**
 * Operation that prints information about an element.
 */
public class Print extends Operation {

    /**
     * For an element whose kind is not statically known: print "Element"
     */
    @Override
    public void operate(Element e) {
    	String typeReel = e.print();
        System.out.println(typeReel+ e.id + " " + e.colour);
    }

}
