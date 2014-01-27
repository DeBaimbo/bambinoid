package bambinoid;

public class CurrentGame {
	
	private int lives;
	private Level level;
	private int score;
	private int remainingBricks = 0;
	private static final int DEFAULT_LIVES = 3;
	private static final int MAX_LIVES = 6;
	private static final int DEFAULT_SCORE = 0;
	private static final Level DEFAULT_LEVEL = Level.get(1);
	
	public CurrentGame(){
		reset();
	}

	public void reset(){
		setLives(DEFAULT_LIVES);
		setScore(DEFAULT_SCORE);
		setLevel(DEFAULT_LEVEL);
		setRemainingBricks(getLevel().calcNormalBricks());
	}
	
	public int getLives() {return lives;}
	public void setLives(int lives) {if(lives<=MAX_LIVES)this.lives = lives;}
	public void incLives(){setLives(getLives()+1);}
	public void decLives(){setLives(getLives()-1);}
	
	public Level getLevel() {return level;}
	public void setLevel(Level level) {this.level = level;}
	public void incLevel(){
		Main.log("CurrentGame.incLevel()");
		this.level = Level.get(getLevel().getNumber()+1);
		setRemainingBricks(getLevel().calcNormalBricks());
	}
	
	public int getScore() {return score;}
	public void setScore(int score) {this.score = score;}
	public void incScore(int inc){setScore(getScore()+inc);}
	
	public void setRemainingBricks(int num){this.remainingBricks = num;}
	public void incRemainingBricks(){remainingBricks++;}
	public void decRemainingBricks(){remainingBricks--;}
	public int getRemainingBricks(){return remainingBricks;}
	
	
}
