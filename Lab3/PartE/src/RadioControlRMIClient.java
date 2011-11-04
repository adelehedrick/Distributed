import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JCheckBox;

/*
 * This program is a simple program that
 * will drive a motor when the user presses a
 * arrow key form in the keyboard.
 *
 */
public class RadioControlRMIClient extends Frame implements Runnable, KeyListener, RadioControlRMIClientInt {

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

	// NXTComm NXTConn;
	
	static NXTRobotServiceInt h;
	
	JCheckBox checkBox;

	/*
	 * Constructor.
	 */
	public RadioControlRMIClient() {
		this.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		
		checkBox = new JCheckBox("Light Source Event");
		checkBox.setFocusable(false);
		this.add(checkBox);
		
		this.addKeyListener(this);
		this.setVisible(true);
		command = COMMAND_NONE;
		direction = DIRECTION_FORWARDS;
	}

	public void run() {		
		while (true) { /* loop forever */
			try {
				switch (command) {
				case COMMAND_NONE:
					setTitle("None");
					
						h.stop('A');
					
					break;
				case COMMAND_FORWARDS:
					setTitle("Forwards");
					direction = DIRECTION_FORWARDS;
					h.forward('A');
					//Motor.A.forward();
					break;
				case COMMAND_BACKWARDS:
					setTitle("Backwards");
					direction = DIRECTION_BACKWARDS;
					h.backward('A');
					//Motor.A.backward();
					break;
				default:
					System.out.println("unknown command " + command);
					System.exit(1);
				}
			
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
		/*try {
			NXTConn = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
		}
		catch(NXTCommException e) {
			System.out.println("Error - creating NXT connection");
		}*/

		int RMIPortNum = Integer.parseInt(args[0]);
		
		String registryURL = "rmi://localhost:" + RMIPortNum + "/NXTRobotService";  
		      // find the remote object and cast it to an 
		      //   interface object
		try {
			h = (NXTRobotServiceInt)Naming.lookup(registryURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		      
		RadioControlRMIClient s = new RadioControlRMIClient();
		
		
		try {
			h.registerForCallback(s);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread t = new Thread(s);
		t.start();
	}

	@Override
	public String notifyMe(String message) throws RemoteException {
		String returnMessage = "Call back received: " + message;
	    System.out.println(returnMessage);
	    
	    checkBox.setSelected(true);
	    return returnMessage;
	}
}