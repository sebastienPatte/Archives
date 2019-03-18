package src.main.java.model;
import java.util.HashSet;
import java.util.Set;




public class Sommet
{

	
	private String nom;
	private int value;
	private Arete[] aretesAdjacentes; 
	

	public Sommet(String nom, int value, Arete[] aretesAdjacentes) {
		this.nom=nom;
		this.value=value;
		this.aretesAdjacentes = aretesAdjacentes;
	}
	
	public int distance(Sommet parameter, Sommet parameter) {
		// TODO implement me
		return 0;
	}

	public Chemin routage(Sommet parameter, Sommet parameter) {
		// TODO implement me
		return null;
	}

}

