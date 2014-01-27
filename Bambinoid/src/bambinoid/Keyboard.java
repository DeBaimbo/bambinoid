// a virtual keyboard made of a few keys and a toggle
// singleton

package bambinoid;

import java.util.EnumMap;

public class Keyboard {
	public final static Keyboard INSTANCE = new Keyboard();
	public enum Key{UP,DOWN,LEFT,RIGHT,SPACE,P;};
	public enum Toggle{PAUSE;}
	private final static boolean KEY_LOGGING = false;
	private EnumMap<Key,Boolean> keyStates = new EnumMap<>(Key.class);
	/*workaround*/private EnumMap<Key,Boolean> keyWantedStates = new EnumMap<>(Key.class);
	private EnumMap<Toggle,Boolean> toggleStates = new EnumMap<>(Toggle.class);
	
	private Keyboard(){
		for(Key key: Key.values()){
			keyStates.put(key,false);
			keyWantedStates.put(key, false);
		}
		for(Toggle toggle: Toggle.values()){
			toggleStates.put(toggle, false);
		}
	}
	
	// methods to be called by the keybinding actions
	public void pressKey(Key key){ // neglects P pressed
		keyStates.put(key, true);
		keyWantedStates.put(key, true);
		if(KEY_LOGGING)Main.log("key " + key + " pressed");
	}
	private void releaseKey(Key key){ // private!
		keyStates.put(key, false);
		keyWantedStates.put(key, false);
		if(KEY_LOGGING)Main.log("key " + key + " released");
		if(key == Key.P){
			toggleStates.put(Toggle.PAUSE,!toggleStates.get(Toggle.PAUSE));
			Main.log("pause: "+toggleStates.get(Toggle.PAUSE));
		}
	}
	public void tryToReleaseKey(final Key key){
		keyWantedStates.put(key, false);
		(new Thread(
				new Runnable(){
					@Override
					public void run() {
						try{Thread.sleep(5);}
						catch(Exception e){;}
						if(keyWantedStates.get(key)==false)releaseKey(key);
					}
					
				}
		)).start();
	}
	// end methods called by keybindings
	
	// methods to be called by any part of the program which wants
	// to receive keyboard input
	/**
	 * @param key is the key you want to know if it's pressed
	 * @return true if it's pressed
	 */
	public boolean getKeyState(Key key){
		return keyStates.get(key);
	}
	/**
	 * @param toggle is the toggle you want to know if it's on
	 * @return true if it's on
	 */
	public boolean getToggleState(Toggle toggle){
		return toggleStates.get(toggle);
	}
	
}
