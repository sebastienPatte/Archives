package Iterateurs;
import java.util.Iterator;

public class DescendingIterator implements Iterator<Object>{
	private FixedCapacityList L;
	protected int indice;
	
	public DescendingIterator(FixedCapacityList L){
		this.L = L;
		this.indice = L.getTaille()+1; // car on part de l'indice 1
		System.out.println(L.getTaille());
	}
	
	public boolean hasNext(){
		if(this.L.get(this.indice-1) == null){
			return false;
		}else{
			return true;
		}
	}
	
	public Object next(){
		Object res= this.L.get(this.indice-1);
		this.indice--;
		return res;
	}
	
	public static void main(String[] args){
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
		DescendingIterator It = new DescendingIterator(L);
		
		while(It.hasNext()){
			System.out.println(It.next());
		}
	}
	
}