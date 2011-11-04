import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.EventObject;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import lejos.nxt.Motor;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;

/*
 * This program is a simple program that
 * will drive a motor when the user presses a
 * arrow key form in the keyboard.
 *
 */
public class RadioControlAndEvent extends Frame implements Runnable, KeyListener, LightSensingEventListenerInt{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int FRAME_HEIGHT = 100;

	public static final int FRAME_WIDTH = 300;

	public static final int DELAY_MS = 100;

	public static final int COMMAND_NONE = 1;

	public static final int COMMAND_FORWARDS = 2;

	public static final int COMMAND_BACKWARDS = 3;

	private static final int DIRECTION_FORWARDS = 1;

	private static final int DIRECTION_BACKWARDS = 2;

	private int command;

	private int direction;

	static NXTComm NXTConn;

	/*
	 * Constructor.
	 */
	public RadioControlAndEvent() 
	{
		this.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		
		
		JCheckBox checkBox = new JCheckBox("Light Sensing Event");
		this.add(checkBox);
		
		checkBox.setFocusable(false);
		
		
		this.addKeyListener(this);
		this.setVisible(true);
		command = COMMAND_NONE;
		direction = DIRECTION_FORWARDS;
	}

	public void run() {		
		while (true) { /* loop forever */
			switch (command) {
			case COMMAND_NONE:
				setTitle("None");
				Motor.A.stop();
				break;
			case COMMAND_FORWARDS:
				setTitle("Forwards");
				direction = DIRECTION_FORWARDS;
				Motor.A.forward();
				break;
			case COMMAND_BACKWARDS:
				setTitle("Backwards");
				direction = DIRECTION_BACKWARDS;
				Motor.A.backward();
				break;
			default:
				System.out.println("unknown command " + command);
				System.exit(1);
			}

			try {
				Thread.sleep(DELAY_MS);
			} catch (Exception e) {
				System.out.println(e);
				System.exit(1);
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();

		switch (kc) {
		case java.awt.event.KeyEvent.VK_UP:
			command = COMMAND_FORWARDS;
			break;
		case java.awt.event.KeyEvent.VK_DOWN:
			command = COMMAND_BACKWARDS;
			break;
		default:
			command = COMMAND_NONE;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		command = COMMAND_NONE;
	}

	public void keyTyped(KeyEvent e) { /* do nothing */
	}

	/*
	 * Window closing event.
	 */
	protected void processWindowEvent(WindowEvent e) {		
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}

	/*
	 * Main.
	 */
	public static void main(String[] args) {
		try {
			NXTConn = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
		}
		catch(NXTCommException e) {
			System.out.println("Error - creating NXT connection");
		}

		
		//LEAVE ALL OF THIS ALONE!
		RadioControlAndEvent s = new RadioControlAndEvent();
		
		LightSensingEventSource source = new LightSensingEventSource(300);
		source.addEventListener(s);
		
		Thread t = new Thread(s);
		t.start();
		
		//THIS MUST STAY HERE!!!!
		source.run();
		//DO NOT MOVE!

		
	}

	public void handleLightSensingEvent(EventObject e) {
		LightSensingEvent lightEvent = (LightSensingEvent) e;
		
		System.out.println(lightEvent.toString());
		
	}
}