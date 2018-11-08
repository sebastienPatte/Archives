package image;


import java.io.FileWriter;
import java.io.File;

public class functions {
	
	public static double scalaire(double[]x, double[] w){
    	double result=0;
    	for(int i=0; i< x.length; i++){
    		result+= x[i]*w[i]; 
    	}return result;
    }

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

	public static double prediction(double[] w, double[] x){
		double prediction= scalaire(x,w)*0.001;
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
	
	public static void maxDecroissant(double[] tauxErreur){
		double[][] res= new double[101][101];
		double tmp=0;
		int tmpi =0;
		for(int cpt=0; cpt<res.length;cpt++){	
			for(int i=0; i<tauxErreur.length;i++){
					if(tmp<=tauxErreur[i]){
						tmp=tauxErreur[i];
						tmpi=i;
					}
			}tauxErreur[tmpi]=0;
//			if(tmp!=0)System.out.println("classe "+(tmpi+1)+" = "+tmp);
			tmp=0;
			tmpi=0;
		}
	}
	public static double[][] initBias(double[][] trainImB,double[][] trainIm,int i){
		//ajout du biais 
		for(int cptTrainIm=0; cptTrainIm <=784; cptTrainIm++){
			//biais à la fin
			if(cptTrainIm==784){
				trainImB[i][784]=1;
				
			}else{
			//recuperation de trainIm
			trainImB[i][cptTrainIm]=trainIm[i][cptTrainIm]; 
			}
		}return trainImB;
	}
	public static float[] initLabels (float[] Tlabel,int Ref){
		//init Tlabel
		
		for (int cptlabel=0; cptlabel<Tlabel.length; cptlabel++){
			Tlabel[cptlabel]=0;
		}Tlabel[Ref-1]=1;
		return Tlabel;
	}
	
	public static double[][] majPoids (double [][]w,double[][] trainImB,float[] Tlabel,double[] predictions, int i){	
		for(int k=0; k<w.length;k++){
			for(int l=0; l<w[k].length;l++){
				w[k][l]= (w[k][l] + (0.8  * trainImB[i][l]* ( Tlabel[k] - predictions[k])));
			}
		}return w;
	}
	
	public static void ecrireFichier(double[] w,int k){
			String str="";
	        final String chemin = "w"+k+".txt";
	        int j=0;
	        final File fichier =new File(chemin); 
	        try {
	            // Creation du fichier
	            fichier .createNewFile();
	            // creation d'un writer 
	            final FileWriter writer = new FileWriter(fichier);
	            try {
	            	for(int i=0; i<w.length-1; i++){ //w.length-1 pour enever le biais
	            		str= ((int)(Math.abs(w[i])%1.0))+",";
	            		writer.write(str);
	            			//System.out.println((int) (w[i]));
	            		j++;
	            		if(j==28){
	            			writer.write("\n");
	            			j=0;
	            		}
	            	}
	            		
	            } finally {
	                // quoiqu'il arrive, on ferme le fichier
	                writer.close();
	            }
	        } catch (Exception e) {
	            System.out.println("Impossible de creer le fichier");
	        }
	    }
	
	
}

