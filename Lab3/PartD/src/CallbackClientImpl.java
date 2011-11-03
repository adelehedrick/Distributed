import java.rmi.*;
import java.rmi.server.*;

/**
 * This class implements the remote interface 
 * CallbackClientInterface.
 * @author M. L. Liu
 */

public class CallbackClientImpl extends UnicastRemoteObject
     implements CallbackClientInterface {
  
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public CallbackClientImpl() throws RemoteException {
      super( );
   }

   public String notifyMe(String message){
      String returnMessage = "Call back received: " + message;
      System.out.println(returnMessage);
      return returnMessage;
   }      

}// end CallbackClientImpl class   
