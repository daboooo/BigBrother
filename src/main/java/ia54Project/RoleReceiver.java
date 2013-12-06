package ia54Project;

import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;


public class RoleReceiver  extends Role{

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
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		Message m = getMessage();
		if( m != null) {
			print(m);
			if( m instanceof StringMessage) {
				if(((StringMessage) m).getContent() == "over") {
					//this.broadcastMessage(RoleSender.class, new StringMessage("send"));
				}
			}
		}
		else {
			//print("waiting message");
		}
		return StatusFactory.ok(this);
	}

	enum State {
		RECEIVING
	}
}
