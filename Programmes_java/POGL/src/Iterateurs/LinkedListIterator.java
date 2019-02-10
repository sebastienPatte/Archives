package Iterateurs;
import java.util.Iterator;


class LinkedListIterator<T> implements Iterator<T> {
    private Block<T> current;


    public LinkedListIterator(Block<T> block) {
        this.current = block;
    }

    public boolean hasNext() {
        return current != null;
    }

    public T next() {
        T elt = current.contents;
        this.current = this.current.nextBlock;
        return elt;
    }
    
    
}
