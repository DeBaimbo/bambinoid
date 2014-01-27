package bambinoid;

import java.applet.Applet;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import javax.imageio.ImageIO;
import javax.swing.InputMap;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class Main extends JApplet {
	public static final int WIDTH  = 640;
	public static final int HEIGHT = 480;
	private static final boolean LOGGING = true; // are we logging?
	public static final Random ranGen = new Random();
	@Override
	public void init(){

		try {
	         SwingUtilities.invokeAndWait(new Runnable() {
	            public void run() {
	        		setSize(new Dimension(WIDTH,HEIGHT));
	        		add(Screen.INSTANCE);
	        		ExecutorService executor = Executors.newFixedThreadPool(1);
	        		executor.execute(Updater.INSTANCE);
	        		
	        		//getContentPane().setFocusable(true);
	        		//getContentPane().requestFocus() ;
	               setFocusable(true);
	               requestFocus();

	               int timerDelay = 100;
	               Timer myTimer = new Timer(timerDelay , new ActionListener() {

	                  @Override
	                  public void actionPerformed(ActionEvent arg0) {
	                     boolean focusObtained = requestFocusInWindow();
	                     System.out.println("focusObtained for JApplet: " + focusObtained);


	                     Screen.INSTANCE.setFocusable(true);
	                     boolean boh = Screen.INSTANCE.requestFocusInWindow();
	                     System.out.println("focus in Screen:"+boh);
	                  }
	               });
	               myTimer.setRepeats(false);
	               myTimer.start();
	            }
	         });
	      } catch (InvocationTargetException | InterruptedException e) {
	         e.printStackTrace();
	      }
	}
	
	@Override 
	public void start(){
		System.out.println("JApplet.start()");
        boolean focusObtained = requestFocusInWindow();
        System.out.println("focusObtained for JApplet: " + focusObtained);


        Screen.INSTANCE.setFocusable(true);
        boolean boh = Screen.INSTANCE.requestFocusInWindow();
        System.out.println("focus in Screen:"+boh);
	}
	
	
	public static void log(String msg){
		if(LOGGING)System.out.println(msg);
	}
	
	public static void err(String msg){ // prints to console even when we're not logging
		System.err.println(msg);
	}
	
	/**
	 * loads an image from a file in folder img
	 */
	public static Image loadImage(String fileName){
		Image ret = null;
		try{
			ret = ImageIO.read(Main.class.getResource("img/"+fileName));
		}
		catch(IOException e){
			e.printStackTrace(); 
			err("can't load "+fileName);
		}
		if(ret == null) err("can't find file "+fileName);
		return ret;
	}
}
