package org.janusproject.kernel.network.jxse.agent;

import java.util.EventListener;

import org.ia54Project.organization.CapacityGetAgentRepository;
import org.ia54Project.organization.OrganizationController;
import org.ia54Project.organization.OrganizationManager;
import org.ia54Project.organization.RoleCollecteur;
import org.ia54Project.organization.RoleControlManager;
import org.ia54Project.organization.RoleExecutant;
import org.ia54Project.organization.RoleGUIManager;
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
	private GroupAddress OrganizationManagerAddress;
	private GroupAddress OrganizationControllerAddress;
	private GUIManagerAgent gUIManagerAgent;

	MonitoredKernelAgent(AgentActivator activator, Boolean commitSuicide, EventListener startUpListener, String applicationName, NetworkAdapter networkAdapter) {
		super(activator, commitSuicide, startUpListener, applicationName, networkAdapter);
		OrganizationManagerAddress = createGroup(OrganizationManager.class);
		OrganizationControllerAddress = getOrCreateGroup(OrganizationController.class);


		gUIManagerAgent = new GUIManagerAgent();

		System.out.println("" + launchHeavyAgent(gUIManagerAgent,"GUIManagerAgent"));
		/*launchHeavyAgent(new ManagerAgent(),"ManagerAgent");
		launchHeavyAgent(new CollecteurAgent(),"CollecteurAgent");
		launchHeavyAgent(new ExecutantAgent(),"ExecutantAgent");
*/
	}

	

	@Override
	public Status live() {
		if(gUIManagerAgent != null) {
			//print(gUIManagerAgent.getAddress());
			kill(gUIManagerAgent.getAddress());
		}
		return super.live();
	}

	// ==================== GUI Manager ====================

	private class GUIManagerAgent extends Agent {
		private static final long serialVersionUID = 2596479307519818391L;

		public Status activate(Object... parameters) {

			getCapacityContainer().addCapacity(new CapacityImplGetAgentRepository());

			if (requestRole(RoleGUIManager.class,OrganizationControllerAddress)==null) {
				return StatusFactory.ok(this);
			} 

			return super.activate(parameters);
		}

		@Override
		public Status live() {
			//print("je prend le role GUIManager");

			return super.live();
		}
	}

	// ==================== Manager ====================

	private class ManagerAgent extends Agent {
		private static final long serialVersionUID = 2596479307519818391L;

		public Status activate(Object... parameters) {

			getCapacityContainer().addCapacity(new CapacityImplGetAgentRepository());

			if (requestRole(RoleManager.class,OrganizationManagerAddress)==null) {
				return StatusFactory.ok(this);
			} 

			if (requestRole(RoleControlManager.class,OrganizationControllerAddress) == null) {
				return StatusFactory.ok(this);
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

			if (requestRole(RoleExecutant.class,OrganizationManagerAddress)==null) {
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

			if (requestRole(RoleCollecteur.class,OrganizationManagerAddress)==null) {
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
