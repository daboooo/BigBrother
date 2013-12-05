package ia54Project;

import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.status.Status;

public class RoleReceiver extends Role{
	
	@Override
	public Status live() {
		Message m = getMessage();
		
		if(m!=null) {
			print("j'ai qq chose");
		}
		else {
			//print("waiting");
		}
		
		return null;
	}

}
