package ch.shires.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ImageIcon;

public class Egg implements Runnable {
  	static Image img;
	
	
	static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	static int width = (int)size.getWidth();
	static int height = (int)size.getHeight();
	
	static Window eggs[] = new Window[10];
	
	Egg() {
		this.img = new ImageIcon(getClass().getClassLoader().getResource("eggo.png")).getImage();
	}
	
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		
		for(int i=0; i<10; i++) {
			makeEgg(i);
		}
	}
	
	void makeEgg(final int i) {
		
		Window w = new Window(null)
		{
			@Override
			public void paint(Graphics g)
			{
				g.drawImage(Egg.img, i*100, 0, new Color(0f,0f,0f,0f), null);
				
			}
			@Override
			public void update(Graphics g)
			{
				paint(g);
			}
		};
		w.setAlwaysOnTop(true);
		w.setBounds(w.getGraphicsConfiguration().getBounds());
		w.setBackground(new Color(0, true));
		w.setVisible(true);
		eggs[i] = w;
	}
	
	
}
