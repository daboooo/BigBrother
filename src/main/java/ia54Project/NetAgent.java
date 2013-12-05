package ia54Project;

import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.status.Status;

public class NetAgent extends Agent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Status activate(Object... parameters) {
		GroupAddress groupAddress = getOrCreateGroup(OrganizationNet.class);
		requestRole(RoleReceiver.class, groupAddress);
		
		return super.activate(parameters);
	}
	
	public Status live() {
		super.live();
//		Message m = getMessage();
//		
//		if(m!=null) {
//			print(m);
//		}
//		else {
//			//print("waiting");
//		}
		
	    return null;
	}
}
