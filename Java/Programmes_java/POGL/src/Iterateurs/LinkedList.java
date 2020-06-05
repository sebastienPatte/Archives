package Iterateurs;
import java.util.Iterator;
import java.lang.Iterable;

public class LinkedList<T> implements List<T>, Iterable<T>{
	Block<T> firstBlock;
	Block<T> lastBlock;
	
	public LinkedList () {
		this.firstBlock = null;
		this.lastBlock = null;
	}
	
	public T get (int i) {
		Block<T> current= this.firstBlock;
		while(i!=0) {
			current = current.nextBlock;
		}
		return current.contents;
	}
	
	public void add (T elt) {
		Block<T> newBlock = new Block<T>(elt);
		if (this.firstBlock == null) {
			this.firstBlock = newBlock;
		}else {
			this.lastBlock.nextBlock = newBlock;
		}
		this.lastBlock = newBlock;
	}
	
    public Iterator<T> iterator() {
        return new LinkedListIterator<T>(this.firstBlock);
    }
    
}
