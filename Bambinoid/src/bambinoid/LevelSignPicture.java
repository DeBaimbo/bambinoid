package bambinoid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class LevelSignPicture extends Entity implements Updatable{
	private Image bgImage; // background
	private Image fgImage; // foreground (smaller)
	private int xfg; // foreground image position
	private int yfg;
	Graphics fgg;
	private final static int MIN_DURATION = 10; // in dt
	private int time = 0; // in dt
	private int levelNum = 1;
	
	public LevelSignPicture(){
		setWidth(Main.WIDTH);
		setHeight(Main.HEIGHT);
		bgImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		fgImage = new BufferedImage(300,300,BufferedImage.TYPE_INT_RGB);
		Graphics bgg = bgImage.getGraphics();
		fgg = fgImage.getGraphics();
		bgg.setColor(Color.CYAN);
		bgg.fillRect(0, 0, bgImage.getWidth(null), bgImage.getHeight(null));
		xfg = (bgImage.getWidth(null)-fgImage.getWidth(null))/2;
		yfg = (bgImage.getHeight(null)-fgImage.getHeight(null))/2;
	}
	
	@Override
	public void paint(Graphics g){
		fgg.setColor(Color.GRAY);
		fgg.fillRect(0, 0, fgImage.getWidth(null), fgImage.getHeight(null));
		fgg.setColor(Color.RED);
		fgg.drawString("Level N " + levelNum, 20, 100);
		g.drawImage(bgImage, 0,0,null);
		g.drawImage(fgImage,xfg,yfg,null);
	}
	
	@Override
	public void update(){
		levelNum = EntityManager.INSTANCE.getGame().getLevel().getNumber();
		if(time<=MIN_DURATION){
			time++;
			return;
		}
		if(Keyboard.INSTANCE.getKeyState(Keyboard.Key.SPACE)){
			EntityManager.INSTANCE.setCurrentState(GameState.START);
			return;
		}
	}
	
	public void setBgImage(Image bgImage){
		Main.log("setting bgImage");
		this.bgImage = bgImage;
	}
	
	public void setLevelNum(int num){this.levelNum = num;}
}
