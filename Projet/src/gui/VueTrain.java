package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import coltExpress.Wagon;

public class VueTrain extends JPanel implements Observer{
	private Modele modele;
	private final static int TAILLE = 111; //taille d'un wagon
	private Image image;
	
	public VueTrain(Modele modele) {
		this.modele=modele;
		ImageIcon icon = new ImageIcon("thief.png");
		this.image = icon.getImage();
		modele.addObserver(this);
		//la dimension du JPanel est adaptée à la taille du tableau tabWagons (bloolean[][]) du modèle
		Dimension dim = new Dimension(modele.getTabWagons().length*Modele.LARGEUR,
				modele.getTabWagons()[0].length*Modele.HAUTEUR);
		this.setPreferredSize(dim);
	}
	
	// Quand le modèle est mis à jour update() est appelée et la vue du train est actualisée
	public void update() { repaint(); }
	
	public void paintComponent(Graphics g) {
		super.repaint();
		
		for(int i=0;i< modele.getTabWagons().length;i++) {
			for (int j=0;j<(modele.getTabWagons()[i]).length;j++) {
				paint(i*modele.LARGEUR,j*modele.HAUTEUR,modele.getTabWagons()[i][j],g);
			}
		}
	}
	public void paint(int x, int y,boolean b,Graphics g) {
		if(b) {
			g.setColor(Color.WHITE);
			g.fillRect(x, y, TAILLE, TAILLE);
			g.drawImage(this.image,x,y,null);
		}else {
			g.setColor(Color.BLACK);
			g.fillRect(x,y, TAILLE, TAILLE);
		}
		
	}
		
	
}
