package ia54Project;

import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;


public class RoleSender extends Role{

	private State state;
	
	@Override
	public Status activate(Object... parameters) {
		this.setState(State.SENDING); 	
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		StringMessage m = new StringMessage("Hello");
		this.broadcastMessage(RoleReceiver.class,m);
		//this.sendMessage(role, message)
		print("BROADCASTING");
		// TODO Auto-generated method stub
		return StatusFactory.ok(this);
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	enum State {
		SENDING
	}

}
