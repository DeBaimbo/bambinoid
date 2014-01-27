package bambinoid;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class Entity {
	private boolean active = true;
	private double x = 0.0;
	private double y = 0.0;
	private int height = 0;
	private int width = 0;
	private Rectangle2D.Double box = new Rectangle2D.Double(); // composition
	
	private double vx = 0.0;    // measured in pix/DT
	private double vy = 0.0;    // --
	private double ax = 0.0;
	private double ay = 0.0;
	private Image image;  // current image
	
	public Entity(){;} // because it get's called implicitly by subclasses 
	/**
	 * constructor that sets image and then width and height accordingly
	 * useful for single-image entities
	 */
	public Entity(String imageFileName){
		setImage(Main.loadImage(imageFileName));
		setWidth(getImage().getWidth(null));
		setHeight(getImage().getHeight(null));
	}
	/**
	 * similar to the previous one but with an Image object instead of file name
	 */
	public Entity(Image image){
		setImage(image);
		setWidth(getImage().getWidth(null));
		setHeight(getImage().getHeight(null));
	}
	/**
	 * constructor that sets image, width, height and initial position
	 */
	public Entity(String imageFileName,double x, double y){
		this(imageFileName);
		setX(x);
		setY(y);
	}
	/**
	 * similar
	 */
	public Entity(Image image, double x, double y){
		this(image);
		setX(x);
		setY(y);
	}
	
	public void paint(Graphics g){
		if(getImage()==null) 
			throw new IllegalStateException("forgot to override Entity.paint()? or to setImage?");
		g.drawImage(getImage(),(int)getX(),(int)getY(),null); 
	}
	
	
	// setters and getters
	public boolean isActive() {return active;}
	public void setActive(boolean active) {this.active = active;}
	public double getX() {return x;}
	public void setX(double x) {this.x = x; updateBox();}
	public double getY() {return y;}
	public void setY(double y) {this.y = y; updateBox();}
	public double getVx() {return vx;}
	public void setVx(double vx) {this.vx = vx;}
	public double getVy() {return vy;}
	public void setVy(double vy) {this.vy = vy;}
	public void invVx(){setVx(-getVx());}
	public void invVy(){setVy(-getVy());}
	public double getAx(){return ax;}
	public void setAx(double ax){this.ax = ax;}
	public double getAy(){return ay;}
	public void setAy(double ay){this.ay = ay;}
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height = height; updateBox();}
	public int getWidth() {return width;}
	public void setWidth(int width) {this.width = width; updateBox();}
	public Image getImage() {return image;}
	public void setImage(Image image) {this.image = image;}
	public Rectangle2D.Double getBox(){return box;}
	private void updateBox(){box.setRect(x,y,width,height);}
	// // // // there is no explicit setBox, box is set by x,y,w,h
	
	// simil-delegators
	public void setMaxX(double maxX){setX(maxX-getWidth());}
	public void setMaxY(double maxY){setY(maxY-getHeight());}
	// delegators
	public double getMinX() {return box.getMinX();}
	public double getMinY() {return box.getMinY();}
	public double getMaxX() {return box.getMaxX();}
	public double getMaxY() {return box.getMaxY();}
	public double getCenterX() {return box.getCenterX();}
	public double getCenterY() {return box.getCenterY();}
	// don't know if I'll ever use these two:
	public boolean intersects(Entity e2) {return box.intersects(e2.getBox());}
	public Rectangle2D createIntersection(Entity e2) {return box.createIntersection(e2.getBox());}
	// //
	
	// methods to view position, velocity and acceleration as vectors
	public Vec getVecR(){return Vec.newCartesian(x,y);}
	public Vec getVecV(){return Vec.newCartesian(vx,vy);}
	public Vec getVecA(){return Vec.newCartesian(ax,ay);}
	public void setVecR(Vec r){setX(r.x);setY(r.y);}
	public void setVecV(Vec v){setVx(v.x);setVy(v.y);}
	public void setVecA(Vec a){setAx(a.x);setAy(a.y);}
	// end view methods
	public double getVmodule(){return getVecV().getModule();}
	public double getVphase(){return getVecV().getPhase();}
	public void setVmodule(double module){setVecV(Vec.newPolar(module, getVphase()));}
	public void setVphase(double phase){setVecV(Vec.newPolar(getVmodule(), phase));}
}
