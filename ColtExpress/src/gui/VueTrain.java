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
	private Image imageWagon;
	private Image background;
	
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
		//image Wagon
		ImageIcon iconWagon = new ImageIcon("wagon.png");
		this.imageWagon = iconWagon.getImage();
		//background
		ImageIcon iconBackground = new ImageIcon("background.jpg");
		this.background = iconBackground.getImage();
		
		
		
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
//		g.setColor(Color.DARK_GRAY);
//		g.fillRect(0, 0, this.getWidth(),this.getHeight());
		g.drawImage(this.background, 0, 0, this);
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
			if(!etage){
				g.drawImage(this.imageWagon,x, y, this);
			}
			
			
			for(int cpt=0;cpt<Train.NB_JOUEURS;cpt++) {
					if(bandits[cpt]) {
						switch (cpt) {
							case 0 : {
								if(etage){
									g.drawImage(this.imageThief1,x+40,y+85,this);break;
								}else{
									g.drawImage(this.imageThief1,x+40,y+50,this);break;
								}
							}
							case 1 : {
								if(etage){
									g.drawImage(this.imageThief2,x+30,y+85,this);break;
								}else{
									g.drawImage(this.imageThief2,x+30,y+50,this);break;
								}
							}
								
							
							default : System.out.println("erreur variable bool Bandits cpt="+cpt);break;
							
						}
					}
				}
			
			
		
		//affichage des recompsenses 
		
			for (int cpt=0; cpt<butins.size();cpt++) {
				switch(butins.get(cpt)) {
				
					case BIJOU : g.drawImage(this.imageJewel,x+(cpt*35%(Train.LARGEUR-this.imageJewel.getWidth(null))),y+80,this);
								 break;
				
					case BOURSE : g.drawImage(this.imageBourse,x+(cpt*35%(Train.LARGEUR-this.imageJewel.getWidth(null))),y+80,this);
								  break;
					
					case MAGOT : g.drawImage(this.imageChest,x+50,y+80,this);
								 break;
					
					default : 	System.err.println("Error printing recompenses in VueTrain.Java/paint");
								break;
					
				}
			}
			
			if (contientMarshall && !etage) {
				g.drawImage(this.imageMarshall,x+5,y+30,this);
			}
			
				
	}
		
	
}
