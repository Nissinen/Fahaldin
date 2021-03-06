package Main;

import java.awt.Dimension;    
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;
import Handlers.Keys;



@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	// dimensions
		public static final int WIDTH = 600;
		public static final int HEIGHT = 400;
		public static final int SCALE = 2;
		
		// game thread
		private Thread thread;
		private boolean running;
		private int FPS = 60;
		private long targetTime = 1000 / FPS;
		
		// image
		private BufferedImage image;
		private Graphics2D g;
		
		// game state manager
		private GameStateManager gsm;
		
		
		public GamePanel() {
			super();
			setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
			setFocusable(true);
			requestFocus();
		}
		
		public void addNotify() {
			super.addNotify();
			if(thread == null) {
				thread = new Thread(this);
				addKeyListener(this);
				thread.start();
			}
		}
		
		private void init() {
			
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			g = (Graphics2D) image.getGraphics();
			//sets game loop ready to run
			running = true;
			
			gsm = new GameStateManager();
			
		}
		
		public void run() {
			init();
			
			long start;
			long elapsed;
			long wait;
			
			// game loop
			while(running) {
				
				start = System.nanoTime();
				
				update();
				draw();
				drawToScreen();
				
				elapsed = System.nanoTime() - start;
				
				wait = targetTime - elapsed / 1000000;
				if(wait < 0) wait = 5;
				
				try {
					Thread.sleep(wait);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		public boolean isRunning(){
			return running;
		}
		
		private void update() {
			gsm.update();
			Keys.update();
		}
		private void draw() {
			gsm.draw(g);
		}
		private void drawToScreen() {
			Graphics g2 = getGraphics();
			//sets image size to 2x
			g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
			g2.dispose();
			
		}
		
		public void keyTyped(KeyEvent key) {}
		//pressed key actions
		public void keyPressed(KeyEvent key) {
			Keys.keySet(key.getKeyCode(), true);
		}
		//released key actions
		public void keyReleased(KeyEvent key) {
			Keys.keySet(key.getKeyCode(), false);
		}

	}