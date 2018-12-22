

import javax.swing.*;

import java.util.*;






@SuppressWarnings("serial")
public class JBrainTetris extends JTetris{

	private JCheckBox brainMode;
	private DefaultBrain brain;
	private JSlider slider;
	private Random rdm;
	private int currentCount;
	Brain.Move bestMove;
	
	// Constructeur
	 JBrainTetris(int pixels) {
		// appel du constructeur de JTetris
		super(pixels);
		//initialisation brain currentCount et rdm
		this.brain = new DefaultBrain();
		this.currentCount=0;
		this.rdm= new Random();
		
	}
	
	 
	 
	 @Override
	 /*
	  *  ajoute un bouton 'Brain active' de type 'JCheckBox'
	  *  et un Slider 'Adversaire :' de type 'JSlider'
	  */
	 public JComponent createControlPanel() {
		 
		 // appel createControlPanel de 'JTetris.java'
		 JComponent panel = super.createControlPanel();
		 
		 // Bouton Brain
		 panel.add(new JLabel("Brain:"));
		 brainMode = new JCheckBox("Brain active");
		 panel.add(brainMode);
		 // Slider Adversaire
		 panel.add(new JLabel("Adversaire:"));
		 slider = new JSlider();
		 slider.setMaximum(100);
		 slider.setMinimum(0);
		 panel.add(slider);
		 
		 return panel;
	 }
	 
	 @Override
	/*
	 * 
	 */
	 public void tick(int verb) {
		 
		  	
		if(brainMode.isSelected() && verb == DOWN)
		{
			/* si le nombre de pieces a changé depuis le dernier appel
			 * on remet a jour currentCount, on fait un undo() et on calcule
			 * le nouveau bestMove    
			 */
			if(currentCount!= super.count)
			{
				currentCount = super.count;
				board.undo();

				this.bestMove = brain.bestMove(super.board, super.currentPiece, HEIGHT);
			}
			
			
			if(this.bestMove != null)
			{
				/**
				 *  on tourne la pièce si la piece n'est pas dans le bon sens
				 *  OU on se déplace à droite si  bestMove est plus a droite
				 *  OU on se déplace à gauche si  bestMove est plus a gauche
				 */
				if(!this.currentPiece.equals(bestMove.piece)) {
					this.currentPiece=this.currentPiece.computeNextRotation();
				}else {
					if(bestMove.x > currentX) {
						currentX++;
					}else {
						if(bestMove.x < currentX) {
							currentX--;
						}
					}
				}
			}
		}
		// on appelle la methode tick de 'JTetris.java'
		super.tick(verb);
	 }

	
	 @Override
	 public Piece pickNextPiece() {
					
		 // si on est en BrainMode et que board est dans l'état commit et que rdm est plus petit que la valeur du Slider Adersaire 
		 if ((brainMode.isSelected())&&(board.isCommitted()) && rdm.nextInt(slider.getMaximum()) < slider.getValue()) {
			 
			 // l'adversaire choisit la pièce (c-a-d la pire piece)
			 int limitHeightSup = super.board.getHeight() - TOP_SPACE;
			 
			 Brain.Move moved = brain.bestMove(board, Piece.getPieces()[0], limitHeightSup) ;
			 Brain.Move temp;
			 //pour chaque piece on stocke son bestMove dans temp
			 // et moved prend la valeur du temp ayant le plus grand score (donc le pire score)
			 for(Piece p : Piece.getPieces()) {
				 temp= brain.bestMove(super.board, p, limitHeightSup);
				 if ((moved == null) || (temp != null && temp.score > moved.score)) {
					 moved = temp;
				 }
			 }
			 // si moved est null on revoie le resultat de la methode pickNextPiece de 'JTetris.java' (piece aléatoire)
			 if(moved == null) {
				 return super.pickNextPiece();
			 }else {
			 // sinon on renvoie la piece de moved (la pire piece possible)
				 return moved.piece;
				 
			 }
		 //sinon on revoie le resultat de la methode pickNextPiece de 'JTetris.java' (piece aléatoire)
		 }else{
			 return super.pickNextPiece();
		 }
	 }
	
	
	 public static void main(String[] args) {
		 try {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 } catch (Exception ignored) {
		 }
		 
		 // créé une instance de JBrainTetris 
		 JBrainTetris brainTetris = new JBrainTetris(16);
		 // créé une frame de JTetris avec l'instance de JBrainTetris 
		 JFrame brainFrame = JTetris.createFrame(brainTetris);
		 //rend la frame visile (invisible par défaut) 
		 brainFrame.setVisible(true);
	 }
	
}

