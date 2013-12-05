package ia54Project;

import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.status.Status;

public class AgentSender extends Agent{
	public Status activate(Object... parameters) {
		GroupAddress groupAddress = getOrCreateGroup(OrganizationNet.class);
		requestRole(RoleSender.class, groupAddress);
		
		return super.activate(parameters);
	}
	
	public Status live() {
		super.live();
	    print("hello world!\n");
	    killMe();
	    return null;
	}
}
