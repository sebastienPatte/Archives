package Iterateurs;

public class Block<T> {
	T contents;
	Block<T> nextBlock;
	
	public Block(T elt) {
        this.contents = elt;
        this.nextBlock = null;
    }
}
