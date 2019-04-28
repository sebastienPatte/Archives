package coltExpress;

public class Recompense {
	private int Bourse;
	private int Bijou;
	
	public Recompense(){
		this.Bourse = (int) Math.random() * 100;
		this.Bijou = (int) Math.random() * 200;
	}
	
	public int getBourse(){
		return this.Bourse;
	}
	
	public int getBijou(){
		return this.Bijou;
	}
}
