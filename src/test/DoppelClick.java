package test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;


public class DoppelClick extends JFrame implements MouseListener {

	public DoppelClick() {
		super("DoppleClick");
		addMouseListener(this);
		setSize(200, 200);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new DoppelClick();
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int i = e.getClickCount();
		System.out.println(i);
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
