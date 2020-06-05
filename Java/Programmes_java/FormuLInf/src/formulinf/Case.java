package formulinf;
import java.awt.Color;

import ig.ZoneCliquable;

public class Case extends ZoneCliquable {
	// Indique si les voitures peuvent passer par cette case
    final boolean traversable;
    // Coordonnees
    final Vect coord;
    // Lien vers le circuit contenant la case
    final Circuit circuit;
    
    /**
     * Creation d'une case de coordonnées donnees, traversable ou non
     * @param c circuit dans lequel se trouve la case
     * @param p coordonnees de la case
     * @param b vrai si la case doit etre traversable
     */
    Case(Circuit c, Vect p, boolean b) {
        super("", 12, 12);
        this.traversable = b;
        this.coord = p;
        this.circuit = c;
        if (!this.traversable)
            this.setBackground(Color.BLACK);
    }
    
    /**
     * Reaction au clic gauche
     */
    public void clicGauche() {
        this.circuit.gereClic(this.coord);
    }
    
    /**
     * Absence de reaction au clic droit
     */
    public void clicDroit() {}
}
