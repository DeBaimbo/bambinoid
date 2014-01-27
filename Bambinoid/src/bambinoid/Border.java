// several instances (no singleton)

package bambinoid;

public class Border extends Entity {
	private boolean deathBorder = false; // does ball die when touches border?
	public Border(String imageFileName){
		super(imageFileName);
	}
	public Border(String imageFileName, double x, double y){
		super(imageFileName,x,y);
	}
	// useful for down border
	public Border(String imageFileName, double x, double y, boolean deathBorder){
		this(imageFileName,x,y);
		this.deathBorder = deathBorder;
	}
	
	public boolean isDeathBorder(){return deathBorder;}
	public void setDeathBorder(boolean death){this.deathBorder=death;}
}
