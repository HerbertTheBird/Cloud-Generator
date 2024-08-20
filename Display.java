import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Display extends JPanel implements KeyListener{
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Main.paint((Graphics2D)g);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == ' ') {
			Main.createCloud();
			repaint();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
