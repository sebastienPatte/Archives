package src.main.java.model;
import java.util.HashSet;
import java.util.Set;


public class Arete
{

	private Sommet s1;
	private Sommet s2;
	public int poids;

	public Arete(Sommet s1,Sommet s2){
		this.s1 = s1;
		this.s2 = s2;
		this.poids = 0;
	}
	

}

