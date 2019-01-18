package grapheneditor.dialoge;

import grapheneditor.GraphenModell;
import grapheneditor.KantenAnsicht;
import grapheneditor.KnotenAnsicht;
import grapheneditor.KnotenElement;
import grapheneditor.darstellung.figuren.Ellipse;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;


public class graphErzeugen extends JDialog implements ActionListener{
	
	private JSpinner knotenSpinner;
	private JSpinner kantenSpinner;
	private JButton ok;
	private JButton cancel;
	private SpinnerNumberModel knotenModel;
	private SpinnerNumberModel kantenModel;
	private GraphenModell modell;
	private Dimension dim;
	private SpinnerNumberModel breitenModel;
	private JSpinner breitenSpinner;
	
	public graphErzeugen(GraphenModell modell, Dimension dim){
		this.modell = modell;
		this.dim = dim;
		init();
		initComponents();
		this.setVisible(true);
	}
	
	private void init(){
		this.setSize(new Dimension(250,150));
		this.setTitle("Zufallsgraph erzeugen");
		this.setModal(true);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int posx = screenSize.width/2 - 125;
        int posy = screenSize.height/2 - 75;
        this.setLocation(posx, posy);
	}
	
	private void initComponents(){
		erzeugeSpinner();
		erzeugeButtons();
		
		
	}
	
	private void erzeugeSpinner(){
		breitenModel = new SpinnerNumberModel(25,4,60,1);
		breitenSpinner = new JSpinner(breitenModel);
		breitenSpinner.setPreferredSize(new Dimension(60,10));
		knotenModel = new SpinnerNumberModel(10,1,null,1);
		knotenSpinner = new JSpinner(knotenModel);
		knotenSpinner.setPreferredSize(new Dimension(60,10));
		kantenModel = new SpinnerNumberModel(10,1,null,1);
		kantenSpinner = new JSpinner(kantenModel);
		kantenSpinner.setPreferredSize(new Dimension(60,10));
		Panel p = new Panel();
		p.setLayout(new SpringLayout());
		p.add(new JLabel("Knotengröße"));
		p.add(breitenSpinner);
		p.add(new JLabel("Knoten"));
		p.add(knotenSpinner);
		p.add(new JLabel("Kanten"));
		p.add(kantenSpinner);
//		SpringUtilities.makeCompactGrid(p,3,2,40,10,15,10);
		this.getContentPane().add("North", p);
	}
	
	private void erzeugeButtons() {
		ok = new JButton("Erzeuge Graph");
		ok.addActionListener(this);
		cancel = new JButton("Abbrechen");
		cancel.addActionListener(this);
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		p.add(ok);
		p.add(cancel);
		this.getContentPane().add("South",p);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ok)){
			int knotenAnzahl = knotenModel.getNumber().intValue();
			int kantenanzahl = kantenModel.getNumber().intValue();
			int knotenbreite = breitenModel.getNumber().intValue();
			System.out.println("Knoten: " + knotenAnzahl);
			System.out.println("Kanten: " + kantenanzahl);
			modell.leeren();
			System.out.println("Breite = " + dim.width);
			System.out.println("Hoehe = " + dim.height);
			KnotenAnsicht[] tmp = new KnotenAnsicht[knotenAnzahl];
			for(int i = 0;i<knotenAnzahl;i++){
				double xpos = knotenbreite + Math.random()*(dim.width - knotenbreite*2);
				double ypos = knotenbreite + Math.random()*(dim.height - knotenbreite*2);
				Ellipse form = new Ellipse(xpos,ypos,knotenbreite,knotenbreite);
				String inhalt = "K" + i;
				KnotenAnsicht k = new KnotenAnsicht(new KnotenElement("randomNode "+i),form);
				modell.hinzufügen(k);
				tmp[i] = k;
			}
			for(int i = 0; i<kantenanzahl;i++){
				int a = (int)(Math.random()*(tmp.length-1)+0.5);
				int b = (int)(Math.random()*(tmp.length-1)+0.5);
				KantenAnsicht k = new KantenAnsicht(tmp[a],tmp[b]);
				modell.hinzufügen(k);
			}
//			this.getParent().repaint();
			this.dispose();
			
		} else if(e.getSource().equals(cancel)){
			this.dispose();
		}
		
	}
	

}
