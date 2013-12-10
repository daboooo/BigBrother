package org.ia54Project.organization;

import org.janusproject.kernel.crio.core.CRIOContext;
import org.janusproject.kernel.crio.core.Organization;

public class OrganizationController extends Organization{

	protected OrganizationController(CRIOContext crioContext) {
		super(crioContext);
		addRole(RoleGUIManager.class);
		addRole(RoleControlManager.class);
	}

}
