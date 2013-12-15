package org.ia54Project.dataModel;

import java.util.Vector;

import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;
import org.janusproject.kernel.mailbox.Mailbox;

public class RoleModel {
	Class<? extends Role> classe;
	RoleAddress roleAddress;
	GroupAddress groupAdress;
	//Mailbox box;
	Boolean hasMesage;
	Boolean isReleased;
	Boolean isSleeping;
	Vector<AgentModel> playerList;
	
	public RoleModel() {
		
	}
	
	public RoleModel(Class<? extends Role> classe) {
		setClasse(classe);
	}
	
	public RoleModel(RoleAddress roleAddress, Class<? extends Role> roleClass, AgentModel goodAgentModel) {
		setRoleAddress(roleAddress);
		playerList = new Vector<AgentModel>();
		playerList.add(goodAgentModel);
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
//	public Mailbox getBox() {
//		return box;
//	}
//	public void setBox(Mailbox box) {
//		this.box = box;
//	}
	public Vector<AgentModel> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(Vector<AgentModel> playerList) {
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

//	getLeaveConditions()
//	getObtainConditions()

}
