package bambinoid;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public enum Level {
	ONE(1,
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 s1 s1 s1 s1 s1 s1 00 00 00 "+
			"00 00 00 s1 s1 s1 s1 s1 s1 00 00 00 "+
			"00 00 00 s1 s1 s1 s1 s1 s1 00 00 00 "+
			"00 00 00 s1 s1 s1 s1 s1 s1 00 00 00 "+
			"00 00 00 s1 s1 s1 s1 s1 s1 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "
			),
	TWO(2,
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 s3 s3 s3 s3 s3 00 s2 s2 s2 00 00 "+
			"00 00 00 s3 00 00 00 s2 00 00 s2 00 "+
			"00 00 00 s3 00 00 00 s2 00 00 s2 00 "+
			"00 00 00 s3 00 00 00 s2 s2 s2 00 00 "+
			"00 00 00 s3 00 00 00 s2 00 00 s2 00 "+
			"00 00 00 s3 00 00 00 s2 00 00 s2 00 "+
			"00 s3 s3 s3 00 00 00 s2 s2 s2 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "
			),
	THREE(3,
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"s2 s2 s2 s2 s2 s2 s2 s2 s2 s2 s2 s2 "+
			"s1 s1 s1 s1 s1 s1 s1 s1 s1 s1 s1 s1 "+
			"s3 s3 s3 s3 s3 s3 s3 s3 s3 s3 s3 s3 "+
			"s2 s2 s2 s2 s2 s2 s2 s2 s2 s2 s2 s2 "+
			"s1 s1 s1 s1 s1 s1 s1 s1 s1 s1 s1 s1 "+
			"s3 s3 s3 s3 s3 s3 s3 s3 s3 s3 s3 s3 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "
			),
	FOUR(4,
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 s1 s1 s1 s1 00 00 s1 s1 s1 s1 00 "+
			"00 s1 s2 s2 s1 00 00 s1 s2 s2 s1 00 "+
			"00 s1 s2 s2 s1 00 00 s1 s2 s2 s1 00 "+
			"00 s1 s2 s2 s1 00 00 s1 s2 s2 s1 00 "+
			"00 s1 s1 s1 s1 00 00 s1 s1 s1 s1 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "
			),
	FIVE(5,
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 s1 00 00 s1 00 00 00 00 "+
			"00 00 00 s1 s1 00 00 s1 s1 00 00 00 "+
			"00 00 s2 s2 s2 00 00 s3 s3 s3 00 00 "+
			"00 00 s2 s2 s2 00 00 s3 s3 s3 00 00 "+
			"00 h1 h1 h1 h1 h1 h1 h1 h1 h1 h1 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "
			),
	SIX(6,
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 s1 s1 s1 s1 s1 s1 s1 s1 s1 00 00 "+
			"00 h2 h2 h2 h2 h2 h2 h2 h2 h2 00 00 "+
			"00 s3 s3 s3 s3 s3 s3 s3 s3 s3 00 00 "+
			"00 s1 00 s2 00 s3 00 s2 00 s1 00 00 "+
			"00 h1 00 h2 00 h3 00 h2 00 h1 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "
			),
	SEVEN(7,
			"di 00 00 00 00 00 00 00 00 00 00 di "+
			"di 00 00 00 00 s2 s2 00 00 00 00 di "+
			"00 00 00 00 00 s1 s1 00 00 00 00 00 "+
			"00 00 00 00 00 s1 s1 00 00 00 00 00 "+
			"00 00 00 00 00 s1 s1 00 00 00 00 00 "+
			"00 00 00 00 00 s1 s1 00 00 00 00 00 "+
			"00 00 00 00 00 s1 s1 00 00 00 00 00 "+
			"00 00 00 di di di di di di 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "
			),
	EIGHT(8,
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 s1 s1 00 00 00 00 00 "+
			"00 00 00 00 s2 s2 s2 s2 00 00 00 00 "+
			"00 00 00 s3 s3 s3 s3 s3 s3 00 00 00 "+
			"00 00 h1 h1 h1 h1 h1 h1 h1 h1 00 00 "+
			"00 h2 h2 h2 h2 h2 h2 h2 h2 h2 h2 00 "+
			"h3 h3 h3 h3 h3 h3 h3 h3 h3 h3 h3 h3 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "
			),
	NINE(9,
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 s2 00 00 00 00 00 00 s2 00 00 "+
			"00 s2 s1 s2 00 00 00 00 s2 s1 s2 00 "+
			"di s1 s1 s1 di 00 00 di s1 s1 s1 di "+
			"00 di s1 di 00 00 00 00 di s1 di 00 "+
			"00 00 di 00 00 00 00 00 00 di 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "
			),
	TEN(10,
			"s1 s1 s1 s1 s1 00 00 00 00 00 00 00 "+
			"s1 s1 s1 s1 s1 00 00 00 00 00 00 00 "+
			"s1 s1 s1 s1 s1 00 00 00 00 00 00 00 "+
			"s1 s1 s1 s1 s1 00 00 00 00 00 00 00 "+
			"di di di di di di di di di h3 di di "+
			"di di di di di di di di di h3 di di "+
			"di di di di di di di di di h3 di di "+
			"di di di di di di di di di h3 di di "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "+
			"00 00 00 00 00 00 00 00 00 00 00 00 "
			)
				
			;
	private int number; // level number
	private String brickCodesTogether; // all brick codes
	Level(int number, String brickCodesTogether){
		this.number = number;
		this.brickCodesTogether = brickCodesTogether;
	}
	
	public List<Brick> getBricks(Rectangle2D gameRectangle){
		List<Brick> list = new ArrayList<Brick>();
		String brickCodes[] = brickCodesTogether.split(" ");
		int x= (int) gameRectangle.getX();
		int y= (int) gameRectangle.getY();
		for(int i=0; i<brickCodes.length; i++){
			if(brickCodes[i].equals("00"))continue;
			//Main.log("creating brick at x:"+(x+(i%12))+"  y:"+(y+(i/12)));
			list.add(Brick.create(brickCodes[i],x+(i%12)*30,y+(i/12)*15));
		}
		return list;
	}
	
	public List<Brick> getBricks(){return getBricks(new Rectangle());}
	
	public static Level get(int number){
		for(int i=0; i<values().length; i++){
			if(values()[i].number==number) return values()[i];
		}
		throw new IllegalArgumentException("level number "+number+" not found");
	}
	
	public int getNumber(){return number;}
	public int calcNormalBricks(){
		int num = 0;
		List<Brick> bricks = getBricks();
		for(Brick brick : bricks){
			if(brick.getType()!=Brick.Type.DIAMOND) num++;
		}
		return num;
	}
	
	public static int totalNumber(){return values().length;}
	
}
