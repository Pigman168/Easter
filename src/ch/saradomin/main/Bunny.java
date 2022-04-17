package ch.saradomin.main;

import java.awt.*;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class Bunny implements Runnable {
	
	GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);

	static boolean clearReady = false;
	
	static Point p = MouseInfo.getPointerInfo().getLocation();
	static int x = (int)p.getX();
	static int y = (int)p.getY();
	
	static int eggsScore = 0;
	
    static Image img;
	static boolean gameover = false;

    
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
		
		
		while(!Bunny.gameover) {
			try {
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
		w.dispose();
		
		Window w2 = new Window(null)
		{
			@Override
			public void paint(Graphics g)
			{
				g.clearRect(0,300,5000,5000);
			    final Font font = getFont().deriveFont(70f);
			    g.setFont(font);
			    g.setColor(Color.MAGENTA);
			    final String message = "Score: "+Bunny.eggsScore;
			    g.drawString(message,120,330);
				g.drawString("Happy Easter! :)", 0, 500);
			}
			@Override
			public void update(Graphics g)
			{
				paint(g);
			}
		};
		w2.setAlwaysOnTop(true);
		w2.setBounds(Egg.width*3/8,Egg.height/6,500,10000);
		w2.setBackground(new Color(0, true));
		w2.setVisible(true);
		w2.repaint();
	}
}

