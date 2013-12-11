package org.janusproject.kernel.network.jxse.agent;

import java.util.EventListener;

import org.ia54Project.organization.CapacityGetAgentRepository;
import org.ia54Project.organization.OrganizationController;
import org.ia54Project.organization.OrganizationManager;
import org.ia54Project.organization.RoleCollecteur;
import org.ia54Project.organization.RoleControlManager;
import org.ia54Project.organization.RoleExecutant;
import org.ia54Project.organization.RoleManager;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.AgentActivator;
import org.janusproject.kernel.crio.capacity.CapacityContext;
import org.janusproject.kernel.crio.capacity.CapacityImplementation;
import org.janusproject.kernel.crio.capacity.CapacityImplementationType;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.network.NetworkAdapter;
import org.janusproject.kernel.repository.Repository;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

public class MonitoredKernelAgent extends JxtaJxseKernelAgent{

	private static final long serialVersionUID = 8826290855685530386L;
	
	MonitoredKernelAgent(AgentActivator activator, Boolean commitSuicide, EventListener startUpListener, String applicationName, NetworkAdapter networkAdapter) {
		super(activator, commitSuicide, startUpListener, applicationName, networkAdapter);
		
		launchHeavyAgent(new ManagerAgent(),"ManagerAgent");
		launchHeavyAgent(new CollecteurAgent(),"CollecteurAgent");
		launchHeavyAgent(new ExecutantAgent(),"ExecutantAgent");
	}
	
	// ==================== Manager ====================

	private class ManagerAgent extends Agent {
		private static final long serialVersionUID = 2596479307519818391L;

		public Status activate(Object... parameters) {
			Status s = null;
			getCapacityContainer().addCapacity(new CapacityImplGetAgentRepository());
			GroupAddress gaddr = getOrCreateGroup(OrganizationManager.class);
			
			if (requestRole(RoleManager.class,gaddr)==null) {
				s = StatusFactory.ok(this);
			} 
			GroupAddress gaddrController = getOrCreateGroup(OrganizationController.class);
			if(requestRole(RoleControlManager.class,gaddrController) == null) {
				s =  StatusFactory.ok(this);
				return s;
			}
			return super.activate(parameters);
		}
		
		@Override
		public Status live() {
			//print("je prend le role Manager");
			
			return super.live();
		}
	}
	
	// ==================== Executant ====================

		private class ExecutantAgent extends Agent {
			private static final long serialVersionUID = 2117008922411038447L;

			public Status activate(Object... parameters) {
				getCapacityContainer().addCapacity(new CapacityImplGetAgentRepository());
				GroupAddress gaddr = getOrCreateGroup(OrganizationManager.class);
				
				if (requestRole(RoleExecutant.class,gaddr)==null) {
					return StatusFactory.ok(this);
				}
				
				return super.activate(parameters);
			}
			
			@Override
			public Status live() {
				//print("je prend le role Executant");
				
				return super.live();
			}
		}
	
	// ==================== Collecteur ====================

	private class CollecteurAgent extends Agent {
		 
		private static final long serialVersionUID = 6935534328201480630L;

		@Override
		public Status activate(Object... parameters) {
			getCapacityContainer().addCapacity(new CapacityImplGetAgentRepository());
			GroupAddress gaddr = getOrCreateGroup(OrganizationManager.class);
			
			if (requestRole(RoleCollecteur.class,gaddr)==null) {
				return StatusFactory.ok(this);
			}
			
			return super.activate(parameters);
		}
		
		@Override
		public Status live() {
			//print("je prend le role Collecteur");
			
			return super.live();
		}	
	}
	
	// ==================== Capacity ====================
	
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
}
