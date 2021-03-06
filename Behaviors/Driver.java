import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {

	public static void main(String[] args) {
		
		//Create sensors
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S2);
		EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S3);
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S4);
		
		//Create Pilot
		MovePilot pilot = getPilot(MotorPort.A, MotorPort.D, 43, 120);
		
		//Create Behaviours
		Behavior trundle = new Trundle(pilot);
		Behavior backup = new Backup(pilot, us);
		Behavior dark = new Dark(pilot, cs);
		Behavior light = new Light(pilot, cs);
		Behavior emergencyStop = new EmergencyStop(pilot, ts);
		Behavior batteryLevel = new BatteryLevel();
		Behavior bluetooth = new Bluetooth();
		Behavior calibrate = new Calibrate(cs);

		//Create Arbitrator and start it
		Arbitrator ab = new Arbitrator(new Behavior[] {batteryLevel, emergencyStop, calibrate, bluetooth, backup, dark, light, trundle});
		ab.go();
		
	}
	
	private static MovePilot getPilot(Port left, Port right, int diam, int offset) { //Code to create pilot
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(left);
		Wheel wLeft = WheeledChassis.modelWheel(mL, diam).offset(-offset/2);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(right);
		Wheel wRight = WheeledChassis.modelWheel(mR, diam).offset(offset/2);
		Chassis chassis = new WheeledChassis((new Wheel[] {wRight, wLeft}), WheeledChassis.TYPE_DIFFERENTIAL);
		return new MovePilot(chassis);
		
	}

}
