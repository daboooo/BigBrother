package org.ia54Project.agent;

import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.status.Status;

public class MyAgent extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 843466501042007629L;

	@Override
	public Status live() {
		GroupAddress ga = getOrCreateGroup(MyOrganization.class);
		requestRole(MyRole.class, ga);
		return super.live();
	}
}
