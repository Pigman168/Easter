package ch.shires.main;

import java.awt.*;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class Bunny implements Runnable {
	
	GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);
	
	static Point p = MouseInfo.getPointerInfo().getLocation();
	static int x = (int)p.getX();
	static int y = (int)p.getY();
	
	static int eggsScore = 0;
	
    static Image img;
    
	public static void main(String[] args) throws IOException {
		
		Thread bunnyThread = new Thread(new Bunny());
		Thread eggThread = new Thread(new Egg());
		bunnyThread.start();
		eggThread.start();
	}
    
    Bunny() {
    	Bunny.img = new ImageIcon(getClass().getClassLoader().getResource("bunny.png")).getImage();
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override 
			public void keyPressed(GlobalKeyEvent event) {
				if(event.getVirtualKeyCode() == 91) {
					System.exit(0);
				}
			}
		});
    }

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
		

		while(true) {
			try {
				KeyStroke.getKeyStroke("a");
				Thread.sleep(5);
			} catch (Exception e) {
			}
			Bunny.p = MouseInfo.getPointerInfo().getLocation();
			Bunny.x = (int)Bunny.p.getX();
			Bunny.y = (int)Bunny.p.getY();
			w.setVisible(true);
			w.setLocation(Bunny.x-110, Bunny.y-75);
			w.repaint();
		}
	}
}

