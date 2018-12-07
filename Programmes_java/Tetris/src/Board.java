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

	protected boolean[][] grid;
	private boolean committed;
	 int[] widths;
	 int[] heights;
	 
	 //backups
	 private boolean[][] grid_backup;
	 private int[] widths_backup;
	 private int[] heights_backup;
	
	/**
	 * Creates an empty board of the given width and height measured in blocks.
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height; 
		
		this.grid = new boolean[width][height];
		this.widths= new int[height];
		this.heights= new int[width];
		this.committed = true;
		// YOUR CODE HERE
		for (int i=0; i<this.grid.length;i++) {
			for (int j=0; j<this.grid[i].length;j++) {
				this.grid[i][j]=false;
			}this.widths[i]=0;
			this.heights[i]=0;
		
		}System.out.println(this.toString());
		
		//backups 
		this.grid_backup = new boolean[width][height];
		this.widths_backup = new int[height];
		this.heights_backup = new int[width];
	}
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	
	public Board (Board board) {
		board.width=this.width;
		board.height=this.height;
		board.grid=this.grid;
		board.committed=this.committed;
		board.widths=this.widths;
		board.heights=this.heights;
		board.grid_backup=this.grid_backup;
		board.widths_backup=this.widths_backup;
		board.heights_backup=this.heights_backup;
	}
//FOR DEBUGGING
	public void affiche_grid(boolean[][] grid)
	{
		String str="";
		System.out.println("Affiche grid");
	for(int i=0; i<grid.length; i++) {
			for (int j=0; j< grid[i].length; j++) {
				if(grid[i][j]) {
					str+="1";
				}else {
					str+="0";
				}
			}System.out.println(str);
			str="";
		}
	}
	
	/**
	 * Returns the max column height present in the board. For an empty board
	 * this is 0.
	 */
	public int getMaxHeight() {
	    // YOUR CODE HERE
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
	 */
	public int dropHeight(Piece piece, int x) {
	    // YOUR CODE HERE
		int result = 0;
		List<Integer> skirt = piece.getSkirt();
		for(int i =0 ; i < skirt.size();i++){
			int y = heights[x+i]-skirt.get(i);
			if(y>result) {
				result=y;
			}
		}
		return result-1;
	}

	/**
	 * Returns the height of the given column -- i.e. the y value of the highest
	 * block + 1. The height is 0 if the column contains no blocks.
	 */
	public int getColumnHeight(int x) {
		// YOUR CODE HERE
		int res=-1;
		for(int y=0; y<this.grid[x].length;y++) {
			if(this.grid[x][y]) {
				res=y;
			}
	    }return res+1;
	}

	/**
	 * Returns the number of filled blocks in the given row.
	 */
	public int getRowWidth(int y) {
	    // YOUR CODE HERE
		return widths[y];
	}

	/**
	 * Returns true if the given block is filled in the board. Blocks outside of
	 * the valid width/height area always return true.
	 */
	public boolean getGrid(int x, int y) {
	    // YOUR CODE HERE
	    return this.grid[x][y];
	}

	
	// to copy the state of the grid in backups 
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
			
		System.out.println("Backup");
		affiche_grid(this.grid_backup);
		
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
		 		List<TPoint> body = piece.getBody();
		 		
		 		for (TPoint point : body) {
		 			
		 			pieceX= x+point.x;
		 			pieceY= y+point.y;
		 			
		 			if(pieceX<0 || pieceY< 0 || pieceX>=this.width || pieceY >=height)
					{
						result = PLACE_OUT_BOUNDS;
						break;
					} 
		 			
		 			if(this.grid[pieceX][pieceY])
					{
						result = PLACE_BAD;
						break;
					}
		 			
		 			this.grid[pieceX][pieceY] = true;
		 			
		 			if(this.heights[pieceX]<pieceY+1) {
		 				this.heights[pieceX]=pieceY+1;
		 			}
		 			System.out.println("pieceY = "+pieceY+"  "+this.widths.length);
		 			
		 			this.widths[pieceY]++;

					if(this.widths[pieceY] == this.width) {
						result = PLACE_ROW_FILLED;
					}
		 		}
		 		System.out.println("finPlace");
		 		affiche_grid(this.grid_backup);
		 		System.out.println(this.toString());
		 		System.out.println(result);
		 		return result;
		 		// recompute WIDTH/HEIGHTS	
		 		
		 		
		}

	/**
	 * Deletes rows that are filled all the way across, moving things above
	 * down. Returns the number of rows cleared.
	 */
	public int clearRows() {
		boolean[][] grid_temp= this.grid;
		affiche_grid(grid_temp); 
		int nbRowsCleared=0;
		if(this.committed) {
			this.committed=false;
			backup();
		}
		
		boolean ligneRemplie= true;
		
		for(int i=0; i<this.height; i++) {
			ligneRemplie=true;
	    	for(int j=0; j<this.width; j++) {
	    		if(!grid_temp[j][i]) {
	    			System.out.println("line "+i);
	    			System.out.println("False");
	    			ligneRemplie=false;
	    		}
	    	}
	    	//si la ligne est pleine
	    	if(ligneRemplie) {
	    		nbRowsCleared++;
	    		System.out.println(nbRowsCleared);
	    		
	    		for (int cptClear=0; cptClear<this.width; cptClear++) {
	    			grid_temp[cptClear][i]=false;
	    		}
	    		
	    		for(int cpty=i+1; cpty<this.height; cpty++) {
	    			for(int cptx=0; cptx<this.width; cptx++) {
	    				grid_temp[cptx][cpty-1]=this.grid[cptx][cpty];
	    			}
	    		}
	    	}
	    	
	    }this.grid=grid_temp;
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
				
				for(int i=0; i<this.grid_backup.length; i++) {
					for (int j=0; j< this.grid_backup[i].length; j++) {
						if(this.grid[i][j]) {
							System.out.println("1");
						}else {
							System.out.println("0");
						}
					}
				}
				
				//-------------------------------------------------------------------
				
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
				System.out.println("UNDO ");
				System.out.println(this.toString());
				
				
				
				
			}
			commit();
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
