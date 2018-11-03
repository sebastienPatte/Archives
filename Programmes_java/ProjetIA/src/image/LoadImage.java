package image;  // mettre le nom du package approprie
 
import java.io.FileNotFoundException;
import java.io.IOException;
import caltech.CalTech101;
 
 
public class LoadImage {
	
	

	public static double scalaire(double[]x, double[] w){
    	double result=0;
    	for(int i=0; i< x.length; i++){
    		result+= x[i]*w[i]; 
    	}return result;
    }
	

	/*
	public static float epoque(double[] data, double[] w, int label, int DIM){

		//vecteur labels
		int[] labels = new int [DIM];
		float predict = 0;
		for (int i=0; i<labels.length; i++ ){
			labels[i]=0;	
		}
		//labels[label]=1;
		
		
		
		
		
			predict = scalaire(w,data);
			return predict;
			
				
			/*
			if(prediction*labels[j] <=0){
					nbErr++;
					for(int k=0; k<  w.length;k++){
						w[k]=w[k]+labels[j]*data[j][k];
					}
				}
			*/
		//}
		
	public static double prediction(double[] w, double[] x){
		double prediction= scalaire(x,w);
		prediction = Math.exp(prediction);
		return prediction;
	}
	
	public static double[] softmax(double[] predicts){
		double sommePredicts=0;
		for(int i=0;i<predicts.length;i++){
			sommePredicts+=predicts[i];
		}
		for(int j=0; j<predicts.length; j++){
			predicts[j]/=sommePredicts;
		}
		return predicts;
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
 
 
		double w[][]= new double[101][785];
		for(int i=0; i<w.length; i++){
			for(int j=0; j<w[i].length;j++){
				w[i][j]=1;
			}
			
		}
		// charger toutes les donnees d'apprentissage (train)
		double[][] trainIm = new double[4100][(28*28)+1];
		int[] trainRefs = new int[4100];
		double[] predictions= new double [101];
		for(int i=0; i<2; i++) {   //i<4100
			trainRefs[i] = CT.getTrainLabel(i);
			trainIm[i] = CT.getTrainImage(i);
			
			//ajout du biais 
			
			trainIm[i][trainIm[i].length-1]=1;
			
			//init Tlabel
			float[] Tlabel= new float [101];
			for (int cptlabel=0; cptlabel<Tlabel.length; cptlabel++){
				Tlabel[cptlabel]=0;
			}Tlabel[trainRefs[i]]=1;
			
			//apprentissage
			for(int j=0; j<w.length;j++){
				predictions[j]=prediction(w[j],trainIm[i]);
				//System.out.println(predictions[j]);	
			}
			//softmax
			predictions=softmax(predictions);
			//test softmax
			double Stest=0;
			for(int test=0; test<predictions.length;test++){
				Stest+=predictions[test];
			}
			
			//MAJ du poids
			for(int k=0; k<w.length;k++){
				for(int l=0; l<w[k].length-1;l++){
					w[k][l]=  (w[k][l] + (0.7  * trainIm[i][l] * (Tlabel[k] - predictions[k])));
					System.out.println("w["+ k +"]["+ l +"] =  "+w[k][l]);
				}
				 
			}
			System.out.println("SommePredicts = "+ Stest);
			//System.out.println(trainIm[i][i] + " "+ trainRefs[i]);
			System.out.println(trainIm[0].length);
		}
		//image2VecteurReel_withB(trainIm[0]);
		
		
		// Pour les donnees de test, utiliser  CT.getTestLabel  et  CT.getTestImage
		
	}
}