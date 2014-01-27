package bambinoid;

import java.awt.Image;

public class Prize extends Entity implements Updatable{
	private Type type;
	private static final double SPEED = 2.0; // in px/dt
	public static final int WIDTH = Type.getWidth();
	public static final int HEIGHT = Type.getHeight();
	
	public Prize(Type type, int x, int y){
		this.type = type;
		setX(x);
		setY(y);
		setVy(SPEED);
		setWidth(type.getImage().getWidth(null));
		setHeight(type.getImage().getHeight(null));
	}
	
	public Image getImage(){return type.getImage();}
	public int getPoints(){return type.getPoints();}
	public Type getType(){return type;}
	@Override
	public String toString(){return type.toString();}
	@Override
	public void update(){
		setY(getY()+getVy());
	}
	
	
	// nested enum
	public static enum Type{
		FASTBALL("prize-fastball.png",3),
		GLUE("prize-glue.png",100),
		GROWER("prize-grower.png",125),
		IRONBALL("prize-ironball.png",199),
		LIFE("prize-life.png",1000),
		MISSILES("prize-missiles.png",500),
		MULTIBALL("prize-multiball.png",350),
		SHRINKER("prize-shrinker.png",1),
		SLOWBALL("prize-slowball.png",13);
		
		private Image image;
		private int points;
		
		Type(String imageFileName, int points){
			this.image = Main.loadImage(imageFileName);
			if(this.image == null) Main.err("problem loading prize image");
			this.points = points;
		}
		
		public Image getImage(){return image;}
		public static int getWidth(){return FASTBALL.getImage().getWidth(null);}
		public static int getHeight(){return FASTBALL.getImage().getHeight(null);}
		public int getPoints(){return points;}
		public static Type getRandomInstance(){
			return values()[Main.ranGen.nextInt(values().length)];
		}
		
	}
	// end nested enum
}
