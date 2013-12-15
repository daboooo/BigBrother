package org.ia54Project.dataModel;

import java.util.Collection;

import org.janusproject.kernel.crio.core.GroupAddress;

public class OrganizationModel {
	String name;
	Collection<GroupAddress> groupAdresses; // getGroupAddresses()
	Integer nbInstance; //getGroupCount()
	
	Collection<RoleModel> roleList;
	

	public OrganizationModel() {
		
	}

	public OrganizationModel(String name) {
		setName(name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<GroupAddress> getGroupAdresses() {
		return groupAdresses;
	}

	public void setGroupAdresses(Collection<GroupAddress> groupAdresses) {
		this.groupAdresses = groupAdresses;
	}

	public Integer getNbInstance() {
		return nbInstance;
	}

	public void setNbInstance(Integer nbInstance) {
		this.nbInstance = nbInstance;
	}

	public Collection<RoleModel> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(Collection<RoleModel> roleList) {
		this.roleList = roleList;
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
