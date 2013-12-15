package org.ia54Project.dataModel;

import java.util.Vector;

import org.janusproject.kernel.crio.core.GroupAddress;

public class GroupModel {
	private GroupAddress groupAddress;
	private Vector<RoleModel> roleList;

	public GroupAddress getGroupAddress() {
		return groupAddress;
	}

	public void setGroupAddress(GroupAddress groupAddress) {
		this.groupAddress = groupAddress;
	}

	public GroupModel() {

	}

	public Vector<RoleModel> getRoleList() {
		return roleList;
	}

	public void setRoleList(Vector<RoleModel> roleList) {
		this.roleList = roleList;
	}
	
	@Override
	public String toString() {
		return getGroupAddress().toString();
	}
}
