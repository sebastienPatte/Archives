package gui;


import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import coltExpress.Train;

public class Vue {
	
	private JFrame frame;
	private VueTrain vueTrain;
	private VueCommandes vueCommandes;
	private VueActions vueActions;
	private VuePrint vuePrint;
	
	public Vue(Train train) {
		/** Définition de la fenêtre principale. */
		this.frame = new JFrame();
		this.frame.setTitle("ColtExpress");
		this.frame.setLayout(new FlowLayout());
		this.frame.setSize(1000,700);
		this.frame.getContentPane().setBackground(Color.DARK_GRAY);
		/** Définition de la vue et ajout à la fenêtre. */
		this.vueTrain = new VueTrain(train);
		frame.add(this.vueTrain);
		this.vueActions = new VueActions(train);
		frame.add(this.vueActions);
		this.vuePrint = new VuePrint(train);
		frame.add(this.vuePrint);
		this.vueCommandes = new VueCommandes(train);
		frame.add(this.vueCommandes); 

		frame.setFocusable(true); 

		frame.requestFocus(); 

		this.frame.addKeyListener( new KeyboardShortcut(this.vueCommandes));
		/**
		 * Fin de la plomberie :
		 *  - Ajustement de la taille de la fenêtre en fonction du contenu.
		 *  - Indiquer qu'on quitte l'application si la fenêtre est fermée.
		 *  - Préciser que la fenêtre doit bien apparaître à l'écran.
		 */
		//frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
