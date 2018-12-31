package bellman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import Jama.*;

public class GridWorld_sql {

	private boolean[][] grid;
	private double[][] reward;
	private int size_x;
	private int size_y;
	private int nbStates;
	private double gamma = 0.5;
	private Random rdmnum;
	private long seed = 521;
	private int MAX_REWARD = 20;
	private HashMap<Integer,HashMap<String,Double>> action;
	private HashMap<String,HashMap<Integer,ArrayList<double[]>>> pi;
	private ArrayList<String> dir;
	
	GridWorld_sql(int num_g) {
		this.rdmnum = new Random(this.seed);
		this.dir = new ArrayList<String>();
		this.dir.add("left");
		this.dir.add("up");
		this.dir.add("right");
		this.dir.add("down");
		this.dir.add("stay");
	 
		CreateGrid(num_g);
		InitRdmPol();
		WallCst();
		InitTransitionMat();
	 
	}
	 
	private void CreateGrid(int g) {
		switch(g) {
			case 0:
				this.size_x = 8;
				this.size_y = 5;
				this.grid = new boolean[size_x][size_y];
				this.reward = new double[size_x][size_y];
				this.nbStates = size_x*size_y;
				for(int i=0; i<size_x; i++) {
					for(int j=0; j<size_y; j++)
					{
						grid[i][j] = true;
						reward[i][j] = -1;
					}
				}
				// put some walls
				reward[2][2] = -1000;
				reward[3][2] = -1000;
				reward[4][2] = -1000;
				reward[5][2] = -1000;
				grid[2][2] = false;
				grid[3][2] = false;
				grid[4][2] = false;
				grid[5][2] = false;
	 
				// put a strong reward somewhere
				reward[0][0] = 20;
				break;
			case 1:
				this.size_x = 6;
				this.size_y = 6;
				this.grid = new boolean[size_x][size_y];
				this.reward = new double[size_x][size_y];
				this.nbStates = size_x*size_y;
				for(int i=0; i<size_x; i++) {
					for(int j=0; j<size_y; j++)
					{
						grid[i][j] = true;
						reward[i][j] = -1;
					}
				}
				reward[0][1] = 100;
				reward[0][2] = -1000;
				reward[0][3] = -1000;
				reward[0][4] = -1000;
				reward[2][0] = -1000;
				reward[2][1] = -1000;
				reward[2][3] = -1000;
				reward[2][4] = -1000;
				reward[3][4] = -1000;
				reward[3][5] = -1000;
				reward[4][1] = -1000;
				reward[4][2] = -1000;
				reward[4][3] = -1000;
				reward[4][5] = -1000;
				reward[5][5] = -1000;
	 
				grid[0][2] = false;
				grid[0][3] = false;
				grid[0][4] = false;
				grid[2][0] = false;
				grid[2][1] = false;
				grid[2][3] = false;
				grid[2][4] = false;
				grid[3][4] = false;
				grid[3][5] = false;
				grid[4][1] = false;
				grid[4][2] = false;
				grid[4][3] = false;
				grid[4][5] = false;
				grid[5][5] = false;
				break;				
		 	default:
				System.out.println("Erreur choix grille!");
				System.exit(-1);
				break;
		}
	}
	
	private void WallCst() {
		for(int i=0; i<size_x; i++) {
			for(int j=0; j<size_y; j++) {				
				if(!grid[i][j]) {
					HashMap<String,Double> a = new HashMap<String,Double>();
					a.put("left", 0.0);
					a.put("up", 0.0);
					a.put("right", 0.0);
					a.put("down", 0.0);
					a.put("stay", 1.0);
					action.put(GridToState(i,j),a);
				}
			}
		}
	}
	
	// choose a random coordinate in the grid
	private void ChooseRdmState() {
		int i = rdmnum.nextInt(size_x);
		int j = rdmnum.nextInt(size_y);
		grid[i][j] = true;
	}
	
	// add a reward randomly on the grid
	private void PutRdmReward(int n_rew) {
		int n = 0;
		while(n<n_rew) {
			int i = rdmnum.nextInt(size_x);
			int j = rdmnum.nextInt(size_y);
			if(reward[i][j] == 0) {
				reward[i][j] = rdmnum.nextInt(MAX_REWARD);
				n++;
			}
		}
	}
	
	// return a state given a coordinate on the grid
	private int GridToState(int i, int j) {
		return i + size_x * j;
	}
	
	// return the coordinate on the grid given the state
	private int[] StateToGrid(int s) {
		int[] index = new int[2];
		index[1] = (int) s/size_x;
		index[0] = s-index[1]*size_x;
		return index;
	}

	// add the possible actions for all states
	private void InitRdmPol() {
		action = new HashMap<Integer,HashMap<String,Double>>();
		for(int i = 0; i < size_x; i++){
			for(int j = 0; j < size_y; j++){
				HashMap<String,Double> probaActions = new HashMap<String,Double>();
				for(String act : dir){
					probaActions.put(act, 0.2);
				}
				action.put(GridToState(i,j), probaActions);
			}
		}
	}
	
	
	// return the direction (on the grid) for a given action
	private int[] getDirNeighbor(String act) {
		int[] d = new int[2];

		if(act.equals("left")) d[0]=-1;
		if(act.equals("right")) d[0]=1;
		if(act.equals("up")) d[1]=-1;
		if(act.equals("down")) d[1]=1;
		
		return d;
	}
	
	// To each state, give the reachable states given an action
	private HashMap<Integer,ArrayList<double[]>> computeTrans(String act) {
		HashMap<Integer,ArrayList<double[]>> trans = new HashMap<Integer,ArrayList<double[]>>();
		for(int i = 0; i < size_x; i++){
			for(int j = 0; j < size_y; j++){
				//System.out.println(act);
				int[] moveGrid = getDirNeighbor(act);
				
				ArrayList<double[]> mostUselessTabEver = new ArrayList<double[]>();
				double[] sndMostUselessTabEver = new double[2];
				
				
				sndMostUselessTabEver[0] = GridToState((i + moveGrid[0] + size_x)%size_x, (j + moveGrid[1] + size_y)%size_y);
				sndMostUselessTabEver[1] = 1;
				//System.out.println(sndMostUselessTabEver[0]);
				
				mostUselessTabEver.add(sndMostUselessTabEver);
				trans.put(GridToState(i,j), mostUselessTabEver);
			}
		}
		System.out.println(act);
		hashToString(trans);
		return trans;
	}
	
	// initiate values of P
	private void InitTransitionMat() {
		pi = new HashMap<String,HashMap<Integer,ArrayList<double[]>>>();
		for(String act : this.dir) {
			pi.put(act,computeTrans(act));
		}
	}
	
	// compute the vector r
	private double[] computeVecR() {
		double[] R = new double[nbStates];
		for(int s=0; s<nbStates; s++) {
			double sum = 0;
			HashMap<String,Double> a = action.get(s);
			// compute the reward obtained from state s, by doing all potential action a
			for(String act : this.dir) {
				Double pA = a.get(act); 
				sum=this.reward[StateToGrid(s)[0]][StateToGrid(s)[1]];
				for(double[] StateNProba : pi.get(act).get(s)) {
					int[] sPrim = new int[2];
					sPrim = StateToGrid((int)StateNProba[0]);
					sum += 0.5*pA*StateNProba[1]*reward[sPrim[0]][sPrim[1]];
				}
			}
			R[s] = sum;
		}
		return R;
	}
	
	private double[][] computeMatP() {
		double[][] P = new double[nbStates][nbStates];
		
		for(int s = 0; s < nbStates; s++) {
			for(int sP = 0; sP < nbStates; sP++)
				P[s][sP] = 0;
		}
		
		for(int s=0; s<nbStates; s++) {
			// from state s, compute P^{\pi}(s,s')
			for(String act : this.dir) {
				HashMap<String,Double> a = action.get(s);
				Double pA = a.get(act);
				for(double[] StateNProba : pi.get(act).get(s)) {
					int sPrim = (int)StateNProba[0];
					//System.out.println("sPrim = " + sPrim);
					P[s][sPrim] += pA * StateNProba[1];
				}	
			}
		}
		return P;
	}
	
	// converting to matrix for the inverse
	private Matrix BuildMatA() {
		double[][] f_A = new double[nbStates][nbStates];
		double[][] P = computeMatP();
		for(int s=0; s<nbStates; s++) {
			f_A[s][s] = 1;
			for(int sp=0; sp<nbStates; sp++) {
				f_A[s][sp] -= this.gamma*P[s][sp];
			}
		}
		
		return new Matrix(f_A);
	}

	// converting to matrix for the inverse
	private Matrix BuildMatb() {
		double[] vec_b = computeVecR();
		double[][] b = new double[vec_b.length][1];
		for(int i=0; i<vec_b.length; i++) {
			b[i][0] = vec_b[i];
		}
		return new Matrix(b);
	}
	
	// solving the linear system
	private double[][] SolvingP() {
		Matrix x = BuildMatA().solve(BuildMatb());
		return x.getArray();
	}
	
	private void showGrid() {
	
		for(int j=0; j<size_y; j++) {
			for(int i=0; i<size_x; i++)
			System.out.print((this.grid[i][j]?1:0));
		System.out.println();
		}
	}
	
	private void showRewGrid() {
		
		for(int j=0; j<size_y; j++) {
			for(int i=0; i<size_x; i++)
			System.out.print(this.reward[i][j]+" ");
		System.out.println();
		}
	}
	
	// improve the policy by looking at the best_a Q(s,a)
	private void ImprovePolicy(double[][] V) {
		action = new HashMap<Integer,HashMap<String,Double>>();
		for(int s = 0; s < nbStates; s++) {
			HashMap<String,Double> probaActions = new HashMap<String,Double>();
			String bestAction = "stay";
			double bestReward = 0;
			for(String act : this.dir) {
				double sumRewardByA = 0;
				for(double[] StateNProba : pi.get(act).get(s)) {
					int sPrim = (int) StateNProba[0];
					int[] sPrimGrid = StateToGrid(sPrim);
					sumRewardByA =+ StateNProba[1] * (this.reward[sPrimGrid[0]][sPrimGrid[1]]+ gamma*V[sPrim][0]); 
				}
				
				if(sumRewardByA > bestReward) {
					bestAction = act;
					bestReward = sumRewardByA;
				}
			}
			//System.out.println("Etat : " + s + " / Action : " + bestAction);
			for(String act : this.dir) {
				if(act == bestAction)
					probaActions.put(act, 1.0);
				else
					probaActions.put(act,0.0);
			}
			action.put(s, probaActions);
		}
		WallCst();
	}
	
	public static void hashToString(HashMap<Integer,ArrayList<double[]>> lol) {
		for(int i = 0; i < 25; i++) {
			ArrayList<double[]> tab = lol.get(i);
			double[] tab2 = tab.get(0);
			
			System.out.println(i + " : " + tab2[0]);
		}
			
	}
	
	public static void main(String[] args) {

		GridWorld_sql gd = new GridWorld_sql(1);
		
		System.out.println("Grille : ");
		System.out.println();
		gd.showGrid();
		
		System.out.println();
		
		System.out.println("Recompenses : ");
		System.out.println();
		gd.showRewGrid();
		
		double[][] V = gd.SolvingP();
		
		// show V
		System.out.println();
		System.out.println("V_pi(s)_0 : ");
		for(int i=0; i<gd.nbStates; i++) {
			if(i%5==0) System.out.println();
			System.out.print(V[i][0]+" ");			
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println("R(s)_0 : ");
		double[] R = gd.computeVecR();
		for(int i=0; i<gd.nbStates; i++) {
			if(i%5==0) System.out.println();
			System.out.print(R[i] + " ");			
		}
		
		System.out.println("\n");
		// Improve the policy !
		for(int i = 0; i < 20; i++) {
			gd.ImprovePolicy(V);
			V = gd.SolvingP();
		}
		// show V
		System.out.println();
		System.out.println("V_pi(s)_t : ");
		for(int i=0; i<gd.nbStates; i++) {
			if(i%5==0) System.out.println();
			System.out.print(V[i][0]+" ");			
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println("R(s)_t : ");
		R = gd.computeVecR();
		for(int i=0; i<gd.nbStates; i++) {
			if(i%5==0) System.out.println();
			System.out.print(R[i] + " ");			
		}
		
	}
}