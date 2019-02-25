package formulinf;
import java.util.List;
import java.util.LinkedList;

public class Bolide {
    Vect position;
    Vect vitesse = Vect.ZERO;
    
    // Constructeur
    Bolide(Vect p) {
    	// A completer
    }
    
    // Traduit la direction en une acceleration elementaire, en prenant le
    // vecteur de norme 1 approchant au mieux la direction.
    Vect calculeAcceleration(Vect cible) {
    	// A completer
        return null;
    }
    
    // Applique une acceleration
    void accelereDe(Vect acceleration) {
    	// A completer
    }

    // Combine les deux methodes precedentes (complet)
    void accelereVers(Vect cible) {
        this.accelereDe(this.calculeAcceleration(cible));
    }
    
    // Arrete le bolide
    void stop() {
        // A completer
    }
    
    // Renvoie une liste de deplacements de norme 1, dont la somme
    // egale la vitesse.
    // Strategie : on part d'un vecteur cible egal a la vitesse, qu'on
    // normalise pour obtenir le premier deplacement, puis on retire ce
    // premier deplacement du vecteur cible et on normalise a nouveau
    // pour obtenir le deuxieme deplacement, etc.
    List<Vect> calculeDeplacements() {
    	// A completer
    	return null;
    }

}
