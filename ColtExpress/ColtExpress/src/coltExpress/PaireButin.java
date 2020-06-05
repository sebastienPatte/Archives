package coltExpress;

public class PaireButin {
	Butin left;
	int right;
	
	PaireButin(Butin left, int right){
		this.left = left;
		this.right = right;
	} 
	
	public Butin getLeft() {
		return this.left;
	}
	
	public void setLeft(Butin b) {
		this.left = b;
	}
	
	public int getRight() {
		return this.right;
	}
	
	public void setRight(int val) {
		this.right = val;
	}


	@Override
	public String toString() {
		return this.left.name()+" "+this.right;
	}

}