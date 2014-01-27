// the main loop
// singleton

package bambinoid;

public class Updater implements Runnable {
	public static Updater INSTANCE = new Updater();
	
	public static final int LFPS = 50; // logic frames per second
	public static final double DT = 1.0/(double)LFPS; // delta time (in seconds)
	public static final double NANODT = DT*1000000000; // delta time (in nanoseconds)
	public static int fps = 0; // frames per second, calculated in real time
	private static int calcFps = 0; // to store
	
	@Override
	public void run() {
		boolean ticked = false;
		long timeOld = System.nanoTime();
		long unprocessedTime = 0L;
		int fpsShowTimer = 1000000000; //
		while(true){
			Screen.INSTANCE.setFocusable(true);
			Screen.INSTANCE.requestFocus();
			ticked = false;
			long timeNow = System.nanoTime();
			unprocessedTime += (timeNow - timeOld);
			timeOld = timeNow;
			while(unprocessedTime >= NANODT){
				EntityManager.INSTANCE.update(); // update logic
				unprocessedTime -= NANODT;
				ticked = true;
				//
				fpsShowTimer -= NANODT;
				if(fpsShowTimer <= 0){
					//Main.log(fps+" fps");
					fpsShowTimer = 1000000000;
					fps = calcFps;
					calcFps = 0;
				}
				//
			}
			if(ticked){
				Screen.INSTANCE.repaint(); // update graphics
				calcFps++;	
			}
			else{
				try{Thread.sleep(1);}
				catch(InterruptedException e){Main.err("interrupted?!");}
			}
		}
	}

}
