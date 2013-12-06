package ia54Project;

import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;


public class RoleSender extends Role{

	private State state;
	private int nbMessageToSend = 5;
	private int sentMsg = 0;
	@Override
	public Status activate(Object... parameters) {
		this.setState(State.SEND_AGENTS_INFO); 	
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		switch (state) {
		case SEND_AGENTS_INFO:
			print ("Size agents: " + MonitoredAgentLauncher.getAgents().size());
			for (Agent ag : MonitoredAgentLauncher.getAgents()) {
				broadcastMessage(RoleReceiver.class, buildMessage(ag));
			}
			broadcastMessage(RoleReceiver.class, new StringMessage("over"));
			state = State.WAITING_ORDER;
			break;
		case WAITING_ORDER:
			Message order = getMessage();
			if (order!= null) {
				if(order instanceof StringMessage) {
					if(((StringMessage) order).getContent() == "AGENT_INFOS") {
						state = State.SEND_AGENTS_INFO;
					}
				}
			}
		break;	
		default:
			print("error default");
		}
		
		
//		switch (state) {		
//		
//		case SENDING:
//				StringMessage m = new StringMessage("Hello");
//				this.broadcastMessage(RoleReceiver.class,m);
//				sentMsg++;
//				if(sentMsg == nbMessageToSend) {
//					state = State.WAITING_ORDER;
//					broadcastMessage(RoleReceiver.class, new StringMessage("over"));
//				}
//		break;
//		case WAITING_ORDER:
//			Message order = getMessage();
//			if (order!= null) {
//				if(order instanceof StringMessage) {
//					if(((StringMessage) order).getContent() == "send") {
//						sentMsg = 0;
//						state = State.SENDING;
//					}
//				}
//			}
//		break;	
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
