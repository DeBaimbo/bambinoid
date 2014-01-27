// singleton

package bambinoid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.color.ICC_ProfileGray;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

public class EntityManager {
	public static final EntityManager INSTANCE = new EntityManager();

	private EnumMap<GameState,List<Entity>> entityLists;
	private GameState currentState = GameState.MENU; // for now
	private final static double PURGE_PERIOD = 1.0; // how many seconds between purges
	private double purgeTimer = PURGE_PERIOD; // initial value
	
	private CurrentGame currentGame;
	private Entity upBorder, downBorder, leftBorder, rightBorder, 
		paddle, pausePicture, startPicture;
	private Rectangle2D.Double gameRectangle;
	private int remainingBricks = 0;
	
	// constructor
	private EntityManager(){
		Main.log("EntityManager constructor");
		// create one key in the map for every game state
		entityLists = new EnumMap<>(GameState.class);
		// then associate a new empty list to every key
		for(GameState state: GameState.values())
			entityLists.put(state, new LinkedList<Entity>());
		
		// fillAllLists() AAAAAAAAARGHHH!
		currentGame = new CurrentGame();
		upBorder = new Border("upborder.png",10,0);
		leftBorder = new Border("leftborder.png",upBorder.getX(),upBorder.getMaxY());
		downBorder = new Border("downborder.png",upBorder.getX(),leftBorder.getMaxY(),true);
		rightBorder = new Border("rightborder.png");
		rightBorder.setMaxX(upBorder.getMaxX());
		rightBorder.setY(upBorder.getMaxY());
		//
		gameRectangle = 
			new Rectangle2D.Double(
				leftBorder.getMaxX(),
				upBorder.getMaxY(),
				upBorder.getWidth()-2*leftBorder.getWidth(),
				leftBorder.getHeight()-2*upBorder.getHeight()
			);
		Main.log(""+gameRectangle);
		//
		paddle = new Paddle(currentGame,leftBorder.getMaxX()+120,downBorder.getY()-60);
		add(GameState.PLAY, upBorder);
		add(GameState.PLAY, leftBorder);
		add(GameState.PLAY, downBorder);
		add(GameState.PLAY, rightBorder);
		add(GameState.PLAY, paddle);
		addAll(GameState.PLAY,currentGame.getLevel().getBricks(gameRectangle));
		add(GameState.PLAY,new InfoArea(currentGame,430,10,200,300));
		
		// pause game state
		pausePicture = new PausePicture();
		add(GameState.PAUSE, pausePicture);
		// end pause game state
		// start level state//
		startPicture = new StartPicture();
		add(GameState.START, startPicture);
		// // //
		// menu state //
		Menu menu = new Menu(this);
		add(GameState.MENU, menu);
		addAll(GameState.MENU,menu.getButtons());
		// // 
		// level_sign state
		add(GameState.LEVEL_SIGN, new LevelSignPicture());
		// //
		// life lost state //
		add(GameState.LIFELOST, new LifeLostPicture());
		// //
		// game over state //
		add(GameState.GAMEOVER_SIGN,new GameOverPicture());
		//
		add(GameState.CREDITS,new CreditsPicture());
		add(GameState.HIGH_SCORES, new HighScoresPicture());
		add(GameState.GAMECOMPLETED_SIGN, new GameCompletedPicture());
	}
	 
	/**
	 * paint all entities of current game state
	 */
	public void paint(Graphics g){
		List<Entity> list = entityLists.get(currentState);
		for(Entity entity: list){
			if(entity.isActive()) entity.paint(g);
		}
	}
	
	/**
	 * update all entities of current game state
	 */
	
	public void update(){
		List<Entity> list = entityLists.get(currentState);
		for(int i=0; i<list.size(); i++){
			Entity entity = list.get(i);
			if(entity.isActive() && entity instanceof Updatable){
				Updatable updatableEntity = (Updatable) entity;
				updatableEntity.update();
			}
		}
		if(currentState==GameState.PLAY)checkCollisions();
		if(purgeTimer <= 0.0){ purgeInactives(); purgeTimer = PURGE_PERIOD;}
		else{purgeTimer -= Updater.DT;}
	}
	
	/** 
	 * checks if objects are colliding and tells them to react 
	 */
	private void checkCollisions(){
		List<Entity> list = entityLists.get(currentState);
		OUTER:for(int i=0; i<list.size(); i++){
			INNER:for(int j=0; j<list.size(); j++){
				Entity e1 = list.get(i);
				if(!e1.isActive()) continue OUTER;
				Entity e2 = list.get(j);
				if(!e2.isActive()) continue INNER;
				if(e1==e2) continue INNER;
				if(!e1.intersects(e2)) continue INNER;
				// paddle
				if(e1==paddle){
					// paddle - left border
					if(e2==leftBorder) paddle.setX(leftBorder.getMaxX());
					// paddle - right border
					else if(e2==rightBorder) paddle.setMaxX(rightBorder.getX());
				}
				// ball
				else if(e1 instanceof Ball){
					Ball ball = (Ball) e1;
					// ball - borders
					if(e2 instanceof Border){
						Border border = (Border) e2;
						if(!border.isDeathBorder())ball.bounceOnBorder(border);
						else{
						  ball.setActive(false);	
						}
					}
					// ball - paddle
					if(e2 instanceof Paddle){
						Paddle paddle = (Paddle) e2;
						ball.bounceOnPaddle(paddle);
						if(paddle.getState()==Paddle.State.GLUE){ball.setGluedToPaddle(true);}
					}
					// ball - brick
					if(e2 instanceof Brick){
						Brick brick = (Brick) e2;
						if(!ball.isMadeOfIron()){
							Main.log("calling ball.bounceOnBrick from manager");
							ball.bounceOnBrick(brick);
							Main.log("calling brick.hit from manager");
							brick.hit();
						}
						else{
							brick.destroy();
						}
					}
				}
				// prize
				else if(e1 instanceof Prize){
					Prize prize = (Prize) e1;
					// prize - border
					if(e2 instanceof Border){
						prize.setActive(false);
					}
					// prize - paddle
					else if(e2 instanceof Paddle){
						((Paddle)e2).applyPrize(prize.getType());
						prize.setActive(false);
					}
				}
				// missile
				else if(e1 instanceof Missile){
					Missile missile = (Missile) e1;
					// missile - border
					if(e2 instanceof Border){
						missile.setActive(false);
					}
					// missile - brick
					else if(e2 instanceof Brick){
						((Brick) e2).hit();
						missile.setActive(false);
					}
				}
			}
		}
		
	}
	
	/**
	 * add an entity to the list of a certain game state
	 */
	public void add(GameState state, Entity entity){
		entityLists.get(state).add(entity);
	}
	
	/*
	/** add many entities to the list of a certain game states
	*/
	public void addAll(GameState state, List<? extends Entity> list){
		for(Entity entity: list) {
			add(state,entity);
		}
	}
	
	
	/*
	 * purge inactive entities of the list of the current state
	 * don't call this function more often than once every half second
	 */
	public void purgeInactives(){
		List<Entity> list = entityLists.get(currentState);
		for(int i=0; i<list.size(); i++){
			if(!list.get(i).isActive()){
				Main.log("purging " + list.get(i));
				list.remove(i);
				i--;
			}
		}
	}

	public Rectangle2D.Double getGameRectangle(){return gameRectangle;}
	
	public void setRemainingBricks(int rb){this.remainingBricks=rb;}
	public void incRemainingBricks(){this.remainingBricks++;}
	public Paddle getPaddle(){return (Paddle)paddle;}
	// retrieve active balls
	public List<Ball> getBalls(){
		List<Ball> balls = new ArrayList<Ball>();
		List<Entity> playList = entityLists.get(GameState.PLAY);
		for(int i=0; i<playList.size(); i++){
			if(playList.get(i) instanceof Ball  &&  playList.get(i).isActive()){
				balls.add((Ball)playList.get(i));
			}
		}
		return balls;
	}
	
	private void removeBalls(){
		List<Entity> playList = entityLists.get(GameState.PLAY);
		for(int i=0; i<playList.size(); i++){
			if(playList.get(i) instanceof Ball){
				playList.remove(i);
				i--;
			}
		}
	}
	
	private void removePrizes(){
		List<Entity> playList = entityLists.get(GameState.PLAY);
		for(int i=0; i<playList.size(); i++){
			if(playList.get(i) instanceof Prize){
				playList.remove(i);
				i--;
			}
		}
	}
	
	private void removeMissiles(){
		List<Entity> playList = entityLists.get(GameState.PLAY);
		for(int i=0; i<playList.size(); i++){
			if(playList.get(i) instanceof Missile){
				playList.remove(i);
				i--;
			}
		}
	}
	
	public void setCurrentState(GameState state){
		
		if(state == GameState.PAUSE){
			Main.log("calling pause");
			BufferedImage bgImage = new BufferedImage
					(Main.WIDTH,Main.HEIGHT,BufferedImage.TYPE_INT_RGB);
			Graphics bgg = bgImage.getGraphics();
			Screen.INSTANCE.paint(bgg);
			convertToGrayScale(bgImage);
			((PausePicture)pausePicture).setBgImage(bgImage);
			Main.log("end call");
		}
		
		else if(state == GameState.LEVEL_SIGN){
			currentGame.setRemainingBricks(currentGame.getLevel().calcNormalBricks());
			restartLevel();
		}

		this.currentState = state;
	}
	
	public CurrentGame getGame(){
		if(currentGame != null) return currentGame;
		else throw new IllegalStateException("game not ready!!!");
	}

	public void restartLevel() {
		Main.log("calling EntityManager.restartLevel()");
		removeBalls();
		removePrizes();
		removeMissiles();
		add(GameState.PLAY,new Ball(200,300));
		((Paddle) paddle).reset();
		
	}
	
	// super useful! //
	public void convertToGrayScale(BufferedImage image){
		ICC_ColorSpace colorSpace = new ICC_ColorSpace(
			ICC_Profile.getInstance(ColorSpace.CS_GRAY)
		);
		Graphics2D bgg2 = (Graphics2D) image.getGraphics();
		ColorConvertOp operation = 
			new ColorConvertOp(colorSpace, bgg2.getRenderingHints());
		operation.filter(image, image);
	}
	
	public void reset(){
		// empty play list
		entityLists.put(GameState.PLAY, new LinkedList<Entity>());
		add(GameState.PLAY, upBorder);
		add(GameState.PLAY, leftBorder);
		add(GameState.PLAY, downBorder);
		add(GameState.PLAY, rightBorder);
		add(GameState.PLAY, paddle);
		addAll(GameState.PLAY,currentGame.getLevel().getBricks(gameRectangle));
		add(GameState.PLAY,new InfoArea(currentGame,430,10,200,300));
	}
}
