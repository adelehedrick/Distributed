import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class represents the object server for the distributed NXT Commands
 * which implements the remote interface NXTRobotServiceInt Interface.
 * @author R. Liscano
 */

public class NXTRobotServiceServer  {

	public static void main(String args[]) {
      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
      String portNum, registryURL;
      try{
    	  int RMIPortNum = Integer.parseInt(args[0]);
    	  startRegistry(RMIPortNum);
    	  NXTRobotService exportedObj = new NXTRobotService();
    	  
    	  
    	  registryURL = "rmi://localhost:" + RMIPortNum + "/NXTRobotService";
    	  Naming.rebind(registryURL, exportedObj);
/**/     System.out.println
/**/        ("Server registered.  Registry currently contains:");
/**/     // list names currently in the registry
/**/     listRegistry(registryURL); 
		  System.out.println("NXTRobotService Server ready.");
      }// end try
      catch (Exception re) {
         System.out.println("Exception in NXTRobotService.main: " + re);
      } // end catch
  } // end main

   // This method starts a RMI registry on the local host, if it
   // does not already exists at the specified port number.
   private static void startRegistry(int RMIPortNum)
      throws RemoteException{
      try {
         Registry registry = LocateRegistry.getRegistry(RMIPortNum);
         registry.list( );  // This call will throw an exception
                            // if the registry does not already exist
      }
      catch (RemoteException e) { 
         // No valid registry at that port.
/**/     System.out.println
/**/        ("RMI registry cannot be located at port " 
/**/        + RMIPortNum);
         Registry registry = 
            LocateRegistry.createRegistry(RMIPortNum);
/**/        System.out.println(
/**/           "RMI registry created at port " + RMIPortNum);
      }
   } // end startRegistry

  // This method lists the names registered with a Registry object
  private static void listRegistry(String registryURL)
     throws RemoteException, MalformedURLException {
       System.out.println("Registry " + registryURL + " contains: ");
       String [ ] names = Naming.list(registryURL);
       for (int i=0; i < names.length; i++)
          System.out.println(names[i]);
  } //end listRegistry
     
} // end class
