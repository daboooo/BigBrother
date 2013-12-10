package ia54Project;

import org.janusproject.kernel.crio.core.CRIOContext;
import org.janusproject.kernel.crio.core.Organization;

public class OrganizationNet extends Organization{

	public OrganizationNet(CRIOContext crioContext) {
		super(crioContext);
		
		addRole(RoleReceiver.class);
		addRole(RoleSender.class);
	}

}
