package gui;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Shape;

import javax.swing.JFrame;

public class Vue {
	
	private JFrame frame;
	private VueTrain vueTrain;
	private VueCommandes vueCommandes;
	private VueActions vueActions;
	
	public Vue(Modele modele) {
		/** Définition de la fenêtre principale. */
		this.frame = new JFrame();
		this.frame.setTitle("ColtExpress");
		this.frame.setLayout(new FlowLayout());
		this.frame.setSize(1000,400);
		
		/** Définition de la vue et ajout à la fenêtre. */
		this.vueTrain = new VueTrain(modele);
		frame.add(this.vueTrain);
		this.vueActions = new VueActions(modele);
		frame.add(this.vueActions);
		this.vueCommandes = new VueCommandes(modele);
		frame.add(this.vueCommandes);
		
	
		
		/**
		 * Fin de la plomberie :
		 *  - Ajustement de la taille de la fenêtre en fonction du contenu.
		 *  - Indiquer qu'on quitte l'application si la fenêtre est fermée.
		 *  - Préciser que la fenêtre doit bien apparaître à l'écran.
		 */
//		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
