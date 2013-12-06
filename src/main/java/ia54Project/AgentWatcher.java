package ia54Project;

import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

public class AgentWatcher extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public Status activate(Object... parameters) {

		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		Status s = super.live();

		return s;
	}

}
