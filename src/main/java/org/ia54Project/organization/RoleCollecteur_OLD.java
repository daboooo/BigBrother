package org.ia54Project.organization;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.Unmarshaller.Listener;

import org.ia54Project.dataModel.AgentModel;
import org.ia54Project.dataModel.GroupModel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.OrganizationModel;
import org.ia54Project.dataModel.RoleModel;
import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.crio.capacity.CapacityContext;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.HasAllRequiredCapacitiesCondition;
import org.janusproject.kernel.crio.core.Organization;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;
import org.janusproject.kernel.crio.organization.Group;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.repository.Repository;
import org.janusproject.kernel.repository.RepositoryChangeEvent;
import org.janusproject.kernel.repository.RepositoryChangeEvent.ChangeType;
import org.janusproject.kernel.repository.RepositoryChangeListener;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;
import org.janusproject.kernel.util.sizediterator.SizedIterator;


public class RoleCollecteur_OLD extends Role implements RepositoryChangeListener{

	private State state;
	private int nbMessageToSend = 5;
	private int sentMsg = 0;
	
	@Override
	public Status activate(Object... parameters) {
		this.setState(State.SEND_AGENTS_INFO); 	
		addObtainCondition(new HasAllRequiredCapacitiesCondition(CapacityGetAgentRepository.class));
		
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		Message message = new StringMessage("Bonjour GUI");
		//print("Je suis dans le role collecteur et j'envoie : " + message.toString());
		broadcastMessage(RoleManager.class, message);
		
		CapacityContext cc = null;
		
		try {
			cc = executeCapacityCall(CapacityGetAgentRepository.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(cc!=null && cc.isResultAvailable()) {
			Object res = cc.getOutputValueAt(0);
			
			if(res instanceof Repository<?, ?>) {
				
				KernelModel kernelModel = new KernelModel();
				Vector<OrganizationModel> organizations = new Vector<OrganizationModel>();
				
				Repository<AgentAddress, Agent> repository = (Repository<AgentAddress, Agent>) res;
				Collection<AgentAddress> agentAddresses = repository.identifiers();
				
				RoleModel roleModele = new RoleModel();
				
				
				for (AgentAddress agentAddress : agentAddresses) {
					AgentModel agentModel = new AgentModel();
					Vector<String> listOfRoles = new Vector<String>();
					Agent agent = repository.get(agentAddress);
					Collection<GroupAddress> groupAddresses = agent.getGroups();
					
					agentModel.setAddress(agent.getAddress());
					agentModel.setCanCommitSuicide(agent.canCommitSuicide());
					agentModel.setCreationDate(agent.getCreationDate());
					agentModel.setCreatorAddress(agent.getCreator());
					agentModel.setIsAlive(agent.isAlive());
					agentModel.setIsCompound(agent.isCompound());
					agentModel.setIsHeavyAgent(agent.isHeavyAgent());
					agentModel.setIsRecruitementAllowed(agent.isRecruitmentAllowed());
					agentModel.setIsSleeping(agent.isSleeping());
					agentModel.setName(agent.getName());
					
					
					kernelModel.setOrgList(organizations);
					
					Vector<GroupModel> groups = new Vector<GroupModel>();
					
					for (GroupAddress groupAddress : groupAddresses) {
						Group group = getGroupObject(groupAddress);
						GroupModel groupModel = new GroupModel();
						
						Organization organization = group.getOrganization();
						OrganizationModel organizationModel = new OrganizationModel();
						organizationModel.setNbInstance(organization.getGroupCount());
						organizations.add(organizationModel);
						
						
						Collection<Class<? extends Role>> roles = agent.getRoles(groupAddress);
						
						for (Class<? extends Role> role : roles) {
							listOfRoles.add(role.getName());
							
							
						}
						agentModel.setListOfRole(listOfRoles);
					}
					
					
				}
				
				broadcastMessage(RoleManager.class, new StringMessage("over"));
				state = State.WAITING_ORDER;
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		switch (state) {
//		case SEND_AGENTS_INFO:
//			CapacityContext cc = null;
//			
//			try {
//				cc = executeCapacityCall(CapacityGetAgentRepository.class);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			if(cc!=null && cc.isResultAvailable()) {
//				Object res = cc.getOutputValueAt(0);
//				
//				if(res instanceof Repository<?, ?>) {
//					Repository<AgentAddress, Agent> repo = (Repository<AgentAddress, Agent>) res;
//					Collection<AgentAddress> addr = repo.identifiers();
//					
//					print ("Size agents: " + addr.size());
//					
//					for (AgentAddress ad : addr) {
//						broadcastMessage(RoleManager.class, buildMessage(repo.get(ad)));
//					}
//					
//					broadcastMessage(RoleManager.class, new StringMessage("over"));
//					state = State.WAITING_ORDER;
//				}
//			}
//			break;
//		case WAITING_ORDER:
//			Message order = getMessage();
//			if (order!= null) {
//				if(order instanceof StringMessage) {
//					if(((StringMessage) order).getContent() == "AGENT_INFOS") {
//						state = State.SEND_AGENTS_INFO;
//					}
//				}
//			}
//		break;	
//		default:
//			print("error default");
//		}
		
		return StatusFactory.ok(this);
	}
	
	private StringMessage buildMessage(Agent ag) {
		StringBuilder msg = new StringBuilder();
		msg.append("\n-----\nName: ");
		msg.append(ag.getName());
		msg.append("\nAdress: ");
		msg.append(ag.getAddress().toString());
//		msg.append("\nOrganization: ");
//		msg.append(ag.getOrganization(null).toString());
		msg.append("\nState: ");
		msg.append(ag.getState().toString());
		msg.append("\nList of groups: [ ");
		for (GroupAddress gadr : ag.getGroups()) {
			msg.append(gadr);
			msg.append(ag.getRoles(gadr));
			msg.append(" - ");
		}
		msg.append(" ]");
		msg.append("\n-----");
		StringMessage m = new StringMessage(msg.toString());
		return m;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	enum State {
		SENDING,
		WAITING_ORDER, 
		SEND_AGENTS_INFO
	}

	public void repositoryChanged(RepositoryChangeEvent evt) {
		print("Les changements sont : " + evt.getType() + " de " + evt.getChangedObject());
		
		if(evt.getType() == ChangeType.ADD) {
			print(evt.getChangedObject());
			
		}
		
	}
}
