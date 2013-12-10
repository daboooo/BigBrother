package org.janusproject.kernel.network.jxse.agent;

import ia54Project.CapacityGetAgentRepository;
import ia54Project.OrganizationNet;
import ia54Project.RoleSender;

import java.util.EventListener;
import java.util.Set;

import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.AgentActivator;
import org.janusproject.kernel.channels.Channel;
import org.janusproject.kernel.channels.ChannelInteractable;
import org.janusproject.kernel.crio.capacity.Capacity;
import org.janusproject.kernel.crio.capacity.CapacityContext;
import org.janusproject.kernel.crio.capacity.CapacityImplementation;
import org.janusproject.kernel.crio.capacity.CapacityImplementationType;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.network.NetworkAdapter;
import org.janusproject.kernel.network.jxse.agent.JxtaJxseKernelAgent;
import org.janusproject.kernel.repository.Repository;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

public class MonitoredKernelAgent extends JxtaJxseKernelAgent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8826290855685530386L;
	
	MonitoredKernelAgent(AgentActivator activator, Boolean commitSuicide,
			EventListener startUpListener, String applicationName,
			NetworkAdapter networkAdapter) {
		super(activator, commitSuicide, startUpListener, applicationName,
				networkAdapter);
		launchHeavyAgent(new SuperAgent(),"superAgent");
		
	}


	private class SuperAgent extends Agent implements ChannelInteractable {
		 
		  /**
		 * 
		 */
		private static final long serialVersionUID = 6935534328201480630L;

		

		@Override
		public Status activate(Object... parameters) {
			getCapacityContainer().addCapacity(new CapacityImplGetAgentRepository());
			GroupAddress gaddr = getOrCreateGroup(OrganizationNet.class);
			if (requestRole(RoleSender.class,gaddr)==null) {
				return StatusFactory.ok(this);
			}
			
			return super.activate(parameters);
		}
		
		@Override
		public Status live() {
			// TODO Auto-generated method stub
			
			return super.live();
		}
		
		
		// Capacity
		private class CapacityImplGetAgentRepository extends CapacityImplementation implements CapacityGetAgentRepository {
			 
		    public CapacityImplGetAgentRepository() {
		      // Define the type of capacity implementation:
		      // the implementation is own by the caller.
		      super(CapacityImplementationType.DIRECT_ACTOMIC);
		    }
		 
		    public void call(CapacityContext call) throws Exception {
		      Repository<AgentAddress, Agent> result = getAgentRepository();
		 
		      // Set output of the capacity with the computed result.
		      call.setOutputValues(result);
		    }
		}
		
		
		// channel method
		public Set<? extends Class<? extends Channel>> getSupportedChannels() {
			// TODO Auto-generated method stub
			return null;
		}

		public <C extends Channel> C getChannel(Class<C> channelClass,
				Object... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	}

	

}
