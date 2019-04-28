package coltExpress;

public class Train {
	
	private int NB_WAGONS = 4;
	private Wagon[] tabWagons; //l'indice des wagons commence à 0
	public Bandit BANDIT_1;
	
	// Constructeur Train
	public Train (){
        this.tabWagons = new Wagon[this.NB_WAGONS];
        for (int i = 0; i<this.NB_WAGONS;i++) {
            this.tabWagons[i]= new Wagon(i,false,false);
        }
        this.BANDIT_1 = new Bandit("John",this.NB_WAGONS-1,1);
        this.tabWagons[this.NB_WAGONS-1].setContientBanditInterieur(false);
        this.tabWagons[this.NB_WAGONS-1].setContientBanditToit(true);
    }
	
	
	
	/*
	 * @param Bandit
	 * @param Direction
	 * return -> String
	 */
	public String deplacement (Bandit b,Direction direction) {
        switch (direction) { 
        case AVANT:
            if(b.getPOS()>0) {
            	//maj Wagons
            	if (b.getEtage()==0){
                	this.getTabWagons()[b.getPOS()].setContientBanditInterieur(false);
                	this.getTabWagons()[b.getPOS()-1].setContientBanditInterieur(true);
                }else{
                	this.getTabWagons()[b.getPOS()].setContientBanditToit(false);
                	this.getTabWagons()[b.getPOS()-1].setContientBanditToit(true);
                }
            	//maj POS bandit
            	b.setPOS(b.getPOS()-1);
                return b.getNom()+" se déplace vers l'avant";
                
            }else {
                return b.getNom()+" est déjà au premier Wagon";
            }
        case ARRIERE:
            if(b.getPOS() < this.NB_WAGONS-1) {            	
            	//maj Wagons
            	if (b.getEtage()==0){
                	this.getTabWagons()[b.getPOS()].setContientBanditInterieur(false);
                	this.getTabWagons()[b.getPOS()+1].setContientBanditInterieur(true);
                }else{
                	this.getTabWagons()[b.getPOS()].setContientBanditToit(false);
                	this.getTabWagons()[b.getPOS()+1].setContientBanditToit(true);
                }
            	
            	//maj POS bandit
            	b.setPOS(b.getPOS()+1);
            	return b.getNom()+" se déplace vers l'arrière";
            }else {
                return b.getNom()+" est déjà au dernier Wagon";
            }
        case HAUT:
            if(b.getEtage()==0) {
            	//maj Wagons
            	this.getTabWagons()[b.getPOS()].setContientBanditInterieur(false);
            	this.getTabWagons()[b.getPOS()].setContientBanditToit(true);
            	//maj POS bandit
            	b.setEtage(b.getEtage()+1);
            	return b.getNom()+" monte";
            }else {
                return b.getNom()+" est déjà sur le toit";
            }
        case BAS:
            if(b.getEtage()==1) {
            	//maj Wagons
            	this.getTabWagons()[b.getPOS()].setContientBanditInterieur(true);
            	this.getTabWagons()[b.getPOS()].setContientBanditToit(false);
            	//maj POS bandit
            	b.setEtage(b.getEtage()-1);
                return b.getNom()+" descend";
            }else {
                return b.getNom()+" est déjà à l'intérieur du wagon";
            }
        default: return "Erreur dans la fonction Train.deplacement : Direction Inconnue";
        }
	}

    //Getters
	public Wagon[] getTabWagons() {
		return tabWagons;
	}

	public void setTabWagons(Wagon[] tabWagons) {
		this.tabWagons = tabWagons;
	}
	
	//Main
	public static void main(String[] args) {
		Train t = new Train();
		System.out.println(t.deplacement(t.BANDIT_1, Direction.AVANT));
		System.out.println(t.deplacement(t.BANDIT_1, Direction.AVANT));
		System.out.println(t.deplacement(t.BANDIT_1, Direction.AVANT));
		System.out.println(t.deplacement(t.BANDIT_1, Direction.HAUT));
		System.out.println(t.deplacement(t.BANDIT_1, Direction.AVANT));
		System.out.println(t.deplacement(t.BANDIT_1, Direction.AVANT));
		System.out.println(t.deplacement(t.BANDIT_1, Direction.ARRIERE));
		System.out.println(t.deplacement(t.BANDIT_1, Direction.BAS));
	}
	
}
