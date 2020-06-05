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
	public static final String Test = "0 0 0 1 1 0 1 1";

	// Attributes
	private List<TPoint> body;
	private List<Integer> skirt;
	private int width;
	private int height;
	
	static private Piece[] pieces; // singleton static array of first rotations

	/**
	 * Defines a new piece given a TPoint[] array of its body. Makes its ownthis.grid_backup=this.grid;
	 * copy of the array and the TPoints inside it.
	 */

	public Piece(List<TPoint> points) {
	    int Xmax=0;
	    int Ymax=0;
	    // copie de points dans body
	    // et calcul de maxX et maxY
	    List<TPoint> body= new ArrayList<TPoint>(points.size());
	    for (TPoint point : points){
	    	body.add(point);
	    	if( Xmax < point.x ) {
	    		Xmax=point.x;
	    	}
	    	
	    	if( Ymax < point.y ) {
	    		Ymax=point.y;	
	    	}
	    }
	    this.body=body;
	    
	    // la valeur minimale de X et Y est 0 donc width=X+1 et height=Y+1
		this.width=Xmax+1;
		this.height=Ymax+1;
		
		// initialisation skirt à sa valeur la plus grande possible (height-1)
		List<Integer> skirt= new ArrayList<Integer>(this.width);
		for (int i=0; i<this.width;i++) {
			skirt.add(i,this.height-1);
		}
		
		// Maj Skirt : on parcours tous les points et si le points est plus bas que skirt 
		// on met à jour skirt (skirt[X]=Y)
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
	 * Constructeur Piece
	 * créé une piece en copiant la piece passée en paramètre dans 'this'
	 */
	 public Piece(Piece piece) {
	    this.body=piece.body;
		this.skirt=piece.skirt;
		this.width=piece.width;
		this.height=piece.height;
	}


	/**
	 * Given a string of x,y pairs ("0 0 0 1 0 2 1 0"), parses the points into a
	 * TPoint[] array. (Provided code)
	 */
	private static List<TPoint> parsePoints(String rep) {
	    
		TPoint point= new TPoint(0,0); 
		String[] repSplited = rep.split(" ");
		List<TPoint> res = new ArrayList<TPoint>(repSplited.length/2);
		
		
		//Remplissage res (avec parsed String)
	    for(int i=0; i<repSplited.length; i+=2) {
	    	point.x= Integer.parseInt(repSplited[i]);
	    	point.y= Integer.parseInt(repSplited[i+1]);
	    	res.add( new TPoint(point.x,point.y));
	    	
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

		//Symetrie par rapport a un axe horizontal et inversion abcisse/ordonee 
		//newX=(height-1)-Y;
		//newY=X;
		
		List<TPoint> nextBody= new ArrayList<TPoint>(this.body.size());
		for (TPoint point : this.body) {
			nextBody.add(new TPoint((this.height-point.y-1),point.x));
		}
		
		Piece res = new Piece(nextBody);
	    return res;
	}

	/**
	 * Returns true if two pieces are the same -- their bodies contain the same
	 * points. Interestingly, this is not the same as having exactly the same
	 * body arrays, since the points may not be in the same order in the bodies.
	 * Used internally to detect if two rotations are effectively the same.
	 */
	public boolean equals(Object obj) {
		//on renvoie true si obj=this
		if(obj==this)return true;
		// on revoie true si this.body contient tout les points de object.body et inversement
		// Sinon on revoie false
		Piece object=new Piece(obj.toString());
	    if(this.body.containsAll(object.body) && object.body.containsAll(this.body)) {
	    	return true;
	    }else {
	    	return false;
	    }
	 }
	
	// renvoie un chaine de caractères contenant tous les points de this.body
	// de la forme "0 0 1 0 1 1 0 1"
	public String toString() {
	    
		String str="";
		boolean premier=true;
		for(TPoint point : this.body) {
			//si on est sur le premier point on nen met pas d'espace au début
			// et on change premier en false pour ne plus repasser dans cette condition
			if(premier){
				str+=point.x+" "+point.y;
				premier=false;
			}else {
			str+=" "+point.x+" "+point.y;
			}
		}return str;
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
					new Piece(Test);
		}

		return Piece.pieces;
	}

}
