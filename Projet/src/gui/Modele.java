package gui;

import java.util.ArrayList;

import coltExpress.Direction;
import coltExpress.Train;
import coltExpress.Wagon;

public class Modele extends Observable{

	public static  int HAUTEUR=115;
	public static  int LARGEUR=115;
	private boolean[][] tabWagons;
	private ArrayList<Direction> actions;
	private Train t;
	private boolean phasePreparation;
	
	public Modele() {
		this.t = new Train();
		this.actions=new ArrayList<Direction>();
		this.tabWagons=new boolean [this.t.getTabWagons().length] [2];
		setTabWagons(this.t.getTabWagons());
		this.phasePreparation = true;
		
	}
	
	private void deplacement(Direction dir) {
		this.t.deplacement(this.t.BANDIT_1,dir);
		
		this.setTabWagons(this.t.getTabWagons());
		
		notifyObservers();
	}
	
	public void suivant() {
		deplacement(this.actions.get(0));
		this.actions.remove(0);
		notifyObservers();
	}
	
	
	
	//Getters/setters
	public void addAction(Direction dir) {
		this.actions.add(dir);
		notifyObservers();
	}
	
	
	public void removeAction() {
		this.actions.remove(this.actions.size()-1);
		//retire la derniere action de la liste (utilisé par le bouton corriger)
	}
	
	public ArrayList<Direction> getActions() {
		return this.actions;
	}
	
	public boolean[][] getTabWagons() {
		return tabWagons;
	}
	
	public void setTabWagons(Wagon[] tabWagons) {
		int cpt=0;
		for(Wagon w : tabWagons) {
			this.tabWagons[cpt][0]= w.getContientBanditToit();
			this.tabWagons[cpt][1]= w.getContientBanditInterieur();
			cpt++;
		}
	}
	
	public void changePhase() {
		if(this.phasePreparation) {
			this.phasePreparation=false;
		}else {
			this.phasePreparation=true;
		}
	}
	
	public boolean PreparationPhase() {
		return this.phasePreparation;
	}
	
}
