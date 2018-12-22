

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
		super(pixels);
		this.brain = new DefaultBrain();
		this.currentCount=0;
		this.rdm= new Random();
		
	}
	
	 @Override
	 public JComponent createControlPanel() {
		 JComponent panel = super.createControlPanel();
		 
		 panel.add(new JLabel("Brain:"));
		 brainMode = new JCheckBox("Brain active");
		 panel.add(brainMode);
		 
		 panel.add(new JLabel("Adversaire:"));
		 slider = new JSlider();
		 slider.setMaximum(100);
		 slider.setMinimum(0);
		 panel.add(slider);
		 
		 return panel;
	 }
	 @Override
		public void tick(int verb) {
		 
			
		 if(brainMode.isSelected() && verb == DOWN)
			{
		
				if(currentCount!= count)
				{
					currentCount = count;
					board.undo();


					
					this.bestMove = brain.bestMove(super.board, super.currentPiece, HEIGHT);
					


				}

				if(this.bestMove != null)
				{

					
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
			super.tick(verb);
			
	 }

	
	 @Override
	 public Piece pickNextPiece() {
					
		 rdm.nextInt(100);
		 if ((brainMode.isSelected())&&(board.committed) && rdm.nextInt(slider.getMaximum()) < slider.getValue()) {
			 
			 int limitHeightSup = super.board.getHeight() - TOP_SPACE;
			 
			 Brain.Move moved = brain.bestMove(board, Piece.getPieces()[0], limitHeightSup) ;
			 Brain.Move temp;
			 for(Piece p : Piece.getPieces()) {
				 temp= brain.bestMove(super.board, p, limitHeightSup);
				 if ((moved == null) || (temp != null && temp.score > moved.score)) {
					 moved = temp;
				 }
			 }
			 
			 if(moved == null) {
				 return super.pickNextPiece();
			 }else {
				 return moved.piece;
				 
			 }
		 }else{
			 return super.pickNextPiece();
		 }
	 }
	
	
	 public static void main(String[] args) {
		 try {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 } catch (Exception ignored) {
		 }
		 
		 JBrainTetris brainTetris = new JBrainTetris(16);
		 JFrame brainFrame = JTetris.createFrame(brainTetris);
		 brainFrame.setVisible(true);
	 }
	
}

