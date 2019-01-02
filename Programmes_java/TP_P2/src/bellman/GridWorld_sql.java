package bellman;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import Jama.*;

public class GridWorld_sql 
{
	// activeSaut controle l'acivation du mouvment 'saut'
	private boolean activeSaut;
	private boolean[][] grid;
	private double[][] reward;
	private int size_x;
	private int size_y;
	private int nbStates;
	private double gamma = 0.74;
	private Random rdmnum;
	private long seed = 124;
	private int MAX_REWARD = 20;
	private HashMap<Integer,HashMap<String,Double>> action;
	private HashMap<String,HashMap<Integer,ArrayList<double[]>>> pi;
	private ArrayList<String> dir;

	/*	Constructeur du projet
	 * 	construit la grille 0 ou 1 avec ou sans 'saut'
	 */
	GridWorld_sql(int num_g, boolean activeSaut) {
		// on initialise activeSaut avec le parametre
		this.activeSaut = activeSaut;
		if(activeSaut) {
			System.out.println("Saut : activé ");
		}else{
			System.out.println("Saut : désactivé ");
		}
		
		this.rdmnum     = new Random(this.seed);
		this.dir        = new ArrayList<String>();
		this.dir.add("left");
		this.dir.add("up");
		this.dir.add("right");
		this.dir.add("down");
		this.dir.add("stay");
		if(this.activeSaut) this.dir.add("saut");
	 
		CreateGrid(num_g);
		InitRdmPol();
		WallCst();
		InitTransitionMat();
	 
	}

// Créé la grille 0 ou 1
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
			case 2:
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
				reward[2][0] = -1000;
				reward[2][1] = -1000;
				reward[2][2] = -1000;
				reward[2][3] = -1000;
				reward[2][4] = -1000;
				
				grid[2][0] = false;
				grid[2][1] = false;
				grid[2][2] = false;
				grid[2][3] = false;
				grid[2][4] = false;
				
				// put a strong reward somewhere
				reward[0][0] = 20;
				break;
		 	default:
				System.out.println("Erreur choix grille!");
				System.exit(-1);
				break;
		}
	}
	
	// force l'agent a rester dans un mur quand il en rencontre un
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
					if(this.activeSaut) a.put("saut", 0.0);
					action.put(GridToState(i,j),a);
				}
			}
		}
	}
	
	
	
	
	
	// choose a random coordinate in the grid
	private void ChooseRdmState() 
	{
		int i = rdmnum.nextInt(this.size_x);
		int j = rdmnum.nextInt(this.size_y);
		this.grid[i][j] = true;
	}
	
	// add a reward randomly on the grid
	private void PutRdmReward(int n_rew) 
	{
		int n = 0;
		while (n < n_rew) {
			int i = this.rdmnum.nextInt(this.size_x);
			int j = this.rdmnum.nextInt(this.size_y);
			if (reward[i][j] == 0) {
				reward[i][j] = this.rdmnum.nextInt(MAX_REWARD);
				n++;
			}
		}
	}
	
	// return a state given a coordinate on the grid
	private int GridToState(int i, int j) 
	{
		return i + this.size_x * j;
	}
	
	// return the coordinate on the grid given the state
	private int[] StateToGrid(int s)
	{
		int[] index = new int[2];
		index[1] = (int) s / this.size_x;
		index[0] = s-index[1] * this.size_x;
		return index;
	}

	// add the possible actions for all states
	// initialise this.action
	private void InitRdmPol() 
	{
		action = new HashMap<Integer,HashMap<String,Double>>();
		HashMap<String,Double> mouv = new HashMap<String,Double>();
		
		// on met chaque mouvement possible (avec sa probabilité 1/dir.size()) dans mouv
		for (int i = 0 ; i < this.dir.size() ; i++) {
			
				mouv.put(this.dir.get(i), 1.0/dir.size());
			
			
		}
		// on remplit action avec la HashMap mouv et un Integer allant de 0 à (taille-1)
		int taille = this.size_x * this.size_y;
		for (int j = 0 ; j < taille ; j++) {
			this.action.put(j,mouv);
		}
	}
	
	// return the direction (on the grid) for a given action
	private int[][] getDirNeighbor(String act) 
	{
		int nb = 1;
		//if (this.activeSaut) nb = 2;
		
		int[][] d = new int[2][nb];
		
		
		if (act.equals("left")) {
			d[0][0] = -1;
			d[1][0] = 0;
		}
		if (act.equals("right")) {
			d[0][0] = 1;
		}
		if (act.equals("up")) {
			d[1][0] = -1;
		}
		if (act.equals("down")) {
			d[1][0] = 1;
		}
		// ----- sauts (deplacement de 2 cases vers la gauche)
		if (act.equals("saut")) {
			//saut gauche
			d[0][0] = -2;
			d[1][0] = 0;
			// haut
			//d[1][1] = -1;
			//d[0][1] = 0;
		}
		
		
		return d;
	}
	
	// To each state, give the reachable states given an action
	// pi is the transition mat
	private HashMap<Integer,ArrayList<double[]>> computeTrans(String act) 
	{

		HashMap<Integer,ArrayList<double[]>> trans = new HashMap<Integer,ArrayList<double[]>>();
		ArrayList<double[]> tabDouble = new ArrayList<double[]>(2);
		
		int[] State;
		int[][] dir;
		
		double[] dbl = new double[2];
		
		// dir : mouvements possibles a partir de act
		dir = this.getDirNeighbor(act);
		
		for (int s = 0 ; s < (this.size_x * this.size_y) ; s++) {
			
			dbl       = new double[2];
			tabDouble = new ArrayList<double[]>();
			
			
			// pour chaque mouvement possible avec act
			for(int i=0; i<dir[0].length;i++){
				State     = this.StateToGrid(s);
				
				// 	on ajoute les valeurs du déplacement
				State[0]  += dir[0][i];
				State[1]  += dir[1][i];
				
				if ((State[0] >= 0) && 
				(State[1] >= 0) && 
				(State[0] < this.size_x) && 
				(State[1] < this.size_y) 
				) {
					// si newS est dans la grille
					dbl[0] = (double) (this.GridToState (State[0], State[1]));
					
				} else {
					// si on depasse de la grille on ne fait pas le mouvement (on reste donc à l'état s)
					dbl[0] = s; 
					
				}
				
				dbl[1] = 1.0 /dir[0].length; //proba
				
				tabDouble.add(dbl);
			}
			trans.put(s,tabDouble);
			
		
		}
		return trans;
	}
	
	// initiate values of P
	private void InitTransitionMat() 
	{
		this.pi = new HashMap<String,HashMap<Integer,ArrayList<double[]>>>();
		for (String act : this.dir) {
			this.pi.put(act,computeTrans(act));	
		}
	}
	
	// compute the vector r
	private double[] computeVecR() 
	{
		double[] R = new double[this.nbStates];
		int newX;
		int newY;
		int[] states;
		
		for (int s = 0 ; s < this.nbStates ; s++) {
			double sum = 0;
			HashMap<String,Double> a = this.action.get(s);
			// compute the reward obtained from state s, by doing all potential action a
			states = StateToGrid(s);
			for (String act : this.dir) {
				// TODO
				// pour chaque mouvement possible avec act
				for (double[] tabDouble : this.pi.get(act).get(s)) {
					
					states = StateToGrid((int)(tabDouble[0]));
					newX   = states[0];
					newY   = states[1];
 
					sum   += ( tabDouble[1]*this.reward[newX][newY] * a.get(act) );

				}
			}
			R[s] = sum;
		}
		
		return R;
	}
	
	//tab : une HashMap < Integer, ArrayList<Double[]> > par direction possible
	//Integer : State ArrayList<Double[]>
	// on met à jour les probas pour chaque state en ignorant les déplacements imposssible  
	private double[][] computeMatP() 
	{	
		double[][] P = new double[this.nbStates][this.nbStates];
		int newS;
		HashMap<Integer,ArrayList<double[]>> tab ;
		for (int s = 0 ; s < this.nbStates ; s++) {
			// from state s, compute P^{\pi}(s,s')
			for (String act : this.dir) {
				//TODO
				tab= this.pi.get(act);
				for (double[] tabDouble : tab.get(s)) {
					
					newS = (int) tabDouble[0];
					
					P[s][newS] += (this.action.get(s).get(act))*tabDouble[1];
			
				}
			}
			
		}
		return P;
	}
	
	// converting to matrix for the inverse
	private Matrix BuildMatA()
	{
		double[][] f_A = new double[this.nbStates][this.nbStates];
		double[][] P   = this.computeMatP();
		
		for (int s = 0 ; s < this.nbStates ; s++) {
			f_A[s][s] = 1;
			for (int sp=0 ; sp < this.nbStates ; sp++) {
				f_A[s][sp] -= this.gamma*P[s][sp];
			
			}
			
		}
		
		return new Matrix(f_A);
	}

	// converting to matrix for the inverse
	private Matrix BuildMatb() 
	{
		String str     = "";
		double[] vec_b = this.computeVecR();
		double[][] b   = new double[vec_b.length][1];
		for (int i = 0 ; i < vec_b.length ; i++) {
			b[i][0] = vec_b[i];
			str += b[i][0] + "\n";
		}
		return new Matrix(b);
	}
	
	// solving the linear system
	private double[][] SolvingP() 
	{
		Matrix x = this.BuildMatA().solve(this.BuildMatb());
		return x.getArray();
	}
	
	private void showGrid() 
	{
		System.out.println("\n"+"showGrid()");
		for (int i = 0 ; i < size_x ; i++) {
			for (int j = 0 ; j < size_y ; j++) {
				System.out.print((this.grid[i][j]?1:0));
			}
			System.out.println();
		}
	}
	
	private void showRewGrid() 
	{
		System.out.println("Grille des récompenses :");
		for (int j = 0 ; j < size_y ; j++) {
			for (int i = 0 ; i < size_x ; i++) {
				System.out.print(this.reward[i][j]+" ");
			}
			System.out.println();
		}
	} 
	
	// improve the policy by looking at the best_a Q(s,a)
	private void ImprovePolicy(double[][] V) 
	{	
		
		this.action = new HashMap<Integer,HashMap<String,Double>>();
		for (int i = 0 ; i < size_x ; i++) {
			for (int j = 0 ; j < size_y ; j++) {
				
				// TODO
				HashMap<String, Double> move = new HashMap<String, Double>();
				String bestAct = "stay";
				double Q       = 0.0;
				double bestQ   = 0.0;
				Integer s      = (Integer) GridToState(i,j);
				int newS       = 0;
				int [] tabNewS;
				
				
				for(String act : this.dir) {
					Q=0.0;
					
					//pour chaque mouvement possible avec act
					for(double tabDouble[] : this.pi.get(act).get(s)) {
						
						newS    = (int) tabDouble[0];
						tabNewS = StateToGrid(newS);
					 
						Q += tabDouble[1] * this.reward[tabNewS[0]][tabNewS[1]] + (this.gamma * V[newS][0]);
					}
					
					if(bestQ <= Q) {
						bestQ   = Q;
						bestAct = act;
					}
				}
				
				for (String act : this.dir) {
					if (act == bestAct) {
						move.put(act, 1.0);
					}else{
						move.put(act, 0.0);
					}
					
					this.action.put(s, move);
				}
			
				
			}
		}
		WallCst();
		
	}
	
	// calcul de V par itération
	private double[][] iterateV(double teta) {
		double delta = 1+teta;
		double[][] V = new double[nbStates][1];
		double tempV     = 0.0;
		double newV     = 0.0;
		int newS     = 0;
		int[] tabNewS;
		   
		//reinit V
		for(int i = 0; i < nbStates; i++) {
			V[i][0] = 0;
		}
		
		while(teta<delta) {
			
			delta=0;
			for (int s=0; s<this.nbStates; s++) {
				
				tempV = V[s][0];
				newV = 0.0;
				
				for (String act: this.dir) {
					
					
					
					for(double[] tabDouble : this.pi.get(act).get(s)) {
						
						newS    = (int) tabDouble[0];
						tabNewS = StateToGrid(newS); 
						newV   += this.action.get(s).get(act) * tabDouble[1] * (this.reward[tabNewS[0]][tabNewS[1]] + (this.gamma * V[newS][0]));
					}
				}
			
				V[s][0] = newV;

				
				
				if(Math.abs(tempV - newV) > delta) {
					delta = Math.abs(tempV - newV);
				}
				
			}
		}
		return V;
	}
	
	private String writePol() throws IOException {
		String res="";
		for (int s=0; s< this.nbStates; s++) {
			int[] tabS = StateToGrid(s);
			String bestAct="";
			for (String act : this.dir){
				
				if(this.action.get(s).get(act)==1.0) {
					bestAct = act;
					
				}
			}
			
			// si la case est un mur on met en majuscules
			if ( !this.grid[tabS[0]][tabS[1]] ) bestAct = bestAct.toUpperCase();
			
			// si il y a une récompense on met un point d'exclamation
			if (this.reward[tabS[0]][tabS[1]] > 0) bestAct+="!";
			
			if(bestAct.length() < 6)
				while(bestAct.length() < 6) {
					bestAct+=" ";
				}
			
			
			res+=bestAct+" ";
			if((s+1)%this.size_x==0.0) res+= "\n";
			
		}
		return res;
		
		
	}
	
	private void afficheV(double[][] V) {
		
		for (int i=0 ; i<this.nbStates ; i++) {
			
			if (i%this.size_x==0) {
				System.out.println();
			}
			System.out.print( (double)(Math.round( V[i][0] * 10))/10+" ");			
			
		}System.out.println();	
	}
	
	//créé les répertoires 'Iteration' et 'Inversion'
		public static void creationDossiers(){
			File It=new File("Iteration");
			if (!(It.exists() && It.isDirectory())){ 
				It.mkdirs();
				System.out.println("\ncreation dossier /Iteration");
				
			}
			
			File In=new File("Inversion");
			if (!(In.exists() || In.isDirectory())){ 
				In.mkdirs();
				System.out.println("\ncreation dossier /Inversion");
			}
			
			
		}
	
	public static void main(String[] args) throws IOException 
	{
		
		System.out.println("-----");
	/* deuxième paramètre du constructeur :
	 * 		true  -> utilise le mouvement 'saut'
	 * 		false -> desactive le mouvement 'saut' 
	 */
		GridWorld_sql gd = new GridWorld_sql(1,true);
		
		System.out.println("-----");
		
		HashMap<Integer,HashMap<String,Double>> backupAction = gd.action; 
		
		gd.showRewGrid();
		double[][] V = gd.SolvingP();
		
		
		System.out.println("\nShow V ");
		gd.afficheV(V);
		int nbIt = 15; // à définir
		creationDossiers();
		String print = "";
		
		// ------------------------------------------------------------------------------
		//  Calcul par inversion
		//on ouvre le fichier
		FileWriter txt1 = new FileWriter("Inversion/politique.txt");
		txt1.write("\n\nLes cases contenant des murs ont leur meilleure action en majuscules."+
				"\nEt les cases contenant une récompense ont leur meilleure action avec un '!'\n\n");
		
		System.out.println("\nCalcul par inversion :\n");
		
		
		for (int cpt = 0 ; cpt < nbIt ; cpt++) {
			gd.ImprovePolicy(V);
			V = gd.SolvingP();
	
			System.out.println("\nShow V - It "+cpt+" :");
			gd.afficheV(V);
			print = gd.writePol();
			txt1.write("Iteration "+cpt+" :\n"+print+"\n\n");
			
		}
		//on ferme le fichier
		txt1.close();
		
		// ------------------------------------------------------------------------------
		// Iterate V
		 
		gd.action = backupAction;
		// on ouvre le fichier
		FileWriter txt2 = new FileWriter("Iteration/politique.txt");
		txt2.write("\n\nLes cases contenant des murs ont leur meilleure action en majuscules."+
				"\nEt les cases contenant une récompense ont leur meilleure action avec un '!'\n\n");
		
		System.out.println("\nIterate V :\n");
		print="";
		V = gd.iterateV(0.1);
		
		for(int cpt = 0; cpt < nbIt; cpt++) {
			gd.ImprovePolicy(V);
			V = gd.iterateV(0.1);
			
			System.out.println("\nShow V - It "+cpt+" :");
			gd.afficheV(V);
			print = gd.writePol();
			txt2.write("Iteration "+cpt+" :\n"+print+"\n\n");
			
		}
		// on ferme le fichier
		txt2.close();
		
		
	}
}