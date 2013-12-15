package org.ia54Project.dataModel;

import java.util.Vector;

import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.Organization;

public class OrganizationModel {
	private Class<? extends Organization> classe;
	//private Vector<GroupAddress> groupAdresses;
	private Integer nbInstance; //getGroupCount()
	private Vector<GroupModel> groupList;

	public OrganizationModel() {
		
	}

	public OrganizationModel(Class<? extends Organization> classe) {
		setClasse(classe);
	}
	
	
	

//	public Vector<GroupAddress> getGroupAdresses() {
//		return groupAdresses;
//	}
//
//	public void setGroupAdresses(Vector<GroupAddress> groupAdresses) {
//		this.groupAdresses = groupAdresses;
//	}

	public OrganizationModel(Organization organization, GroupModel groupModel) {
		groupList = new Vector<GroupModel>();
		groupList.add(groupModel);
		
		classe = organization.getClass();
		nbInstance = organization.getGroupCount();
	}

	public Class<? extends Organization> getClasse() {
		return classe;
	}

	public void setClasse(Class<? extends Organization> classe) {
		this.classe = classe;
	}

	public Integer getNbInstance() {
		return nbInstance;
	}

	public void setNbInstance(Integer nbInstance) {
		this.nbInstance = nbInstance;
	}

	public Vector<GroupModel> getGroupList() {
		return groupList;
	}
	
	public void setGroupList(Vector<GroupModel> groupList) {
		this.groupList = groupList;
	}

}
