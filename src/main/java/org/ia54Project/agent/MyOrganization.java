package org.ia54Project.agent;

import org.janusproject.kernel.crio.core.CRIOContext;
import org.janusproject.kernel.crio.core.Organization;

public class MyOrganization extends Organization{

	public MyOrganization(CRIOContext crioContext) {
		super(crioContext);
		addRole(MyRole.class);
	}

}


