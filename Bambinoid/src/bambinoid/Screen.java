// represents the game "screen", the rectangle in the web page in 
// which the applet runs
// and also takes care of key bindings
// singleton

package bambinoid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Screen extends JPanel {
	public static final Screen INSTANCE = new Screen();
	private static final boolean ANTIALIASING = true;
	
	private final BufferedImage backBuffer = new BufferedImage(Main.WIDTH,Main.HEIGHT,BufferedImage.TYPE_INT_RGB);
	private final Graphics bbg = backBuffer.createGraphics();
	
	private Screen(){
		setSize(new Dimension(Main.WIDTH, Main.HEIGHT));
		if(ANTIALIASING){
			((Graphics2D)bbg).setRenderingHint(
					RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		setKeyBindings();
        setFocusable(true);
        requestFocusInWindow();   
	}
	
	@Override
	public void paintComponent(Graphics g){
		//super.paintComponent(g);
		// draw on back buffer
		bbg.setColor(Color.BLACK);
		bbg.fillRect(0,0,Main.WIDTH,Main.HEIGHT);
		EntityManager.INSTANCE.paint(bbg);
		// put back buffer on screen
		g.drawImage(backBuffer,  0,0,null);
		
	}
	
	private void setKeyBindings(){	
		// local class
		class MyAction extends AbstractAction{
        	private String s;
        	public MyAction(String s){
        		this.s = s;
        	}
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(s){
					case "leftPressed":
						Keyboard.INSTANCE.pressKey(Keyboard.Key.LEFT);
						break;
					case "leftReleased":
						Keyboard.INSTANCE.tryToReleaseKey(Keyboard.Key.LEFT);
						break;
					case "rightPressed":
						Keyboard.INSTANCE.pressKey(Keyboard.Key.RIGHT);
						break;
					case "rightReleased":
						Keyboard.INSTANCE.tryToReleaseKey(Keyboard.Key.RIGHT);
						break;
					case "upPressed":
						Keyboard.INSTANCE.pressKey(Keyboard.Key.UP);
						break;
					case "upReleased":
						Keyboard.INSTANCE.tryToReleaseKey(Keyboard.Key.UP);
						break;
					case "downPressed":
						Keyboard.INSTANCE.pressKey(Keyboard.Key.DOWN);
						break;
					case "downReleased":
						Keyboard.INSTANCE.tryToReleaseKey(Keyboard.Key.DOWN);
						break;
					case "spacePressed":
						Keyboard.INSTANCE.pressKey(Keyboard.Key.SPACE);
						break;
					case "spaceReleased":
						Keyboard.INSTANCE.tryToReleaseKey(Keyboard.Key.SPACE);
						break;
					case "pPressed":
						Keyboard.INSTANCE.pressKey(Keyboard.Key.P);
						break;
					case "pReleased":
						Keyboard.INSTANCE.tryToReleaseKey(Keyboard.Key.P);
						break;
						
				}
			}
        }
		// end local class
		InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "leftPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "rightPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rightReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "upPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "upReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "downPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "downReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "spacePressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "spaceReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, false), "pPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true), "pReleased");

        am.put("leftPressed", new MyAction("leftPressed"));
        am.put("leftReleased", new MyAction("leftReleased"));
        am.put("rightPressed", new MyAction("rightPressed"));
        am.put("rightReleased", new MyAction("rightReleased"));	        
        am.put("upPressed", new MyAction("upPressed"));	        
        am.put("upReleased", new MyAction("upReleased"));	        
        am.put("downPressed", new MyAction("downPressed"));	       
        am.put("downReleased", new MyAction("downReleased"));
        am.put("spacePressed", new MyAction("spacePressed"));	       
        am.put("spaceReleased", new MyAction("spaceReleased"));
        am.put("pPressed", new MyAction("pPressed"));	       
        am.put("pReleased", new MyAction("pReleased"));
        
        setFocusable(true);
        requestFocusInWindow();   
 
	}// end method setKeyBindings
		
}
