import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import lejos.nxt.*;
import lejos.pc.comm.*;


public class NXTRobotService extends UnicastRemoteObject implements NXTRobotServiceInt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static NXTComm NXTConn;

	public NXTRobotService() throws RemoteException {
		super( );
		try {
			NXTConn = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
		}
		catch(NXTCommException e) {
			System.out.println("Error - creating NXT connection");
		}
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
}
