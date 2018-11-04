package image;  // mettre le nom du package approprie
 
import java.io.FileNotFoundException;
import java.io.IOException;

//import perceptron.OnlinePerceptron;
import caltech.CalTech101;
 
 
public class LoadImage {
	
	public static int maxT (double[] T, int taille){
		int res = 0;
		double tmp=0;
		for (int cpt=0; cpt<taille; cpt++){
			if(tmp<=T[cpt]){
				tmp=T[cpt];
				res=cpt;
			}
		}return res;
	}

	public static double scalaire(double[]x, double[] w){
    	double result=0;
    	for(int i=0; i< x.length; i++){
    		result+= x[i]*w[i]; 
    	}return result;
    }
	
	public static int score(double[][] data, int[] refs, int prediction){
		int nbErr = 0;
		for (int i = 0; i < data.length; i++) {
			if(prediction*refs[i] <=0){
				nbErr++;
			}
		}
		return nbErr;
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
				w[i][j]=0.5;
			}
			
		}
		// charger toutes les donnees d'apprentissage (train)
		double[][] trainIm = new double[4100][784];
		double[][] trainImB = new double[4100][785];
		int[] trainRefs = new int[4100];
		double[] predictions= new double [101];
		for(int i=0; i<4100; i++) {   //i<4100
			trainRefs[i] = CT.getTrainLabel(i);
			trainIm[i] = CT.getTrainImage(i);
			
			//ajout du biais 
			for(int cptTrainIm=0; cptTrainIm <=784; cptTrainIm++){
				//biais à la fin
				if(cptTrainIm==784){
					trainImB[i][784]=1;
					
				}else{
				//recuperation de trainIm
				trainImB[i][cptTrainIm]=trainIm[i][cptTrainIm]; 
				}
			}
			
			//init Tlabel
			float[] Tlabel= new float [101];
			for (int cptlabel=0; cptlabel<Tlabel.length; cptlabel++){
				Tlabel[cptlabel]=0;
			}Tlabel[trainRefs[i]-1]=1;
			
			//apprentissage
			for(int j=0; j<w.length;j++){
				predictions[j]=prediction(w[j],trainImB[i]);
				//System.out.println("predictions["+j+"] = "+predictions[j]);
				
				
			}
			//softmax
			predictions=softmax(predictions);
			//test softmax
			double Stest=0;
			for(int test=0; test<predictions.length;test++){
				Stest+=predictions[test];
				//System.out.println("predictions["+test+"] = "+predictions[test]);
			}
			
			//MAJ du poids
			for(int k=0; k<w.length;k++){
				for(int l=0; l<w[k].length;l++){
					w[k][l]+=   (0.25  * trainImB[i][l]* ( Tlabel[k] - predictions[k]));
					
					//System.out.println("w["+ k +"]["+ l +"] =  "+w[k][l]);
				}
				//System.out.println(( predictions[k]));
				
			}
			System.out.println("prediction["+maxT(predictions,101)+"] = "+predictions[maxT(predictions,101)]);
			System.out.println("image "+i);
			System.out.println("SommePredicts = "+ Stest);
			//System.out.println(trainIm[i][i] + " "+ trainRefs[i]);
			//System.out.println("biais : trainImB["+ i +"][784] = "+trainImB[i][784]);
			
			//reinitialisation des parametres
			for(int cptPredict=0;cptPredict<predictions.length;cptPredict++){
				predictions[cptPredict]=0;
			}
			Stest=0;
			
		}//fin boucle Train
		//image2VecteurReel_withB(trainIm[0]);
		
		// Pour les donnees de test, utiliser  CT.getTestLabel  et  CT.getTestImage
		int nbErr=0;
		double[][] testIm = new double [2000][784];
		int[] testRefs = new int [2000];
		int prediction = 0;
		for (int cptTest=0; cptTest<testIm.length;cptTest++){
			//chargement des images et labels de test
			testIm[cptTest]=CT.getTestImage(cptTest);
			testRefs[cptTest]=CT.getTestLabel(cptTest);
			double[] predictionsTest= new double [101];
			//TEST
			for(int cptTestClasse=0; cptTestClasse<w.length; cptTestClasse++){
				predictionsTest[cptTestClasse]= prediction(w[cptTestClasse],testIm[cptTest]);
			}
			predictionsTest=(softmax(predictionsTest));
			prediction=maxT(predictionsTest,101)+1; //on ajoute 1 car maxT renvoie l'indice du tableau (qui commence à 0) alors que le nb de classes commence à 1
			System.out.println("prediction "+cptTest+" = "+prediction+" label = "+testRefs[cptTest]);
			if(prediction!=testRefs[cptTest])nbErr++;
			
		}
		System.out.println(nbErr);
		
		
	}
}