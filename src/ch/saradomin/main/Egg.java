package ch.saradomin.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

public class Egg implements Runnable {
  	static Image img;
	
	static volatile boolean caught;
  	
	static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	static int width = (int)size.getWidth();
	static int height = (int)size.getHeight();
	
	volatile ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    static volatile Window[] eggs = new Window[10];
    static volatile Point[] eggPoints = new Point[10];
    static volatile int eggCount = 0;
	
	Egg() {
		Egg.img = new ImageIcon(getClass().getClassLoader().getResource("eggo.png")).getImage();
	}
	
	static boolean doOverlap(Point l1, Point r1, Point l2, Point r2) {
		
	 	if (l1.x == r1.x || l1.y == r1.y || l2.x == r2.x || l2.y == r2.y) {
      		return false;
 		}
		 
	 	if (r1.y <= l2.y || r2.y <= l1.y) {
	 		return false;
	 	}
		 
	 	if (l1.x >= r2.x || l2.x >= r1.x) {
	 		return false;
	 	}
	 	return true;
	 }	
	
	public void run() {
		
        Thread timeout = new Thread(new Runnable() {
            public void run() {
            	Egg.caught = true;
            }
        });
		
		for(int i=0; i<10; i++) {
			Egg.eggCount = i;
			
			makeEgg(i);

			int xDir=1;
			int yDir=1;
			int xPos, yPos;
			
			Egg.caught = false;
	        ScheduledFuture sf = scheduler.schedule(timeout, 2000, TimeUnit.MILLISECONDS);
			while(!Egg.caught) {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
				}
				Point eggPos = Egg.eggs[i].getLocationOnScreen();
				Point mouse = MouseInfo.getPointerInfo().getLocation();
				int xMouse = (int) mouse.getX();
				int yMouse = (int) mouse.getY();
				
				xPos = (int)(eggPos.getX() + Egg.eggPoints[i].getX());
				yPos = (int)(eggPos.getY() + Egg.eggPoints[i].getY());
				
				
				
				if(doOverlap(mouse, new Point(xMouse+200,yMouse+200), new Point(xPos,yPos), new Point(xPos+100,yPos+120))) {
					sf.cancel(true);
					Egg.caught = true;
					Bunny.eggsScore++;
					Bunny.clearReady = true;
				}
				
				if(xPos+150>Egg.width) {
					xDir = -1;
				} else if(xPos-1<0) {
					xDir=1;
				}
				
				if(yPos+250>Egg.height) {
					yDir = -1;
				} else if(yPos-1<0) {
					yDir=1;
				}
				
                Egg.eggs[i].setLocation(
                		(int)eggPos.getX()+(1*xDir),
                		(int)eggPos.getY()+(1*yDir)
                );
                
                Egg.eggs[i].repaint();
			}
        	Egg.eggs[Egg.eggCount].dispose();
		}
		Bunny.gameover = true;
	}
	
	void makeEgg(final int i) {
		final int randX = (int) ((int) (Math.random() * (10 - 1 + 1) + 1) * (Egg.width-300) * 0.1);
		final int randY = (int) ((int) (Math.random() * (10 - 1 + 1) + 1) * (Egg.height-300) * 0.1);
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
		Egg.eggPoints[i] = new Point(randX, randY);
	}
	
	
}
