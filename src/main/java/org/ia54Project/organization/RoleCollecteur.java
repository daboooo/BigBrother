package org.ia54Project.organization;

import java.util.Collection;
import java.util.List;

import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.crio.capacity.CapacityContext;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.HasAllRequiredCapacitiesCondition;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.repository.Repository;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;
import org.janusproject.kernel.util.sizediterator.SizedIterator;


public class RoleCollecteur extends Role{

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
		Message message = new StringMessage("Bonjour Manager");
		//print("Je suis dans le role collecteur et j'envoie : " + message.toString());
		broadcastMessage(RoleManager.class, message);
		
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
}
