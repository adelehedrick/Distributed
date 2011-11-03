import java.util.ArrayList;
import java.util.Iterator;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class LightSensingEventSource implements Runnable{
	
	private static int MIN_TRIGGER = 30;
	boolean event_triggered = false;
	int delay_ms=100;
	
	private ArrayList<LightSensingEventListenerInt> _listeners = new ArrayList<LightSensingEventListenerInt>();
	private LightSensor lightSensor;
	
	LightSensingEventSource(int delay) {
		delay_ms = delay;
		/* Create light sensor object */
		lightSensor = new LightSensor(SensorPort.S1);
	}
	
	public void LightSensingEventSource(){
	}
	public synchronized void addEventListener(LightSensingEventListenerInt listener)	{
		_listeners.add(listener);
	}
	public synchronized void removeEventListener(LightSensingEventListenerInt listener)	{

		_listeners.remove(listener);
	}
	
	public void run() {
	
		int dist=0;
		while(true) {
			
			dist = lightSensor.getLightValue();
			System.out.println("Light Sensor Distance: " + dist);
			
			/* if dist > MIN_TRIGGER and event_trigger == true then
			 * reset the trigger.
			 */
			
			if(event_triggered && dist >= MIN_TRIGGER)
				event_triggered = false;
			
			/* if dist is less than MIN_TRIGGER send event */
			else if(!event_triggered && dist < MIN_TRIGGER) {
				System.out.println("EVENT IS ABOUT TO BE FIRED!");
				event_triggered = true;
				fireEvent();
			}
			try {
				Thread.sleep(delay_ms);
			} catch (Exception e) {
				System.out.println(e);
				System.exit(1);
			}
		}
	}

	// call this method whenever you want to notify
	//the event listeners of the particular event
	private synchronized void fireEvent()	{
		LightSensingEvent event = new LightSensingEvent(this);
		Iterator<LightSensingEventListenerInt> i = _listeners.iterator();
		while(i.hasNext())	{
			i.next().handleLightSensingEvent(event);
		}
	}
}