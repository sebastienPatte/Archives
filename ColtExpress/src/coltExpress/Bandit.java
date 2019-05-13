package coltExpress;

import java.util.ArrayList;

public class Bandit extends Personne{
	public static int NB_BALLES = 8;
	
	private Wagon[] tabWagons ;
	private String nom;
	private int id;
	private boolean etage; //true : sur le toit, false : dans le wagon
	private ArrayList<PaireButin> recompenses;
	private ArrayList<Actions> actions;
	private ArrayList<Direction> tabDirTir;
	private int nbBalles;
	
	Bandit (Wagon[] tabWagons,int id,int POS, boolean etage, String nom){
		this.tabWagons=tabWagons;
		this.id=id;
		this.POS=POS;
		this.etage =etage;
		this.nom = nom;
		this.recompenses = new ArrayList<PaireButin>();
		this.actions = new ArrayList<Actions>();
		this.tabDirTir = new ArrayList<Direction>();
		this.nbBalles=Bandit.NB_BALLES;
	}

	protected void braquage() {
		if(this.tabWagons[this.POS].getRecompenses(this.etage).size()>0) {
			this.recompenses.add(this.tabWagons[this.POS].getRdmRecompense(this.etage));
		}
	}
	
	protected void lacheRecompense() {
		if(this.getRecompenses().size()>0) {
			int rdmIndex = this.tabWagons[this.POS].randint(0,this.getRecompenses().size()-1);
			int POS = this.getPOS();
			//ajout de la recompense sur le wagon
			this.tabWagons[POS].addRecompense(this.getRecompenses().get(rdmIndex), this.etage);
			//suppression de la recompense dans l'inventaire du bandit
			this.recompenses.remove(rdmIndex);
		}	
	}
	
	protected ArrayList<String> tire() {
		ArrayList<String> res = new ArrayList<String>();
		int POS=0;
		boolean etage=false;
		boolean dansLeMur = false;
		if(this.getNbBalle()>0) {
			switch (this.getTabDirTir().get(0)) {
				case HAUT: {
					if(!this.etage) {
						POS=this.getPOS();etage=true;break;
					}else {
						dansLeMur=true;break;
					}
				}
				case ARRIERE:{
					if(this.getPOS()+1<Train.NB_WAGONS) {
						POS=this.getPOS()+1;etage=this.getEtage();break;
					}else {
						dansLeMur = true;break;
					}
				}
				case BAS: {
					if(this.etage) {
						POS=this.getPOS();etage=false;break;
					}else {
						dansLeMur = true;break;
					}
				}
				case AVANT: {
					if(this.getPOS()-1 >= 0) {
						POS=this.getPOS()-1;etage=this.getEtage();break;
					}else {
						dansLeMur = true;break;
					}
				}
				default : break;
			}
			if(!dansLeMur) {
				res.add(this.getNom()+" tire "+this.getTabDirTir().get(0)+"\n");
			
				if(etage) {
					for(int cpt=0; cpt < Train.NB_JOUEURS;cpt++) {
						if(cpt!=this.id) {
							if(this.tabWagons[POS].getBanditsToit()[cpt]) {
								res.add(this.getNom()+" est touché\n");
								this.lacheRecompense();
							}
						}
					}
				}else {
					for(int cpt=0; cpt < Train.NB_JOUEURS;cpt++) {
						if(cpt!=this.id) {
							if(this.tabWagons[POS].getBanditsInterieur()[cpt]) {
								res.add(this.getNom()+" est touché\n");
								this.lacheRecompense();
							}
						}
					}
				}
			}else {
				res.add(this.getNom()+" tire dans le Mur\n");
			}
			
			this.nbBalles--;
		}else{
			res.add( this.getNom()+" essaye de tirer mais n'a plus de balles\n");
		}
		this.removeDirTir();
		return res;
	}
	
	/*
	 * @param Bandit
	 * @param Direction
	 * return -> String
	 */
	public String deplacement(Direction direction) {
        switch (direction) { 
        case AVANT:
            if(this.getPOS()>0) {
            	//maj Wagons
            	if (!this.getEtage()){
                	this.tabWagons[this.getPOS()].setBanditsInterieur(this.id,false);
                	this.tabWagons[this.getPOS()-1].setBanditsInterieur(this.id,true);
                }else{
                	this.tabWagons[this.getPOS()].setBanditsToit(this.id,false);
                	this.tabWagons[this.getPOS()-1].setBanditsToit(this.id,true);
                }
            	//maj POS bandit
            	this.setPOS(this.getPOS()-1);
                return this.getNom()+" se déplace vers l'avant";
                
            }else {
                return this.getNom()+" est déjà au premier Wagon";
            }
        case ARRIERE:
            if(this.getPOS() < Train.NB_WAGONS-1) {            	
            	//maj Wagons
            	if (!this.getEtage()){
                	this.tabWagons[this.getPOS()].setBanditsInterieur(this.id,false);
                	this.tabWagons[this.getPOS()+1].setBanditsInterieur(this.id,true);
                }else{
                	this.tabWagons[this.getPOS()].setBanditsToit(this.id,false);
                	this.tabWagons[this.getPOS()+1].setBanditsToit(this.id,true);
                }
            	
            	//maj POS bandit
            	this.setPOS(this.getPOS()+1);
            	return this.getNom()+" se déplace vers l'arrière";
            }else {
                return this.getNom()+" est déjà au dernier Wagon";
            }
        case HAUT:
            if(!this.getEtage()) {
            	//maj Wagons
            	this.tabWagons[this.getPOS()].setBanditsInterieur(this.id,false);
            	this.tabWagons[this.getPOS()].setBanditsToit(this.id,true);
            	//maj POS bandit
            	this.setEtage(true);
            	return this.getNom()+" monte";
            }else {
                return this.getNom()+" est déjà sur le toit";
            }
        case BAS:
            if(this.getEtage()) {
            	//maj Wagons
            	this.tabWagons[this.getPOS()].setBanditsInterieur(this.id,true);
            	this.tabWagons[this.getPOS()].setBanditsToit(this.id,false);
            	//maj POS bandit
            	this.setEtage(false);
                return this.getNom()+" descend";
            }else {
                return this.getNom()+" est déjà à l'intérieur du wagon";
            }
        default: return "Erreur dans la fonction Train.deplacement : Direction Inconnue";
        
        }
	}
	
	public boolean getEtage() {
		return this.etage;
	}
	
	protected void setEtage(boolean etage) {
		this.etage=etage;
	}
			
	public String getNom() {
		return this.nom;
	}
	
	
	
	public ArrayList<PaireButin> getRecompenses(){
		return this.recompenses;
	}
	
	
	
	public ArrayList<Actions> getActions() {
		return this.actions;
	}
	
	//ajouter une nouvelle action à la suite 
	public void addAction(Actions a) {
		this.actions.add(a);
	}
	
	//retire la derniere action de la liste (utilisé par le bouton corriger)
	public void removeAction(int i) {
		this.actions.remove(i);
	}
	/*
	public int getId() {
		return this.id;
	}
	*/
	public void addTabDirTir(Direction dir) {
		this.tabDirTir.add(dir);
	}
	
	public ArrayList<Direction> getTabDirTir() {
		return this.tabDirTir;
	}
	
	public void removeDirTir() {
		this.tabDirTir.remove(0);
	}
	
	public int getNbBalle() {
		return this.nbBalles;
	}
	
	
}
