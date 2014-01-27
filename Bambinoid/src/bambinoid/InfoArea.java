package bambinoid;

import java.awt.Color;
import java.awt.Graphics;

public class InfoArea extends Entity implements Updatable{
	private int levelNum = -1;
	private int remainingBricks = -1;
	private int lives = -1;
	private int fps = -1;
	private CurrentGame game;
	public InfoArea(CurrentGame game, int x, int y, int width, int height){
		this.game = game;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.GREEN);
		g.drawRect((int)getX(), (int)getY(), getWidth(), getHeight());
		g.drawString("level: "+levelNum, (int)getX()+10, (int)getY()+20);
		g.drawString("lives: "+lives, (int)getX()+10, (int)getY()+50);
		g.drawString
			("remaining bricks: "+remainingBricks, (int)getX()+10, (int)getY()+80);
		g.drawString("fps: "+fps, (int)getX()+10, (int)getY()+110);
		g.drawString("score: "+game.getScore(),(int)getX()+10,(int)getY()+150);
	}
	
	@Override
	public void update(){
		setFps(Updater.fps);
		setLives(game.getLives());
		setLevelNum(game.getLevel().getNumber());
		setRemainingBricks(game.getRemainingBricks());
	}

	public int getLevelNum() {return levelNum;}
	public void setLevelNum(int levelNum) {this.levelNum = levelNum;}
	public int getRemainingBricks() {return remainingBricks;}
	public void setRemainingBricks(int remainingBricks) {this.remainingBricks = remainingBricks;}
	public int getLives() {return lives;}
	public void setLives(int lives) {this.lives = lives;}
	public void setFps(int fps){this.fps = fps;}
}
