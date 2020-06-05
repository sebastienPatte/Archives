package vector;

public class vector {
	double x,y,z;
	vector(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	@Override
    public String toString(){
        return "(" + this.x + ";" + this.y + ";" + this.z + ")";
    }

	public static double norme (vector v){
		return Math.sqrt((v.x*v.x)+(v.y*v.y)+(v.z*v.z));
	}
	
	public static vector add (vector v1, vector v2){
		vector res = new vector(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);
		return res;
	}
	
	public static double scalaire(vector v1, vector v2){
		double res=(v1.x*v2.x)+ (v1.y*v2.y)+ (v1.z*v2.z);
		return res;
		
	}
	
	public static void main(String[] args){
		vector v1 = new vector(5,5,5);
		vector v2 = new vector(3,2,5);
		System.out.println("v1= "+ v1);
		System.out.println("norme v1= "+norme(v1));
		System.out.println("v1+v2="+add(v1,v2));
		System.out.println("sacalaire(v1,v2)= "+scalaire(v1,v2));
		
	}
}