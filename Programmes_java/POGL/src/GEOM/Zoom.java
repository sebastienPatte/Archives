package GEOM;

public class Zoom extends Operation{
	int z;
	Zoom(int z){
		this.z=z;
	}

	@Override
	public void operate(Element e) {
		
	}

	@Override
	public void visitLine(Line l) {
		l.x1 *= this.z;
	    l.y1 *= this.z;
	    l.x2 *= this.z;
	    l.y2 *= this.z;
	}

	@Override
	public void visitCircle(Circle c) {
	    c.x *= this.z;
	    c.y *= this.z;
	    c.r *= this.z;
	}
}
