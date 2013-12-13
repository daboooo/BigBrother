package org.ia54Project.organization;

import org.ia54Project.dataModel.DataModel;
import org.ia54Project.dataModel.MessageDataModel;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.status.Status;

public class RoleControlManager extends Role{

	@Override
	public Status live() {
		Message m = getMessage();
		if(m != null) {
			print("got message:" + m);
			print("SENDER ADDRESS: " + m.getSender());
			RoleAddress guiManager = getRoleAddress(getOrCreateGroup(OrganizationController.class), RoleGUIManager.class, RoleAddress.class.cast(m.getSender()).getPlayer());
			print("SHOULD BE:  " + guiManager);
			
			if(m.getSender() == guiManager) {
				if(m instanceof StringMessage) {
					// request of dataModel
					if(StringMessage.class.cast(m).getContent().equals("request")) {
						// sending response
						print("sending RESPONSE");
						sendMessage(RoleGUIManager.class, new MessageDataModel(new DataModel(null)));
					}
				}
				
			}
		}
		return null;
	}

}
