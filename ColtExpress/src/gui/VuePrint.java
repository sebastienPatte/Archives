package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import coltExpress.Train;

public class VuePrint extends JPanel implements Observer{

	private Train train;
	
	protected VuePrint(Train train){
		this.train=train;
		train.addObserver(this);
		this.setPreferredSize(new Dimension(300,150));
	}
	
	@Override
	public void update(){repaint();}
	
	@Override
	public void paintComponent(Graphics g) {
		super.repaint();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 300,150);
		g.setColor(Color.WHITE);
		int cpt = 1;
		for(String str : train.getToPrint()) {
			g.drawString(str, 0, 15*cpt);
			cpt++;
		}
	}
}
