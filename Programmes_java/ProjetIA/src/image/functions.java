package image;

import java.io.FileWriter;
import java.io.File;

public class functions {
	//revoie le produit scalaire de deux tableaux de type double
	//la valeur renvoyée est un double
	public static double scalaire(double[]x, double[] w){
    	double result=0;
    	for(int i=0; i< x.length; i++){
    		result+= x[i]*w[i]; 
    	}return result;
    }

	
	//on parcours le tableau passé en paramètre en remplacant  
	//tmp par la valeur parcourue si elle est plus grande
	//revoie l'indice de la valeur la plus grande du tableau
	public static int maxT (double[] T){
		int res = 0;
		double tmp=0;
		for (int cpt=0; cpt<T.length; cpt++){
			if(tmp<=T[cpt]){
				tmp=T[cpt];
				res=cpt;
			}
		}return res;
	}

	// on fait le produit scalaire des deux tableaux en paramètre
	// et on le multiplie par 0.001 pour ne pas avoir de dépassement de variable
	// on revoie çe résulat à l'exponentielle, il sera ensuite classé dans un tableau 
	// predictions que l'on modifiera avec la fonction softmax pour avoir les prédictions
	public static double prediction(double[] w, double[] x){
		double prediction= scalaire(x,w)*0.001;
		prediction = Math.exp(prediction);
		return prediction;
	}
	
	//divise chaque élément du tableau (les prédictions) par la somme de tous les autres
	//puis renvoie le tableau predicts contenant les nouvelles valeurs
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
	
	//renvoie le minimum d'un tableau de type double
	public static double minT(double[] w){
		double res=0;
		
		for (int i=0; i<w.length-1; i++){  //length-1 pour ignorer le biais
			if(w[i]<res){
				res=w[i];
			}
		}return res;
	}
	
	// ajoute le biais en dernier élément de chaque image
	// et renvoie un tableau de type double avec les nouvelles valeurs
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
	
	//rempli le tableau de type double 'Tlabel' avec que des 0 sauf pour
	// l'indice Ref qui où on place un 1 pour indiquer que cet indice correspond
	//à la bonne classe. Puis renvoie Tlabel 
	public static float[] initLabels (float[] Tlabel,int Ref){
		for (int cptlabel=0; cptlabel<Tlabel.length; cptlabel++){
			Tlabel[cptlabel]=0;
		}Tlabel[Ref]=1;
		return Tlabel;
	}
	
	//met à jour le poids pour chaque élément de w avec la formule
	//détaillée dans le compte rendu et renvoie w
	public static double[][] majPoids (double [][]w,double[][] trainImB,float[] Tlabel,double[] predictions, int i){	
		for(int k=0; k<w.length;k++){
			for(int l=0; l<w[k].length;l++){
				w[k][l]= (w[k][l] + (0.8  * trainImB[i][l]* ( Tlabel[k] - predictions[k])));
			}
		}return w;
	}
	
	//prend un vecteur w de classe k en paramètre et modifie ses valeurs
	//pour qu'elles soient comprises entre 0 et 1
	//puis retourne le vecteur w
	public static double[] wPourImage (double[] w){
		double min=minT(w);
		for (int i=0; i<w.length-1; i++){
			w[i]=(w[i]-min);
		}return w;
	}
	
	//prend en paramètre un vecteur w et sa classe k
	//puis enregistre sa matrice (en 28x28 sans tenir compte du biais)
	//dans un fichier qui sera utilisé pour l'afficher en nuances de gris
	public static void ecrireFichierW(double[] w,int k){
			String str="";
			w=wPourImage(w);
			
	        final String chemin = "w/w"+(k+1)+".d";
	        int j=0;
	        final File fichier =new File(chemin); 
	        try {
	            // Creation du fichier
	            fichier .createNewFile();
	            // creation d'un writer 
	            final FileWriter writer = new FileWriter(fichier);
	            try {
	            	for(int i=0; i<w.length-1; i++){ //w.length-1 pour ignorer le biais
	            		str= (w[i])+" ";
	            		writer.write(str);
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
	
	// enregistre le taux d'erreur pour chaque classe dans un fichier dans /Err
	// qui sera utilisé pour faire un graphe
	public static void ecrireFichierErr(double[] tauxErr, String endFilename){
		final String chemin = "Err/Err"+endFilename+".d";
		String str="";
        final File fichier =new File(chemin); 
        try {
            // création du fichier
            fichier .createNewFile();
            // création du writer 
            final FileWriter writer = new FileWriter(fichier);
            try {
            	for (int k=0; k<tauxErr.length-1; k++){
            		str=(k+1)+" "+(tauxErr[k])+"\n";
            		writer.write(str);
        		}
            		
            } finally {
                //on ferme le fichier
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de creer le fichier");
        }
		
	}
	
	//créé les répertoires 'w', 'wImgs' et 'Err'
	public static void creationDossiers(){
		File w=new File("w");
		if (!(w.exists() && w.isDirectory())){ 
			w.mkdirs();
			System.out.println("creation dossier /w");
			
		}
		
		File Err=new File("Err");
		if (!(Err.exists() || Err.isDirectory())){ 
			Err.mkdirs();
			System.out.println("creation dossier /Err");
		}
		
		File wImgs=new File("wImgs");
		if (!(wImgs.exists() && wImgs.isDirectory())){ 
			wImgs.mkdirs();
			System.out.println("creation dossier /wImgs");
		}
	}
}


