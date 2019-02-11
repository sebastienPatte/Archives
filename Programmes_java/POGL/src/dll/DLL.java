package dll;

import java.lang.Iterable;
import java.util.Iterator;
import java.util.function.Predicate;

public class DLL {
    public static void main(String[] args) {
         DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
//         list.add(1);
//         list.add(2);
         list.print();
//         list.swap();
//         list.print();
//         list.add(3);
//         list.print();
    }
}

class DoublyLinkedList<T> implements Iterable<T> {

    // À compléter : attributs
	private final Block anchor;
	private int size;
    /**
     * Constructeur d'une liste doublement chaînée vide
     *
     * Ce constructeur définit également l'ancre de la liste
     */ 
    public DoublyLinkedList() {
        // À compléter : code du constructeur
    	Block newBlock = new Block(null);
    	newBlock.nextBlock = newBlock;
    	newBlock.prevBlock = newBlock;
    	this.anchor        = newBlock;
    	this.size          = 0;
    }

    /**
     * Block (classe interne)
     *
     * Classe représentant les blocs de la liste doublement chaînée, ancre
     * comprise
     */
    class Block {
        // À compléter : attributs
    	private final T elt;
    	private Block prevBlock;
    	private Block nextBlock;
        /**
         * Constructeur d'un block
         *
         * @param e Élément à placer dans le bloc
         */
        public Block(T e) {
            // À compléter : code du constructeur
        	this.elt = e;
        }
    }

    public static void main(String[] args) {
        // À compléter : tests internes
    }

    // À compléter : méthodes
    
    public void print() {
    	for(T elt : this) {
    		System.out.println(elt);
    	}
    }
    
    private int countElements() {
    	int res = 0;
    	for (T elt : this)res++;
    	return res;
    }
    
    private boolean checkInvariants() {
    	return (this.size == this.countElements()); 
    }
    // verifie si tous les blocks verifient le predicat p
    private boolean forAllBlocks(Predicate<Block> p) {
    	Block current = this.anchor;
    	
    	do {
    		if(!p.test(current)) return false;
    		current = current.nextBlock;
    	}while(current != this.anchor);
    	return true;
    }
    
    // public void swap() {
    //  Block pivot = this.anchor.nextBlock.nextBlock.nextBlock;
    //  this.anchor.nextBlock = this.anchor.nextBlock.nextBlock;
    //  this.anchor.nextBlock.nextBlock = this.anchor.nextBlock.prevBlock;
    //  this.anchor.nextBlock.nextBlock.nextBlock = pivot;
    // }

    // Cadeau : un itérateur sur les éléments de la liste
    /**
     * Méthode de création d'un itérateur
     */
    public Iterator<T> iterator() { return new DLLIterator(); }

    /**
     * Itérateur séquentielle sur une liste doublement chaînée
     */
    class DLLIterator implements Iterator<T> {
        /** Le bloc courant est le prochain bloc qui sera considéré */
        private Block currentBlock;

        /** 
         * À l'initialisation, le bloc courant est celui situé juste après
         * l'ancre.
         */ 
        public DLLIterator() {
            this.currentBlock = anchor.nextBlock;
        }

        /**
         * Il existe un prochain élément tant que le bloc courant n'a pas
         * atteint l'ancre.
         */
        public boolean hasNext() {
            return this.currentBlock != anchor;
        }

        /**
         * L'élément sélectionné est celui du bloc courant, puis on fait
         * avancer le bloc courant en prévision de la prochaine étape.
         */
        public T next() {
            T elt = this.currentBlock.elt;
            this.currentBlock = this.currentBlock.nextBlock;
            return elt;
        }
    }
    
}
