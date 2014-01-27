package bambinoid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class CreditsPicture extends Entity implements Updatable{
	private Image bgImage; // background
	private Image fgImage; // foreground (smaller)
	private int xfg; // foreground image position
	private int yfg;
	private final static int MIN_DURATION = 10; // in dt
	private int time = 0; // in dt
	
	public CreditsPicture(){
		setWidth(Main.WIDTH);
		setHeight(Main.HEIGHT);
		bgImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		fgImage = new BufferedImage(300,300,BufferedImage.TYPE_INT_RGB);
		Graphics bgg = bgImage.getGraphics();
		Graphics fgg = fgImage.getGraphics();
		bgg.setColor(Color.CYAN);
		bgg.fillRect(0, 0, bgImage.getWidth(null), bgImage.getHeight(null));
		fgg.setColor(Color.YELLOW);
		fgg.fillRect(0, 0, fgImage.getWidth(null), fgImage.getHeight(null));
		fgg.setColor(Color.RED);
		fgg.drawString("Game created, but not finished yet by debaimbo", 20, 100);
		xfg = (bgImage.getWidth(null)-fgImage.getWidth(null))/2;
		yfg = (bgImage.getHeight(null)-fgImage.getHeight(null))/2;
	}
	
	@Override
	public void paint(Graphics g){
		g.drawImage(bgImage, 0,0,null);
		g.drawImage(fgImage,xfg,yfg,null);
	}
	
	@Override
	public void update(){
		if(time<=MIN_DURATION){
			time++;
			return;
		}
		if(Keyboard.INSTANCE.getKeyState(Keyboard.Key.SPACE)){
			time = 0;
			EntityManager.INSTANCE.setCurrentState(GameState.MENU);
			return;
		}
	}
	
	public void setBgImage(Image bgImage){
		this.bgImage = bgImage;
	}
}
