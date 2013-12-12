package org.ia54Project.organization;

import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.status.Status;

public class RoleControlManager extends Role{

	@Override
	public Status live() {
		Object message = getMemorizedData("MY_DATA");
		
		print("je suis dans le role controlManager et je lis dans la mémoire : " + message);
		
		print("je suis dans le role controlManager et j'envoie le message : " + message);
		
		if(message instanceof Message) {
			broadcastMessage(RoleGUIManager.class, (Message)message);
		}
		
		return null;
	}

}
