package bambinoid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class PausePicture extends Entity implements Updatable {
	private Image bgImage; // background
	private Image fgImage; // foreground (smaller)
	private int xfg; // foreground image position
	private int yfg;
	
	public PausePicture(){
		Main.log("calling PausePicture constructor");
		setWidth(Main.WIDTH);
		setHeight(Main.HEIGHT);
		bgImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		fgImage = new BufferedImage(200,70,BufferedImage.TYPE_INT_RGB);
		Graphics bgg = bgImage.getGraphics();
		Graphics fgg = fgImage.getGraphics();
		bgg.setColor(Color.MAGENTA);
		bgg.fillRect(0, 0, bgImage.getWidth(null), bgImage.getHeight(null));
		fgg.setColor(Color.BLUE);
		fgg.fillRect(0, 0, fgImage.getWidth(null), fgImage.getHeight(null));
		fgg.setColor(Color.YELLOW);
		fgg.drawString("pause",20,30);
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
		if(!Keyboard.INSTANCE.getToggleState(Keyboard.Toggle.PAUSE)){
			EntityManager.INSTANCE.setCurrentState(GameState.PLAY);
			return;
		}
	}
	
	public void setBgImage(Image bgImage){
		Main.log("setting bgImage");
		this.bgImage = bgImage;
	}
}
