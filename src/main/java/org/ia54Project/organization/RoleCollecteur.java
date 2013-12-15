package org.ia54Project.organization;

import java.util.Collection;
import java.util.Vector;

import org.ia54Project.dataModel.AgentModel;
import org.ia54Project.dataModel.GroupModel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.MessageKernelModel;
import org.ia54Project.dataModel.OrganizationModel;
import org.ia54Project.dataModel.RoleModel;
import org.janusproject.kernel.KernelEvent;
import org.janusproject.kernel.KernelListener;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;
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

//, KernelListener
public class RoleCollecteur extends Role implements KernelListener, RolePlayingListener, GroupListener {
	
	private KernelModel kernelModel = new KernelModel();
	
	@Override
	public Status activate(Object... parameters) {	
		addObtainCondition(new HasAllRequiredCapacitiesCondition(CapacityGetAgentRepository.class));
		
		return StatusFactory.ok(this);
	}
	 
	@Override
	public Status live() {
		Message message = getMessage();
		
		// Lorsque l'on recoit un message "request", celui-ci provient du role Manager
		// Cela signifie qu'il nous demande des informations
		// On lui envoie donc un message contenant le KernelModel construit
		if(message != null && message instanceof StringMessage) {
			String stringMessage = ((StringMessage)message).getContent();
			if(stringMessage.equals("request")) {
				MessageKernelModel messageKernelModel = new MessageKernelModel(kernelModel);
				RoleAddress roleAddress = getRoleAddress(RoleManager.class);
				sendMessage(roleAddress, messageKernelModel);
			}
		}
		return null;
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
				kernelModel.getLonelyAgentList().add(agentModel);
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
		AgentAddress agentAddress = event.getPlayer();
		RoleAddress roleAddress = event.getRoleAddress();
		Class<? extends Role> roleClass = event.getRole();
		Group group = event.getGroup();
		Organization organization = group.getOrganization();
		
		insert(agentAddress, roleAddress, roleClass, group, organization);
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
		AgentModel goodAgentModel = new AgentModel();
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
				Vector<GroupModel> groupModels = organizationModel.getGroupList();
				for (GroupModel groupModel : groupModels) {
					if (groupModel.getGroupAddress() == group.getAddress()) {
						Vector<RoleModel> roleModels = groupModel.getRoleList();
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
				Vector<GroupModel> groupModels = organizationModel.getGroupList();
				for (GroupModel groupModel : groupModels) {
					if (groupModel.getGroupAddress() == group.getAddress()) {
						Vector<RoleModel> roleModels = groupModel.getRoleList();
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
//		Group group = event.getGroup();
//		GroupModel groupModel = new GroupModel();
//		groupModel.setRoleList(new Vector<RoleModel>());
//		groupModel.setGroupAddress(group.getAddress());
//		
//		Organization organization = group.getOrganization();
//		print("GROUP_CREATED--------------------------------------------------------------------------------");
//		insert(groupModel, organization);
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
}
