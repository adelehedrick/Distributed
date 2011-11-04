import java.rmi.*;
import java.rmi.server.*;

/*
 * This program is a simple program that
 * will drive a motor when the user presses a
 * arrow key form in the keyboard.
 *
 */
public class RadioControlRMIClientImpl extends UnicastRemoteObject implements RadioControlRMIClientInt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	RadioControlRMIClient client;

	public RadioControlRMIClientImpl(RadioControlRMIClient c) throws RemoteException {
	      super( );
	      
	      client = c;
	   }



	@Override
	public String notifyMe(String message) throws RemoteException {
		String returnMessage = "Call back received: " + message;
	    System.out.println(returnMessage);
	    
	    client.checkBox.setSelected(true);
	    return returnMessage;
	}

}