package org.ia54Project.dataModel;

import java.util.Collection;
import java.util.Vector;

import org.janusproject.kernel.address.AgentAddress;

/**
 * Storage class, contains all information related to a kernel
 * @author  Arnaud Roblin, Julien Benichou
 *
 */
public class KernelModel implements Cloneable{
	private String kname;
	private AgentAddress kernelAddress;
	private Collection<OrganizationModel> orgList;
	private Collection<AgentModel> lonelyAgentList;
	
	public KernelModel() {
		setName("Not fully initialized");
		setOrgList(new Vector<OrganizationModel>());
		lonelyAgentList = new Vector<AgentModel>();
	}
	
	public KernelModel(AgentAddress kernelAddress) {
		setName("Not fully initialized");
		setKernelAddress(kernelAddress);
		setOrgList(new Vector<OrganizationModel>());
		setLonelyAgentList(new Vector<AgentModel>());
		
	}
	
	public KernelModel(String kname, AgentAddress kernelAddress) {
		setName(kname);
		setKernelAddress(kernelAddress);
		setOrgList(new Vector<OrganizationModel>());
		setLonelyAgentList(new Vector<AgentModel>());
	}
	
	public KernelModel(String kname, AgentAddress kernelAddress,
			Collection<OrganizationModel> orgList,
			Collection<AgentModel> lonelyAgentList) {
		setName(kname);
		setKernelAddress(kernelAddress);
		setOrgList(orgList);
		setLonelyAgentList(lonelyAgentList);
	}
	
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
	
	public String getName() {
		return kname;
	}
	public void setName(String kname) {
		this.kname = kname;
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
	
	public KernelModel clone() {
		KernelModel kernelModel = new KernelModel();
		
		kernelModel.setKernelAddress(this.getKernelAddress());
		kernelModel.setName(this.getName());
		Vector<OrganizationModel> newVector = new Vector<OrganizationModel>();
		for (OrganizationModel organizationModel : this.getOrgList()) {
			newVector.add(organizationModel.clone());
		}
		kernelModel.setOrgList(newVector);
		Vector<AgentModel> newVector2 = new Vector<AgentModel>();
		for (AgentModel agentModel : this.getLonelyAgentList()) {
			newVector2.add(agentModel.clone());
		}
		kernelModel.setLonelyAgentList(newVector2);
		
		return kernelModel;
	}
	
}
