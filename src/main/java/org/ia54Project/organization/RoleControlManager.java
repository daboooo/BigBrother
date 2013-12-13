package org.ia54Project.organization;

import org.janusproject.kernel.agentsignal.Signal;
import org.janusproject.kernel.agentsignal.SignalListener;
import org.janusproject.kernel.agentsignal.SignalPolicy;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.status.Status;

public class RoleControlManager extends Role{
	private final SignalListener signalListener = new MySignalListener();

	public Status activate(Object... parameters) {
	   getSignalManager().setPolicy(SignalPolicy.FIRE_SIGNAL);
	   addSignalListener(signalListener);
	   
	   return null;
	}
	
	@Override
	public Status live() {
		Object message = getMemorizedData("MY_DATA");
		
		//print("je suis dans le role controlManager et je lis dans la mémoire : " + message);
		
		//print("je suis dans le role controlManager et j'envoie le message : " + message);
		
		if(message instanceof Message) {
			broadcastMessage(RoleGUIManager.class, (Message)message);
		}
		
		return null;
	}

	private class MySignalListener implements SignalListener {
		public void onSignal(Signal signal) {
			//print("je suis dans le role controlManager et je reçoit le signal : "+ signal.getValueAt(0));
			
		}
	}
}
