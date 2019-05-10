package coltExpress;

public class Marshall extends Personne{
	Marshall (){
		this.POS=0;
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
}
