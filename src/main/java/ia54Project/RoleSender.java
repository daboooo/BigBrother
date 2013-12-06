package ia54Project;

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
		this.setState(State.SENDING); 	
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		switch (state) {
		
		case SENDING:
				StringMessage m = new StringMessage("Hello");
				this.broadcastMessage(RoleReceiver.class,m);
				sentMsg++;
				if(sentMsg == nbMessageToSend) {
					state = State.WAITING_ORDER;
					broadcastMessage(RoleReceiver.class, new StringMessage("over"));
				}
		break;
		case WAITING_ORDER:
			Message order = getMessage();
			if (order!= null) {
				if(order instanceof StringMessage) {
					if(((StringMessage) order).getContent() == "send") {
						sentMsg = 0;
						state = State.SENDING;
					}
				}
			}
		break;
		
		}
		return StatusFactory.ok(this);
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}


	enum State {
		SENDING,
		WAITING_ORDER
	}

}
