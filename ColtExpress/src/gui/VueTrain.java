package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import coltExpress.Butin;
import coltExpress.Train;

public class VueTrain extends JPanel implements Observer{
	private Train train;
	private final static int TAILLE = 150; //taille d'un wagon
	private Image imageThief1;
	private Image imageThief2;
	private Image imageMarshall;
	private Image imageJewel;
	private Image imageBourse;
	private Image imageChest;
	
	public VueTrain(Train train) {
		this.train=train;
		//image Bandit 1
		ImageIcon iconThief1 = new ImageIcon("thief.png");
		this.imageThief1 = iconThief1.getImage();
		//image Bandit 2
		ImageIcon iconThief2 = new ImageIcon("thief2.png");
		this.imageThief2 = iconThief2.getImage();
		//image Marshall
		ImageIcon iconMarshall = new ImageIcon("policeman.png");
		this.imageMarshall = iconMarshall.getImage();
		//image bijou
		ImageIcon iconJewel = new ImageIcon("jewel.png");
		this.imageJewel = iconJewel.getImage();
		//image Bourse
		ImageIcon iconBourse = new ImageIcon("bourse.png");
		this.imageBourse = iconBourse.getImage();
		//image coffre
		ImageIcon iconChest = new ImageIcon("chest.png");
		this.imageChest = iconChest.getImage();		
		
		train.addObserver(this);
		
		//la dimension du JPanel est adaptée à la taille du tableau tabWagons (bloolean[][]) du modèle
		Dimension dim = new Dimension(train.getTabWagons().length*Train.LARGEUR,
				2*Train.HAUTEUR);
		this.setPreferredSize(dim);
		
		
	}
	
	// Quand le modèle est mis à jour update() est appelée et la vue du train est actualisée
	public void update() { repaint(); }
	
	public void paintComponent(Graphics g) {
		super.repaint();
		
		ArrayList<Butin> butins;
		for(int i=0; i<train.getTabWagons().length; i++) {
			butins = new ArrayList<Butin>();
			
			for(int cpt=0; cpt<train.getTabWagons()[i].getRecompenses(false).size(); cpt++) {
				butins.add(train.getTabWagons()[i].getRecompenses(false).get(cpt).getLeft());
			}
			paint(i*Train.LARGEUR, 1*Train.HAUTEUR,false, train.getTabWagons()[i].getContientMarshall(), train.getTabWagons()[i].getBanditsInterieur(),butins,g);
			
			butins = new ArrayList<Butin>();
			for(int cpt=0; cpt<train.getTabWagons()[i].getRecompenses(true).size(); cpt++) {
				butins.add(cpt,train.getTabWagons()[i].getRecompenses(true).get(cpt).getLeft());
			}
			paint(i*Train.LARGEUR,0*Train.HAUTEUR,true, train.getTabWagons()[i].getContientMarshall(), train.getTabWagons()[i].getBanditsToit(), butins,g);
		}
	}
	public void paint(int x, int y, boolean etage, boolean contientMarshall,boolean[] bandits,ArrayList<Butin> butins,Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(x, y, TAILLE, TAILLE);
			//affichage bandits interieur
			
				for(int cpt=0;cpt<Train.NB_JOUEURS;cpt++) {
					if(bandits[cpt]) {
						switch (cpt) {
							case 0 : g.drawImage(this.imageThief1,x+60,y+25,null);break;
							case 1 : g.drawImage(this.imageThief2,x+60,y,null);break;
							
							default : System.out.println("erreur variable bool Bandits cpt="+cpt);break;
						}
					}
				}
					
			
		
		//affichage des recompsenses 
		
			for (int cpt=0; cpt<butins.size();cpt++) {
				switch(butins.get(cpt)) {
				
					case BIJOU : g.drawImage(this.imageJewel,x+(cpt*35),y+100,null);
								 break;
				
					case BOURSE : g.drawImage(this.imageBourse,x+(cpt*35),y+100,null);
								  break;
					
					case MAGOT : g.drawImage(this.imageChest,x+50,y+100,null);
								 break;
					
					default : 	System.err.println("Error printing recompenses in VueTrain.Java/paint");
								break;
					
				}
			}
			
			if (contientMarshall && !etage) {
				g.drawImage(this.imageMarshall,x,y,null);
			}
			
				
	}
		
	
}
