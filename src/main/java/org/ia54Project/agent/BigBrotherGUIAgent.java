package org.ia54Project.agent;

import org.ia54Project.organization.OrganizationController;
import org.ia54Project.organization.RoleGUIManager;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

/**
 * A simple agent taking the role RoleGUIManager
 * @author Arnaud Roblin, Julien Benichou
 *
 */
public class BigBrotherGUIAgent extends Agent{
	private static final long serialVersionUID = 449664383910713509L;

	public BigBrotherGUIAgent() {
		
	}
	
	@Override
	public Status activate(Object... parameters) {
		GroupAddress gaddr = getOrCreateGroup(OrganizationController.class);
		if(requestRole(RoleGUIManager.class, gaddr) != null) {
			return StatusFactory.ok("guiAgent");
		}
		return super.activate(parameters);
	}
	
	@Override
	public Status live() {


		return super.live();
	}
	
	


}
