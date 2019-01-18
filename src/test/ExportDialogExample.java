package test;

//Copyright 2003, SLAC, Stanford, U.S.A.

//import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Insets;
//import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

//import org.freehep.util.export.ExportDialog;
//import org.freehep.graphics2d.VectorGraphics;

/**
 * @author Mark Donszelmann
 * @version $Id: ExportDialogExample.java,v 1.1 2005/08/03 07:16:02 devel Exp $
 */
public class ExportDialogExample extends JPanel {


    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	public ExportDialogExample() {
        setPreferredSize(new Dimension(600,400));
    }

    public void paintComponent(Graphics g) {

/*        if (g == null) return;

        Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        VectorGraphics vg = VectorGraphics.create(g2);

        Dimension dim = getSize();
        Insets insets = getInsets();

        vg.setColor(Color.white);
        vg.fillRect(insets.left, insets.top,
                    dim.width-insets.left-insets.right,
                    dim.height-insets.top-insets.bottom);

        vg.setColor(Color.black);

        vg.setLineWidth(4.0);
        vg.drawLine(10, 10, dim.width-10, dim.height-10);	*/
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("ExportDialogExample");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        final ExportDialogExample panel = new ExportDialogExample();
        frame.getContentPane().add(panel);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem exportItem = new JMenuItem("Export...");
        exportItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                ExportDialog export = new ExportDialog();
//                export.showExportDialog(panel, "Export view as ...", panel, "export");
            }
        });
        file.add(exportItem);

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(quitItem);

        frame.pack();
        frame.setVisible(true);
    }
}
