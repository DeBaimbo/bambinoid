package bambinoid;

import java.awt.Image;
import java.awt.geom.Rectangle2D;

import bambinoid.Entity;
import bambinoid.Main;


public class Ball extends Entity implements Updatable {
	private final Image imageIron = Main.loadImage("ball-iron.png");
	private final Image imageNormal = Main.loadImage("ball-normal.png");
	private static final Vec DEFAULT_V = Vec.newPolar(5.0, 89.5);
	private static final double MAX_SPEED = 8.0;
	private static final double MIN_SPEED = 3.0;
	private static final int MAX_BALLS = 9;
	private static final int IRONBALL_DURATION = 150; // in dt
	private boolean madeOfIron = false;
	private boolean gluedToPaddle = false;
	private double xCoordOnPad = 0.0;  // x-coordinate on paddle (when glued)
	private int ironBallTimer = 0;
	public Ball(double x, double y, Vec v){
		super("ball-normal.png",x,y);
		setVecV(v);
		Main.log("calling ball constructor with x:"+x+"   y:"+y);
	}
	public Ball(double x, double y){
		this(x,y,DEFAULT_V);
	}
	
	public boolean isMadeOfIron(){return madeOfIron;}
	public void setMadeOfIron(boolean madeOfIron)
	{
		this.madeOfIron = madeOfIron;
		if(madeOfIron)setImage(imageIron);
		else setImage(imageNormal);
		ironBallTimer = IRONBALL_DURATION;
	}
	
	@Override
	public void update(){
		if(ironBallTimer > 0) ironBallTimer--;
		else{setMadeOfIron(false);}
		
		if(!isGluedToPaddle())setVecR(getVecR().plus(getVecV()));
		else if(isGluedToPaddle()){
			Paddle paddle = EntityManager.INSTANCE.getPaddle();
			setX(paddle.getX()+xCoordOnPad);
			if(Keyboard.INSTANCE.getKeyState(Keyboard.Key.SPACE)){
				setGluedToPaddle(false);
			}
		}
	}

	public void bounceOnBorder(Border border){
		Rectangle2D intersection = this.createIntersection(border);
		if(intersection.getHeight()<=intersection.getWidth()){
			if(getVy()>0.0){
				setMaxY(border.getY());
				invVy();
			}
			else if(getVy()<0.0){
				setY(border.getMaxY());
				invVy();
			}
		}
		else{
			if(getVx()>0.0){
				setMaxX(border.getX());
				invVx();
			}
			else if(getVx()<0.0){
				setX(border.getMaxX());
				invVx();
			}
		}
	}
	
	public void bounceOnPaddle(Paddle paddle){
		Rectangle2D intersection = this.createIntersection(paddle);
		// bounce on short side
		if(intersection.getHeight()>intersection.getWidth()){
			if(getCenterX()<paddle.getCenterX()){
				setMaxX(paddle.getX());
				if(getVx()>0.)invVx();
				Main.log("1");
			}
			else{
				setX(paddle.getMaxX());
				if(getVx()<0.)invVx();
				Main.log("2");
			}
			return;
		}
		
		// bounce on long side
		if(getVy()>0.){
			setMaxY(paddle.getY());
			double newPhase = 
				270.+45.*(getCenterX()-paddle.getCenterX())/((paddle.getWidth()/2.)+(getWidth()/2.));
			setVphase(newPhase);
			//Main.log("v phase:"+getVphase());
			Main.log("3");
		}
		else if(getVy()<0.){
			setY(paddle.getMaxY());
			invVy();
			Main.log("4");
		}
	}
	
	public void bounceOnBrick(Brick brick){
		Rectangle2D intersection = this.createIntersection(brick);
		// bounce on long side
		if(intersection.getWidth()>intersection.getHeight()){
			Main.log("on long side");
			// from down
			if(getCenterY()>brick.getCenterY()){
				Main.log("from down");
				setY(brick.getMaxY());
				if(getVy()<0.)invVy();
			}
			// from up
			else{
				Main.log("from up");
				setMaxY(brick.getY());
				if(getVy()>0.)invVy();
			}
		}
		// bounce on short side
		else if(intersection.getWidth()<intersection.getHeight()){
			Main.log("on short side");
			// from left
			if(getCenterX()<brick.getCenterX()){
				Main.log("from left");
				setMaxX(brick.getX());
				if(getVx()>0.)invVx();
			}
			// from right
			else{
				Main.log("from right");
				setX(brick.getMaxX());
				if(getVx()<0.)invVx();
			}
		}
		// bounce on vertex
		else{
			Main.log("on vertex");
			invVx();
			invVy();
		}
		Main.log("end function bounceOnBrick");
	}
	public void spawnTwoBalls() {
		Main.log("calling Ball.spawnTwoBalls()");
		Ball ball1 = new Ball(getX(),getY(),Vec.newPolar(getVmodule(), 275));
		Ball ball2 = new Ball(getX(),getY(),Vec.newPolar(getVmodule(), 265));
		int numBalls = EntityManager.INSTANCE.getBalls().size();
		if(isGluedToPaddle()){
			ball1.setGluedToPaddle(true);
			ball2.setGluedToPaddle(true);
		}
		if(numBalls<MAX_BALLS)EntityManager.INSTANCE.add(GameState.PLAY, ball1);
		if(numBalls<MAX_BALLS-1)EntityManager.INSTANCE.add(GameState.PLAY, ball2);
	}
	
	@Override
	public void setVmodule(double speed){
		if(speed>MAX_SPEED) speed = MAX_SPEED;
		else if(speed < MIN_SPEED) speed = MIN_SPEED;
		super.setVmodule(speed);
	}
	
	public void speedUp(){
		setVmodule(getVmodule()+2.0);
	}
	public void slowDown(){
		setVmodule(getVmodule()-2.0);
	}
	
	public boolean isGluedToPaddle(){return gluedToPaddle;}
	public void setGluedToPaddle(boolean gtp){
		this.gluedToPaddle=gtp;
		Paddle paddle = EntityManager.INSTANCE.getPaddle();
		setMaxY(paddle.getY());
		if(getX()<paddle.getX())setX(paddle.getX());
		else if(getMaxX()>paddle.getMaxX())setMaxX(paddle.getMaxX());
		xCoordOnPad = getX()-paddle.getX();
	}
}
