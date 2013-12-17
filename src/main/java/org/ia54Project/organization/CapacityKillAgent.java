package org.ia54Project.organization;

import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.crio.capacity.Capacity;
import org.janusproject.kernel.crio.capacity.CapacityPrototype;
import org.janusproject.kernel.repository.Repository;



@CapacityPrototype(
		fixedParameters= AgentAddress.class
)

public interface CapacityKillAgent extends Capacity {

}
