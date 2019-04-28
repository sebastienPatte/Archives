package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueActions  extends JPanel implements Observer{
	private Modele modele;
	
	
	
	
	protected VueActions(Modele modele){
		this.modele=modele;
		modele.addObserver(this);
		this.setPreferredSize(new Dimension(400,200));
	}
	
	public void update(){repaint();}
	
	@Override
	public void paintComponent(Graphics g) {
		super.repaint();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 400,200);
		g.setColor(Color.BLACK);
		if(this.modele.PreparationPhase()) {	
			g.drawString("Preparation Phase : choisir 5 actions ", 20,20);
			g.drawString(this.modele.getActions().toString(), 20, 50);
		}else {
			g.drawString("Action Phase", 20,20);
			g.drawString("Il reste "+this.modele.getActions().size()+" actions.", 20, 50);
		}
	}
	
	
	
	
}
