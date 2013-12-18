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
public class RoleCollecteur extends Role implements KernelListener, RolePlayingListener, GroupListener {
	
	private MachineModel machineModel = new MachineModel();
	private KernelModel kernelModel = new KernelModel();
	
	@Override
	public Status activate(Object... parameters) {	
		addObtainCondition(new HasAllRequiredCapacitiesCondition(CapacityGetAgentRepository.class));
		addObtainCondition(new HasAllRequiredCapacitiesCondition(CapacityGetGroupList.class));
		machineModel.setName(BigBrotherUtil.getComputerFullName());
		machineModel.setIp(BigBrotherUtil.getIP());
		Collection<KernelModel> kernelModels = new Vector<KernelModel>();
		kernelModel.setName(Kernels.get().getAddress().toString());
		kernelModel.setKernelAddress(Kernels.get().getAddress());
		kernelModels.add(kernelModel);
		machineModel.setKernelList(kernelModels);
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
				machineModel.setName(BigBrotherUtil.getComputerFullName());
				machineModel.setIp(BigBrotherUtil.getIP());
				Collection<KernelModel> kernelModels = new Vector<KernelModel>();
				kernelModels.add(kernelModel);
				machineModel.setKernelList(kernelModels);
				
				MachineModel newMachineModel = machineModel.clone();
				clean(newMachineModel);
				
				MessageMachineModel messageMachineModel = new MessageMachineModel(newMachineModel);
				//RoleAddress roleAddress = getRoleAddress(RoleManager.class,message.getSender());
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
	
	// -------------------- Agents --------------------
	
	// ---------- Listeners ----------
	
	public void agentLaunched(KernelEvent event) {
		AgentAddress agentAddress = event.getAgent();
		CapacityContext cc = null;
		
		try {
			cc = executeCapacityCall(CapacityGetAgentRepository.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(cc!=null && cc.isResultAvailable()) {
			Object res = cc.getOutputValueAt(0);
			
			if(res instanceof Repository<?, ?>) {
				Agent agent = ((Repository<AgentAddress, ? extends Agent>)res).get(agentAddress);
				AgentModel agentModel = new AgentModel(agent);
				//kernelModel.getLonelyAgentList().add(agentModel);
			}
		}
	}

	public void agentKilled(KernelEvent event) {
		AgentAddress agentAddress = event.getAgent();
		remove(agentAddress);
	}

	public boolean exceptionUncatched(Throwable error) {
		return false;
	}

	public void kernelAgentLaunched(KernelEvent event) {
	}

	public void kernelAgentKilled(KernelEvent event) {
		// TODO Auto-generated method stub
	}
	
	// ---------- Methods ----------
	
	/**
	 * 
	 * @param agentAddress
	 */
	private void remove(AgentAddress agentAddress) {
		Collection<AgentModel> agentModels = kernelModel.getLonelyAgentList();
		for (AgentModel agentModel : agentModels) {
			if(agentModel.getAddress() == agentAddress) {
				agentModels.remove(agentModel);
				return;
			}
		}
	}
	
	// -------------------- Roles --------------------
	
	// ---------- Listeners ----------

	public void roleTaken(RolePlayingEvent event) {
		System.out.println("ROLE EVENT");
		AgentAddress agentAddress = event.getPlayer();
		RoleAddress roleAddress = event.getRoleAddress();
		Class<? extends Role> roleClass = event.getRole();
		Group group = event.getGroup();
		Organization organization = group.getOrganization();
		
		insert(agentAddress, roleAddress, roleClass, group, organization);
		System.out.println("ROLE INSERTED");
		BigBrotherUtil.printKernelModel(kernelModel);
	}
	
	public void roleReleased(RolePlayingEvent event) {
		AgentAddress agentAddress = event.getPlayer();
		RoleAddress roleAddress = event.getRoleAddress();
		Group group = event.getGroup();
		Organization organization = group.getOrganization();
		
		remove(agentAddress, roleAddress, group, organization);
	}
	
	// ---------- Methods ----------
	
	/**
	 * 
	 * @param agentAddress
	 * @param roleAddress
	 * @param group
	 * @param organization
	 */
	private void insert(AgentAddress agentAddress, RoleAddress roleAddress, Class<? extends Role> roleClass, Group group, Organization organization) {
		Collection<AgentModel> agentModels = kernelModel.getLonelyAgentList();
		AgentModel goodAgentModel = new AgentModel(null);
		// search if the new player was in the lonely agentList
		// if it's the case we remove it, he is not lonely anymore
		for (AgentModel agentModel : agentModels) {
			if(agentModel.getAddress() == agentAddress) {
				goodAgentModel = agentModel;
				agentModels.remove(agentModel);
			}
		}
		
		boolean roleModelExist = false;
		Collection<OrganizationModel> organizationModels = kernelModel.getOrgList();
		for (OrganizationModel organizationModel : organizationModels) {
			if (organizationModel.getClasse() == organization.getClass()) {
				Collection<GroupModel> groupModels = organizationModel.getGroupList();
				for (GroupModel groupModel : groupModels) {
					if (groupModel.getGroupAddress() == group.getAddress()) {
						// case where the group is already known
						Collection<RoleModel> roleModels = groupModel.getRoleList();
						for (RoleModel roleModel : roleModels) {
							if (roleModel.getRoleAddress() == roleAddress) {
								goodAgentModel.getListOfRole().add(roleModel.toString());
								roleModel.getPlayerList().add(goodAgentModel);
								roleModelExist = true;
								return;
							}
						}
						if(!roleModelExist) {
							RoleModel roleModel = new RoleModel(roleAddress, roleClass, goodAgentModel);
							goodAgentModel.getListOfRole().add(roleModel.toString());
							roleModels.add(roleModel);
							return;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param agentAddress
	 * @param roleAddress
	 * @param group
	 * @param organization
	 */
	private void remove(AgentAddress agentAddress, RoleAddress roleAddress, Group group, Organization organization) {
		Collection<OrganizationModel> organizationModels = kernelModel.getOrgList();
		for (OrganizationModel organizationModel : organizationModels) {
			if (organizationModel.getClasse() == organization.getClass()) {
				Collection<GroupModel> groupModels = organizationModel.getGroupList();
				for (GroupModel groupModel : groupModels) {
					if (groupModel.getGroupAddress() == group.getAddress()) {
						Collection<RoleModel> roleModels = groupModel.getRoleList();
						for (RoleModel roleModel : roleModels) {
							if (roleModel.getRoleAddress() == roleAddress) {
								Vector<AgentModel> agentModels = (Vector<AgentModel>) roleModel.getPlayerList();
								for (AgentModel agentModel : agentModels) {
									if(agentModel.getAddress() == agentAddress) {
										agentModel.removeRoleFromList(roleModel);
										if(agentModel.getListOfRole().size() == 0) {
											kernelModel.getLonelyAgentList().add(agentModel);
										}
										agentModels.remove(agentModel);
										if(agentModels.size() == 0) {
											roleModels.remove(roleModel);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	// -------------------- Groups --------------------
	
	// ---------- Listeners ----------
	
	public void groupCreated(GroupEvent event) {
		Group group = event.getGroup();
		GroupModel groupModel = new GroupModel(null);
		groupModel.setRoleList(new Vector<RoleModel>());
		groupModel.setGroupAddress(group.getAddress());
		
		Organization organization = group.getOrganization();
		System.out.println("GROUP_CREATED-------------------------------------------- test " + group.getOrganization().getClass().toString());
		insert(groupModel, organization);
		System.out.println("AFFICHAGE DU KERNELMODEL : " );
		BigBrotherUtil.printKernelModel(kernelModel);
		
	}

	public void groupDestroyed(GroupEvent event) {
		Group group = event.getGroup();
		GroupAddress groupAddress = group.getAddress();
		Organization organization = group.getOrganization();
		
		remove(groupAddress, organization);
		
	}
	
	// ---------- Methods ----------
	
	/**
	 * 
	 * @param groupModel
	 * @param organization
	 */
	private void insert(GroupModel groupModel, Organization organization) {
		Collection<OrganizationModel> organizationModels = kernelModel.getOrgList();
		boolean organizationModelExist = false;
		
		if(organizationModels != null) {
			for (OrganizationModel organizationModel : organizationModels) {
				if (organizationModel.getClasse() == organization.getClass()) {
					organizationModel.getGroupList().add(groupModel);
					organizationModelExist = true;
				}
			}
		}
		else {
			organizationModels = new Vector<OrganizationModel>();
			kernelModel.setOrgList(organizationModels);
		}
		
		if (organizationModelExist == false) {
			OrganizationModel newOrganizationModel = new OrganizationModel(organization, groupModel);
			kernelModel.getOrgList().add(newOrganizationModel);
		}
	}
	
	/**
	 * 
	 * @param groupAddress
	 * @param organization
	 */
	private void remove(GroupAddress groupAddress, Organization organization) {
		Collection<OrganizationModel> organizationModels = kernelModel.getOrgList();
		
		if(organizationModels != null) {
			for (OrganizationModel organizationModel : organizationModels) {
				if (organizationModel.getClasse() == organization.getClass()) {
					Collection<GroupModel> groupModels = organizationModel.getGroupList();
					for (GroupModel groupModel : groupModels) {
						if(groupModel.getGroupAddress() == groupAddress) {
							groupModels.remove(groupModel);
							if(groupModels.size() == 0) {
								organizationModels.remove(organizationModel);
							}
							return;
						}
					}
				}
			}
		}
	}

	public void agentLifeChanged(AgentAddress agent, AgentLifeState state) {
		// TODO Auto-generated method stub
		System.out.println("AGENT CHANGED");
	}
	
	public void testAccessByRepo() {
		// ALL DATA ARE HERE -- no listener cuz it looks like its not working for roles , and agent????
		
		
		Repository<AgentAddress, ? extends Agent> repo = null;
		CapacityContext cc = null;
		try {
			cc = executeCapacityCall(CapacityGetAgentRepository.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(cc!= null && cc.isResultAvailable()) {
			repo =(Repository<AgentAddress, ? extends Agent>) cc.getOutputValueAt(0);
		}
		
		try {
			cc = executeCapacityCall(CapacityGetGroupList.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<Group> groups = null;
		if(cc!= null && cc.isResultAvailable()) {
			groups =(Vector<Group>) cc.getOutputValueAt(0);
		}
		
		
		Vector<Agent> agents = new Vector<Agent>();
		Iterator<AgentAddress> itagAd = repo.iterator();
		SizedIterator<Class <? extends Role>> rolesClasses = null;
		Vector<String> rolesStringed = new Vector<String>();
		while(itagAd.hasNext()) {
			agents.add(repo.get(itagAd.next()));
		}
		
		System.out.println(" ******** FROM kernel: printing group  * * ** **");
		for (Group group : groups) {
			rolesClasses = getExistingRoles(group.getAddress()); // only replied played roles
			System.out.println("Org: " + group.getOrganization());
			System.out.println("PlayerCount: " + group.getPlayerCount());
			System.out.println("Existing roles in the group: ");
			while(rolesClasses.hasNext()) {
				System.out.println("    role: " + rolesClasses.next());
			}
			
			// ---> to get non played role cross data with this !
			System.out.println(" roleEXECUTANT is NOT played now and should appear in this list:" + getOrganization(OrganizationManager.class).getDefinedRoles());
			 
			// getRole address using getRoleAddress functions...
			
			// access to all org class easly with this: getOrganizationRepository().iterator(); may need a capacity though
			
//			for (Agent agent : agents) {
//				System.out.println("Agent " + agent.getName() + " joue " + agent.getRoles(group.getAddress()) + " DANS " + group.getOrganization());
//			}

		}
		System.out.println("<<<<<<<<<<<<<<< kernel: printing group END");
	}
}
