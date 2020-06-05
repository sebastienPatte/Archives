package GEOM;

public class Translate extends Operation{
	int dx;
	int dy;
	
	Translate(int dx, int dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public void operate(Element e){
		if(e instanceof Line)operateLine((Line)e);
		if(e instanceof Circle)operateCircle((Circle)e);
	}
	
	public void operateLine(Line l){
		l.x1 += dx; 
		l.y1 += dy;
		l.x2 += dx;
		l.y2 += dy;
	}
	
	public void operateCircle(Circle c){
		
	}
}
