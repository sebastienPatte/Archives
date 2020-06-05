package alea;
 
import java.util.Random;

public class GenVA {
    static long seed = 1265;
 
    public static void main(String[] args) {
        // Initialiser la graine pour le générateur de nombres aléatoires
        Random RanNumb = new Random(seed);
        System.out.println(RanNumb);
        int[] tab = new int [6];
        String strTemp="";
        int NbNum = 1000;
        for(int i=0; i<NbNum; i++){
        	tab[(int) (1+RanNumb.nextDouble()*6)-1]++;
            
        }
        
        for(int j=0; j<tab.length; j++){
        	System.out.println("tab["+j+"] : "+tab[j]);
        	
        	for(int k=0; k<tab[j]/10;k++){
        			strTemp+="*";
        	}System.out.println(strTemp);
        	
        	strTemp="";
        }
        
    }
}
