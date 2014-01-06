package org.ia54Project.dataModel;

import java.util.Collection;
import java.util.Vector;

import javax.management.relation.RoleList;

import org.janusproject.kernel.crio.core.GroupAddress;

/**
 * Storage class, contains all information related to a group
 * @author  Arnaud Roblin, Julien Benichou
 *
 */
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
		Vector<RoleModel> newVector = new Vector<RoleModel>();
		for (RoleModel roleModel : this.getRoleList()) {
			newVector.add(roleModel.clone());
		}
		groupModel.setRoleList(newVector);
		
		return groupModel;
	}
}
