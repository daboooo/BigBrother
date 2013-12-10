package ia54Project;

import org.janusproject.kernel.crio.core.CRIOContext;
import org.janusproject.kernel.crio.core.Organization;

public class OrganizationManager extends Organization{

	public OrganizationManager(CRIOContext crioContext) {
		super(crioContext);
		
		addRole(RoleManager.class);
		addRole(RoleCollecteur.class);
		addRole(RoleExecutant.class);
	}

}
