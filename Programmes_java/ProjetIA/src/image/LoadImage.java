package image;  // mettre le nom du package approprie
 
import java.io.FileNotFoundException;
import java.io.IOException;
import caltech.CalTech101;
 
public class LoadImage {
	
	public static float scalaire(float[]x, float[]y){
    	float result=0;
    	for(int i=0; i< x.length; i++){
    		result+= x[i]*y[i]; 
    	}return result;
    }
	
	public static int epoque(float[][] data, float[] w, int label, int DIM){
		int nbErr = 0;
		//vecteur labels
		int[] labels = new int [DIM];
		for (int i=0; i<labels.length; i++ ){
			labels[i]=0;
		}
		labels[label]=1;
		float[] predicts = new float [DIM];
		for (int i=0; i<predicts.length; i++ ){
			predicts[i]=0;
		}
		
		
		
		for (int j = 0; j < data.length; j++) {
			float prediction = scalaire(w,data[j]);
				
			/*
			if(prediction*labels[j] <=0){
					nbErr++;
					for(int k=0; k<  w.length;k++){
						w[k]=w[k]+labels[j]*data[j][k];
					}
				}
			*/
		}
		return nbErr;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Test load images");
                String path="/home/seb/Bureau/Git/Java/IA/"; //  indiquer le path correct
                String CaltechCheminFichier=path+"caltech101.mat";
		CalTech101 CT = new CalTech101(CaltechCheminFichier);
 
		// charge une image 
		double[] im = CT.getTrainImage(0); // charge l'image numero 0
		for(int i=0; i<28; i++) {          // affiche cette image, pour voir
			for(int j=0; j<28; j++) {
				System.out.print((int) im[i+j*28] + " ");
			}
			System.out.println();
		}
		int lab = CT.getTrainLabel(0);
		System.out.println("Label "+lab); // print le label de cette image la
 
 
		// charger toutes les donnees d'apprentissage (train)
		double[][] trainIm = new double[4100][0];
		int[] trainRefs = new int[4100];
		for(int i=0; i<4100; i++) {
			trainRefs[i] = CT.getTrainLabel(i);
			trainIm[i] = CT.getTrainImage(i);
		}
 
		//apprentissage
		float w[]= new float[785];
		for(int i=0; i<w.length; i++){
			w[i]=1;
		}
		
		// Pour les donnees de test, utiliser  CT.getTestLabel  et  CT.getTestImage
		
	}
}