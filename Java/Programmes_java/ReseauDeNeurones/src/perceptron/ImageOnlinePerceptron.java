package perceptron;
 
import image.ImageConverter;
import mnisttools.MnistReader;
 
public class ImageOnlinePerceptron {
 
	/* Les donnees */
	public static String path="../../IA/";
	public static String labelDB=path+"train-labels-idx1-ubyte";
	public static String imageDB=path+"train-images-idx3-ubyte";
 
	/* Parametres */
	// les N premiers exemples pour l'apprentissage
	public static final int N = 1000; 
	// les T derniers exemples  pour l'evaluation
	public static final int T = 500; 
	// Nombre d'epoque max
	public final static int EPOCHMAX=40;
	// Classe positive (le reste sera considere comme des ex. negatifs):
	public static int  classe = 5 ;
 
 

	public static int score(float[][] data, int[] refs, float[] w){
		int nbErr = 0;
		for (int i = 0; i < data.length; i++) {
			float prediction = OnlinePerceptron.scalaire(w,data[i]);
			if(prediction*refs[i] <=0){
				nbErr++;
			}
		}
		return nbErr;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.err.println("# Load the database !");
		/* Lecteur d'image */ 
		MnistReader db = new MnistReader(labelDB, imageDB);
		/* Taille des images et donc de l'espace de representation */
		final int SIZEW = ImageConverter.image2VecteurReel_withB(db.getImage(1)).length;
 
 
		/* Creation des donnees */
		System.err.println("# Build train for "+ classe);
		float[][] trainData = new float[N][SIZEW];
		int[] trainRefs = new int[N];
		int cptPos=0;
		int cpt=0;
		/* Donnees d'apprentissage */
		for (int i = 1; i <= N; i++) {
			cpt++;
			trainData[i-1]=ImageConverter.image2VecteurReel_withB(db.getImage(i));
			int label = db.getLabel(i);
			if (label == classe){
				trainRefs[i-1]=1;
				cptPos++;
			}else 
				trainRefs[i-1]=-1;
		}	
 
		System.err.println("Train set with "+cptPos*100.0/cpt+ " % positives /"+cpt);
 
		/* Donnees de test */
		System.err.println("# Build test for "+ classe);
		cptPos=0;
		cpt=0;
		final int TOTAL = db.getTotalImages();
		if (N+T >= TOTAL){
			System.out.println("N+T > Total");
			throw new RuntimeException();
		}
		float[][] testData = new float[T][SIZEW];
		int[] testRefs = new int[T];
		for (int i = 0; i < T; i++) {
			cpt++;
			testData[i]=ImageConverter.image2VecteurReel_withB(db.getImage(TOTAL-i));
			int label = db.getLabel(TOTAL-i);
			if (label == classe){
				testRefs[i]=1;
				cptPos++;
			}else 
				testRefs[i]=-1;
		}
		
		System.err.println("Test set with "+cptPos*100.0/cpt+ " % positives /"+cpt);
	        
		/* A vous de jouer ici !!! */ 
		int nbErr=1; cpt=0; float w[]= new float[785]; cptPos=0;
		for(int i=0; i<w.length; i++){
			w[i]=1;
		}
		while((0 < nbErr) && (cpt < EPOCHMAX)){
			nbErr= OnlinePerceptron.epoque(trainData, trainRefs,w);
			cpt++;
			System.out.println(score(testData, testRefs,w));
		}
		
		
		
		System.out.println("nbErr = "+ score(trainData, trainRefs,w));
	}
 
 
}