package org.ia54Project.dataModel;

import java.util.Vector;

import org.janusproject.kernel.crio.core.GroupAddress;

public class OrganizationModel {
	String name;
	Vector<GroupAddress> groupAdresses; // getGroupAddresses()
	Integer nbInstance; //getGroupCount()
	
	Vector<RoleModel> roleList;
	

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

	public Vector<GroupAddress> getGroupAdresses() {
		return groupAdresses;
	}

	public void setGroupAdresses(Vector<GroupAddress> groupAdresses) {
		this.groupAdresses = groupAdresses;
	}

	public Integer getNbInstance() {
		return nbInstance;
	}

	public void setNbInstance(Integer nbInstance) {
		this.nbInstance = nbInstance;
	}

	public Vector<RoleModel> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(Vector<RoleModel> roleList) {
		this.roleList = roleList;
	}

}
