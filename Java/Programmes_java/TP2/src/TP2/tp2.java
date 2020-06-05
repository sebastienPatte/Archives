package TP2;

public class tp2 {
	
	public static void testCheckIP() {
		String[] validIp = {"127.0.0.1",
		"127.231.1.1",
		"1.2.3.4"};
		
		String[] invalidIp = {"12.2.3",
		"12.3.213.123.123",
		"1231.12.2.3",
		".1.2.3",
		"1.2.3.",
		"1.2.3.4.",
		"1.2..3",
		"0.1.2.3"};
		
		for (String ip : validIp) {
			if (!check(ip)) {
				System.out.println("erreur: " + ip);
			}
		}
		
		for (String ip : invalidIp) {
			if (check(ip)) {
				System.out.println("erreur: " + ip);
			}
		}
	}

	public static boolean isInteger(String s) {
		try {
		Integer.parseInt(s);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	public static boolean check(String ip){
		int nb=0;
		int compteur=0;
		String separateur = "\\.";
		String[] chiffre = ip.split(separateur,4);
		for(String c : chiffre){
			if(isInteger(c)){
				nb=Integer.parseInt(c);
				
				if(compteur==0){
					if(!((nb>0)&(nb<=255))){
						return false;
					}
				}else{
					if(!((nb>=0)&(nb<=255))){
						return false;
					}
				}compteur++;
			}
		}if((compteur<4) ||(compteur>4)){
			return false;
		}return true;
		
		
	}
	
	public static String removeSpaces(String texte){
		String[] newTexte =texte.split(" ");		
		String result="";
		for(String t : newTexte){
			result+=t;
		}return result;
	}
	
	public static String inverse(String texte){
		String[] newTexte =texte.split("");
		String result="";
		for(int i=texte.length()-1; i>=0; i-- ){
			if(newTexte[i]!=" "){
				result+=newTexte[i];
			}
		}
		return result; 
	}
	
	public static String expandNumber(String input){
		String chara="";
		String result="";
		String nombre="";
		String[] newTexte= input.split("");
		int nb=0;
		
		
		for(int i=0; i< input.length();i++){
			if(isInteger(newTexte[i])){
				if(i!=0){
					chara=newTexte[i-1];
					nombre+=newTexte[i];
					
//					while((i+1<input.length()) & (isInteger(newTexte[i+1]))){
//						i++;
//						nombre+=newTexte[i];
//					}
					
					nb=Integer.parseInt(nombre);
					for(int j=0;j<nb-1;j++){
						result+=chara;
					}
					nombre="";
				}
			}else{
				result+=newTexte[i];
			}
		}return result;
	}
	
	public static void main(String[] args) {
		testCheckIP();
		System.out.println(removeSpaces("hello world"));
		System.out.println(inverse("hello world"));
		System.out.println(expandNumber("hel2o world tt12"));
	}
}
