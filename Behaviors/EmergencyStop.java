import lejos.hardware.Button;
import lejos.robotics.navigation.MovePilot;

public class EmergencyStop {
	private MovePilot pilot;
	public EmergencyStop(MovePilot pilot) {
		this.pilot = pilot;
	}
	
	public void action() {
		pilot.stop();
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		Button.ESCAPE.waitForPressAndRelease();
		return true;
	}
}
