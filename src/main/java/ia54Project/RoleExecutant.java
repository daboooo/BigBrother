package ia54Project;

import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.status.Status;

public class RoleExecutant extends Role{

	@Override
	public Status live() {
		print("Je suis dans le role executant");
		return null;
	}

}
