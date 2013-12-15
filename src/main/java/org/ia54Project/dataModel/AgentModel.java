package org.ia54Project.dataModel;

import java.util.Vector;

import org.janusproject.kernel.address.AgentAddress;

public class AgentModel {
	// add container for all required agent info
	private String name;
	private AgentAddress address;
	private float creationDate;
	private AgentAddress creatorAddress;
	private Boolean isHeavyAgent; // if false, is a lightAgent
	private Boolean isAlive;
	private Boolean isCompound;
	private Boolean isRecruitementAllowed;
	private Boolean isSleeping;
	private Boolean canCommitSuicide;
	private Vector<String> listOfRole;

	
	public AgentModel() {
		
	}

	public AgentModel(String name) {
		setName(name);
	}

	
	public AgentModel(String name, AgentAddress adr) {
		setName(name);
		setAddress(adr);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AgentAddress getAddress() {
		return address;
	}

	public void setAddress(AgentAddress address) {
		this.address = address;
	}

	public float getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(float creationDate) {
		this.creationDate = creationDate;
	}

	public AgentAddress getCreatorAddress() {
		return creatorAddress;
	}

	public void setCreatorAddress(AgentAddress creatorAddress) {
		this.creatorAddress = creatorAddress;
	}

	public Boolean getIsHeavyAgent() {
		return isHeavyAgent;
	}

	public void setIsHeavyAgent(Boolean isHeavyAgent) {
		this.isHeavyAgent = isHeavyAgent;
	}

	public Boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Boolean getIsCompound() {
		return isCompound;
	}

	public void setIsCompound(Boolean isCompound) {
		this.isCompound = isCompound;
	}

	public Boolean getIsRecruitementAllowed() {
		return isRecruitementAllowed;
	}

	public void setIsRecruitementAllowed(Boolean isRecruitementAllowed) {
		this.isRecruitementAllowed = isRecruitementAllowed;
	}

	public Boolean getIsSleeping() {
		return isSleeping;
	}

	public void setIsSleeping(Boolean isSleeping) {
		this.isSleeping = isSleeping;
	}

	public Boolean getCanCommitSuicide() {
		return canCommitSuicide;
	}

	public void setCanCommitSuicide(Boolean canCommitSuicide) {
		this.canCommitSuicide = canCommitSuicide;
	}

	public Vector<String> getListOfRole() {
		return listOfRole;
	}

	public void setListOfRole(Vector<String> listOfRole) {
		this.listOfRole = listOfRole;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
//	getRoles()
//	--isAlive()
//	isCompound()
//	isPlayingRole()
//	isRecruitmentAllowed()
//	isSleeping()
//	canCommitSuicide()
//	getCredentials()
}
