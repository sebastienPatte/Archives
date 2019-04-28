package coltExpress;
import java.util.Random;

public class Wagon {
    private int id;
	private boolean contientBanditToit;
    private boolean contientBanditInterieur;
    private int NB_RECOMPENSE = (int) (Math.random() * (1-4));
    private Recompense[] butin;
    
    //Constructeur Wagon
    public Wagon(int id,boolean contientBanditToit, boolean contientBanditInterieur) {
        this.contientBanditToit = contientBanditToit;
        this.contientBanditInterieur = contientBanditInterieur;
    }
    public Wagon(){
    	this.butin=new Recompense[this.NB_RECOMPENSE];
    }
    
    
    
    //Getters
    public int getId() {
    	return this.id;
    }
    public boolean getContientBanditToit() {
    	return this.contientBanditToit;
    }
    public boolean getContientBanditInterieur() {
    	return this.contientBanditInterieur;
    }
    //Setters
    public void setContientBanditToit(boolean b) {
    	this.contientBanditToit = b;
    }
    public void setContientBanditInterieur(boolean b) {
    	this.contientBanditInterieur = b;
    }
    
}
