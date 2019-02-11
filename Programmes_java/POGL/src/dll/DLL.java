package dll;

import java.lang.Iterable;
import java.util.Iterator;
import java.util.function.Predicate;

public class DLL {
    public static void main(String[] args) {
         DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
         DoublyLinkedList<Integer> list2 = new DoublyLinkedList<>();
         //list1
         System.out.println("list");
         list.add(1);
         list.add(2);
         list.add(3);
         list.add(2);
         list.print();
         //list2
         System.out.println("list2");
         list2.add(5);
         list2.add(2);
         list2.add(2);
         list.print();
         System.out.println("list.extend(list2)");
         list.extend(list2);
         list.print();
//         list.swap();
         list.removeAll(2);
         list.print();
         list.add(3);
         list.print();
         list.removeAll(3);
         list.print();
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
    	assert checkInvariants() : "Err Init";
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
    	}System.out.println();
    }
    
    private int countElements() {
    	int res = 0;
    	for (T elt : this)res++;
    	return res;
    }
    
    private boolean checkInvariants() {
    	return this.size == this.countElements()
    			// verification (anchor -> null, autres val !null)
    			&& forAllBlocks(b -> {
    				if(b==anchor){
    					return b.elt==null;
    				}else {
    					return b.elt!=null;
    				}
    			})
    			// verification des nextBlock, prevBlock 
    			&& forAllBlocks(b->{
    				return b == b.nextBlock.prevBlock;
    			});
    			
    	
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
    
    //ajoute un élément en fin de liste
    public void add(T elt) {
    	Block temp            = this.anchor.prevBlock;
    	Block newBlock        = new Block(elt);
    	temp.nextBlock        = newBlock;
    	newBlock.nextBlock    = this.anchor;
    	newBlock.prevBlock    = temp;
    	temp.nextBlock        = newBlock;
    	this.anchor.prevBlock = newBlock;
    	this.size++;
    	assert checkInvariants() : "Err add";
    }
    

    
    
    // exemple de permutation qui ne marche pas
     public void swap() {
      Block pivot = this.anchor.nextBlock.nextBlock.nextBlock;
      this.anchor.nextBlock = this.anchor.nextBlock.nextBlock;
      this.anchor.nextBlock.nextBlock = this.anchor.nextBlock.prevBlock;
      this.anchor.nextBlock.nextBlock.nextBlock = pivot;
      assert checkInvariants() : "Err swap";
     }
     
     // supprime toutes les instances de elt     
     public void removeAll(T elt) {
    	 Block current = this.anchor;
    	 do {
    		 if (elt== current.elt)removeBlock(current);
    		 current = current.nextBlock;
    		 
    	 }while(current!=this.anchor);
     }
     
     // supprime le block b
     private void removeBlock (Block b) {
    	 b.prevBlock.nextBlock = b.nextBlock;
    	 b.nextBlock.prevBlock = b.prevBlock;
    	 this.size--;
    	 assert checkInvariants() : "Err removeBlock";
     }
     
     // ajoute la liste 'l' à la suite de la liste 'this'
     public void extend(DoublyLinkedList<T> l) {
    	 if (l.size!=0) {
    	 this.anchor.prevBlock = l.anchor.nextBlock;
    	 this.anchor.nextBlock = l.anchor.prevBlock;
    	 l.anchor.prevBlock.nextBlock = this.anchor;
    	 l.anchor.nextBlock.prevBlock = this.anchor;
    	 this.size+=l.size;
    	 assert checkInvariants() : "Err extend";
    	 }
    	 
     }
     
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
