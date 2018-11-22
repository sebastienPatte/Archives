package rl;
 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
 
public class bandit {
	static long seed= 1202;
	public int T; // Horizon
	public int n; // Nombre de bras
	public double[] Q; // Estimation de la fonction de valeur Q
	public double[] paramB; // parametre des bras (probabilite de recompense)
	public int[] nbTireBras; // Nb de fois que j'ai tiré un bras
 
	Random rdm;
 
	// Constructeur : specifie le nombre de bras et l'horizon!
	public bandit(int T, int n){
		// TODO
		this.T=T;
		this.n=n;
		Init(seed);
	}
 
	// Renvoie la récompense lorsqu'on tire le bras b
	public int TireBras(int b){
		if(this.rdm.nextDouble() <= paramB[b]) {
			return 1;
		}else {
			return 0;
		}
	}
 
	// Initialise les différentes variables de la classe bandit
	public void Init(long seed){
		// Init VA
		this.rdm = new Random(seed);
		this.Q = new double[this.T]; 
		// Init paramB
		this.paramB = new double[this.T];
		for (int i=0; i<paramB.length; i++) {
			paramB[i]=0.0;
		}
		// Init nbTireBras
		this.nbTireBras= new int[this.T];
	}
 
	// Retourne l'indice du maximum d'une liste
	public int argmax(double[] list) {
		int arg=-1;
		int val=0;
		for (int i=0; i<list.length; i++) {
			if(list[i]>val)arg=i;
		}
		return arg;
	}
 
	/*
	 *  Implementation de epsilon-greedy
	 *  - choix du bras
	 *  - renvoie la recompense
	 */
	public double ChoixBras_EpsG(double epsilon){
		// TODO
		int rew=0;
		int bras=0;
		double alea = this.rdm.nextDouble();
		if(alea >= 1-epsilon) {
			bras=argmax(this.paramB);
			rew =TireBras(bras);
			MAJ_Q(bras,rew,1/epsilon);
			return rew;
		}else {
			bras =(int) this.rdm.nextDouble()*this.T;
			rew = TireBras(bras);
			MAJ_Q(bras,rew,1/epsilon);
			return rew;
		}
		
	}
 
	/*
	 *  Mise à jour de la fonction Q
	 *  - alpha : parametre d'apprentissage (= 1/k pour le cas classique)
	 */
	//rew means reward
	public void MAJ_Q(int bras, double rew, double alpha){
		// TODO
		
		this.Q[bras]=(rew/nbTireBras[bras])*alpha;
		System.out.println("Q["+bras+"] = "+this.Q[bras]);
		
		
	}
 
	/*
	 *  Effectue une expérience (on tire T bras avec parametre epsilon)
	 *  - renvoie le gain total en fonction de t
	 */
	public double[] Experiment(double eps) {
		// init gain vs t
		double[] gain = new double[T];
		for(int i=0; i<T; i++) gain[i]=0;
 
		// TODO
		for(int j=0; j<T; j++) {
			gain[j]=ChoixBras_EpsG(eps);
		}
		return gain;
		
	}
 
 
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
 
		int Horizon = 1000;
		int n_bras = 10;
		double[] av_gain = new double[Horizon];
 
		// Calcul le gain total moyen en fonction du temps
		for(int xp=0; xp<1000; xp++) {
			//TODO : on initialise les bras et appelle Experiment
			
			
		}
 
		// Ecrit le resultat dans un fichier
		PrintWriter f = new PrintWriter("MesBellesDonnes.d","UTF-8");
		for(int i=0; i<av_gain.length; i++) f.println(i+" "+av_gain[i]/Horizon);
		f.close();
	}
}