import java.util.List;

import java.util.ArrayList;


/**
 * An immutable representation of a tetris piece in a particular rotation. Each
 * piece is defined by the blocks that make up its body.
 * 
 * Typical client code looks like...
 * 
 * <pre>
 * Piece pyra = new Piece(PYRAMID_STR); // Create piece from string
 * int width = pyra.getWidth(); // 3
 * Piece pyra2 = pyramid.computeNextRotation(); // get rotation
 * 
 * Piece[] pieces = Piece.getPieces(); // the array of all root pieces
 * </pre>
 */
public class Piece {

	// String constants for the standard 7 Tetris pieces
	public static final String STICK_STR = "0 0 0 1 0 2 0 3";
	public static final String L1_STR = "0 0 0 1 0 2 1 0";
	public static final String L2_STR = "0 0 1 0 1 1 1 2";
	public static final String S1_STR = "0 0 1 0 1 1 2 1";
	public static final String S2_STR = "0 1 1 1 1 0 2 0";
	public static final String SQUARE_STR = "0 0 0 1 1 0 1 1";
	public static final String PYRAMID_STR = "0 0 1 0 1 1 2 0";

	// Attributes
	private List<TPoint> body;
	private List<Integer> skirt;
	private int width;
	private int height;
	
	static private Piece[] pieces; // singleton static array of first rotations

	/**
	 * Defines a new piece given a TPoint[] array of its body. Makes its own
	 * copy of the array and the TPoints inside it.
	 */

	public Piece(List<TPoint> points) {
	    int width=0;
	    int height=0;
	    List<TPoint> body = new ArrayList<TPoint>(points.size());
	    int cpt=0;
	    for (TPoint point : points){
	    	body.add(cpt,point);
	    	cpt++;
	    }
	    this.body=body;
	    
	    for (TPoint point : points){	    	
	    	if( width < point.x ) {
	    		width=point.x;
	    	}
	    	
	    	if( height < point.y ) {
	    		height=point.y;	
	    	}
	    }
		this.width=width+1;
		this.height=height+1;
		
		//init skirt
		List<Integer> skirt= new ArrayList<Integer>(this.width);
		for (int i=0; i<this.width;i++) {
			skirt.add(i,this.height-1);
		}
		
		//Maj Skirt
		for (TPoint point : this.body){
			if(skirt.get(point.x)>point.y) {
				skirt.set(point.x,point.y);
			}
					
		}
		this.skirt=skirt;
	    
	}
	
	/**
	 * Alternate constructor, takes a String with the x,y body points all
	 * separated by spaces, such as "0 0 1 0 2 0 1 1". (provided)
	 */
	public Piece(String points) {
		this(parsePoints(points));
	}

	/*
	 * créé une piece en copiant la piece 'this'
	 */
	public Piece(Piece piece) {
	    piece.body=this.body;
		piece.skirt=this.skirt;
		piece.width=this.width;
		piece.height=this.height;
	}


	/**
	 * Given a string of x,y pairs ("0 0 0 1 0 2 1 0"), parses the points into a
	 * TPoint[] array. (Provided code)
	 */
	private static List<TPoint> parsePoints(String rep) {
	    TPoint point= new TPoint(0,0); 
		String[] repSplited = rep.split(" ");
		
		//Init res
		List<TPoint> res= new ArrayList<TPoint>(repSplited.length/2);
		for (int k=0;k<(repSplited.length/2);k++) {			
			res.add(k,new TPoint(0,0));
		}
		
		//Remplissage res (with parsed String)
		int j=0;
	    for(int i=0; i<rep.length()/2; i+=2) {
	    	point.x= Integer.parseInt(repSplited[i]);
	    	point.y= Integer.parseInt(repSplited[i+1]);
	    	res.set(j, new TPoint(point.x,point.y));
	    	j++;
	    }
	    return res;
	}
	
	/**
	 * Returns the width of the piece measured in blocks.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Returns the height of the piece measured in blocks.
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Returns a reference to the piece's body. The caller should not modify this
	 * list.
	 */
	public List<TPoint> getBody() {
		return this.body;
	}

	/**
	 * Returns a reference to the piece's skirt. For each x value across the
	 * piece, the skirt gives the lowest y value in the body. This is useful for
	 * computing where the piece will land. The caller should not modify this
	 * list.
	 */
	public List<Integer> getSkirt() {
		return this.skirt;
	}

	/**
	 * Returns a new piece that is 90 degrees counter-clockwise rotated from the
	 * receiver.
	 */
	public Piece computeNextRotation() {
	    // YOUR CODE HERE
		int X=0;
		int Y=0;
		//Symetrie par rapport a un axe horizontal
		//newY=(height-1)-Y;
		TPoint point=new TPoint(0,0);
		List<TPoint> body= this.body;
		for (int i=0; i<body.size();i++) {
			point = body.get(i);
			point.y=(this.height-1)-point.y;
			body.set(i, point);
		}System.out.println(body);
		//inversion abcisse/ordonee 
		
		
		for (int j=0; j<body.size();j++) {
			point = body.get(j);
			X=point.y;
			Y=point.x;
			body.set(j,new TPoint(X, Y));	
		}
		Piece res = new Piece(body);
		System.out.println(body);
		System.out.println(res.width+" "+res.height);
		
	    return res;
	}

	/**
	 * Returns true if two pieces are the same -- their bodies contain the same
	 * points. Interestingly, this is not the same as having exactly the same
	 * body arrays, since the points may not be in the same order in the bodies.
	 * Used internally to detect if two rotations are effectively the same.
	 */
	public boolean equals(Object obj) {
	    return false;
	    // YOUR CODE HERE
	    
	}

	public String toString() {
	    // YOUR CODE HERE
		return null;
	}

	/**
	 * Returns an array containing the first rotation of each of the 7 standard
	 * tetris pieces in the order STICK, L1, L2, S1, S2, SQUARE, PYRAMID. The
	 * next (counterclockwise) rotation can be obtained from each piece with the
	 * {@link #fastRotation()} message. In this way, the client can iterate
	 * through all the rotations until eventually getting back to the first
	 * rotation. (provided code)
	 */
	public static Piece[] getPieces() {
		// lazy evaluation -- create static array if needed
		if (Piece.pieces == null) {
			Piece.pieces = new Piece[] { 
					new Piece(STICK_STR), 
					new Piece(L1_STR),
					new Piece(L2_STR), 
					new Piece(S1_STR),
					new Piece(S2_STR),
					new Piece(SQUARE_STR),
					new Piece(PYRAMID_STR)};
		}

		return Piece.pieces;
	}

}
