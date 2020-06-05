package gui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyboardShortcut implements KeyListener{
	private VueCommandes c;
    
    public KeyboardShortcut(VueCommandes c) {
    	this.c = c;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'a') {
        	c.boutonAction.doClick();
        }
        if(e.getKeyChar() == 'v') {
        	c.boutonValider.doClick();
        }
        if(e.getKeyChar() == 'c') {
        	c.boutonCorriger.doClick();
        }
        if(e.getKeyChar() == 'b') {
        	c.boutonBraquage.doClick();
        }
        if(e.getKeyChar() == 't') {
        	c.boutonTire.doClick();
        }
        
        
    }

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
        switch( keyCode ) { 
            case 38:
            	c.boutonHaut.doClick();
                break;
            case 40:
                c.boutonBas.doClick();
                break;
            case 37:
                c.boutonAvant.doClick();
                break;
            case 39:
                c.boutonArriere.doClick();
                break;
         }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
