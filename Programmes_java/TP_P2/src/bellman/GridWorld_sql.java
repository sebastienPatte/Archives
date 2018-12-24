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
	private long seed = 124;
	private int MAX_REWARD = 20;
	private HashMap<Integer,HashMap<String,Double>> action;
	private HashMap<String,HashMap<Integer,ArrayList<double[]>>> pi;
	private ArrayList<String> dir;
	
	GridWorld_sql(int size_x, int size_y, int n_rew) {
		this.rdmnum = new Random(this.seed);
		
		this.grid = new boolean[size_x][size_y];
		this.reward = new double[size_x][size_y];
		this.size_x = size_x;
		this.size_y = size_y;
		this.nbStates = size_x*size_y;
		
		// list of actions
		this.dir = new ArrayList<String>();
		this.dir.add("left");
		this.dir.add("up");
		this.dir.add("right");
		this.dir.add("down");
		this.dir.add("stay");
		
		for(int i=0; i<size_x; i++) {
			for(int j=0; j<size_y; j++)
				grid[i][j] = false;
		}
		
		this.ChooseRdmState();
		// put n_rew reward randomly
		this.PutRdmReward(n_rew);
		// initialize the random policy
		this.InitRdmPol();
		// initialize the transition matrices
		this.InitTransitionMat();
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
		return i+size_x*j;
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
		// init action
		action = new HashMap<Integer,HashMap<String,Double>>();
		//init mouv
		HashMap<String,Double> mouv = new HashMap<String,Double>();
		// on met chaque mouvement possible (avec sa probabilité 1/nbDirs) dans mouv
		for(int i=0; i< this.dir.size(); i++) {
			mouv.put(this.dir.get(i), (double) (1/this.dir.size()));
		}
		// on remplit action avec la HashMap mouv et un Integer allant de 0 à (taille-1)
		int taille = size_x*size_y;
		for (int j=0; j< taille-1;j++) {
			action.put(j,mouv);
		}
	}
	
	// return the direction (on the grid) for a given action
	private int[] getDirNeighbor(String act) {
		int[] d = new int[2];

		if(act.equals("left")) d[0]=-1;
		if(act.equals("right")) d[0]=1;
		if(act.equals("up")) d[1]=1;
		if(act.equals("down")) d[1]=-1;
		
		return d;
	}
	
	// To each state, give the reachable states given an action
	private HashMap<Integer,ArrayList<double[]>> computeTrans(String act) {
		// init trans
		HashMap<Integer,ArrayList<double[]>> trans = new HashMap<Integer,ArrayList<double[]>>();
		// init tabDouble of size 2
		ArrayList<double[]> tabDouble = new ArrayList<double[]>(2);
		// init State and dir 2 int tabs of size 2 each
		int[] State= new int[2];
		int[] dir= new int[2];
		// init sbl an double tab of size 2
		double[] dbl = new double[2];
		
		// for each State in grid
		for(int s=0; s<size_x*size_y; s++) {
			
			// reinit dbl and tabDouble 
			dbl = new double[2];
			tabDouble = new ArrayList<double[]>(2);
			// get the value of the tab dir[]
			dir=getDirNeighbor(act);
			// on remplit State avec StateToGrid(s)
			State=StateToGrid(s);
			// puis on y ajoute les valeurs du déplacement
			State[0]+=dir[0];
			State[1]+=dir[1];
			
			if( !((State[0] < 0) || (State[1] < 0) || (State[0] >= size_x) || (State[1] >= size_y) ) ) {
				// if new State is in the limit of grid we fill dbl with new state and probability of 1.0
				dbl[0] =  (double) (GridToState (State[0], State[1]));
				dbl[1] =  1.0; //proba
			}else {
				// else we put State= -1.0 and probability is 0
				dbl[0] = -1.0;
				dbl[1] = 0.0;
			}
			
			// print of new State
			System.out.println(dbl[0]);
			
			// fill trans with (s, {s', p}) 
			tabDouble.add(0, dbl);
			trans.put(s,tabDouble);
			
		
		}
		
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
		int X,Y;
		for(int s=0; s<nbStates; s++) {
			double sum = 0;
			// a = (String action, Double proba)
			HashMap<String,Double> a = action.get(s);
			// compute the reward obtained from state s, by doing all potential action a
			for(String act : this.dir) {
				// TODO 
				//  tabDouble = p(s'|s,a)
				double[] tabDouble = pi.get(act).get(s).get(0);
				X =	StateToGrid ( (int) (tabDouble[0]) ) [0];
				Y = StateToGrid ( (int) (tabDouble[0]) ) [1];
				// a.get(act) = p(a|s)	
				sum+=this.reward[X][Y] * a.get(act);
				
			}
			R[s] = sum;
		}
		return R;
	}
	
	//tab : une HashMap < Integer, ArrayList<Double[]> > par direction possible
	//Integer : State ArrayList<Double>
	private double[][] computeMatP() {
		double[][] P = new double[nbStates][nbStates];
		HashMap<Integer,ArrayList<double[]>> tab = new HashMap<Integer, ArrayList<double[]>>(this.dir.size());
		for(int s=0; s<nbStates; s++) {
			// from state s, compute P^{\pi}(s,s')
			for(String act : this.dir) {
				//TODO
				tab.put=computeTrans(act);
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
		
		Matrix matP = new Matrix(f_A);
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
		for(int i=0; i<size_x; i++) {
			for(int j=0; j<size_y; j++)
				System.out.print((this.grid[i][j]?1:0));
			System.out.println();
		}
	}
	
	private void showRewGrid() {
		for(int i=0; i<size_x; i++) {
			for(int j=0; j<size_y; j++)
				System.out.print(this.reward[i][j]+" ");
			System.out.println();
		}
	} 
	
	// improve the policy by looking at the best_a Q(s,a)
	private void ImprovePolicy(double[][] V) {
		action = new HashMap<Integer,HashMap<String,Double>>();
		for(int i=0; i<size_x; i++) {
			for(int j=0; j<size_y; j++) {
				// TODO
			}
		}			
	}
	
	public static void main(String[] args) {
		GridWorld_sql gd = new GridWorld_sql(5,5,2);
		
		gd.showRewGrid();
		double[][] V = gd.SolvingP();
		// show V
		for(int i=0; i<gd.nbStates; i++) {
			if(i%5==0) System.out.println();
			System.out.print(V[i][0]+" ");			
		}
		System.out.println("\n");
		// Improve the policy !
		gd.ImprovePolicy(V);
		
	}
}