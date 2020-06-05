package image;  // mettre le nom du package approprie
 
import java.io.FileNotFoundException;
import java.io.IOException;
import caltech.CalTech101;

 
 
public class LoadImage {
	


	public static void main(String[] args) throws FileNotFoundException, IOException {
	functions.creationDossiers();
	//Initialisation	
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
		double[] tauxErr= new double [101];
		// charger toutes les donnees d'apprentissage (train)
		double[][] trainIm = new double[4100][784];
		double[][] trainImB = new double[4100][785];
		int[] trainRefs = new int[4100];
		double[] predictions= new double [101];
		int nbErr=0;
		int prediction=0;
		
		//TRAINING	
		System.out.println("Apprentissage\n");
		for(int cpt=0;cpt<20;cpt++){	//boucle de boucles Train
			float[] Tlabel= new float [101];	
				for(int i=0; i<4100; i++) {   	//boucle Train				
					
					trainRefs[i] = CT.getTrainLabel(i);
					trainIm[i] = CT.getTrainImage(i);
					
					// initialisation biais et le tableau de label Tlabel
					trainImB = functions.initBias(trainImB, trainIm, i);
					Tlabel = functions.initLabels(Tlabel,trainRefs[i]-1);
					
					// remplissage du tableau predictions
					for(int j=0; j<w.length;j++){
						predictions[j] = functions.prediction(w[j],trainImB[i]);

					}
					
					//softmax
					predictions=functions.softmax(predictions);
					
					//MAJ du poids et remplissage ErrClassT, BonneClassT et nbErr
					prediction=functions.maxT(predictions); //on prend la prediction la plus grande
					if(prediction!=trainRefs[i]-1){	 
						ErrClassT[prediction]++; 
						nbErr++;
						w=functions.majPoids(w, trainImB, Tlabel, predictions, i);
					}else{
						BonneClassT[prediction]++;
					}

					//reinitialisation du tableau predictions
					for(int cptPredict=0;cptPredict<predictions.length;cptPredict++){
						predictions[cptPredict]=0;
					}	
				}//fin boucle Train
					
					
			System.out.println("nbErr["+cpt+"] = "+nbErr);
			nbErr=0;
		}//fin boucle de boucles train
		
		//remplissage tauxErr (le taux d'erreur par classe)
		for(int cptErr=0;cptErr<ErrClass.length;cptErr++){
			if((BonneClassT[cptErr]+ErrClassT[cptErr])!=0){
				
				tauxErr[cptErr]=100*(ErrClassT[cptErr])/ (BonneClassT[cptErr]+ErrClassT[cptErr]);
			}else{
			
				tauxErr[cptErr]=0;
			}
		}
		//ecriture du taux d'erreur par classe dans un fichier dans projetIA/Err/ pour faire un graphe
		functions.ecrireFichierErr(tauxErr,"Train");
		
		
		// Début TEST
		System.out.println("\nTest\n");
		nbErr=0;
		double[][] testIm = new double [2000][784];
		int[] testRefs = new int [2000];
		prediction = 0;
		
		for (int cptTest=0; cptTest<testIm.length;cptTest++){
			//chargement des images et labels de test
			testIm[cptTest]=CT.getTestImage(cptTest);
			testRefs[cptTest]=CT.getTestLabel(cptTest);
			double[] predictionsTest= new double [101];
			
			// remplissage du tableau predictionsTest
			for(int cptTestClasse=0; cptTestClasse<w.length; cptTestClasse++){
				predictionsTest[cptTestClasse]= functions.prediction(w[cptTestClasse],testIm[cptTest]);
			}
			//softmax
			predictionsTest=(functions.softmax(predictionsTest));
			//on prend la plus grande prediction
			prediction=functions.maxT(predictionsTest)+1; //on ajoute 1 car maxT renvoie l'indice
						//du tableau (qui commence à 0) alors que le nb de classes commence à 1
			
			//remplissage ErrClass, BonneClass et nbErr
			if(prediction!=testRefs[cptTest]){
				ErrClass[prediction-1]++;
				nbErr++;
			}else{
				BonneClass[prediction-1]++;
			}
			
			
		}//fin boucle TEST
		
		//remplissage de tauxErr (le taux d'erreur par classe)
		for(int cptRes=0;cptRes<ErrClass.length;cptRes++){
			if((BonneClass[cptRes]+ErrClass[cptRes])!=0){
				tauxErr[cptRes]=100*(ErrClass[cptRes])/ (BonneClass[cptRes]+ErrClass[cptRes]);
			}else{
				tauxErr[cptRes]=0;
			}
		}
		
		// ecriture du taux d'erreur par classe dans un fichier dans ProjetIA/Err
		functions.ecrireFichierErr(tauxErr,"Test");
		
		System.out.println("nombre d'erreur Test = "+nbErr+"/2000");
		System.out.println("soit un taux de reussite de "+100*(2000-nbErr)/2000.0+" %");
		
		//ecriture de w[k] dans un fichier dans ProjetIA/w/
		for(int k=0; k< w.length; k++){
			functions.ecrireFichierW(w[k],k);
		}
		
	}
}
