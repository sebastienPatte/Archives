package TP3;

import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TP3 {
	
	public static HashMap<Character, Character> createKey(int n, String alphabet){
		
		
		HashMap<Character, Character> H = new HashMap<Character, Character>();
		String[] newAlphabet = alphabet.split("");
		char c1,c2;
		for (int i=0; i < alphabet.length(); i++){
			System.out.println(newAlphabet[i]);
			if(i+n >= alphabet.length()){
				c1 = newAlphabet[i].charAt(0);
				c2 = newAlphabet[i+n-alphabet.length()].charAt(0);
			}else{
				c1 = newAlphabet[i].charAt(0);
				c2 = newAlphabet[i+n].charAt(0);
			}
			H.put(c1, c2);
			
		}
		return H;
	}

	public static String encode(String text, HashMap<Character, Character> key){
		String[] newText = text.split("");
		String result="";
		char c;
		for(int i=0; i<newText.length;i++){
			c=newText[i].charAt(0);
			result+= key.get(c);
		}
		return result;
	}
	
	public static ArrayList<String> readLines(String filename) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(filename), Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture de " + filename);
			System.exit(1);
		}
		return (ArrayList<String>) lines;
	}

	public static HashMap<String, Double> computeWordProbability(String filename){
		ArrayList<String> Lines = readLines(filename);
		HashMap<String, String> H = new HashMap<String, String>();
		String[] newLine;
		for(String line:Lines){
			newLine=line.split("	");
			
			
		}
		
	}
	
	public static void main(String[] args) {
		HashMap<Character, Character> key= createKey(5,"abcdefghijklmnopqrstuvwxyz");
		System.out.println(key);
		String text_encode = encode("test",key);
		System.out.println(text_encode);
		ArrayList<String> Lines = readLines("count_1w.txt");
		//System.out.println(Lines);
		
		
	}
}