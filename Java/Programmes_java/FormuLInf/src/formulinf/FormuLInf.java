package formulinf;
import ig.Fenetre;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FormuLInf {
	/**
	 * Fonction principale, qui initialise le jeu.
	 * @param args tableau d'arguments, dans lequel on attend seulement le nom du fichier contenant le circuit
	 * @throws IOException
	 */
    public static void main(String[] args) throws IOException {
        Fenetre f = new Fenetre("FormuL∞");
        Circuit t = decodeCircuit(args[0]);
        f.ajouteElement(t);
        f.dessineFenetre();
    }
    
    /**
     * Fonction lisant le descriptif du circuit pour construire l'objet circuit qui sera au centre du jeu.
     * @param fichier nom du fichier contenant une description du circuit
     * @return le circuit, dans le format interne
     * @throws IOException
     */
    private static Circuit decodeCircuit(String fichier) throws IOException {
    	// Initialisation de la lecture du fichier
        BufferedReader lecteur = new BufferedReader(new FileReader(fichier));
        // Les deux premieres lignes doivent donner les dimensions du circuit
        int nbLignes = Integer.parseInt(lecteur.readLine());
        int nbColonnes = Integer.parseInt(lecteur.readLine());
        // Creation du tableau servant de support au circuit
        boolean[][] carte = new boolean[nbLignes][nbColonnes];
        int ligneDepart=0, colonneDepart=0;
        for (int l=0; l<nbLignes; l++) {
        	// Chaque ligne suivante code une ligne du tableau
            String ligne = lecteur.readLine();
            for (int c=0; c<nbColonnes; c++) {
            	// On memorise au passage la position de depart quand on voit le symbole '@'
                if (ligne.charAt(c) == '@') {
                    ligneDepart = l;
                    colonneDepart = c;
                }
                carte[l][c] = ligne.charAt(c) != '#';
            }
        }
        // Fin de lecture
        lecteur.close();
        // Construction de l'objet circuit avec les informations lues
        return new Circuit(nbLignes, nbColonnes, carte, ligneDepart, colonneDepart);
    }
}
