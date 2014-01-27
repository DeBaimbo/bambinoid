package bambinoid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class Menu extends Entity implements Updatable {
	private Image bgImage; // background
	private Button[] buttons;
	private EntityManager entityManager;
	private int selection = 0; // number of button currently selected
	private static final int CHANGE_PAUSE = 4; // in dt
	private int changeTimer = 0; // to avoid superfast changes
	
	private static final Image PLAYSELECTED_IMAGE = 
			Main.loadImage("button-play-selected.png");
	private static final Image PLAYUNSELECTED_IMAGE = 
			Main.loadImage("button-play-unselected.png");
	private static final Image CREDITSSELECTED_IMAGE = 
			Main.loadImage("button-credits-selected.png");
	private static final Image CREDITSUNSELECTED_IMAGE = 
			Main.loadImage("button-credits-unselected.png");
	private static final Image HIGHSCORESSELECTED_IMAGE = 
			Main.loadImage("button-highscores-selected.png");
	private static final Image HIGHSCORESUNSELECTED_IMAGE = 
			Main.loadImage("button-highscores-unselected.png");
	private static final int BUTTON_WIDTH  = PLAYSELECTED_IMAGE.getWidth(null);
	private static final int BUTTON_HEIGHT = PLAYSELECTED_IMAGE.getHeight(null);
	
	
	public Menu(EntityManager entityManager){
		this.entityManager = entityManager;
		setWidth(Main.WIDTH);
		setHeight(Main.HEIGHT);
		bgImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics bgg = bgImage.getGraphics();
		bgg.setColor(Color.MAGENTA);
		bgg.fillRect(0, 0, bgImage.getWidth(null), bgImage.getHeight(null));	
		bgg.setColor(Color.RED);
		bgg.drawString("menu", 100, 20);
		
		int buttonXcoord = (Main.WIDTH-BUTTON_WIDTH)/2;
		buttons = new Button[3];
		buttons[0] = new Button(PLAYSELECTED_IMAGE,PLAYUNSELECTED_IMAGE,
				buttonXcoord,150,true);
		buttons[0].setNextGameState(GameState.LEVEL_SIGN);
		buttons[1] = new Button(CREDITSSELECTED_IMAGE,CREDITSUNSELECTED_IMAGE,
				buttonXcoord,250,false);
		buttons[1].setNextGameState(GameState.CREDITS);
		buttons[2] = new Button(HIGHSCORESSELECTED_IMAGE,HIGHSCORESUNSELECTED_IMAGE,
				buttonXcoord,350,false);
		buttons[2].setNextGameState(GameState.HIGH_SCORES);
		
		
	}
	
	@Override
	public void paint(Graphics g){
		g.drawImage(bgImage, 0,0,null);
	}
	
	@Override
	public void update(){
		if(changeTimer>0)changeTimer--;
		else{
			if(Keyboard.INSTANCE.getKeyState(Keyboard.Key.DOWN)){
				incSelection();
			}
			else if(Keyboard.INSTANCE.getKeyState(Keyboard.Key.UP)){
				decSelection();
			}
			changeTimer = CHANGE_PAUSE;

			for(int i=0; i<buttons.length; i++){
				if(i==selection){
					buttons[i].setSelected(true);
				}
				else buttons[i].setSelected(false);
			}
			
			if(Keyboard.INSTANCE.getKeyState(Keyboard.Key.SPACE)){
				buttons[selection].press();
			}
		}
	}
	
	public void setBgImage(Image bgImage){
		this.bgImage = bgImage;
	}
	
	// nested class
	public static class Button extends Entity{
		private Image selectedImage;
		private Image unselectedImage;
		private boolean selected;
		private GameState nextGameState;
		public Button(Image selectedImage, Image unselectedImage, int x, int y, boolean selected){
			super(selectedImage, x, y);
			this.selectedImage = selectedImage;
			this.unselectedImage = unselectedImage;
			setSelected(selected);
			//Main.log("selected image: "+selectedImage+"\nunselected image: "+unselectedImage);
			//Main.log(""+getImage());
		}
		
		public void setSelected(boolean selected){
			this.selected = selected;
			if(selected)setImage(selectedImage);
			else setImage(unselectedImage);
		}
		
		public void setNextGameState(GameState state){this.nextGameState=state;}
		public void press(){
			EntityManager.INSTANCE.setCurrentState(nextGameState);
		}
	}
	
	public List<Button> getButtons(){
		return Arrays.asList(buttons);
	}
	
	private void incSelection(){
		selection = (selection + 1)%buttons.length;
	}
	
	private void decSelection(){
		selection--;
		if(selection == -1) selection = buttons.length-1;
	}
}


