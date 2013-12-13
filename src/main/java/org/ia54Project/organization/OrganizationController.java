package org.ia54Project.organization;

import org.janusproject.kernel.crio.core.CRIOContext;
import org.janusproject.kernel.crio.core.Organization;

public class OrganizationController extends Organization{

	public OrganizationController(CRIOContext crioContext) {
		super(crioContext);
		addRole(RoleGUIManager.class);
		addRole(RoleControlManager.class);

		
	}

}
