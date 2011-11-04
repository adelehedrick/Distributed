import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.EventObject;
import java.util.Vector;

import lejos.nxt.*;
import lejos.pc.comm.*;


public class NXTRobotService extends UnicastRemoteObject implements NXTRobotServiceInt, LightSensingEventListenerInt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static NXTComm NXTConn;
	
	private Vector<RadioControlRMIClientInt> clientList;

	public NXTRobotService() throws RemoteException {
		super( );
		try {
			NXTConn = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
		}
		catch(NXTCommException e) {
			System.out.println("Error - creating NXT connection");
		}
		
		clientList = new Vector<RadioControlRMIClientInt>();
		
		LightSensingEventSource source = new LightSensingEventSource(300);
		source.addEventListener(this);
	}

	public void open()throws RemoteException { 
	}
	
	public void close()throws RemoteException { 
	}

	public void forward(char motorLabel)throws RemoteException {
		switch (motorLabel) {
		case 'A':
			Motor.A.forward();
			break;
		case 'B':
			Motor.B.forward();
			break;
		case 'C':
			Motor.C.forward();
			break;
		default:
		}
	}
	
	public void backward(char motorLabel)throws RemoteException {
		switch (motorLabel) {
		case 'A':
			Motor.A.backward();
			break;
		case 'B':
			Motor.B.backward();
			break;
		case 'C':
			Motor.C.backward();
			break;
		default:
		}
	}
	
	public void stop(char motorLabel)throws RemoteException {
		switch (motorLabel) {
		case 'A':
			 Motor.A.stop();
		case 'B':
			 Motor.B.stop();
		case 'C':
			 Motor.C.stop();
		default:
		}
	}

	@Override
	public void handleLightSensingEvent(EventObject e) {
		System.out.println(
	       "**************************************\n"
	        + "Callbacks initiated ---");
	    for (int i = 0; i < clientList.size(); i++){
	      System.out.println("doing "+ i +"-th callback\n");    
	      // convert the vector object to a callback object
	      RadioControlRMIClientInt nextClient = 
	        (RadioControlRMIClientInt)clientList.elementAt(i);
	      // invoke the callback method
	        try {
				nextClient.notifyMe("NOTIFIED");
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }// end for
	    System.out.println("********************************\n" +
	                       "Server completed callbacks ---");
		
	}

	  public synchronized void registerForCallback(
			  RadioControlRMIClientInt callbackClientObject)
	    throws java.rmi.RemoteException{
	      // store the callback object into the vector
	      if (!(clientList.contains(callbackClientObject))) {
	         clientList.addElement(callbackClientObject);
	      System.out.println("Registered new client ");
	    } // end if
	  }  

	// This remote method allows an object client to 
	// cancel its registration for callback
	// @param id is an ID for the client; to be used by
	// the server to uniquely identify the registered client.
	  public synchronized void unregisterForCallback(
			  RadioControlRMIClientInt callbackClientObject) 
	    throws java.rmi.RemoteException{
	    if (clientList.removeElement(callbackClientObject)) {
	      System.out.println("Unregistered client ");
	    } else {
	       System.out.println(
	         "unregister: clientwasn't registered.");
	    }
	  } 
}
