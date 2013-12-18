package org.janusproject.kernel.network.jxse.agent;

import java.util.Collection;
import java.util.EventListener;
import java.util.Iterator;
import java.util.Vector;

import org.ia54Project.agent.BigBrotherGUIAgent;
import org.ia54Project.agent.BigBrotherTestAgent;
import org.ia54Project.organization.CapacityGetAgentRepository;
import org.ia54Project.organization.CapacityGetGroupList;
import org.ia54Project.organization.CapacityKillAgent;
import org.ia54Project.organization.OrganizationController;
import org.ia54Project.organization.OrganizationManager;
import org.ia54Project.organization.RoleCollecteur;
import org.ia54Project.organization.RoleControlManager;
import org.ia54Project.organization.RoleExecutant;
import org.ia54Project.organization.RoleManager;
import org.ia54Project.swingGUI.BigBrotherFrame;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.AgentActivator;
import org.janusproject.kernel.crio.capacity.CapacityContext;
import org.janusproject.kernel.crio.capacity.CapacityImplementation;
import org.janusproject.kernel.crio.capacity.CapacityImplementationType;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.organization.Group;
import org.janusproject.kernel.network.NetworkAdapter;
import org.janusproject.kernel.repository.Repository;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

public class MonitoredKernelAgent extends JxtaJxseKernelAgent {

	private static final long serialVersionUID = 8826290855685530386L;
	private GroupAddress OrganizationManagerAddress;
	private GroupAddress OrganizationControllerAddress;
	private Boolean guiEnabled = false;	
	private Collection<Class<? extends Role>> roleList;
	private CollecteurAgent collecteur = new CollecteurAgent();
	private BigBrotherTestAgent testy;

	
	public Boolean getGuiEnabled() {
		
		return guiEnabled;
	}

	public void setGuiEnabled(Boolean guiEnabled) {
		this.guiEnabled = guiEnabled;
		if(guiEnabled) {
			getOrCreateGroup(OrganizationController.class);
			AgentAddress gui = launchHeavyAgent(new BigBrotherGUIAgent(), "GUI Agent");
			BigBrotherFrame frame = new BigBrotherFrame(gui,OrganizationControllerAddress);
			frame.setVisible(true);
		}
	}

	MonitoredKernelAgent(Boolean guiB, AgentActivator activator, Boolean commitSuicide, EventListener startUpListener, String applicationName, NetworkAdapter networkAdapter) {

		super(activator, commitSuicide, startUpListener, applicationName, networkAdapter);
		guiEnabled = guiB;
		
		System.out.println("je suis le kernel");
		
		OrganizationManagerAddress = createGroup(OrganizationManager.class);
		OrganizationControllerAddress = getOrCreateGroup(OrganizationController.class);
		testy = new BigBrotherTestAgent();
		
		launchHeavyAgent(new ManagerAgent(),"ManagerAgent");
		launchHeavyAgent(collecteur,"CollecteurAgent");
		launchHeavyAgent(new ExecutantAgent(),"ExecutantAgent");
		launchHeavyAgent(testy, "TEST AGENT");
		
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
		if(testy != null) {
			kill(testy.getAddress());

			
		}
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
			getCapacityContainer().addCapacity(new CapacityImplKillAgent());
			
			if (requestRole(RoleExecutant.class,OrganizationManagerAddress)==null) {
				throw new IllegalArgumentException("RoleExecutant");
			}
			
			return super.activate(parameters);
		}
		
		@Override
		public Status live() {
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
			getCapacityContainer().addCapacity(new CapacityImplGetGroupList());

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

	private class CapacityImplKillAgent extends CapacityImplementation implements CapacityKillAgent{

		public CapacityImplKillAgent() {
			super(CapacityImplementationType.DIRECT_ACTOMIC);
		}
		
		@Override
		public void call(CapacityContext call) throws Exception {
			AgentAddress toKill = (AgentAddress)call.getInputValueAt(0);
			kill(toKill);
		}
		
	}
	
	private class CapacityImplGetGroupList extends CapacityImplementation implements CapacityGetGroupList{

		public CapacityImplGetGroupList() {
			super(CapacityImplementationType.DIRECT_ACTOMIC);
		}
		
		@Override
		public void call(CapacityContext call) throws Exception {
			Vector<Group> groups = new Vector<Group>();
			Iterator<GroupAddress> itgr = getGroupRepository().iterator();
			while(itgr.hasNext()) {
				groups.add(getGroupObject(itgr.next()));
			}
			call.setOutputValues(groups);
			
		}
		
	}
	
	

	
	
	
	

	
		
}
