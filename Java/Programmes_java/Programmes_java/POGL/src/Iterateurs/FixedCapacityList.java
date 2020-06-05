package Iterateurs;

public class FixedCapacityList implements List<Object>{
	private Object[] tab;
	private int taille;
	
	FixedCapacityList (int capacity){
		this.tab = new Object[capacity+1]; // car on part de l'indice 1
		this.taille = 0;
	}
	
	public int getTaille(){
		return this.taille;
	}
	
	public Object get(int i){
		if(this.taille >= i && i>0){
			return tab[i];
		}else{
			//System.out.println("Erreur l'indice '"+i+"' n'existe pas");
			return null;
		}
	}
	
	public void add(Object elt){
		if(this.taille+1 < this.tab.length){
			this.tab[this.taille+1]=elt;
			this.taille++;
		}else{
			System.out.println("Impossible d'ajouter l'element '"+elt+"' la capacite '"+this.tab.length+"' de la liste est atteinte");
			return;
		}
	}
	
	public static void main (String[] args){
		FixedCapacityList L = new FixedCapacityList(10);
		System.out.println("L[5] = "+L.get(5));
		L.add("position1");
		L.add("position2");
		L.add("position3");
		L.add("position4");
		L.add("position5");
		L.add("position6");
		L.add("position7");
		L.add("position8");
		L.add("position9");
		L.add("position10");
		L.add("position11");
		System.out.println("L[1] = "+L.get(1));
	}
}
