package org.ia54Project.organization;

import org.ia54Project.organization.RoleCollecteur.State;
import org.janusproject.kernel.crio.core.HasAllRequiredCapacitiesCondition;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

public class RoleExecutant extends Role{
	
	@Override
	public Status activate(Object... parameters) { 	
		addObtainCondition(new HasAllRequiredCapacitiesCondition(CapacityGetAgentRepository.class));
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		//print("Je suis dans le role executant");
		return null;
	}

}
