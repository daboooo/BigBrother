package ia54Project;

import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.status.Status;

public class RoleSender extends Role{

	@Override
	public Status live() {
		System.out.println("Je suis le role sender");
		return null;
	}

}
