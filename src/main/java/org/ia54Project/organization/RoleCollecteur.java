package org.ia54Project.organization;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.ia54Project.BigBrotherUtil;
import org.ia54Project.dataModel.AgentModel;
import org.ia54Project.dataModel.GroupModel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.MachineModel;
import org.ia54Project.dataModel.MessageKernelModel;
import org.ia54Project.dataModel.MessageMachineModel;
import org.ia54Project.dataModel.OrganizationModel;
import org.ia54Project.dataModel.RoleModel;
import org.janusproject.kernel.KernelEvent;
import org.janusproject.kernel.KernelListener;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.AgentLifeState;
import org.janusproject.kernel.agent.AgentLifeStateListener;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.crio.capacity.CapacityContext;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.HasAllRequiredCapacitiesCondition;
import org.janusproject.kernel.crio.core.Organization;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;
import org.janusproject.kernel.crio.organization.Group;
import org.janusproject.kernel.crio.organization.GroupEvent;
import org.janusproject.kernel.crio.organization.GroupListener;
import org.janusproject.kernel.crio.role.RolePlayingEvent;
import org.janusproject.kernel.crio.role.RolePlayingListener;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.repository.Repository;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;
import org.janusproject.kernel.util.sizediterator.SizedIterator;

//, KernelListener
public class RoleCollecteur extends Role /*implements KernelListener, RolePlayingListener, GroupListener*/ {
	
	private MachineModel machineModel;
	private KernelModel kernelModel;
	
	@Override
	public Status activate(Object... parameters) {	
		addObtainCondition(new HasAllRequiredCapacitiesCondition(CapacityGetAgentRepository.class));
		addObtainCondition(new HasAllRequiredCapacitiesCondition(CapacityGetGroupList.class));
		
		return StatusFactory.ok(this);
	}
	 
	@Override
	public Status live() {
		Message message = getMessage();
		//testAccessByRepo();
		// Lorsque l'on recoit un message "request", celui-ci provient du role Manager
		// Cela signifie qu'il nous demande des informations
		// On lui envoie donc un message contenant le KernelModel construit

		if(message != null && message instanceof StringMessage) {
			String stringMessage = ((StringMessage)message).getContent();
			if(stringMessage.equals("request")) {
				machineModel = new MachineModel();
				kernelModel = new KernelModel();
				
				Collection<KernelModel> kernelModels = new Vector<KernelModel>();
				kernelModel.setName(Kernels.get().getAddress().toString());
				kernelModel.setKernelAddress(Kernels.get().getAddress());
				kernelModels.add(kernelModel);
				
				machineModel.setName(BigBrotherUtil.getComputerFullName());
				machineModel.setIp(BigBrotherUtil.getIP());
				machineModel.setKernelList(kernelModels);
				
				buildModel();

				BigBrotherUtil.printKernelModel(kernelModel);
				
				//MachineModel newMachineModel = machineModel.clone();
				//clean(newMachineModel);
				
				MessageMachineModel messageMachineModel = new MessageMachineModel(machineModel);
				sendMessage((RoleAddress) message.getSender(), messageMachineModel);
			}
		}
		return null;
	}
	
	private synchronized void clean(MachineModel machineModel) {
		Collection<KernelModel> kernelModels = machineModel.getKernelList();
		if(kernelModels != null) {
			for (KernelModel kernelModel : kernelModels) {
				Collection<OrganizationModel> organizationModels = kernelModel.getOrgList();
				if(organizationModels != null) {
					for (OrganizationModel organizationModel : organizationModels) {
						Collection<GroupModel> groupModels = organizationModel.getGroupList();
						if(groupModels != null) {
							if(groupModels.size() == 0) {
								organizationModels.remove(organizationModel);
							}
							else {
								for (GroupModel groupModel : groupModels) {
									if(groupModel.getRoleList().size() == 0) {
										groupModels.remove(groupModel);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void buildModel() {
		Boolean organizationAlreadyExist = false;
		Boolean lonely = true;
		CapacityContext cc = null;
		
		Repository<AgentAddress, ? extends Agent> agentRepository = null;
		Vector<Agent> agents = new Vector<Agent>();
		Vector<Group> groups = null;
		SizedIterator<Class<? extends Role>> itRoles = null;
		Vector<Organization> organizations = new Vector<Organization>();
		
		Vector<OrganizationModel> organizationModels = new Vector<OrganizationModel>();
		
		// ---------- Recuperation des donnees ----------
		
		// Recuperation de l'agentRepository grace a la capacity CapacityGetAgentRepository
		try {
			cc = executeCapacityCall(CapacityGetAgentRepository.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(cc!= null && cc.isResultAvailable()) {
			agentRepository = (Repository<AgentAddress, ? extends Agent>) cc.getOutputValueAt(0);
		}
		
		// Recuperation des agents grace a l'agentRepository
		Iterator<AgentAddress> itAgentAddresses = agentRepository.iterator();
		while(itAgentAddresses.hasNext()) {
			agents.add(agentRepository.get(itAgentAddresses.next()));
		}
		
		
		// Recuperation de la liste des groupes grace a la capacity CapacityGetGroupList
		try {
			cc = executeCapacityCall(CapacityGetGroupList.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(cc!= null && cc.isResultAvailable()) {
			groups = (Vector<Group>) cc.getOutputValueAt(0);
		}
		
		// Recupration de la liste des organisations grace a la liste des groupes
		Organization organizationTemp;
		for (Group group : groups) {
			organizationTemp = group.getOrganization();
			organizationAlreadyExist = false;
			for (Organization organization : organizations) {
				if(organization == organizationTemp) {
					organizationAlreadyExist = true;
				}
			}
			if(organizationAlreadyExist == false) {
				organizations.add(organizationTemp);
			}
		}
		
		// ---------- Construction du Model ----------
		
		// Construction des OrganizationModels
		for (Organization organization : organizations) {
			organizationModels.add(new OrganizationModel(organization));
		}
		
		// Construction des GroupModels
		for (Group group : groups) {
			for (OrganizationModel organizationModel : organizationModels) {
				if(organizationModel.getClasse() == group.getOrganization().getClass()) {
					organizationModel.getGroupList().add(new GroupModel(group.getAddress()));
				}
			}
		}
		
		// Construction des RoleModels
		for (OrganizationModel organizationModel : organizationModels) {
			Collection<GroupModel> groupModels = organizationModel.getGroupList();
			for (GroupModel groupModel : groupModels) {
				itRoles = getExistingRoles(groupModel.getGroupAddress());
				while(itRoles.hasNext()) {
					Class<? extends Role> role = itRoles.next();
					RoleModel roleModel = new RoleModel(role);
					roleModel.setRoleAddress(getRoleAddress(role));
					roleModel.setGroupAdress(groupModel.getGroupAddress());
					groupModel.getRoleList().add(roleModel);
				}
			}
		}
		
		// Construction des AgentModels
		for (Agent agent : agents) {
			lonely = true;
			for (OrganizationModel organizationModel : organizationModels) {
				Collection<GroupModel> groupModels = organizationModel.getGroupList();
				for (GroupModel groupModel : groupModels) {
					Collection<RoleModel> roleModels = groupModel.getRoleList();
					for (RoleModel roleModel : roleModels) {
						Collection<Class<? extends Role>> rolesByGroup = agent.getRoles(groupModel.getGroupAddress());
						for (Class<? extends Role> roleByGroup : rolesByGroup) {
							if(roleByGroup == roleModel.getClasse()) {
								AgentModel agentModel = new AgentModel(agent);
								agentModel.getListOfRole().add(roleByGroup.toString());
								roleModel.getPlayerList().add(agentModel);
								lonely = false;
							}
						}
					}
				}
			}
			if(lonely) {
				kernelModel.getLonelyAgentList().add(new AgentModel(agent));
			}
		}
		
		kernelModel.setOrgList(organizationModels);
	}
	
//	public void testAccessByRepo() {
//		// ALL DATA ARE HERE -- no listener cuz it looks like its not working for roles , and agent????
//		
//		
//		Repository<AgentAddress, ? extends Agent> repo = null;
//		CapacityContext cc = null;
//		try {
//			cc = executeCapacityCall(CapacityGetAgentRepository.class);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		if(cc!= null && cc.isResultAvailable()) {
//			repo =(Repository<AgentAddress, ? extends Agent>) cc.getOutputValueAt(0);
//		}
//		
//		try {
//			cc = executeCapacityCall(CapacityGetGroupList.class);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Vector<Group> groups = null;
//		if(cc!= null && cc.isResultAvailable()) {
//			groups =(Vector<Group>) cc.getOutputValueAt(0);
//		}
//		
//		
//		Vector<Agent> agents = new Vector<Agent>();
//		Iterator<AgentAddress> itagAd = repo.iterator();
//		SizedIterator<Class <? extends Role>> rolesClasses = null;
//		Vector<String> rolesStringed = new Vector<String>();
//		while(itagAd.hasNext()) {
//			agents.add(repo.get(itagAd.next()));
//		}
//		
//		System.out.println(" ******** FROM kernel: printing group  * * ** **");
//		for (Group group : groups) {
//			rolesClasses = getExistingRoles(group.getAddress()); // only replied played roles
//			System.out.println("Org: " + group.getOrganization());
//			System.out.println("PlayerCount: " + group.getPlayerCount());
//			System.out.println("Existing roles in the group: ");
//			while(rolesClasses.hasNext()) {
//				System.out.println("    role: " + rolesClasses.next());
//			}
//			
//			// ---> to get non played role cross data with this !
//			System.out.println(" roleEXECUTANT is NOT played now and should appear in this list:" + getOrganization(OrganizationManager.class).getDefinedRoles());
//			 
//			// getRole address using getRoleAddress functions...
//			
//			// access to all org class easly with this: getOrganizationRepository().iterator(); may need a capacity though
//			
////			for (Agent agent : agents) {
////				System.out.println("Agent " + agent.getName() + " joue " + agent.getRoles(group.getAddress()) + " DANS " + group.getOrganization());
////			}
//
//		}
//		System.out.println("<<<<<<<<<<<<<<< kernel: printing group END");
//	}
}
