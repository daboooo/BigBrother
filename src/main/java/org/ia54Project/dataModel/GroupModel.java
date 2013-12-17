package org.ia54Project.dataModel;

import java.util.Collection;
import java.util.Vector;

import javax.management.relation.RoleList;

import org.janusproject.kernel.crio.core.GroupAddress;

public class GroupModel implements Cloneable{
	private GroupAddress groupAddress;
	private Collection<RoleModel> roleList;
	
	public GroupModel() {
		roleList = new Vector<RoleModel>();
	}
	
	public GroupModel(GroupAddress groupAddress) {
		roleList = new Vector<RoleModel>();
		this.groupAddress = groupAddress;
	}

	public GroupAddress getGroupAddress() {
		return groupAddress;
	}

	public void setGroupAddress(GroupAddress groupAddress) {
		this.groupAddress = groupAddress;
	}


	public Collection<RoleModel> getRoleList() {
		return roleList;
	}

	public void setRoleList(Vector<RoleModel> roleList) {
		this.roleList = roleList;
	}
	
	@Override
	public String toString() {
		return getGroupAddress().toString();
	}
	
	public GroupModel clone() {
		GroupModel groupModel = new GroupModel();
		
		groupModel.setGroupAddress(this.getGroupAddress());
		groupModel.setRoleList(new Vector<RoleModel>(this.getRoleList()));
		
		return groupModel;
	}
}
