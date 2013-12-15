package org.ia54Project.dataModel;

import java.util.Collection;

import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.RoleAddress;
import org.janusproject.kernel.mailbox.Mailbox;

public class RoleModel {
	String name;
	RoleAddress roleAddress;
	GroupAddress groupAdress;
	Mailbox box;
	Boolean hasMesage;
	Boolean isReleased;
	Boolean isSleeping;
	Collection<AgentModel> playerList;
	
	
	public RoleModel() {
		
	}
	
	public RoleModel(String name) {
		setName(name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public Mailbox getBox() {
		return box;
	}
	public void setBox(Mailbox box) {
		this.box = box;
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
		return getName();
	}
//	getLeaveConditions()
//	getObtainConditions()

}
