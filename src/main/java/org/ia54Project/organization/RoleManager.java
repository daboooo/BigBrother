package org.ia54Project.organization;

import org.ia54Project.organization.RoleCollecteur.State;
import org.janusproject.kernel.agentsignal.Signal;
import org.janusproject.kernel.agentsignal.SignalPolicy;
import org.janusproject.kernel.crio.core.HasAllRequiredCapacitiesCondition;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;


public class RoleManager  extends Role{

	private State state;
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public Status activate(Object... parameters) {
		this.setState(State.RECEIVING); 
		addObtainCondition(new HasAllRequiredCapacitiesCondition(CapacityGetAgentRepository.class));
		getSignalManager().setPolicy(SignalPolicy.FIRE_SIGNAL);
		
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		Message m = getMessage();
		//print("je suis dans le Role Manager et je recoit : " + m);
		
		//print("Je suis dans le role manager et j'écris dans la mémoire : " + m);
		putMemorizedData("MY_DATA", m);
		
		Signal signal = new Signal(this, "MY_SIGNAL", m);
		getSignalManager().fireSignal(signal);
		
//		Message m = getMessage();
//		if( m != null) {
//			//print("MailboxSize: " + getMailboxSize());
//			print(m);
//			if( m instanceof StringMessage) {
//				if(((StringMessage) m).getContent() == "over") {
//					this.broadcastMessage(RoleCollecteur.class, new StringMessage("AGENT_INFOS"));
//				}
//			}
//		}
//		else {
//			//print("waiting message");
//		}
		return StatusFactory.ok(this);
	}

	enum State {
		RECEIVING
	}
}
