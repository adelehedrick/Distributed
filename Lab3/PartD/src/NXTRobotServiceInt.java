import lejos.nxt.*;
import lejos.pc.comm.*;

import java.rmi.*;

public interface NXTRobotServiceInt extends Remote {
	
	public void open()throws java.rmi.RemoteException;
	public void close()throws java.rmi.RemoteException;
	public void forward(char motorLabel)throws java.rmi.RemoteException;
	public void backward(char motorLabel)throws java.rmi.RemoteException;
	public void stop(char motorLabel)throws java.rmi.RemoteException;

}
