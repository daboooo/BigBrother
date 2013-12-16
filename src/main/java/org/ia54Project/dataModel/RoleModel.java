package org.ia54Project.dataModel;

import java.util.Collection;
import java.util.Vector;

import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;

public class RoleModel {
	Class<? extends Role> classe;
	RoleAddress roleAddress;
	GroupAddress groupAdress;
	Boolean hasMesage;
	Boolean isReleased;
	Boolean isSleeping;
	Collection<AgentModel> playerList;
	
	public RoleModel(RoleAddress roleAddress, GroupAddress groupAddress, Class<? extends Role> classe) {
		this.classe = classe;
		this.roleAddress = roleAddress;
		this.groupAdress = groupAddress;
		hasMesage = false;
		isReleased = false;
		isSleeping = false;
		playerList = new Vector<AgentModel>();
	}
	
	public RoleModel(Class<? extends Role> classe) {
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
	public Boolean getHasMesage() {
		return hasMesage;
	}
	public void setHasMesage(Boolean hasMesage) {
		this.hasMesage = hasMesage;
	}
	public Boolean getIsReleased() {
		return isReleased;
	}
	public void setIsReleased(Boolean isReleased) {
		this.isReleased = isReleased;
	}
	public Boolean getIsSleeping() {
		return isSleeping;
	}
	public void setIsSleeping(Boolean isSleeping) {
		this.isSleeping = isSleeping;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getClasse().toString();
	}
	
}
