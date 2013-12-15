package org.ia54Project.dataModel;

import java.util.Collection;
import java.util.Collection;

import org.janusproject.kernel.address.AgentAddress;

public class KernelModel {
	private String name;
	private AgentAddress kernelAddress;
	private Collection<OrganizationModel> orgList;
	private Collection<AgentModel> lonelyAgentList;
	
	public Collection<OrganizationModel> getOrgList() {
		return orgList;
	}

	public void setOrgList(Collection<OrganizationModel> orgList) {
		this.orgList = orgList;
	}

	public Collection<AgentModel> getLonelyAgentList() {
		return lonelyAgentList;
	}

	public void setLonelyAgentList(Collection<AgentModel> lonelyAgentList) {
		this.lonelyAgentList = lonelyAgentList;
	}

	public KernelModel() {
		
	}
	
	public KernelModel(String name, AgentAddress kernelAddress) {
		this.name = name;
		this.kernelAddress = kernelAddress;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AgentAddress getKernelAddress() {
		return kernelAddress;
	}
	public void setKernelAddress(AgentAddress kernelAddress) {
		this.kernelAddress = kernelAddress;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
	
}
