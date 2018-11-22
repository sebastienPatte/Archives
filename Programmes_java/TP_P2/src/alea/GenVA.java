package alea;
 
import java.util.Random;

public class GenVA {
    static long seed = 1265;
 
    public static void main(String[] args) {
        // Initialiser la graine pour le générateur de nombres aléatoires
        Random RanNumb = new Random(seed);
        System.out.println(RanNumb);
        Double rdm=0.0;
        int[] tab = new int [6];
        String strTemp="";
        int NbNum = 100;
        for(int i=0; i<NbNum; i++){
            rdm=RanNumb.nextDouble();
            System.out.println(rdm);
        	//tab[(int) (1+RanNumb.nextDouble()*6)-1]++;
            
        }
        /*
        for(int j=0; j<tab.length; j++){
        	System.out.println("tab["+j+"] : "+tab[j]);
        	
        	for(int k=0; k<tab[j]/1000;k++){
        			strTemp+="*";
        	}System.out.println(strTemp);
        	strTemp="";
        }
        */
    }
}
