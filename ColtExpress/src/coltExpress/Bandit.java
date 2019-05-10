package coltExpress;

import java.util.ArrayList;

public class Bandit extends Personne{
	String nom;
	int id;
	boolean etage; //true : sur le toit, false : dans le wagon
	private ArrayList<PaireButin> recompenses;
	private ArrayList<Actions> actions;
	private ArrayList<Direction> tabDirTir;
	 
	
	Bandit (int id,int POS, boolean etage, String nom){
		this.id=id;
		this.POS=POS;
		this.etage =etage;
		this.nom = nom;
		this.recompenses = new ArrayList<PaireButin>();
		this.actions = new ArrayList<Actions>();
		this.tabDirTir = new ArrayList<Direction>();
	}

	
	public boolean getEtage() {
		return this.etage;
	}
	
	public void setEtage(boolean etage) {
		this.etage=etage;
	}
			
	public String getNom() {
		return this.nom;
	}
	
	public void addRecompense(PaireButin rew) {
		this.recompenses.add(rew);
	}
	
	public ArrayList<PaireButin> getRecompenses(){
		return this.recompenses;
	}
	
	public void removeRecompense(int i) {
		this.recompenses.remove(i);
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
	
	public int getId() {
		return this.id;
	}
	public void addTabDirTir(Direction dir) {
		this.tabDirTir.add(dir);
	}
	
	public ArrayList<Direction> getTabDirTir() {
		return this.tabDirTir;
	}
	
	public void removeDirTir() {
		this.tabDirTir.remove(0);
	}
}
