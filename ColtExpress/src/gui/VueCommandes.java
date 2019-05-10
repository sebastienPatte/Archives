package gui;


import javax.swing.JButton;
import javax.swing.JPanel;

import coltExpress.Actions;
import coltExpress.Bandit;
import coltExpress.Direction;
import coltExpress.Train;

public class VueCommandes extends JPanel {
	private Train train;
	private JButton boutonAction;
	private JButton boutonHaut;
	private JButton boutonBas;
	private JButton boutonArriere;
	private JButton boutonAvant;
	private JButton boutonValider;
	private JButton boutonCorriger;
	private JButton boutonBraquage;
	private JButton boutonTire;
	private Bandit bandit;
	private boolean actionTir;
	
	
	public VueCommandes(Train train) {
		this.actionTir = false;
		this.train = train;
		this.boutonHaut = new JButton("HAUT");
		this.add(boutonHaut);
		this.boutonArriere = new JButton("ARRIERE");
		this.add(boutonArriere);
		this.boutonBas = new JButton("BAS");
		this.add(boutonBas);
		this.boutonAvant = new JButton("AVANT");
		this.add(boutonAvant);
		this.boutonBraquage = new JButton("Braquage");
		this.add(boutonBraquage);
		
		this.boutonTire = new JButton("Tirer");
		this.add(boutonTire);
		
		this.boutonAction = new JButton("Action");
		this.boutonAction.setEnabled(false);
		this.add(boutonAction);
		
		this.boutonValider = new JButton("Valider");
		this.boutonValider.setEnabled(false);
		this.add(boutonValider);
		
		this.boutonCorriger = new JButton("Corriger");
		this.boutonCorriger.setEnabled(false);
		this.add(boutonCorriger);
		
		

		if(train.PreparationPhase()) {
			this.bandit = train.tabBandits[train.getPhase()];
			
			boutonHaut.addActionListener(e -> { bandit.addAction(Actions.HAUT); updateAllButtons();});
			boutonArriere.addActionListener(e -> { bandit.addAction(Actions.ARRIERE); updateAllButtons();});
			boutonBas.addActionListener(e -> { bandit.addAction(Actions.BAS); updateAllButtons();});
			boutonAvant.addActionListener(e -> { bandit.addAction(Actions.AVANT); updateAllButtons();});
			
			
			boutonBraquage.addActionListener(e -> { bandit.addAction(Actions.BRAQUAGE); updateAllButtons();});
			boutonCorriger.addActionListener(e -> {bandit.removeAction(bandit.getActions().size()-1);updateAllButtons();});
			boutonTire.addActionListener(e -> {
					this.actionTir=true;
					updateAllButtons();
					enableSelectDir(true);
					bandit.addAction(Actions.TIRE);
				});
		}
		
		boutonValider.addActionListener(e -> {train.changePhase();updateAllButtons();});
		boutonAction.addActionListener(e -> { train.suivant();updateAllButtons();});
		
		
	}
	
	public Actions dirToAction(Direction dir) {
		switch(dir) {
			case HAUT: return Actions.HAUT;
			case ARRIERE: return Actions.ARRIERE;
			case BAS: return Actions.BAS;
			case AVANT: return Actions.AVANT;
			default : System.out.println("Erreur dans dirToAction");return Actions.ARRIERE;
		}
	}
	
	private void enableSelectDir( boolean enable) {
		JButton bouton = this.boutonHaut;
		//on parcours toutes les direction
		for(Direction dir : Direction.values()) {
			//on recupere le bouton correspondant
			switch(dir) {
				case HAUT: bouton = this.boutonHaut;break;
				case ARRIERE: bouton = this.boutonArriere;break;
				case BAS: bouton = this.boutonBas;break;
				case AVANT: bouton = this.boutonAvant;break;
				default : System.out.println("Erreur dans la direction du tir");break;
			}
			
	
		
			if(enable) {
				//on remplace l'actionListener actuel par celui pour selectionner la direction du tir
				bouton.removeActionListener(bouton.getActionListeners()[0]);
				//si ce listener est appelé il rapelle enableSelectDir avec false pour remmetre les ActionListener de base (pour tout les boutons) 
				bouton.addActionListener(e -> { this.bandit.addTabDirTir(dir); enableSelectDir(false); updateAllButtons();});
			}else {
				//si la fonction a été apelée avec false on remplace l'ActionListener avec celui de base pour arreter la selection de la direction du tir
				bouton.removeActionListener(bouton.getActionListeners()[0]);
				bouton.addActionListener(e -> { train.tabBandits[train.getPhase()].addAction(dirToAction(dir)); updateAllButtons();});
			}
		}
		
	}
	
	private void updateAllButtons() {
		if(train.PreparationPhase()) {
		//Phase de preparation	
			this.bandit = train.tabBandits[train.getPhase()];
			if(bandit.getActions().size() >=1) this.boutonCorriger.setEnabled(true);
			
			if(bandit.getActions().size() >= 5) {
				
				this.boutonHaut.setEnabled(false);
				this.boutonArriere.setEnabled(false);
				this.boutonBas.setEnabled(false);
				this.boutonAvant.setEnabled(false);
				this.boutonBraquage.setEnabled(false);
				this.boutonTire.setEnabled(false);
				this.boutonValider.setEnabled(true);
				
			}else {
				this.boutonHaut.setEnabled(true);
				this.boutonArriere.setEnabled(true);
				this.boutonBas.setEnabled(true);
				this.boutonAvant.setEnabled(true);
				this.boutonValider.setEnabled(false);
				this.boutonAction.setEnabled(false);
				
				if(this.actionTir) {
					//on masque les boutons qui ne sont pas des directions pour selectionner la direction du tir
					this.boutonTire.setEnabled(false);
					this.boutonBraquage.setEnabled(false);
					this.actionTir = false;
					
				}else {
					
					this.boutonBraquage.setEnabled(true);
					this.boutonTire.setEnabled(true);
					
				}
			}
		}else {
		//Phase d'action
			this.boutonCorriger.setEnabled(false);
			this.boutonAction.setEnabled(true);
			this.boutonValider.setEnabled(false);
			this.boutonHaut.setEnabled(false);
			this.boutonArriere.setEnabled(false);
			this.boutonBas.setEnabled(false);
			this.boutonAvant.setEnabled(false);
			this.boutonBraquage.setEnabled(false);
			this.boutonTire.setEnabled(false);
			
			if (this.bandit.getActions().size() <= 0) {
				this.train.changePhase();
				updateAllButtons();
			}
		}
	}
}
