

import javax.swing.*;

import java.util.*;






public class JBrainTetris extends JTetris{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox brainMode;
	private DefaultBrain brain;
	private JSlider slider;
	private Random rdm;
	private int lastCount;
	
	// Constructeur
	 JBrainTetris(int pixels) {
		super(pixels);
		this.brain = new DefaultBrain();
		this.lastCount=-1;
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
			
			if ((verb == DOWN) && (brainMode.isSelected()) && (this.lastCount!=super.count) && (super.currentPiece != null)) {
				
				super.board.undo();
				lastCount=super.count;
				System.out.println(super.board.toString()+" "+super.currentPiece+" "+super.board.getHeight() );
				Brain.Move moved = brain.bestMove(super.board, super.currentPiece, super.board.getHeight() - TOP_SPACE);
				
				if(moved != null) {
					setCurrent(moved.piece, moved.x, moved.y );
				}else {
					verb= DROP;
				}
				
				
			}else {
			// on lance la methode tick de JTetris.java 
			super.tick(verb);}
	 }
	/*
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

	 */
	 public static void main(String[] args) {
		 try {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 } catch (Exception ignored) {
		 }
		 
		 JBrainTetris brainTetris = new JBrainTetris(32);
		 JFrame brainFrame = JTetris.createFrame(brainTetris);
		 brainFrame.setVisible(true);
	 }
	
}

