// a 2-d mathematical vector, immutable

package bambinoid;

public class Vec {
	public final double x;
	public final double y;
	private final static Vec ZERO = new Vec(0.0,0.0);
	
	private Vec(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public static Vec newCartesian(double x, double y){
		return new Vec(x,y);
	}
	
	public static Vec newPolar(double module, double phase){// phase in degrees
		if(Double.compare(module, 0.0)<0.0) 
			throw new IllegalArgumentException("module must be non-negative");
		else if(Double.compare(module,0.0)==0.0) return ZERO;
		else{
			double x = module*Math.cos(phase*Math.PI/180.0);
			double y = module*Math.sin(phase*Math.PI/180.0);
			return new Vec(x,y);
		}
	}
	
	public Vec plus(Vec v2){
		return new Vec(x+v2.x,y+v2.y);
	}
	
	public double getModule(){
		return Math.sqrt(x*x+y*y);
	}
	
	public double getPhase(){
		if(this.equals(ZERO)) 
			throw new IllegalStateException("there's no phase for the zero vector");
		double radians = 0.0;
		if(x>0 && y>0) radians = Math.atan(y/x);
		else if(x>0 && y<0) radians = Math.atan(y/x) + 2*Math.PI;
		else if(x<0 && y>0) radians = Math.atan(y/x) + Math.PI;
		else if(x<0 && y<0) radians = Math.atan(y/x) + Math.PI;
		else if(x==0 && y>0) radians = Math.PI/2.0;
		else if(x==0 && y<0) radians = 3.0*Math.PI/2.0;
		else if(y==0 && x>0) radians = 0.0;
		else if(y==0 && x<0) radians = Math.PI;
		double phase = radians*180.0/Math.PI;
		return phase;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Vec)) return false;
		Vec v2 = (Vec) o;
		return x==v2.x && y==v2.y;
	}
	
	@Override
	public String toString(){
		return String.format("x = %5.2f\ty = %5.2f", x,y);
	}
}
