package image;  // mettre le nom du package approprie
 
import java.io.FileNotFoundException;
import java.io.IOException;
import caltech.CalTech101;

 
 
public class LoadImage {
	


	public static void main(String[] args) throws FileNotFoundException, IOException {

	//INIT	
		System.out.println("Test load images");
                String path="/home/seb/Bureau/Git/Java/IA/"; //  indiquer le path correct
                String CaltechCheminFichier=path+"caltech101.mat";
		CalTech101 CT = new CalTech101(CaltechCheminFichier);
 
 
		double w[][]= new double[101][785];
		for(int i=0; i<w.length; i++){
			for(int j=0; j<w[i].length;j++){
				w[i][j]=0.5;
			}
			
		}
		
		double[] ErrClass= new double [101];
		double[] BonneClass = new double [101];
		double[] ErrClassT= new double [101];
		double[] BonneClassT = new double [101];
		
		// charger toutes les donnees d'apprentissage (train)
		double[][] trainIm = new double[4100][784];
		double[][] trainImB = new double[4100][785];
		int[] trainRefs = new int[4100];
		double[] predictions= new double [101];
		int nbErr=0;		
		
		//TRAINING	
		for(int cpt=0;cpt<20;cpt++){
			
			float[] Tlabel= new float [101];
				
				for(int i=0; i<4100; i++) {   //i<4100					

					trainRefs[i] = CT.getTrainLabel(i);
					trainIm[i] = CT.getTrainImage(i);
					//apprentissage
					trainImB = functions.initBias(trainImB, trainIm, i);
					Tlabel = functions.initLabels(Tlabel,trainRefs[i]);
					for(int j=0; j<w.length;j++){
						predictions[j] = functions.prediction(w[j],trainImB[i]);

					}
					//softmax
					predictions=functions.softmax(predictions);
					
					//MAJ du poids
					int prediction=functions.maxT(predictions,101);
					if(prediction!=trainRefs[i]-1){	
						ErrClassT[prediction]++;
						nbErr++;
						w=functions.majPoids(w, trainImB, Tlabel, predictions, i);
					}else{
						BonneClassT[prediction]++;
					}

					//reinitialisation des parametres
					for(int cptPredict=0;cptPredict<predictions.length;cptPredict++){
						predictions[cptPredict]=0;
					}	
				}//fin boucle Train
					
					
			if(cpt!=0){
				System.out.println("nbErr["+cpt+"] = "+nbErr/cpt);	
			}else{
				System.out.println("nbErr["+cpt+"] = "+nbErr);
			}
		}//fin boucle de boucles train
		
		//affichage Err
		double[] tauxErrT= new double [101];
		for(int cptRes=0;cptRes<ErrClass.length;cptRes++){
			//System.out.println("Erreurs classe "+cptRes+" = "+ErrClass[cptRes]);
			//System.out.println("Bonne classe "+cptRes+" = "+BonneClass[cptRes]);
			if((BonneClassT[cptRes]+ErrClassT[cptRes])!=0){
				System.out.println("taux d'Erreur classe "+(cptRes+1)+" = "+100*(ErrClassT[cptRes])/ (BonneClassT[cptRes]+ErrClassT[cptRes])+"%  "+(BonneClassT[cptRes]+ErrClassT[cptRes])+" image(s)");
				tauxErrT[cptRes]=100*(ErrClass[cptRes])/ (BonneClassT[cptRes]+ErrClassT[cptRes]);
			}else{
				System.out.println("taux d'Erreur classe "+(cptRes+1)+" =  0 car aucune image n'est de cette classe");
				tauxErrT[cptRes]=0;
			}
		}
		functions.maxDecroissant(tauxErrT);
	
		
		// Pour les donnees de test, utiliser  CT.getTestLabel  et  CT.getTestImage
		System.out.println("nbErr "+nbErr);
		nbErr=0;
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
				predictionsTest[cptTestClasse]= functions.prediction(w[cptTestClasse],testIm[cptTest]);
			}
			predictionsTest=(functions.softmax(predictionsTest));
			prediction=functions.maxT(predictionsTest,101)+1; //on ajoute 1 car maxT renvoie l'indice du tableau (qui commence à 0) alors que le nb de classes commence à 1
//			System.out.println("prediction "+cptTest+" = "+prediction+" label = "+testRefs[cptTest]);
			if(prediction!=testRefs[cptTest]){
				ErrClass[prediction-1]++;
				nbErr++;
			}else{
				BonneClass[prediction-1]++;
			}
			
			
		}//fin boucle TEST
		double[] tauxErr= new double [101];
		for(int cptRes=0;cptRes<ErrClass.length;cptRes++){
			//System.out.println("Erreurs classe "+cptRes+" = "+ErrClass[cptRes]);
			//System.out.println("Bonne classe "+cptRes+" = "+BonneClass[cptRes]);
			if((BonneClass[cptRes]+ErrClass[cptRes])!=0){
//				System.out.println("taux d'Erreur classe "+(cptRes+1)+" = "+100*(ErrClass[cptRes])/ (BonneClass[cptRes]+ErrClass[cptRes])+"%       "+(BonneClass[cptRes]+ErrClass[cptRes])+"   images");
				tauxErr[cptRes]=100*(ErrClass[cptRes])/ (BonneClass[cptRes]+ErrClass[cptRes]);
			}else{
				//System.out.println("taux d'Erreur classe "+(cptRes+1)+" =  0 car aucune image n'est de cette classe");
				tauxErr[cptRes]=0;
			}
		}
		functions.maxDecroissant(tauxErr);
		System.out.println(nbErr);
		
		functions.ecrireFichier(w[0],1);
		
	}
}
