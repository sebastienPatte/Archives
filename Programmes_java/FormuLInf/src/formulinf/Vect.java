package formulinf;

public class Vect {
    final int x, y;
    static final Vect ZERO = new Vect(0, 0);
    // Tan(pi/8), sert pour la normalisation
    private static final double tanpi8 = Math.sqrt(2.)-1.;

    
    // Constructeur
    Vect(int x, int y) {
    	// A modifier
        this.x = 0;
        this.y = 0;
    }
    
    // Addition
    Vect add(Vect v) {
    	// A completer
        return null;
    }
    
    // Soustraction
    Vect sub(Vect v) {
    	// A completer
        return null;
    }
    
    // Test d'egalite
    boolean egale(Vect other) {
    	// A completer
        return false;
    }

    // Renvoie le vecteur a coordonnees entieres de norme 1 dont la direction
    // est la plus proche de celle du vecteur, ou ZERO pour le vecteur nul.
    // (complet)
    Vect normalise() {
        int ax = (abs(this.x)>tanpi8*abs(this.y))?((this.x>0)?1:-1):0;
        int ay = (abs(this.y)>tanpi8*abs(this.x))?((this.y>0)?1:-1):0;
        return new Vect(ax, ay);
    }
    // Auxiliaire : valeur absolue d'un nombre
    private static double abs(int z) { return (z<0)?-z:z; }


    // Methode de comparaison, qui est utilisee par [assertEquals]
    // Elle fonctionnera quand vous aurez complete le code de [egale]
    // d'une maniere convenable. (complet)
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        return this.egale((Vect) other);
    }
    
}
