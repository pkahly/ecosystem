package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import data.world.World;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
		
	public MainFrame(World world) {
		ImagePanel imagePanel = new ImagePanel(world);
		
		Container cp = getContentPane();
		cp.add(imagePanel);
		
		setupMainFrame();
	}
	   
	private void setupMainFrame() {   
		Toolkit tk;
		Dimension d;
	      
		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();
		
		setSize(d.width, d.height);
		setLocation(0, 0);
		  
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setTitle("Environment Simulator");
		setVisible(true);
	}
}
