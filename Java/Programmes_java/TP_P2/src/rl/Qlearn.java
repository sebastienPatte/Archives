package rl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Qlearn {
	public double epsilon = 0.1; // parametre epsilon pour \epsilon-greedy
	public double alpha   = 0.2; // taux d'apprentissage
	public double gamma   = 0.9; // parametre gamma des eq. de Bellman/
	private long seed     = 1234;
	private Random rdm;
	
	// Suggestions
	public int actions[];
	public Hashtable< Tuple<Integer,Integer>, Double> q; 
	
	// Constructeurs
	public Qlearn(int[] actions) {
		this.rdm     = new Random(this.seed);
		this.actions = actions;
		this.q       = new Hashtable< Tuple<Integer,Integer>, Double>();
	}
	
	public Qlearn(int[] actions, double epsilon, double alpha, double gamma) {
		this.actions = actions;
		this.epsilon = epsilon;
		this.alpha   = alpha;
		this.gamma   = gamma;
		this.q       = new Hashtable< Tuple<Integer,Integer>, Double>();
	}

	// Calcule le nouveau Q(s,action) 
	// Paramètres : l'état s, l'action, s résultant du déplacement, récompense associée, méthode d'apprentissage
	// La méthode d'apprentissage est 1 pour Q Learning et 0 pour Sarsa
	public void learn(int state, int action,int stateNew,double stateNewRew,int LearnMethod) {
		
		if (state != -1) {
			
			Tuple<Integer,Integer> stateAction = new Tuple<Integer,Integer>(state,action);	
			Double QValue                      = this.q.get(stateAction);
			Double SelectedQValue              = -100.0; // on prend une valeur inférieure à la plus petite récompense possible
			
			// si la valeur de Q est nulle, on met la valeur 0.0 dans this.q
			// et Qvalue = 0.0
			if (QValue == null) {			
				this.q.put(stateAction, 0.0);
				QValue = 0.0;				
			}

			if (LearnMethod == 1){
				// méthode QLearning
			
					
			
				for (int actNew : this.actions){
					Tuple<Integer,Integer> stateActionNew = new Tuple<Integer,Integer>(stateNew,actNew);
					Double QValueNew = this.q.get(stateActionNew);
				
					if (QValueNew == null){
						this.q.put(stateActionNew, 0.0);
						QValueNew = 0.0;
					}
					
					SelectedQValue = Math.max(QValueNew, SelectedQValue);
					
				}
				

			}else{
				// méthode Sarsa
				Double QValueNew      = 0.0;
				
				
				
				if (this.rdm.nextDouble() < this.epsilon){
					
					int rdmAction                         = this.rdm.nextInt(8);
					Tuple<Integer,Integer> rdmStateAction = new Tuple<Integer,Integer>(stateNew,rdmAction);
					Double rdmQValue                      = this.q.get(rdmStateAction);
					
					if (rdmQValue == null){
						this.q.put(rdmStateAction, 0.0);
						rdmQValue = 0.0;
					}
					
					SelectedQValue = rdmQValue;
				}else{
					for(int actNew : this.actions) {
						Tuple<Integer,Integer> stateActionNew = new Tuple<Integer,Integer>(stateNew,actNew);
						QValueNew = this.q.get(stateActionNew);
						
						if(QValueNew == null) {
							this.q.put(stateActionNew, 0.0);
							QValueNew = 0.0;
						}
						
						SelectedQValue = Math.max(QValueNew, SelectedQValue);
					}
				}
				
				
			}
			QValue += this.alpha * (stateNewRew + this.gamma * SelectedQValue - QValue);
			this.q.replace(stateAction, QValue);
		}
	}
	
	
    // Choix d'une action à partir de l'état courant
	public int chooseAction(int state) {
		if(this.rdm.nextDouble() < this.epsilon)
			return this.rdm.nextInt(8);
		else {
			int bestAction = 0;
			for(int actNew : this.actions) {
				
				Tuple<Integer,Integer> stateAction     = new Tuple<Integer,Integer>(state,actNew);
				Tuple<Integer,Integer> bestStateAction =  new Tuple<Integer,Integer>(state,bestAction);
				Double QValue                          = this.q.get(stateAction);
				Double bestQ                           = this.q.get(bestStateAction);
				
				if(bestQ == null) {
					this.q.put(bestStateAction,0.0);
					bestQ = 0.0;
				}
				
				if(QValue == null) {
					this.q.put(stateAction, 0.0);
					QValue = 0.0;
				}
				
				if(bestQ < QValue){
					bestAction = actNew;
				}
			}
			return bestAction;
		}	
	}
}
