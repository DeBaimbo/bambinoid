package bambinoid;

import java.awt.Image;

public class Missile extends Entity implements Updatable {
	public final static Image IMAGE = Main.loadImage("missile.png");
	public final static int WIDTH = IMAGE.getWidth(null);
	public final static int HEIGHT = IMAGE.getHeight(null);
	public static final double SPEED = 3.0; //  in  px/dt


	public Missile(int x, int y){
		setImage(IMAGE);
		setX(x);
		setY(y);
		setVy(-SPEED);
		setWidth(WIDTH);
		setHeight(HEIGHT);
	}
	
	@Override
	public void update(){
		setY(getY()+getVy());
	}
	
}
