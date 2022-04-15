package ch.shires.main;

import java.awt.*;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Bunny implements Runnable {
	
    static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	static int width = (int)size.getWidth();
	static int height = (int)size.getHeight();
	
	static Point p = MouseInfo.getPointerInfo().getLocation();
	static int x = (int)p.getX();
	static int y = (int)p.getY();
	
    static Image img = new ImageIcon("bunny.png").getImage();
	
    
	public static void main(String[] args) throws IOException {
		Thread bunnyThread = new Thread(new Bunny());
		bunnyThread.run();
	}
    
    Bunny() {
    	
    }

	@Override
	public void run() {
		Window w = new Window(null)
		{
			@Override
			public void paint(Graphics g)
			{
				g.drawImage(Bunny.img, 0, 0, new Color(0f,0f,0f,0f), null);
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

		while(true) {
			try {
				Thread.sleep(5);
			} catch (Exception e) {
			}
			Bunny.p = MouseInfo.getPointerInfo().getLocation();
			Bunny.x = (int)Bunny.p.getX();
			Bunny.y = (int)Bunny.p.getY();
			w.setLocation(Bunny.x-110, Bunny.y-75);
			w.repaint();
		}
	}
}
