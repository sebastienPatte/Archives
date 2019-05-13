package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import coltExpress.Train;

public class VueActions  extends JPanel implements Observer{
	private Train train;
	
	
	
	
	protected VueActions(Train train){
		this.train=train;
		train.addObserver(this);
		this.setPreferredSize(new Dimension(400,300));
	}
	
	public void update(){repaint();}
	
	@Override
	public void paintComponent(Graphics g) {
		super.repaint();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 400,300);
		g.setColor(Color.BLACK);
		if(this.train.PreparationPhase()) {	
			g.drawString("Preparation Phase : choisir 5 actions ", 20,20);
			g.drawString("Actions:", 10, 40);
		}else {
			g.drawString("Action Phase : Il reste "+this.train.tabBandits[0].getActions().size()+" actions.", 20,20);
		}
		
		//affichage actions
		for(int i=0; i < train.tabBandits.length; i++) {
			g.drawString("P"+(i+1)+":",(i*70)+10,60);
			for(int j=0; j<train.tabBandits[i].getActions().size();j++) {
				g.drawString(train.getActions().get(i).get(j).name(), (i*70)+10, 80+(j*20));
			}
		}
		
		//affichage butin courant
		g.drawString("Butin:", 170, 40);
		for(int i=0; i < train.tabBandits.length; i++) {
			g.drawString("P"+(i+1)+":",(i*100)+180,60);
			for(int j=0; j < train.tabBandits[i].getRecompenses().size(); j++) {
				g.drawString(this.train.tabBandits[i].getRecompenses().get(j).toString(), (i*100)+180, 80+(j*20));
			}
		}
		for(int cpt=0; cpt < train.tabBandits.length;cpt++) {
			g.drawString("P"+(cpt+1)+" a "+train.tabBandits[cpt].getNbBalle()+" balles", 10, 280+(15*cpt));
		}
		
	}
	
	
	
	
}
