package Iterateurs;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.Iterable;

public class Iterateur {
	public static void main (String[] args){
		String temp = "";
		int print= 1;
		ArrayList<String> vent = new ArrayList<String>();
		vent.add("’             ,             ,   <<	-	>>.");
		vent.add("           ,             ,   ’           ,   ’    ,   ’   .");
		vent.add("          ,       ,               .");
		// Creation d’un iterateur
		Iterator<String> it = vent.iterator();
		// Tant qu’il reste des  ́el ́ements...
		while (it.hasNext()) {
			// prendre le prochain  ́el ́ement et l’afficher.
			temp = it.next();
			/*
				//afficher deux fois chaque element
				System.out.println(temp+"\n"+temp);
			*/
				//afficher un element sur deux
			if(print==1){
				System.out.println(temp);
			}
			print=1-print;
		}
	}
}
