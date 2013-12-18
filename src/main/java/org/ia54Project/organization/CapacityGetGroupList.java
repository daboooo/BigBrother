package org.ia54Project.organization;

import java.util.Collection;

import org.janusproject.kernel.crio.capacity.Capacity;
import org.janusproject.kernel.crio.capacity.CapacityPrototype;
import org.janusproject.kernel.repository.Repository;

@CapacityPrototype(
		fixedOutput=Collection.class
)
public interface CapacityGetGroupList extends Capacity{

}
