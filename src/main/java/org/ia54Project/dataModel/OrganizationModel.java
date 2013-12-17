package org.ia54Project.dataModel;

import java.util.Collection;
import java.util.Vector;

import org.janusproject.kernel.crio.core.Organization;

public class OrganizationModel implements Cloneable{
	private Class<? extends Organization> classe;
	private Integer nbInstance;
	private Collection<GroupModel> groupList;


	public OrganizationModel() {
		setGroupList(new Vector<GroupModel>());
	}
	
	public OrganizationModel(Class<? extends Organization> classe) {
		setClasse(classe);
		setNbInstance(0);
		setGroupList(new Vector<GroupModel>());
	}

	public OrganizationModel(Organization organization, GroupModel groupModel) {
		setGroupList( new Vector<GroupModel>());
		getGroupList().add(groupModel);
		setClasse(organization.getClass());
		setNbInstance(organization.getGroupCount());
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

	public Collection<GroupModel> getGroupList() {
		return groupList;
	}
	
	public void setGroupList(Vector<GroupModel> groupList) {
		this.groupList = groupList;
	}
	
	@Override
	public String toString() {
		return getClasse().toString();
	}
	
	public OrganizationModel clone() {
		OrganizationModel organizationModel = new OrganizationModel();
		
		organizationModel.setClasse(this.getClasse());
		organizationModel.setNbInstance(new Integer(this.getNbInstance()));
		
		Vector<GroupModel> newVector = new Vector<GroupModel>();
		for (GroupModel groupModel : this.getGroupList()) {
			newVector.add(groupModel.clone());
		}
		organizationModel.setGroupList(newVector);
		
		return organizationModel;
	}

}
