package ia54Project;

import org.janusproject.kernel.crio.capacity.Capacity;
import org.janusproject.kernel.crio.capacity.CapacityPrototype;
import org.janusproject.kernel.repository.Repository;
@CapacityPrototype(
		fixedOutput= Repository.class
)
public interface CapacityGetAgentRepository extends Capacity{


}
