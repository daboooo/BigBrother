package org.ia54Project.dataModel;

import java.util.Vector;

import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;

public class AgentModel implements Cloneable{
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

	
	public AgentModel(AgentAddress address, AgentAddress creator) {
		setAddress(address);
		setCreationDate(0);
		setCreatorAddress(creator);
		setIsAlive(false);
		setIsCompound(false);
		setIsHeavyAgent(false);
		setIsRecruitementAllowed(false);
		setIsSleeping(false);
		setListOfRole( new Vector<String>());
		setName("Not fully initialized");
	}

	public AgentModel() {
		super();
		listOfRole = new Vector<String>();
	}
	
	public AgentModel(String name, AgentAddress adr) {
		setName(name);
		setAddress(adr);
	}
	
	public AgentModel(Agent agent) {
		name = agent.getName();
		address = agent.getAddress();
		creationDate = agent.getCreationDate();
		creatorAddress = agent.getCreator();
		isHeavyAgent = agent.isHeavyAgent();
		isAlive = agent.isAlive();
		isCompound = agent.isCompound();
		isRecruitementAllowed = agent.isRecruitmentAllowed();
		isSleeping = agent.isSleeping();
		canCommitSuicide = agent.canCommitSuicide();
		listOfRole = new Vector<String>();
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

	public void removeRoleFromList(RoleModel roleModel) {
		String roleClass = roleModel.getClasse().toString();
		Vector<String> getListOfRoles = this.getListOfRole();
		for (String roleString : getListOfRoles) {
			if(roleClass == roleString) {
				getListOfRoles.remove(roleString);
			}
		}
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
	
	public AgentModel clone() {
		AgentModel agentModel = new AgentModel();
		
		agentModel.setAddress(this.getAddress());
		agentModel.setCanCommitSuicide(new Boolean(this.getCanCommitSuicide()));
		agentModel.setCreationDate(this.getCreationDate());
		agentModel.setCreatorAddress(this.getCreatorAddress());
		agentModel.setIsAlive(new Boolean(this.getIsAlive()));
		agentModel.setIsCompound(new Boolean(this.getIsCompound()));
		agentModel.setIsHeavyAgent(new Boolean(this.getIsHeavyAgent()));
		agentModel.setIsRecruitementAllowed(new Boolean(this.getIsRecruitementAllowed()));
		agentModel.setIsSleeping(new Boolean(this.getIsSleeping()));
		agentModel.setListOfRole(new Vector<String>(this.getListOfRole()));
		agentModel.setName(new String(this.getName()));
		
		return agentModel;
	}



	
}
