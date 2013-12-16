package org.janusproject.kernel.network.jxse.agent;

import java.util.Collection;
import java.util.EventListener;
import java.util.Iterator;
import java.util.Vector;
import java.util.UUID;
import java.util.logging.Logger;

import org.ia54Project.agent.BigBrotherGUIAgent;
import org.ia54Project.organization.CapacityGetAgentRepository;
import org.ia54Project.organization.OrganizationController;
import org.ia54Project.organization.OrganizationManager;
import org.ia54Project.organization.RoleCollecteur;
import org.ia54Project.organization.RoleControlManager;
import org.ia54Project.organization.RoleExecutant;
import org.ia54Project.organization.RoleManager;
import org.ia54Project.swingGUI.BigBrotherFrame;
import org.janusproject.kernel.KernelEvent;
import org.janusproject.kernel.KernelListener;
import org.janusproject.kernel.address.Address;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.AgentActivator;
import org.janusproject.kernel.crio.capacity.CapacityContext;
import org.janusproject.kernel.crio.capacity.CapacityImplementation;
import org.janusproject.kernel.crio.capacity.CapacityImplementationType;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.Organization;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.crio.core.Organization;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;
import org.janusproject.kernel.crio.organization.Group;
import org.janusproject.kernel.crio.organization.GroupCondition;
import org.janusproject.kernel.crio.organization.GroupEvent;
import org.janusproject.kernel.crio.organization.GroupListener;
import org.janusproject.kernel.crio.organization.MembershipService;
import org.janusproject.kernel.crio.role.RolePlayingEvent;
import org.janusproject.kernel.crio.role.RolePlayingListener;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.network.NetworkAdapter;
import org.janusproject.kernel.repository.Repository;
import org.janusproject.kernel.repository.RepositoryChangeEvent;
import org.janusproject.kernel.repository.RepositoryChangeListener;
import org.janusproject.kernel.repository.RepositoryChangeEvent.ChangeType;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;
import org.janusproject.kernel.util.sizediterator.SizedIterator;
import org.janusproject.kernel.util.throwable.Throwables;

public class MonitoredKernelAgent extends JxtaJxseKernelAgent {

	private static final long serialVersionUID = 8826290855685530386L;
	private GroupAddress OrganizationManagerAddress;
	private GroupAddress OrganizationControllerAddress;
	private Boolean guiEnabled = false;	
	private Collection<Class<? extends Role>> roleList;
	private CollecteurAgent collecteur = new CollecteurAgent();
	
	public Boolean getGuiEnabled() {
		
		return guiEnabled;
	}

	public void setGuiEnabled(Boolean guiEnabled) {
		this.guiEnabled = guiEnabled;
	}

	MonitoredKernelAgent(AgentActivator activator, Boolean commitSuicide, EventListener startUpListener, String applicationName, NetworkAdapter networkAdapter) {
		super(activator, commitSuicide, startUpListener, applicationName, networkAdapter);
		
		OrganizationManagerAddress = createGroup(OrganizationManager.class);
		OrganizationControllerAddress = getOrCreateGroup(OrganizationController.class);
		
		launchHeavyAgent(new ManagerAgent(),"ManagerAgent");
		launchHeavyAgent(collecteur,"CollecteurAgent");
		launchHeavyAgent(new ExecutantAgent(),"ExecutantAgent");

		if(this.guiEnabled) {
			getOrCreateGroup(OrganizationController.class);
			AgentAddress gui = launchHeavyAgent(new BigBrotherGUIAgent(), "GUI Agent");
			BigBrotherFrame frame = new BigBrotherFrame(gui,OrganizationControllerAddress);
			frame.setVisible(true);
		}
		

	}
	
	@Override
	public Status live() {
		
		//launchHeavyAgent(new ExecutantAgent(), "Agent");
		
		return super.live();
	}

	// ==================== GUI Manager ====================

	@Override
	public Status activate(Object... parameters) {
		super.activate(parameters);

	
		return 	StatusFactory.ok(this);
	}
	// ==================== Manager ====================

	private class ManagerAgent extends Agent {
		private static final long serialVersionUID = 2596479307519818391L;

		public Status activate(Object... parameters) {

			getCapacityContainer().addCapacity(new CapacityImplGetAgentRepository());

			if (requestRole(RoleManager.class,OrganizationManagerAddress)==null) {
				throw new IllegalArgumentException("RoleManager");
			} 

			if (requestRole(RoleControlManager.class,OrganizationControllerAddress) == null) {
				throw new IllegalArgumentException("RoleControlManager");
			}

			return super.activate(parameters);
		}

		@Override
		public Status live() {
			//print("je prend le role Manager");

//			Organization org = getOrganization(OrganizationController.class);
//			print(org);
//			Collection<Class<? extends Role>> roles = org.getDefinedRoles();
//			if(roles != null) {
//				if(roles.iterator().hasNext()) {
//					Class<? extends Role> c = roles.iterator().next();
//					if(c == RoleGUIManager.class)
//						try {
//							print(c.getField("state").getName());
//						} catch (SecurityException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (NoSuchFieldException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					if(c == RoleControlManager.class)
//						print("fu2");
//				}
//			}
			
			return super.live();
		}
	}

	// ==================== Executant ====================

	private class ExecutantAgent extends Agent {
		private static final long serialVersionUID = 2117008922411038447L;

		public Status activate(Object... parameters) {
			getCapacityContainer().addCapacity(new CapacityImplGetAgentRepository());
			
			if (requestRole(RoleExecutant.class,OrganizationManagerAddress)==null) {
				throw new IllegalArgumentException("RoleExecutant");
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
		private RoleCollecteur myRole;

		@Override
		public Status activate(Object... parameters) {
			getCapacityContainer().addCapacity(new CapacityImplGetAgentRepository());

			if (requestRole(RoleCollecteur.class,OrganizationManagerAddress)==null) {
				throw new IllegalArgumentException("RoleCollecteur");
			}
			
			this.setRole(getRole(OrganizationManagerAddress, RoleCollecteur.class));
			
			MonitoredKernelAgent.this.addGroupListener(myRole);
			MonitoredKernelAgent.this.addRolePlayingListener(myRole);
			MonitoredKernelAgent.this.addKernelListener(myRole);
			
			return super.activate(parameters);
		}
		
		private void setRole(RoleCollecteur role) {
			myRole = role;
		}
		
		private RoleCollecteur getRole() {
			return myRole;
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
