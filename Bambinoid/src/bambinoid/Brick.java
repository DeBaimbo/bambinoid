package bambinoid;

import java.awt.Image;

public class Brick extends Entity {
	private Type type;
	private int lives;
	private Prize prize = null;
	private static final int PRIZE_PROBABILITY = 20; // in percentage

	private Brick(Type type, double x, double y){
		setX(x);
		setY(y);
		setType(type);
		// maybe assign prize
		if(Main.ranGen.nextInt(100) < PRIZE_PROBABILITY) assignPrize();
	}
	
	// static factory, returns a brick of a certain type
	public static Brick create(String brickCode, double x, double y){
		return new Brick(Type.fromCode(brickCode),x,y);
	}
	
	public void hit(){
		Main.log("start function Brick.hit()");
		EntityManager.INSTANCE.getGame().incScore(getHitPoints());
		Main.log("type:"+getType());
		if(getType()==Type.DIAMOND) return;
		decLives();
		if(getLives()<=0)destroy();
		Main.log("remaining lives of brick:"+getLives());
	}
	
	public void destroy(){
		EntityManager.INSTANCE.getGame().incScore(getDestroyPoints());
		if(type!=Type.DIAMOND)
			EntityManager.INSTANCE.getGame().decRemainingBricks();
		if(hasPrize()){
			EntityManager.INSTANCE.add(GameState.PLAY, prize);
			prize = null;
		}
		setActive(false);
		
	}
	
	private void assignPrize(){
		prize = new Prize
			(Prize.Type.getRandomInstance(),(int)getCenterX()-Prize.WIDTH/2,(int)getCenterY());
	}
	
	private boolean hasPrize(){return prize!=null;}
	
	// nested enum
	public static enum Type{
		HARD1("brick-hard1.png","h1",20,80,2),
		HARD2("brick-hard2.png","h2",25,130,3),
		HARD3("brick-hard3.png","h3",40,200,4),
		SOFT1("brick-soft1.png","s1",0,1,1),
		SOFT2("brick-soft2.png","s2",0,3,1),
		SOFT3("brick-soft3.png","s3",0,5,1),
		DIAMOND("brick-diamond.png","di",15,1000,-1);
		
		private Image image;
		private String code;
		private int hitPoints;
		private int destroyPoints;
		private int initialLives; // how many hits to destroy
		
		Type(String imageFileName, String code,int hitPoints, int destroyPoints, int initialLives){
			this.image = Main.loadImage(imageFileName);
			this.code = code;
			this.hitPoints = hitPoints;
			this.destroyPoints = destroyPoints;
			this.initialLives = initialLives;
		}
		
		public String getCode(){return code;}
		public int getHitPoints(){return hitPoints;}
		public int getDestroyPoints(){return destroyPoints;}
		public int getInitialLives(){return initialLives;}
		public Image getImage(){return image;}
		public static Type fromCode(String code){
			for(int i=0; i<values().length; i++){
				if(values()[i].getCode().equals(code)) 
					return values()[i];
			}
			throw new IllegalArgumentException("brick type code not recognized!");
		}
	}
	// end nested enum
	
	private void setType(Type type){
		this.type = type; 
		this.lives=type.getInitialLives();
		setWidth(type.getImage().getWidth(null));
		setHeight(type.getImage().getHeight(null));
	}
	
	public Type getType(){return type;}
	
	public int getHitPoints(){return type.getHitPoints();}
	public int getDestroyPoints(){return type.getDestroyPoints();}
	public int getLives(){return lives;}
	@Override
	public Image getImage(){return type.getImage();}
	public void setLives(int lives){this.lives = lives;}
	public void decLives(){setLives(getLives()-1);}
	
}
