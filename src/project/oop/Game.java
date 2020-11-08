package project.oop;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import project.oop.gui.GuiPanel;
import project.oop.gui.GuiScreen;
import project.oop.gui.LeaderboardsPanel;
import project.oop.gui.MainMenuPanel;
import project.oop.gui.PlayPanel;
import project.oop.gui.TutorialPanel;

public class Game extends JPanel implements KeyListener, MouseListener, MouseMotionListener, Runnable {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 560;
    public static final Font main = new Font("Arial", Font.PLAIN, 28);
    private Thread game;
    private boolean running;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
//    private GameBoard Board;
    private GuiScreen screen;

//    private long startTime;
//    private long elapsed;
//    private boolean set;

    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        
        
//        board = new GameBoard(WIDTH/2-GameBoard.BOARD_WIDTH/2, HEIGHT-GameBoard.BOARD_HEIGHT-10);
        screen = GuiScreen.getInstance();
        screen.add("Menu", new MainMenuPanel());
        screen.add("Play", new PlayPanel());
        screen.add("Scores", new LeaderboardsPanel());
        screen.add("Tutorial", new TutorialPanel());
        screen.setCurrentPanel("Menu");
    }

    private void update() {
//        board.update();
    	screen.update();
        Keyboard.update();
    }

    private void render() {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        screen.render(g);
//        board.render(g);
        g.dispose();

        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }

    
    public void run() {
        int fps = 0, updates = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0 / 60;

        // last update time in nanosecs
        double then = System.nanoTime();
        double unprocessed = 0;

        while (running) {
            boolean shouldRender = false;
            double now = System.nanoTime();
            unprocessed += (now - then) / nsPerUpdate;
            then = now;
            // update queue
            while (unprocessed >= 1) {
                updates++;
                update();
                unprocessed--;
                shouldRender = true;
            }

            // render
            if (shouldRender) {
                fps++;
                render();
                shouldRender = false;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // FPS Timer
        if (System.currentTimeMillis() - fpsTimer > 1000) {
            System.out.printf("%d fps %d updates", fps, updates);
            System.out.println();
            fps = 0;
            updates = 0;
            fpsTimer += 1000;
        }
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        game = new Thread(this, "game");
        game.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        System.exit(0);
    }

    
    public void keyTyped(KeyEvent e) {
        
    }

   
    public void keyPressed(KeyEvent e) {
        Keyboard.keyPressed(e);
    }

    
    public void keyReleased(KeyEvent e) {
        Keyboard.keyReleased(e);
    }

	
	public void mouseDragged(MouseEvent e) {
		screen.mouseDragged(e);
		
	}

	
	public void mouseMoved(MouseEvent e) {
		screen.mouseMoved(e);
		
	}

	
	public void mouseClicked(MouseEvent e) {
		
	}

	
	public void mousePressed(MouseEvent e) {
		screen.mousePressed(e);
		
	}

	
	public void mouseReleased(MouseEvent e) {
		screen.mouseReleased(e);
		
	}

	
	public void mouseEntered(MouseEvent e) {
		
	}

	
	public void mouseExited(MouseEvent e) {
		
	}

}
