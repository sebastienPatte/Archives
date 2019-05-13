package coltExpress;
import java.util.ArrayList;

public class Wagon {
    private int id;
	private boolean[] BanditsToit;
    private boolean[] BanditsInterieur;
    private boolean contientMarshall;
    private int NB_RECOMPENSE;
    private ArrayList<PaireButin> recompensesInterieur;
    private ArrayList<PaireButin> recompensesToit;
    
    //Constructeur Wagon
    public Wagon(int id) {
        this.BanditsToit = new boolean[Train.NB_JOUEURS];
        this.BanditsInterieur = new boolean[Train.NB_JOUEURS];
        for (int cpt=0;cpt<Train.NB_JOUEURS;cpt++) {
        	this.BanditsToit[cpt]=false;
        	this.BanditsInterieur[cpt]=false;
        }
        this.contientMarshall = false;
        this.id=id;
        this.initRecompenses();
    }
    
    public int randint (int min, int max) {
    	return (int) (Math.random() * (max-min+1)) + min;
    }
    
    private void initRecompenses() {
    	this.recompensesInterieur = new ArrayList<PaireButin>(); 
    	this.recompensesToit = new ArrayList<PaireButin>(); 
    	// si le wagon est la locomotive on met le Magot
    	if(this.id==0){
    		this.recompensesInterieur.add(new PaireButin(Butin.MAGOT, 1000));
    	//sinon on met de 1 a 4 recompenses
    	}else {
    		this.NB_RECOMPENSE = this.randint(1, 4);
    		for(int i=0; i< this.NB_RECOMPENSE;i++) {
    			// une chance sur deux qu'il y ait une bourse ou un bijou
    			if(randint(0,1)==0) {
    				// une bourse a une valeur de 0 à 500$
    				this.recompensesInterieur.add(new PaireButin(Butin.BOURSE, this.randint(0, 500)));
    			}else {
    				// un Bijou a toujours une valeur de 500$
    				this.recompensesInterieur.add(new PaireButin(Butin.BIJOU, 500));	
    			}
    		}
    	}
    }
    
    public PaireButin getRdmRecompense(boolean etage) {
    	ArrayList<PaireButin> list;
    	if(etage) {
    		list = this.recompensesToit;
    	}else {
    		list = this.recompensesInterieur;
    	}
    		int rdmIndex = this.randint(0, list.size()-1);
    		PaireButin res = list.get(rdmIndex);
    		list.remove(rdmIndex);
    		return res;
    }
    
    //Getters
    public int getId() {
    	return this.id;
    }
    public boolean[] getBanditsToit() {
    	return this.BanditsToit;
    }
    public boolean[] getBanditsInterieur() {
    	return this.BanditsInterieur;
    }
    //Setters
    public void setBanditsToit(int nb,boolean b) {
    	this.BanditsToit[nb]=b ;
    }
    public void setBanditsInterieur(int nb, boolean b) {
    	this.BanditsInterieur[nb]=b;
    }

	public ArrayList<PaireButin> getRecompenses(boolean etage) {
		if (etage) {
			return this.recompensesToit;
		}else {
			return this.recompensesInterieur;
		}
		
	}
	
	
	public void addRecompense(PaireButin rew, boolean etage) {
		if(etage) {
			this.recompensesToit.add(rew);
		}else {
			this.recompensesInterieur.add(rew);
		}	
	}
	
	public boolean getContientMarshall() {
		return this.contientMarshall;
	}
	
	public void setContientMarshall(boolean b) {
		this.contientMarshall = b;
	}

    
}
