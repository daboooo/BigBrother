package org.ia54Project.dataModel;

import java.util.Collection;
import java.util.Vector;

import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;

/**
 * Storage class, contains all information related to a RoleModel
 * @author  Arnaud Roblin, Julien Benichou
 *
 */
public class RoleModel implements Cloneable{
	Class<? extends Role> classe;
	RoleAddress roleAddress;
	GroupAddress groupAdress;
	Collection<AgentModel> playerList;
	
	public RoleModel() {
		playerList = new Vector<AgentModel>();
	}
	
	public RoleModel(RoleAddress roleAddress, GroupAddress groupAddress, Class<? extends Role> classe) {
		this.classe = classe;
		this.roleAddress = roleAddress;
		this.groupAdress = groupAddress;
		playerList = new Vector<AgentModel>();
	}
	
	public RoleModel(Class<? extends Role> classe) {
		playerList = new Vector<AgentModel>();
		setClasse(classe);
	}
	
	public RoleModel(RoleAddress roleAddress, Class<? extends Role> roleClass, AgentModel agentModel) {
		setRoleAddress(roleAddress);
		playerList = new Vector<AgentModel>();
		playerList.add(agentModel);
	}

	public Class<? extends Role> getClasse() {
		return classe;
	}

	public void setClasse(Class<? extends Role> classe) {
		this.classe = classe;
	}

	public RoleAddress getRoleAddress() {
		return roleAddress;
	}
	public void setRoleAddress(RoleAddress roleAddress) {
		this.roleAddress = roleAddress;
	}
	public GroupAddress getGroupAdress() {
		return groupAdress;
	}
	public void setGroupAdress(GroupAddress groupAdress) {
		this.groupAdress = groupAdress;
	}
	public Collection<AgentModel> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(Collection<AgentModel> playerList) {
		this.playerList = playerList;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getClasse().toString();
	}
	
	public RoleModel clone() {
		RoleModel roleModel = new RoleModel();
		
		roleModel.setClasse(this.getClasse());
		roleModel.setGroupAdress(this.getGroupAdress());
		roleModel.setRoleAddress(this.getRoleAddress());
		Vector<AgentModel> newVector = new Vector<AgentModel>();
		for (AgentModel agentModel : this.getPlayerList()) {
			newVector.add(agentModel.clone());
		}
		roleModel.setPlayerList(newVector);
		
		return roleModel;
	}
	
}
