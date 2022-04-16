package ch.shires.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

public class Egg implements Runnable {
  	static Image img;
	
	static volatile boolean caught;
  	
	static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	static int width = (int)size.getWidth();
	static int height = (int)size.getHeight();
	
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    static Window[] eggs = new Window[10];
    static volatile int eggCount = 0;
	
	Egg() {
		Egg.img = new ImageIcon(getClass().getClassLoader().getResource("eggo.png")).getImage();
	}
	
	public void run() {
		
        Thread timeout = new Thread(new Runnable() {
            public void run() {
            	Egg.caught = true;
            }
        });
		
        Thread bounce = new Thread(new Runnable() {
            public void run() {

            	int dir = (int) (Math.random() * (1 - 0 + 1) + 0);
            	while(true) {
                    Egg.eggs[Egg.eggCount].setLocation(100,100);
                    Egg.eggs[Egg.eggCount].repaint();	
            	}
            }
        });
        
		for(int i=0; i<10; i++) {
			Egg.eggCount = i;
			makeEgg(i);
			Egg.caught = false;
	        scheduler.schedule(timeout, 1650, TimeUnit.MILLISECONDS);
	        scheduler.schedule(bounce, 0, TimeUnit.MILLISECONDS);
			while(!Egg.caught) {
				
			}
			
		}
	}
	
	void makeEgg(final int i) {
		final int randX = (int) ((int) (Math.random() * (10 - 1 + 1) + 1) * (Egg.width-300) * 0.1);
		final int randY = (int) ((int) (Math.random() * (10 - 1 + 1) + 1) * (Egg.height-300) * 0.1);
		System.out.println(randX+","+randY);
		Window w = new Window(null)
		{
			@Override
			public void paint(Graphics g)
			{
				g.drawImage(Egg.img, randX, randY, new Color(0f,0f,0f,0f), null);
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
		Egg.eggs[i] = w;
	}
	
	
}
