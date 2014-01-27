
package bambinoid;

import java.awt.Image;
import java.util.List;

public class Paddle extends Entity implements Updatable {
	private final State DEFAULT_STATE = State.NORMAL; 
	private final double SPEED = 3.0;
	private State state; //current state
	private double defaultX;
	private double defaultY;
	private static final int LAUNCH_PAUSE = 25; // in dt
	private int launchTimer = 0;
	private CurrentGame game;
	
	public Paddle(CurrentGame game, double defaultX, double defaultY){
		this.game = game;
		this.defaultX = defaultX;
		this.defaultY = defaultY;
		reset();
	}
	
	public void reset(){
		setX(defaultX);
		setY(defaultY);
		setState(DEFAULT_STATE);
	}
	
	private void setState(State state){
		this.state = state;
		setImage(state.getImage());
		setWidth(getImage().getWidth(null));
		setHeight(getImage().getHeight(null));
	}
	public State getState(){return state;}
	// nested enum
	public static enum State{
		NORMAL("paddle-normal.png"),
		GROW1("paddle-grow1.png"),
		GROW2("paddle-grow2.png"),
		SHRINK1("paddle-shrink1.png"),
		SHRINK2("paddle-shrink2.png"),
		GLUE("paddle-glue.png"),
		MISSILES("paddle-missiles.png");
		
		private Image image;
		
		State(String imageFileName){
			image = Main.loadImage(imageFileName);
		}
		
		public Image getImage(){return image;}
	}
	// end nested enum
	
	@Override
	public void update() {
		if(EntityManager.INSTANCE.getBalls().size()==0){
			game.decLives();
			if(game.getLives()>0)EntityManager.INSTANCE.setCurrentState(GameState.LIFELOST);
			else{
				game.reset();
				EntityManager.INSTANCE.reset();
				EntityManager.INSTANCE.setCurrentState(GameState.GAMEOVER_SIGN);
			}
			return;
		}
		if(game.getRemainingBricks()==0){
			if(Level.totalNumber()==game.getLevel().getNumber()){
				game.reset();
				EntityManager.INSTANCE.reset();
				EntityManager.INSTANCE.setCurrentState(GameState.GAMECOMPLETED_SIGN);
				return;
			}
			game.incLevel();
			EntityManager.INSTANCE.setCurrentState(GameState.LEVEL_SIGN);
			EntityManager.INSTANCE.reset();
			return;
		}
		if(Keyboard.INSTANCE.getToggleState(Keyboard.Toggle.PAUSE)){
			pauseGame();
			return;
		}
		if(Keyboard.INSTANCE.getKeyState(Keyboard.Key.LEFT)){
			setVx(-SPEED);
		}
		else if(Keyboard.INSTANCE.getKeyState(Keyboard.Key.RIGHT)){
			setVx(+SPEED);
		}
		else setVx(0.0);
		
		setX(getX()+getVx());
		if(getState()==State.MISSILES){
			if(Keyboard.INSTANCE.getKeyState(Keyboard.Key.SPACE)){
				if(launchTimer<=0){
					launchTimer = LAUNCH_PAUSE;
					launchMissiles();
				}	
			}
			launchTimer--;
		}
	}



	public void applyPrize(Prize.Type prizeType) {
		if(prizeType == null)
			throw new IllegalArgumentException("trying to apply null prize!?");
		game.incScore(prizeType.getPoints());
		switch(prizeType){
			case FASTBALL:
				fastBall();
				break;
			case GLUE:
				setState(State.GLUE);
				break;
			case GROWER:
				freeGluedBalls();
				grow();
				break;
			case IRONBALL:
				ironBall();
				break;
			case LIFE:
				incLives();
				break;
			case MISSILES:
				freeGluedBalls();
				setState(State.MISSILES);
				break;
			case MULTIBALL:
				multiball();
				break;
			case SHRINKER:
				freeGluedBalls();
				shrink();
				break;
			case SLOWBALL:
				slowBall();
				break;
		}
	}
	
	private void grow(){
		switch(getState()){
			case GROW1: 
				setState(State.GROW2);
				break;
			case GROW2: // do nothing
				break;
			case SHRINK2: 
				setState(State.SHRINK1);
				break;
			case SHRINK1:
				setState(State.NORMAL);
				break;
			default:
				setState(State.GROW1);
		}
	}
	
	private void shrink(){
		switch(getState()){
			case GROW1: 
				setState(State.NORMAL);
				break;
			case GROW2: 
				setState(State.GROW1);
				break;
			case SHRINK2: 
				// nothing
				break;
			case SHRINK1:
				setState(State.SHRINK2);
				break;
			default:
				setState(State.SHRINK1);
		}
	}
	

	private void incLives(){game.incLives();}
	private void decLives(){game.decLives();}
	private void launchMissiles(){
		EntityManager.INSTANCE.add(GameState.PLAY, 
				new Missile((int)getX()+3,(int)getY()));
		EntityManager.INSTANCE.add(GameState.PLAY, 
				new Missile((int)getMaxX()-Missile.WIDTH-3,(int)getY()));
	}
	private void multiball(){
		List<Ball> balls = EntityManager.INSTANCE.getBalls();
		for(Ball ball: balls)ball.spawnTwoBalls();
		Main.log("Paddle.multiball() called");
	}
	private void fastBall(){
		List<Ball> balls = EntityManager.INSTANCE.getBalls();
		for(Ball ball: balls)ball.speedUp();
	}
	private void slowBall(){
		List<Ball> balls = EntityManager.INSTANCE.getBalls();
		for(Ball ball: balls)ball.slowDown();
	}
	private void ironBall(){
		List<Ball> balls = EntityManager.INSTANCE.getBalls();
		for(Ball ball: balls)ball.setMadeOfIron(true);
	}
	
	private void freeGluedBalls(){
		List<Ball> balls = EntityManager.INSTANCE.getBalls();
		for(Ball ball : balls){
			if(ball.isGluedToPaddle())
				ball.setGluedToPaddle(false);
		}
	}
	
	public void pauseGame(){
		EntityManager.INSTANCE.setCurrentState(GameState.PAUSE);
	}
}
