package formulinf;
import java.util.List;
import java.util.LinkedList;

public class Bolide {
    Vect position;
    Vect vitesse = Vect.ZERO;
    
    // Constructeur
    Bolide(Vect p) {
    	// A completer
    	this.position = p;
    	
    }
    
    // Traduit la direction en une acceleration elementaire, en prenant le
    // vecteur de norme 1 approchant au mieux la direction.
    Vect calculeAcceleration(Vect cible) {
    	// A completer
    	return (cible.sub(this.position)).normalise();
    }
    
    // Applique une acceleration
    void accelereDe(Vect acceleration) {
    	// A completer
    	this.vitesse  = this.vitesse.add(acceleration);
    }

    // Combine les deux methodes precedentes (complet)
    void accelereVers(Vect cible) {
        this.accelereDe(this.calculeAcceleration(cible));
    }
    
    // Arrete le bolide
    void stop() {
        // A completer
    	this.vitesse = Vect.ZERO;
    }
    
    // Renvoie une liste de deplacements de norme 1, dont la somme
    // egale la vitesse.
    // Strategie : on part d'un vecteur cible egal a la vitesse, qu'on
    // normalise pour obtenir le premier deplacement, puis on retire ce
    // premier deplacement du vecteur cible et on normalise a nouveau
    // pour obtenir le deuxieme deplacement, etc.
    List<Vect> calculeDeplacements() {
    	// A completer
    	List<Vect> res = new LinkedList<>();
    	res.add(this.vitesse.normalise());
    	
    	res.add(this.vitesse.sub(res.get(0)).normalise());
    	res.add(this.vitesse.sub(res.get(1)).normalise());
    	res.add(this.vitesse.sub(res.get(2)).normalise());
    	res.add(this.vitesse.sub(res.get(3)).normalise());
    	
    	System.out.println(res.get(0).x+" "+res.get(0).y);
    	System.out.println(res.get(1).x+" "+res.get(1).y);
    	System.out.println(res.get(2).x+" "+res.get(2).y);
    	System.out.println(res.get(3).x+" "+res.get(3).y);
    	System.out.println(res.get(4).x+" "+res.get(4).y);
    	System.out.println();
    	Vect currentPos = Vect.ZERO;
    	Vect lastPos = Vect.ZERO;
    	while(this.position.add(res.get(i))) {
    		currentPos = lastPos.add(res.get(i));
    		lastPos = currentPos;
    	}
    	return res;
    }

}
