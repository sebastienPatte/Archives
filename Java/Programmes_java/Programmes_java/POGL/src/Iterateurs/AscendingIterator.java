package Iterateurs;
import java.util.Iterator;

public class AscendingIterator implements Iterator<Object>{
	private FixedCapacityList L;
	protected int indice;
	
	public AscendingIterator(FixedCapacityList L){
		this.L = L;
		this.indice = 0;
	}
	
	public boolean hasNext(){
		if(this.L.get(this.indice+1) == null){
			return false;
		}else{
			return true;
		}
	}
	
	public Object next(){
		Object res= this.L.get(this.indice+1);
		this.indice++;
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
		System.out.println("L[1] = "+L.get(9));
		AscendingIterator It = new AscendingIterator(L);
		
		while(It.hasNext()){
			System.out.println(It.next());
		}
	}
	
}
