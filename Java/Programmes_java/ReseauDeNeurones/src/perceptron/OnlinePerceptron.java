package perceptron;
 
import java.util.Arrays;
 
public class OnlinePerceptron  {
        public static final int DIM = 3; // dimension de l'espace de représentation
        public static float[] w = new float[DIM]; // paramètres du modèle
        public static float[][] data = { // les observations
          {1,0,0}, {1,0,1} , {1,1,0},
          {1,1,1}
        };
    public static int[] refs = {-1, -1, -1, 1}; // les réferences
 
    
    public static int epoque(float[][] data, int[] refs, float[] w){
		int nbErr = 0;
		for (int i = 0; i < data.length; i++) {
			float prediction = scalaire(w,data[i]);
			if(prediction*refs[i] <=0){
				nbErr++;
				for(int j=0; j<  w.length;j++){
					w[j]=w[j]+refs[i]*data[i][j];
				}
			}
		}
		return nbErr;
	}
    
    public static float scalaire(float[]x, float[]y){
    	float result=0;
    	for(int i=0; i< x.length; i++){
    		result+= x[i]*y[i]; 
    	}return result;
    }
    
    public static void main(String[] args) {
        // Exemple de boucle qui parcourt tous les exemples d'apprentissage
        // pour en afficher à chaque fois l'observation et la référence.
        int maxIt =20;
        int iteration=0;
        int nbErr=1;
        System.out.println("w= "+Arrays.toString(w));
        while((nbErr>0) & (iteration<maxIt)){
        	iteration++;
        	nbErr=epoque(data,refs,w);
        }
    	for (int i = 0; i < data.length; i++) {
            float[] x = data[i];
            System.out.println("x= "+Arrays.toString(x)+ " / y = "+refs[i]);
        }
    	System.out.println("w= "+Arrays.toString(w));
    	System.out.println("nbIteration = "+ iteration);
    }
 
}


