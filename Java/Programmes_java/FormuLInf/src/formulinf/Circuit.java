package formulinf;
import java.util.List;
import java.util.LinkedList;
import java.awt.Color;
import ig.Grille;

public class Circuit extends Grille {
    int nbLignes, nbColonnes;
    Case[][] terrain;
    Bolide bolide;
    List<Vect> historique;

    // Construit un circuit a partir de dimensions, d'une carte, et
    // de coordonnees de départ.
    Circuit(int nbL, int nbC, boolean[][] carte, int lD, int cD) {
    	// Initialisation de la partie ig
        super(nbL, nbC);
        // Suite a completer
    }

    // Applique les deplacements indiques, dans la mesure du possible
    boolean deplaceBolide(List<Vect> deplacements) {
    	// A completer
        return false;
    }
    
    // Calcule et effectue le deplacement
    void gereClic(Vect cible) {
        // A completer
    }
    
    // Renvoie la case aux coordonnées fournies (complet)
    Case getCase(Vect p) {
        return terrain[p.x][p.y];
    }
}
