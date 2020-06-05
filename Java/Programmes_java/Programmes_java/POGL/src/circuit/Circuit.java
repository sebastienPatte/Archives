package circuit;

import java.util.ArrayList;

abstract class Noeud {
	abstract public int valeur();
}
abstract class NoeudSimple extends Noeud{
	public String toString(){
		return ""+this.valeur();
	}
}

class Constante extends NoeudSimple {
	private int cst;
	public Constante(int c) { this.cst = c; }
	public int valeur() { return this.cst; }
	
	
}

class Entree extends NoeudSimple{
	 private Circuit circuit;
	
	public Entree (Circuit c){
		this.circuit = c;
	}
	
	public int valeur(){
		return this.circuit.litEntree();
	}
	
	@Override
	public String toString(){
		return "x";
	}
	
}

abstract class NoeudBinaire extends Noeud{
	private Noeud source1;
	private Noeud source2;
	
	NoeudBinaire(Noeud n1, Noeud n2){
		this.source1=n1;
		this.source2=n2;
	}
	
	public int valeurSource1(){
		return this.source1.valeur();
	}
	
	public int valeurSource2(){
		return this.source2.valeur();
	}
	
	public String toString(){
		return " ("+this.source1.toString()+" "+this.operation()+" "+this.source2.toString()+") ";
	}

	abstract String operation();
}

class Addition extends NoeudBinaire{
	
	Addition(Noeud n1, Noeud n2){
		super(n1,n2);
	}
	
	public int valeur(){
		return this.valeurSource1()+this.valeurSource2();
	}
	
	public String operation(){
		return "+";
	}
}

class Multiplication extends NoeudBinaire{
	Multiplication(Noeud n1, Noeud n2){
		super(n1,n2);
	}
	
	public int valeur(){
		return this.valeurSource1()*this.valeurSource2();
	}
	
	public String operation(){
		return "*";
	}
}

class Soustraction extends NoeudBinaire{
	Soustraction(Noeud n1, Noeud n2){
		super(n1,n2);
	}
	
	public int valeur(){
		return this.valeurSource1()-this.valeurSource2();
	}
	
	public String operation(){
		return "-";
	}
}



public class Circuit {

    public static void main(String[] args) {
	 Circuit c = new Circuit();
 	 Noeud n1 = c.creeMultiplication(c.creeConstante(2),
	 				c.creeEntree());
	 c.sortie = n1;
	 System.out.println("Ceci doit afficher 2 : " + c.calcule(1));
	 System.out.println("Ceci doit afficher 12 : " + c.calcule(6));
	 Noeud n2 = c.creeMultiplication(n1,
	 				c.creeAddition(c.creeConstante(1),
	 					       n1));
	 c.sortie = n2;
	 System.out.println("Ceci doit afficher 6 : " + c.calcule(1));
	 System.out.println("Ceci doit afficher 156 : " + c.calcule(6));
//	 System.out.println("Ceci doit afficher 2 : " + c.nbNoeudsMult());
//	 System.out.println("Ceci doit afficher 10 : " + c.nbOpEffectuees());
//	 Circuit p20 = expRapide(20);
//	 System.out.println("Ceci doit afficher 7 : " + p20.nbNoeudsMult());
//	 System.out.println("Ceci doit afficher 1048576 : " + p20.calcule(2));
//	 System.out.println("Ceci doit afficher 51 : " + p20.nbOpEffectuees());
//	 Circuit p20m = expRapideMemoisee(20);
//	 System.out.println("Ceci doit afficher 7 : " + p20m.nbNoeudsMult());
//	 System.out.println("Ceci doit afficher 1048576 : " + p20m.calcule(2));
//	 System.out.println("Ceci doit afficher 7 : " + p20m.nbOpEffectuees());
//	 System.out.print("Ceci doit afficher ((2 * x) * (1 + (2 * x))) : ");
	 c.affiche();
    }

    /**
     * Valeur de la variable dont dépend le calcul
     */ 
    private int entree;
    
    /**
     * Dernier nœud, calculant le résultat
     */
    private Noeud sortie;

    /**
     * Ensemble des nœuds
     */
    private ArrayList<Noeud> noeuds;

    /**
     * Constructeur.
     * N'initialise pas l'entrée : la méthode calcule s'en chargera avant
     * chaque calcul. La sortie n'est pas initialisée non plus pour éviter
     * un problème de circularité.
     */
    private Circuit() {
    	this.noeuds = new ArrayList<Noeud>();
    }

    /**
     * Ajout d'un nœud à la liste
     * @param n Nœud à ajouter
     */
    private void ajouteNoeud(Noeud n) {
    	noeuds.add(n);
    }


    // Création de nœuds avec ajout direct
    /**
     * Création d'un nœud de valeur constante
     * @param n Valeur calculée par le nœud
     */
    public Noeud creeConstante(int n) {
	Noeud c = new Constante(n);
	this.ajouteNoeud(c);
	return c;
    }
    
    // Création d'un Noeud Entree
    public Noeud creeEntree(){
    	return new Entree(this);
    }
    
    // Création d'un Noeud Addition
    public Noeud creeAddition(Noeud n1, Noeud n2){
    	return new Addition(n1,n2);
    }
    
    // Création d'un Noeud Multiplication 
    public Noeud creeMultiplication(Noeud n1, Noeud n2){
    	return new Multiplication(n1,n2);
    }
    
    
    
    /**
     * Affichage de l'expression mathématique calculée
     */
    public void affiche (){
    	
    		System.out.println(this.sortie.toString());
    	
    }
    
    /**
     * Compte le nom de multiplications dans le circuit 
     */
    public int nbNoeudMult(){
    	int res = 0;
    	for(Noeud n : this.noeuds){
    		if(n instanceof Multiplication){
    			res++;
    		}
    	}
    	return res;
    }
    
    
    
    
    /**
     * Donne la valeur de l'entrée, dont auront besoin certains nœuds
     */
    public int litEntree() {
	return this.entree;
    }

    /**
     * Initialise la variable d'entrée et calcule le résultat
     * @param e Valeur affectée à la variable d'entrée
     * @return Valeur calculée par le circuit
     */
    public int calcule(int e) {
	this.entree = e;
	return this.sortie.valeur();
    }

    /**
     * Construction d'un ensemble de nœuds utilisant la technique
     * d'exponentiation rapide
     * @param c Circuit auquel rattacher les nœuds créés
     * @param n Puissance calculée
     * @return Nœud principal
     */
    public static Noeud expRapide(Circuit c, int n) {
	if (n == 0) {
	    // x^0 = 1
	    return (c.creeConstante(1));
	} else if (n % 2 == 0) {
	    // Si n pair, x^n = (x^{n/2})^2
	    Noeud e = expRapide(c, n/2);
	    return (c.creeMultiplication(e, e));
	} else {
	    // Si n impair, x^n = x*((x^{n/2})^2)
	    Noeud e = expRapide(c, n/2);
	    return (c.creeMultiplication(c.creeEntree(),
					 c.creeMultiplication(e, e)));
	}
    }
    /**
     * Construction d'un circuit utilisant la technique d'exponentiation rapide 
     * @param n Puissance calculée
     * @return Circuit calculant la n-ème puissance de sa variable d'entrée
     */
    public static Circuit expRapide(int n) {
	// On crée d'abord un circuit vide...
	Circuit c = new Circuit();
	// puis on y ajoute des nœuds, et on connecte le dernier à la sortie.
	c.sortie = expRapide(c, n);
	return c;
    }
}
