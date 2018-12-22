import java.util.Arrays;
import java.util.List;

/**
 * Represents a Tetris board -- essentially a 2-d grid of booleans. Supports
 * tetris pieces and row clearing. Has an "undo" feature that allows clients to
 * add and remove pieces efficiently. Does not do any drawing or have any idea
 * of pixels. Instead, just represents the abstract 2-d board.
 */
public class Board {

	private int width;
	private int height;
	private boolean committed;
	 
	// backups
	 private boolean[][] grid_backup;
	 private int[] widths_backup;
	 private int[] heights_backup;
	 
	// utilisé dans les tests
	 protected boolean[][] grid; 
	 protected int[] widths;
	 protected int[] heights;
	
	/**
	 * Creates an empty board of the given width and height measured in blocks.
	 */
	public Board(int width, int height) {
		// copie de width et height
		this.width = width;
		this.height = height; 
		// initialisation grid widths heights et committed
		this.grid = new boolean[width][height];
		this.widths= new int[height];
		this.heights= new int[width];
		this.committed = true;
		
		// Initialisation à 0/false (board vide) des valeurs de widths heights et grid
		for (int i=0; i<this.grid.length;i++) {
			for (int j=0; j<this.grid[i].length;j++) {
				this.grid[i][j]=false;
			}this.widths[i]=0;
			this.heights[i]=0;
		
		}
		
		// initialisation des backups 
		this.grid_backup = new boolean[width][height];
		this.widths_backup = new int[height];
		this.heights_backup = new int[width];
	}
	/*
	 * retourne la largeur de board
	 */
	public int getWidth() {
		return this.width;
	}
	/*
	 * retourn la hauteur de board 
	 */
	public int getHeight() {
		return this.height;
	}
	
	/*
	 * copie la piece passée en paramètre dans 'this'
	 * Utilisé pour créer une copie d'une pièce
	 */
	public Board (Board board) {
		this.width=board.width;
		this.height=board.height;
		this.grid=board.grid;
		this.committed=board.committed;
		this.widths=board.widths;
		this.heights=board.heights;
		this.grid_backup=board.grid_backup;
		this.widths_backup=board.widths_backup;
		this.heights_backup=board.heights_backup;
	}

	
	/**
	 * Returns the max column height present in the board. For an empty board
	 * this is 0.
	 */
	public int getMaxHeight() {
	    
		int maxHeight= 0;
	    for (int i=0; i<this.grid.length;i++) {
	    	if (getColumnHeight(i)>maxHeight) {
	    		maxHeight=getColumnHeight(i);
	    	}
	    }return maxHeight;
	}

	/**
	 * Given a piece and an x, returns the y value where the piece would come to
	 * rest if it were dropped straight down at that x.
	 * 
	 * <p>
	 * Implementation: use the skirt and the col heights to compute this fast --
	 * O(skirt length).
	 * </p>
	 */
	public int dropHeight(Piece piece, int x) {
	    /** 
	     * la valeur y qu'il faut renvoyer est l'ordonnée où tombe  le point (0,0)
	     * de la pièce même si ce point n'existe pas
	     * la valeur de y pour une colonne est donc y = getColumnHeight - skirt[i]
	     * puis on renvoie le y le plus grand trouvé
	     */
		int result = 0;
		List<Integer> skirt = piece.getSkirt();
		for(int i =0 ; i < skirt.size();i++){
			int y = getColumnHeight(i+x)-skirt.get(i);
			
			if(y>result) {
				result=y;
			}
		}
		return result;
	}

	/**
	 * Returns the height of the given column -- i.e. the y value of the highest
	 * block + 1. The height is 0 if the column contains no blocks.
	 */
	public int getColumnHeight(int x) {
		int res=0; // valeur par défaut
		// on parcours la colonne x et si on trouve un bloc res = yBloc + 1
		for(int y=0; y<this.grid[x].length;y++) {
			if(this.grid[x][y]) {
				res=y+1;
			}
	    }return res;
	    
	    //si on peut utiliser heights on fait juste :
	    	// return heights[x]+1
	}

	/**
	 * Returns the number of filled blocks in the given row.
	 */
	public int getRowWidth(int y) {
		int res=0; // valeur par défaut
		// on parcours la ligne y et si on trouve un bloc res += 1
		for(int x=0; x<this.width;x++) {
			if(this.grid[x][y]) {
				res+=1;
			}
	    }return res;
		
		//si on peut utiliser widths on fait juste : 
			// return widths[y];
	}

	/**
	 * Returns true if the given block is filled in the board. Blocks outside of
	 * the valid width/height area always return true.
	 */
	public boolean getGrid(int x, int y) {
	    return this.grid[x][y];
	}

	
	/*
	 *  Met à jour les backups de board 
	 */
	private void backup(){
		
		for(int cptWidths=0; cptWidths<this.height; cptWidths++) {
			this.widths_backup[cptWidths]=this.widths[cptWidths];
		}
		for(int cptHeights=0; cptHeights<this.width; cptHeights++) {
			this.heights_backup[cptHeights]=this.heights[cptHeights];
		}
		
		for(int i=0; i<this.grid.length; i++) {
			for(int j = 0; j <this.grid[i].length; j++)
			this.grid_backup[i][j]=this.grid[i][j];
		}
		
	}
	
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	
	/**
	 * Attempts to add the body of a piece to the board. Copies the piece blocks
	 * into the board grid. Returns PLACE_OK for a regular placement, or
	 * PLACE_ROW_FILLED for a regular placement that causes at least one row to
	 * be filled.
	 * 
	 * <p>
	 * Error cases: A placement may fail in two ways. First, if part of the
	 * piece may falls out of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 * Or the placement may collide with existing blocks in the grid in which
	 * case PLACE_BAD is returned. In both error cases, the board may be left in
	 * an invalid state. The client can use undo(), to recover the valid,
	 * pre-place state.
	 */
	public int place(Piece piece, int x, int y) {
		
		if (!this.committed) {
		    	throw new RuntimeException("can only place object if the board has been commited");
		    }
		  		backup();
		    	int pieceX=0;
		    	int pieceY=0;
		    	committed = false;
		    	int result = PLACE_OK;
		    	
		    	//on parcours chaque bloc de la piece passée en paramètre
		 		List<TPoint> body = piece.getBody();
		 		for (TPoint point : body) {
		 			pieceX= x+point.x;
		 			pieceY= y+point.y;
		 			
		 			// si le bloc dépasse du board on renvoie PLACE_OUT_BOUNDS
		 			if(pieceX<0 || pieceY< 0 || pieceX>=this.width || pieceY >=height)
					{
						result = PLACE_OUT_BOUNDS;
						break;
					} 
		 			
		 			// si l'endroit où le bloc doit être placé est déjà occupé par un autre bloc
		 			//  on renvoie PLACE_BAD
		 			if(this.grid[pieceX][pieceY])
					{
						result = PLACE_BAD;
						break;
					}
		 			
		 			// Sinon on place le bloc dans grid et on met a jour heights et widths
		 			this.grid[pieceX][pieceY] = true;
		 			
		 			if(this.getColumnHeight(pieceX)-1<pieceY+1) {
		 				this.heights[pieceX]=pieceY+1;
		 			}
		 			this.widths[pieceY]++;
		 			
		 			// Si la ligne est remplie result = PLACE_ROW_FILLED et on continue la boucle
					if(this.getRowWidth(pieceY) == this.width) {
						result = PLACE_ROW_FILLED;
					}
		 		}
		 		return result;
		}

	/**
	 * Deletes rows that are filled all the way across, moving things above
	 * down. Returns the number of rows cleared.
	 */
	public int clearRows() {
		int nbRowsCleared=0;
		
		// si board est dans l'état committed on met à jour les backups
		// et on passe committed à false
		 if(this.committed) {
			this.committed=false;
			backup();
		}
		
		
		for(int i=0; i<this.widths.length; i++) {
			if(this.getRowWidth(i)==this.width) {
				nbRowsCleared++;
			
				//MAJ heights
				for (int cptClear=0; cptClear<this.width; cptClear++) {
					this.heights[cptClear]-=1;
				}
			
				//decalage de valeurs de widths et grille
				for(int cpty=i; cpty<this.height-1; cpty++) {
					//MAJ widths
					this.widths[cpty]=this.widths[cpty+1];
					//MAJ grid
					for(int cptx=0; cptx<this.width; cptx++) {
						this.grid[cptx][cpty]=this.grid[cptx][cpty+1];
					}
				}
	    	
				//réinitialisation de la ligne du haut
				this.widths[this.height-1]=0;
				for (int x=0; x<this.width; x++) {
					this.grid[x][this.height-1]=false;
				}
	    	
				/*
				 * on a supprimé une ligne on doit donc rester 
				 * au même i car la ligne suivante a été 
				 * déplacée à la position currente. donc i--
				 */
				i--;
			}
	    }
		return nbRowsCleared;
	}	
	

	/**
	 * Reverts the board to its state before up to one place and one
	 * clearRows(); If the conditions for undo() are not met, such as calling
	 * undo() twice in a row, then the second undo() does nothing. See the
	 * overview docs.
	 */
	public void undo() {
	    // YOUR CODE HERE
			if(!committed) {


				for(int cpt1=0; cpt1<this.widths.length;cpt1++) {
					int temp = this.widths_backup[cpt1];
					this.widths_backup[cpt1] = this.widths[cpt1];
					this.widths[cpt1]=temp;
				}
				
				for(int cpt2=0; cpt2<this.heights.length;cpt2++) {
					int temp = this.heights_backup[cpt2];
					this.heights_backup[cpt2] = this.heights[cpt2];
					this.heights[cpt2]=temp;
				}
				boolean[][] grid_temp = this.grid_backup;
				this.grid_backup=this.grid;
				this.grid=grid_temp;
				
				

				
			}
			commit();
	}
	
	/*
	 * méthode publique qui revoie si le board est dans l'état committed
	 * utilisée dans 'JBrainTetris.java'
	 */
	public boolean isCommitted() {
		return this.committed;
	}
	/**
	 * Puts the board in the committed state.
	 */
	public void commit() {
	    // YOUR CODE HERE
	    this.committed = true;
	}


	
	/*
	 * Renders the board state as a big String, suitable for printing. This is
	 * the sort of print-obj-state utility that can help see complex state
	 * change over time. (provided debugging utility)
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = this.height - 1; y >= 0; y--) {
			buff.append('|');
			for (int x = 0; x < this.width; x++) {
				if (getGrid(x, y))
					buff.append('+');
				else
					buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x = 0; x < this.width + 2; x++)
			buff.append('-');
		return buff.toString();
	}
	
	// Only for unit tests
	protected void updateWidthsHeights() {
		Arrays.fill(this.widths, 0);

		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (this.grid[i][j]) {
					this.widths[j] += 1;
					this.heights[i] = Math.max(j + 1, this.heights[i]);
				}
			}
		}
	}

}

