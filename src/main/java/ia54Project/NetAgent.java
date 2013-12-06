package ia54Project;

import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

public class NetAgent extends Agent{
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	
	NetAgent(String name) {
		super();
		setName(name); 
	}
	
	@Override
	public Status activate(Object... parameters) {
		GroupAddress gaddr = getOrCreateGroup(OrganizationNet.class);
		if(parameters[0] instanceof String) {
			if(parameters[0] == "collecteur") {
				if (requestRole(RoleSender.class,gaddr)==null) {
					return StatusFactory.cancel(this);
				}
			}
			else if(parameters[0] == "receiver")  {
				if (requestRole(RoleReceiver.class,gaddr)==null) {
					return StatusFactory.cancel(this);
				}
			}
		}
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		Status s = super.live();
//		StringMessage m = new StringMessage("Salut ptite bite");
//		broadcastMessage(m, (AgentAddress[]) null);
//		print("spaming");
//		if(getRole(getExistingGroup(OrganizationNet.class),CollecteurRole.class) != null) {
//			// switch to receiver
//			leaveRole(CollecteurRole.class, getExistingGroup(OrganizationNet.class));
//			requestRole(ReceiverRole.class, getExistingGroup(OrganizationNet.class));
//			return s;
//		}
//		
//		if(getRole(getExistingGroup(OrganizationNet.class),ReceiverRole.class) != null) {
//			// switch to receiver
//			leaveRole(ReceiverRole.class, getExistingGroup(OrganizationNet.class));
//			requestRole(CollecteurRole.class, getExistingGroup(OrganizationNet.class));
//			return s;
//		}
		
		return s;
	}

}
