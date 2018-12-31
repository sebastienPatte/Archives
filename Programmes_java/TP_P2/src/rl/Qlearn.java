package rl;

import java.util.ArrayList;
import java.util.Hashtable;

public class Qlearn {
	public double epsilon = 0.1; // parametre epsilon pour \epsilon-greedy
	public double alpha = 0.2; // taux d'apprentissage
	public double gamma = 0.9; // parametre gamma des eq. de Bellman/
	
	// Suggestions
	public int actions[];
	public Hashtable< Tuple<Integer,Integer>, Double> q;
	
	// Constructeurs
	public Qlearn(int[] actions) {
		this.actions = actions;
		q = new Hashtable< Tuple<Integer,Integer>, Double>();
	}
	
	public Qlearn(int[] actions, double epsilon, double alpha, double gamma) {
		this.actions = actions;
		this.epsilon = epsilon;
		this.alpha = alpha;
		this.gamma = gamma;
		q = new Hashtable< Tuple<Integer,Integer>, Double>();
	}
	
	public void learn(int State, int action) {
		this.q.put(alpha);
	}
	
	public void chooseAction() {
		
	}
}
