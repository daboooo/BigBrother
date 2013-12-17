package org.ia54Project.dataModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.ia54Project.BigBrotherUtil;

public class DataModel {
	private Collection<MachineModel> content;
	private List<BigBrotherListener> _listeners = new ArrayList();
	private Collection<AgentModel> allAgents;
	private Collection<RoleModel> allRoles;
	private Collection<GroupModel> allGroups;
	private Collection<OrganizationModel> allOrgs;
	private Collection<KernelModel> allKernels;
	private Collection<MachineModel> allMachines;
	
	public synchronized void addEventListener(BigBrotherListener listener)  {
		_listeners.add(listener);
	}
	public synchronized void removeEventListener(BigBrotherListener listener)   {
		_listeners.remove(listener);
	}

	// call this method whenever you want to notify
	//the event listeners of the particular event
	private synchronized void fireEvent() {
		BigBrotherDataChangeEvent event = new BigBrotherDataChangeEvent(this);
		for (BigBrotherListener listener : _listeners) {
			listener.onDataChange(event);
			
		}
		
	}



	public DataModel() {
		content = new Vector<MachineModel>();
		setContent(content);
		
	}

	public DataModel(Collection<MachineModel> content) {
		super();
		setContent(content);
	}

	
	private void buildCollection() {
		allAgents = new Vector<AgentModel> ();
		allRoles = new Vector<RoleModel> ();
		allGroups = new Vector<GroupModel> ();
		allOrgs = new Vector<OrganizationModel> ();
		allKernels = new Vector<KernelModel> ();
		allMachines = new Vector<MachineModel> ();
		if(content == null)
			return;
		for (MachineModel machine : content) {
			allMachines.add(machine);
			if(machine.getKernelList() != null) {
				for (KernelModel kernel : machine.getKernelList()) {
						allKernels.add(kernel);
						if(kernel.getLonelyAgentList() != null) {
							for(AgentModel lonelyAgent : kernel.getLonelyAgentList()) {
								if(lonelyAgent != null) {
									allAgents.add(lonelyAgent);
								}
							}
							
							if( kernel.getOrgList() != null) {
								for(OrganizationModel org : kernel.getOrgList()) {
									allOrgs.add(org);
									if(org.getGroupList() != null) {
										for(GroupModel group: org.getGroupList()) {
											allGroups.add(group);
											if(group.getRoleList() != null) {
												for(RoleModel role: group.getRoleList()) {
													allRoles.add(role);
													if(role.getPlayerList() != null) {
														for(AgentModel agent: role.getPlayerList()) {
															allAgents.add(agent);
														}
													}
												}
												
											}
										}
									}
								}
							}
						}
				}
			}
		}
	}

	public synchronized Collection<MachineModel> getContent() {
		return content;
	}

	public synchronized void setContent(Collection<MachineModel> content) {
		this.content = content;
		buildCollection();
		fireEvent();
	}
	
	public Collection<AgentModel> getAllAgents() {
		return allAgents;
	}
	
	public Collection<RoleModel> getAllRoles() {
		return allRoles;
	}

	public Collection<GroupModel> getAllGroups() {
		return allGroups;
	}
	public void setAllGroups(Collection<GroupModel> allGroups) {
		this.allGroups = allGroups;
	}

	public Collection<OrganizationModel> getAllOrgs() {
		return allOrgs;
	}

	public Collection<KernelModel> getAllKernels() {
		return allKernels;
	}

	public Collection<MachineModel> getAllMachines() {
		return allMachines;
	}

	


}
