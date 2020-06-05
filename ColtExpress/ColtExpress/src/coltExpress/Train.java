package coltExpress;

import java.awt.EventQueue;
import java.util.ArrayList;

import gui.Observable;
import gui.Vue;


public class Train extends Observable{
	public static int NB_JOUEURS=2;
	public static  int HAUTEUR=160;
	public static  int LARGEUR=160;
	public static int NB_BALLES = 8;
	public static double NERVOSITE_MARSHALL=0.3;
	public static int NB_WAGONS = 4;
	public static boolean SHOW_BUTTONS = true;
	
	private Wagon[] tabWagons; //l'indice des wagons commence à 0
	private Bandit[] tabBandits;
	private Marshall marshall;
	private int phase; 
	private ArrayList<ArrayList<Actions>> actions;
	private ArrayList<String> toPrint;
	
	
	// Constructeur Train
	public Train (){
        this.tabWagons = new Wagon[Train.NB_WAGONS];
        for (int i = 0; i<Train.NB_WAGONS;i++) {
            this.tabWagons[i]= new Wagon(i);
        }
        this.tabBandits = new Bandit[NB_JOUEURS];
        if(NB_JOUEURS>=1) {
        	this.tabBandits[0]= new Bandit(this.tabWagons,0,Train.NB_WAGONS-1,true,"P1");
        }
        if(NB_JOUEURS>=2) {
        	this.tabBandits[1]= new Bandit(this.tabWagons,1,Train.NB_WAGONS-1,true,"P2");
        }
        this.marshall = new Marshall(this.tabWagons);
        for(int cpt=0 ; cpt < NB_JOUEURS;cpt++) {
        	this.tabWagons[Train.NB_WAGONS-1].setBanditsToit(cpt,true);
        }
        	this.tabWagons[0].setContientMarshall(true);
        this.phase= 0;
        this.toPrint = new ArrayList<String>();
        updateActions();
    }
	
	//met a jour le tableau actions
	private void updateActions(){
		this.actions = new ArrayList<ArrayList<Actions>>();
		for(Bandit bandit : this.tabBandits) {
			this.actions.add(bandit.getActions());
		}
		
	}

	//vérifie qu'il n'y a pas de bandit sur la meme case que le marshall
	private void verifieFuite() {
		for(Bandit bandit : tabBandits) {
			if((this.marshall.getPOS()==bandit.getPOS()) && (!bandit.getEtage())){
				this.toPrint.add(bandit.getNom()+" fuit");
				bandit.lacheRecompense();
				bandit.deplacement(Direction.HAUT);
			}
		}
	}
	

	private void tire(Bandit b) {
		for (String str : b.tire()) {
			this.toPrint.add(str);
		}
	}
	
	public void suivant() {
		this.toPrint = new ArrayList<String>();
		this.toPrint.add(this.marshall.deplacement());
		
		//modifie la prochaine action des bandits si ils sont sur la meme case que le Marshall
		verifieFuite();
		for(Bandit bandit : this.tabBandits){ 
			switch(bandit.getActions().get(0)) {
				// si le Bandit se déplace on appelle la fonction deplacement
				case HAUT : this.toPrint.add(bandit.deplacement(Direction.HAUT));break;
				case ARRIERE : this.toPrint.add(bandit.deplacement(Direction.ARRIERE));break;
				case BAS : this.toPrint.add(bandit.deplacement(Direction.BAS));break;
				case AVANT : this.toPrint.add(bandit.deplacement(Direction.AVANT));break;
				
				case BRAQUAGE :
					//Action braquage :
					this.toPrint.add(bandit.getNom()+" braque le wagon");
					bandit.braquage();
					break;
				
				case TIRE :
					tire(bandit);
				default : break;	
			}
		
			bandit.removeAction(0);
		}
		verifieFuite();
		updateActions();
		notifyObservers();
	}
	
    /*
     * Getters/Setters
     */
	public Wagon[] getTabWagons() {
		return tabWagons;
	}
	
	
	public ArrayList<ArrayList<Actions>> getActions() {
		return this.actions;
	}
	
	public int getPhase() {
		return this.phase;
	}
	
	/*on passe a la phase suivante
	 *une phase de preparation par joueur
	 *et une phase d'actions 
	*/
	public void changePhase() {
		if(this.phase>=NB_JOUEURS) {
			this.phase=0;
		}else {
			this.phase++;
		}
	}
	
	//revoie true si on est en phase de preparation
	public boolean PreparationPhase() {
		return this.phase<NB_JOUEURS;
	}
	
	// phrases a afficher pour decrire comment le jeu se déroule
	public ArrayList<String> getToPrint(){
		return this.toPrint;
	}
	
	public Bandit[] getTabBandits() {
		return this.tabBandits;
	}
	
	
	//Main
	public static void main(String[] args) {
		boolean err_syntax =false;
		for(int cpt=0; cpt<args.length;cpt++) {
			if(args[cpt].equals( "-w")) {
				if(Integer.parseInt(args[cpt+1])>0) {
					Train.NB_WAGONS = Integer.parseInt(args[cpt+1]);
					System.out.println("Nombre Wagons = "+Train.NB_WAGONS);
				}else {
					err_syntax =true;
				}
			}
			if(args[cpt].equals("-j")) {
				if(Integer.parseInt(args[cpt+1])>0 && Integer.parseInt(args[cpt+1])<=2) {
					Train.NB_JOUEURS = Integer.parseInt(args[cpt+1]);
					System.out.println("Nombre Joueurs = "+Train.NB_JOUEURS);
				}else{
					err_syntax =true;
				}
			}
			if(args[cpt].equals("-n")) {
				if(Float.parseFloat(args[cpt+1])>=0 && Float.parseFloat(args[cpt+1]) <=1){
					Train.NERVOSITE_MARSHALL = Float.parseFloat(args[cpt+1]);
					System.out.println("Nervosité Marshall = "+Train.NERVOSITE_MARSHALL);
				}else {
					err_syntax =true;
				}
			}
			if(args[cpt].equals("-b")) {
				if(Float.parseFloat(args[cpt+1])>=0 && Float.parseFloat(args[cpt+1]) <=1){
					Train.NB_BALLES = Integer.parseInt(args[cpt+1]);
					System.out.println("Nombre balles = "+Train.NERVOSITE_MARSHALL);
				}else {
					err_syntax =true;
				}
			}
			if(args[cpt].equals("--noButtons")) {
				Train.SHOW_BUTTONS = false;
			}
		}
		if(err_syntax) {
			System.out.println("\nSyntaxe :\n\n\"-w <Nombre wagons>\"\n\"-j <Nombre joueurs>\"\n\"-n <Nervosité Marshall>\"\n\"-b <Nombre balles>\"");
			System.out.println("--noButtons : démarre le jeu sans les boutons");
			System.out.println("\nNombre Wagons entre 0 et 10\nNombre Joueurs : entre 0 et 3\nNervosité Marshall : entre 0.0 et 1.0\nNombre balles : 0 ou plus\n");
		}else {
			EventQueue.invokeLater(() -> {
				Train train = new Train();
				new Vue(train);
		    });
		}
	}
	
}
