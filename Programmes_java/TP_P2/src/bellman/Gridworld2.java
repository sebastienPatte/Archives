package bellman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import Jama.*;

public class Gridworld2 {

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
	private HashMap<String,HashMap<Integer,Integer>> pi;
	private ArrayList<String> dir;
	
	Gridworld2(int size_x, int size_y, int n_rew) {
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
		
		//this.ChooseRdmState();
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
	
	// return the coordinate on the gris given the state
	private int[] StateToGrid(int s) {
		int[] index = new int[2];
		index[1] = (int) s/size_x;
		index[0] = s-index[1]*size_x;
		return index;
	}

	// add the possible actions for all states
	private void InitRdmPol() {
		action = new HashMap<Integer,HashMap<String,Double>>();
		HashMap<String,Double> set = new HashMap<String,Double>();
		set.put("up", 0.2); set.put("down", 0.2); set.put("left", 0.2);
		set.put("right", 0.2); set.put("stay", 0.2);
		for(int i=0; i<this.size_x; i++) {
			for(int j=0; j<this.size_y; j++) {
				action.put(i*this.size_x+j, set);
			}
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
	private HashMap<Integer,Integer> computeTrans(String act) {
		HashMap<Integer,Integer> trans = new HashMap<Integer,Integer>();
		int a = 0;
		for(int i=0; i<this.size_x; i++) {
			for(int j=0; j<this.size_y; j++) { 
				if(act=="up") {
					if(i==0) {
						a=i*this.size_x+j;
					} else {
						a=(i-1)*this.size_x+j;
					}
				}
				if(act=="down") {
					if(i==this.size_x-1) {
						a=i*this.size_x+j;
					} else {
						a=(i+1)*this.size_x+j;
					}
				}
				if(act=="right") {
					if(j==this.size_x-1) {
						a=i*this.size_x+j;
					} else {
						a=i*this.size_x+j+1;
					}
				}
				if(act=="left") {
					if(j==0) {
						a=i*this.size_x+j;
					} else {
						a=i*this.size_x+j-1;
					}
				}
				if(act=="stay") {
					a=i*this.size_x+j;
				}
				trans.put(i*this.size_x+j, a);
			}
		}
		return trans;
	}
	
	// initiate values of P
	private void InitTransitionMat() {
		pi = new HashMap<String,HashMap<Integer,Integer>>();
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
			// compute the reward obtained fomo state s, by doing all potential action a
			for(String act : this.dir) {
				int case_cible = pi.get(act).get(s);
				double proba_action = a.get(act);
				sum +=this.reward[StateToGrid(case_cible)[0]][StateToGrid(case_cible)[1]]*proba_action;
			}
			R[s] = sum;
		}
		return R;
	}
	
	private double[][] computeMatP() {
		double[][] P = new double[nbStates][nbStates];
		
		for(int s=0; s<nbStates; s++) {
			// from state s, compute P^{\pi}(s,s')
			for(String act : this.dir) {
				int s_prime = pi.get(act).get(s);
				P[s][s_prime] += action.get(s).get(act);
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
				//TODO
			}
		}			
	}
	
	public static void main(String[] args) {
		Gridworld2 gd = new Gridworld2(5,5,2);
		
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

