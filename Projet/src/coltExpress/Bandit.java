package coltExpress;

public class Bandit {
	private String nom;
    private int POS;
    private int etage;
    
    //Constructeur Bandit
    Bandit (String nom,int POS,int etage){
        this.nom = nom;
        this.POS = POS;
        this.etage = etage;
    }
    
    //Getters
    public String getNom() {
    	return this.nom;
    }
    public int getPOS() {
    	return this.POS;
    }
    public int getEtage() {
    	return this.etage;
    }
    //Setters
    public void setPOS(int POS) {
    	this.POS=POS;
    }
    public void setEtage(int etage) {
    	this.etage=etage;
    }
}
