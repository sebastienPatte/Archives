package coltExpress;

public class Marshall extends Personne{
	private Wagon[] tabWagons;
	
	Marshall (Wagon[] tabWagons){
		this.POS=0;
		this.tabWagons = tabWagons;
	}
	
	public boolean seDeplace() {
		return Math.random()<=Train.NERVOSITE_MARSHALL;
	}
	
	public Direction rdmDir () {
		//si le marshall est sur le premier wagon il va vers l'arriere
		if(this.POS <= 0) {
			return Direction.ARRIERE;
		}else { 
			//sinon si le marshall est sur le dernier wagon il va vers l'avant
			if(this.POS >= Train.NB_WAGONS-1){
				return Direction.AVANT;
			}else {
				//sinon on tire au hasard une direction AVANT ou ARRIERE (avec une proba de 0,5)
				if(Math.random()<0.5) {
					return Direction.AVANT;
				}else {
					return Direction.ARRIERE;
				}
			}
			
		}	
	}
	
	public String deplacement() {
		if(this.seDeplace()) {
			switch(this.rdmDir()) {
				case AVANT: 
					if(this.getPOS()>0) {
						this.tabWagons[this.getPOS()].setContientMarshall(false);
						this.setPOS(this.getPOS()-1);
						this.tabWagons[this.getPOS()].setContientMarshall(true);
						return "Le Marshall se deplace vers l'avant";
					}else {
						return "Le Marshall est déjà sur le premier Wagon";
						
					}
					
					
					
				case ARRIERE: 
					if(this.getPOS()<Train.NB_WAGONS-1) {
						this.tabWagons[this.getPOS()].setContientMarshall(false);
						this.setPOS(this.getPOS()+1);
						this.tabWagons[this.getPOS()].setContientMarshall(true);
						return "Le Marshall se deplace vers l'arrière";
					}else {
						return "Le Marshall est déjà sur le dernier Wagon";
					}
				default : return "Erreur deplacement Marshall, Direction non reconnue";
			}
		}else {
			return "Le Marshall ne se deplace pas";
		}
	}
}
