package coltExpress;

import java.awt.EventQueue;
import java.util.ArrayList;

import gui.Observable;
import gui.Vue;


public class Train extends Observable{
	public static int NB_JOUEURS=2;
	public static  int HAUTEUR=160;
	public static  int LARGEUR=160;
	public static double NERVOSITE_MARSHALL=0.3;
	public static int NB_WAGONS = 4;

	
	private Wagon[] tabWagons; //l'indice des wagons commence à 0
	public Bandit[] tabBandits;
	public Marshall marshall;
	private int phase; 
	public ArrayList<ArrayList<Actions>> actions;
	
	// Constructeur Train
	public Train (){
        this.tabWagons = new Wagon[Train.NB_WAGONS];
        for (int i = 0; i<Train.NB_WAGONS;i++) {
            this.tabWagons[i]= new Wagon(i);
        }
        this.tabBandits = new Bandit[NB_JOUEURS];
        this.tabBandits[0]= new Bandit(0,Train.NB_WAGONS-1,true,"P1");
        if(NB_JOUEURS>1)this.tabBandits[1]= new Bandit(1,Train.NB_WAGONS-1,true,"P2");
        this.marshall = new Marshall();
        for(int cpt=0 ; cpt < NB_JOUEURS;cpt++) {
        	this.tabWagons[Train.NB_WAGONS-1].setBanditsToit(cpt,true);
        }
        	this.tabWagons[0].setContientMarshall(true);
        this.phase= 0;
        updateActions();
    }
	
	
	private void updateActions(){
		this.actions = new ArrayList<ArrayList<Actions>>();
		for(Bandit bandit : this.tabBandits) {
			this.actions.add(bandit.getActions());
		}
		
	}
	public void verifieFuite() {
		for(Bandit bandit : tabBandits) {
			if((this.marshall.getPOS()==bandit.getPOS()) && (!bandit.getEtage())){
				System.out.println(bandit.getNom()+" fuit");
				lacheRecompense(bandit);
				deplacementBandit(bandit, Direction.HAUT);
			}
		}
	}
	
	
	
	public void braquage(Bandit bandit) {
		if(this.tabWagons[bandit.POS].getRecompenses(bandit.etage).size()>0) {
			bandit.addRecompense(this.tabWagons[bandit.POS].getRdmRecompense(bandit.etage));
		}
	}
	
	public int randint (int min, int max) {
    	return (int) (Math.random() * (max-min+1)) + min;
    }
	
	
	//
	public String deplacementMarshall() {
		if(this.marshall.seDeplace()) {
			switch(this.marshall.rdmDir()) {
				case AVANT: 
					if(this.marshall.getPOS()>0) {
						this.tabWagons[this.marshall.getPOS()].setContientMarshall(false);
						this.marshall.setPOS(this.marshall.getPOS()-1);
						this.tabWagons[this.marshall.getPOS()].setContientMarshall(true);
						return "Le Marshall se deplace vers l'avant";
					}else {
						return "Le Marshall est déjà sur le premier Wagon";
						
					}
					
					
					
				case ARRIERE: 
					if(this.marshall.getPOS()<Train.NB_WAGONS-1) {
						this.tabWagons[this.marshall.getPOS()].setContientMarshall(false);
						this.marshall.setPOS(this.marshall.getPOS()+1);
						this.tabWagons[this.marshall.getPOS()].setContientMarshall(true);
						return "Le Marshall se deplace vers l'arrière";
					}else {
						return "Le Marshall est déjà sur le dernier Wagon";
					}
				default : return "Erreur deplacement Marshall, Direction non reconnue";
			}
		}else {
			return "Le Marshall ne se deplace pas";
		}
	}
	
	/*
	 * @param Bandit
	 * @param Direction
	 * return -> String
	 */
	public String deplacementBandit (Bandit b,Direction direction) {
        switch (direction) { 
        case AVANT:
            if(b.getPOS()>0) {
            	//maj Wagons
            	if (!b.getEtage()){
                	this.getTabWagons()[b.getPOS()].setBanditsInterieur(b.getId(),false);
                	this.getTabWagons()[b.getPOS()-1].setBanditsInterieur(b.getId(),true);
                }else{
                	this.getTabWagons()[b.getPOS()].setBanditsToit(b.getId(),false);
                	this.getTabWagons()[b.getPOS()-1].setBanditsToit(b.getId(),true);
                }
            	//maj POS bandit
            	b.setPOS(b.getPOS()-1);
                return b.getNom()+" se déplace vers l'avant";
                
            }else {
                return b.getNom()+" est déjà au premier Wagon";
            }
        case ARRIERE:
            if(b.getPOS() < Train.NB_WAGONS-1) {            	
            	//maj Wagons
            	if (!b.getEtage()){
                	this.getTabWagons()[b.getPOS()].setBanditsInterieur(b.getId(),false);
                	this.getTabWagons()[b.getPOS()+1].setBanditsInterieur(b.getId(),true);
                }else{
                	this.getTabWagons()[b.getPOS()].setBanditsToit(b.getId(),false);
                	this.getTabWagons()[b.getPOS()+1].setBanditsToit(b.getId(),true);
                }
            	
            	//maj POS bandit
            	b.setPOS(b.getPOS()+1);
            	return b.getNom()+" se déplace vers l'arrière";
            }else {
                return b.getNom()+" est déjà au dernier Wagon";
            }
        case HAUT:
            if(!b.getEtage()) {
            	//maj Wagons
            	this.getTabWagons()[b.getPOS()].setBanditsInterieur(b.getId(),false);
            	this.getTabWagons()[b.getPOS()].setBanditsToit(b.getId(),true);
            	//maj POS bandit
            	b.setEtage(true);
            	return b.getNom()+" monte";
            }else {
                return b.getNom()+" est déjà sur le toit";
            }
        case BAS:
            if(b.getEtage()) {
            	//maj Wagons
            	this.getTabWagons()[b.getPOS()].setBanditsInterieur(b.getId(),true);
            	this.getTabWagons()[b.getPOS()].setBanditsToit(b.getId(),false);
            	//maj POS bandit
            	b.setEtage(false);
                return b.getNom()+" descend";
            }else {
                return b.getNom()+" est déjà à l'intérieur du wagon";
            }
        default: return "Erreur dans la fonction Train.deplacement : Direction Inconnue";
        
        }
	}

	public void lacheRecompense(Bandit bandit) {
		if(bandit.getRecompenses().size()>0) {
			int rdmIndex = randint(0,bandit.getRecompenses().size()-1);
			int POS = bandit.getPOS();
			//ajout de la recompense sur le wagon
			this.tabWagons[POS].addRecompense(bandit.getRecompenses().get(rdmIndex), bandit.etage);
			//suppression de la recompense dans l'inventaire du bandit
			bandit.removeRecompense(rdmIndex);
		}	
	}
	
	public void tire(Bandit b) {
		int POS=0;
		boolean etage=false;
		boolean dansLeMur = false;
		
		switch (b.getTabDirTir().get(0)) {
			case HAUT: {
				if(!b.etage) {
					POS=b.getPOS();etage=true;break;
				}else {
					dansLeMur=true;break;
				}
				
			}
			case ARRIERE:{
				if(b.getPOS()+1<NB_WAGONS-1) {
					POS=b.getPOS()+1;etage=b.getEtage();break;
				}else {
					dansLeMur = true;break;
				}
			}
			case BAS: {
				if(b.etage) {
					POS=b.getPOS();etage=false;break;
				}else {
					dansLeMur = true;break;
				}
			}
			case AVANT: {
				if(b.getPOS()-1 >= 0) {
				POS=b.getPOS()-1;etage=b.getEtage();break;
				}else {
					dansLeMur = true;break;
				}
			}
			default : break;
		}
		if(!dansLeMur) {
			System.out.println(b.getNom()+" tire "+b.getTabDirTir().get(0));
			
			if(etage) {
				for(int cpt=0; cpt < NB_JOUEURS;cpt++) {
					if(cpt!=b.getId()) {
						if(this.tabWagons[POS].getBanditsToit()[cpt]) {
							System.out.println(tabBandits[cpt].getNom()+" est touché");
							lacheRecompense(tabBandits[cpt]);
						}
					}
				}
			}else {
				for(int cpt=0; cpt < NB_JOUEURS;cpt++) {
					if(cpt!=b.getId()) {
						if(this.tabWagons[POS].getBanditsInterieur()[cpt]) {
							System.out.println(tabBandits[cpt].getNom()+" est touché");
							lacheRecompense(tabBandits[cpt]);
						}
					}
				}
			}
		}else {
			System.out.println(b.getNom()+" tire dans le Mur");
		}
		
		
		b.removeDirTir();
		
		
	}
	
	public void suivant() {
		System.out.println("--------------------------------------------");
		System.out.println(this.deplacementMarshall());
		
		//modifie la prochaine action des bandits si ils sont sur la meme case que le Marshall
		verifieFuite();
		for(Bandit bandit : this.tabBandits){ 
			switch(bandit.getActions().get(0)) {
				// si le Bandit se déplace on appelle la fonction deplacement
				case HAUT : System.out.println(deplacementBandit(bandit,Direction.HAUT));break;
				case ARRIERE : System.out.println(deplacementBandit(bandit,Direction.ARRIERE));break;
				case BAS : System.out.println(deplacementBandit(bandit,Direction.BAS));break;
				case AVANT : System.out.println(deplacementBandit(bandit,Direction.AVANT));break;
			
				// 	si le Bandit fait un Braquage :
				case BRAQUAGE :
					//Action braquage :
					System.out.println(bandit.getNom()+" braque le wagon");
					braquage(bandit);
					
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
	
    //Getters/Setters
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

	
	
	//Main
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Train train = new Train();
			Vue vue = new Vue(train);
		    });
	}
	
}
