package gui;

import javax.swing.JButton;
import javax.swing.JPanel;

import coltExpress.Direction;

public class VueCommandes extends JPanel {
	private Modele modele;
	private JButton boutonAction;
	private JButton boutonHaut;
	private JButton boutonBas;
	private JButton boutonArriere;
	private JButton boutonAvant;
	private JButton boutonValider;
	private JButton boutonCorriger;
	
	
	public VueCommandes(Modele modele) {
		this.modele = modele;
		this.boutonHaut = new JButton("UP");
		this.add(boutonHaut);
		this.boutonArriere = new JButton("RIGTH");
		this.add(boutonArriere);
		this.boutonBas = new JButton("DOWN");
		this.add(boutonBas);
		this.boutonAvant = new JButton("LEFT");
		this.add(boutonAvant);
		
		this.boutonAction = new JButton("Action");
		this.boutonAction.setEnabled(false);
		this.add(boutonAction);
		
		this.boutonValider = new JButton("Valider");
		this.boutonValider.setEnabled(false);
		this.add(boutonValider);
		
		this.boutonCorriger = new JButton("Corriger");
		this.boutonCorriger.setEnabled(false);
		this.add(boutonCorriger);
		
		boutonCorriger.addActionListener(e -> {modele.removeAction();updateAllButtons();});
		boutonValider.addActionListener(e -> {modele.changePhase();updateAllButtons();});
		boutonAction.addActionListener(e -> { modele.suivant();updateAllButtons();});
		boutonHaut.addActionListener(e -> { modele.addAction(Direction.HAUT); updateAllButtons();});
		boutonArriere.addActionListener(e -> { modele.addAction(Direction.ARRIERE); updateAllButtons();});
		boutonBas.addActionListener(e -> { modele.addAction(Direction.BAS); updateAllButtons();});
		boutonAvant.addActionListener(e -> { modele.addAction(Direction.AVANT); updateAllButtons();});
		
		
	}
	
	private void updateAllButtons() {
		if(modele.PreparationPhase()) {
		//Phase de preparation	
			
			if(modele.getActions().size() >=1) this.boutonCorriger.setEnabled(true);
			if(modele.getActions().size() >= 5) {
				
				this.boutonHaut.setEnabled(false);
				this.boutonArriere.setEnabled(false);
				this.boutonBas.setEnabled(false);
				this.boutonAvant.setEnabled(false);
				this.boutonValider.setEnabled(true);
			}else {
				this.boutonHaut.setEnabled(true);
				this.boutonArriere.setEnabled(true);
				this.boutonBas.setEnabled(true);
				this.boutonAvant.setEnabled(true);
				
				this.boutonValider.setEnabled(false);
				this.boutonAction.setEnabled(false);
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
			if (this.modele.getActions().size() <= 0) {
				this.modele.changePhase();
				updateAllButtons();
			}
		}
	}
}
