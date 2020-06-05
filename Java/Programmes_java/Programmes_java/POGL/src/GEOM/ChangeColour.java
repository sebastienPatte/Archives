package GEOM;

/**
 * Operation that changes the colour of an element.
 */
public class ChangeColour extends Operation {
    
    /**
     * The new colour
     */
    private String colour;

    public ChangeColour(String c) {
        colour = c;
    }

    /**
     * Change the colour of an element.
     * @param e The element whose colour has to be assigned
     */
    @Override
    public void operate(Element e) {
        e.colour = colour;
    }
    
}
