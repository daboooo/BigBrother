package org.ia54Project.swingGUI;

import java.util.Collection;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.ia54Project.dataModel.AgentModel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.MachineModel;
import org.ia54Project.dataModel.OrganizationModel;
import org.ia54Project.dataModel.RoleModel;

public class TreeUtil {
	public static void buildKernelModelNode(DefaultMutableTreeNode machineNode, MachineModel machineModel) {
		Collection<KernelModel> kList = machineModel.getKernelList();
		if(kList != null) {
			for (KernelModel kernelModel : kList) {
				//original Tutorial
				DefaultMutableTreeNode kernelNode = new DefaultMutableTreeNode(kernelModel);
				machineNode.add(kernelNode);

				buildOrganizationModelNode(kernelNode, kernelModel);
				buildLonelyAgentModelNode(kernelNode, kernelModel);
			}
		}
	}

	public static void buildOrganizationModelNode(DefaultMutableTreeNode kernelNode, KernelModel kernelModel) {
		Collection<OrganizationModel> orgs = kernelModel.getOrgList();
		if(orgs!=null) {
			for(OrganizationModel orgModel : orgs) {
				DefaultMutableTreeNode orgNode = new DefaultMutableTreeNode(orgModel);
				kernelNode.add(orgNode);
				buildRoleModelNode(orgNode, orgModel);
			}
		}
	}

	public static void buildLonelyAgentModelNode(DefaultMutableTreeNode kernelNode, KernelModel kernelModel) {

		Collection<AgentModel> lonelyAgents = kernelModel.getLonelyAgentList();
		if(lonelyAgents != null) {
			for (AgentModel agentModel : lonelyAgents) {
				DefaultMutableTreeNode lonelyAgentNode = new DefaultMutableTreeNode(agentModel);
				kernelNode.add(lonelyAgentNode);
			}
		}
	}

	public static void buildRoleModelNode(DefaultMutableTreeNode orgNode, OrganizationModel orgModel) {
		Collection<RoleModel> roles = orgModel.getRoleList();
		if(roles != null) {
			for (RoleModel roleModel : roles) {
				DefaultMutableTreeNode roleNode = new DefaultMutableTreeNode(roleModel);
				orgNode.add(roleNode);
				buildAgentModelNode(roleNode, roleModel);
			}
		}
	}

	public static void buildAgentModelNode(DefaultMutableTreeNode roleNode, RoleModel roleModel) {
		Collection<AgentModel> agents = roleModel.getPlayerList();
		if(agents != null) {
			for (AgentModel agentModel : agents) {
				DefaultMutableTreeNode agentNode = new DefaultMutableTreeNode(agentModel);
				roleNode.add(agentNode);
			}
		}
	}

	public static void setTreeState(JTree tree, boolean expanded) {
		Object root = tree.getModel().getRoot();
		setTreeState(tree, new TreePath(root),expanded);
	}

	public static void setTreeState(JTree tree, TreePath path, boolean expanded) {
		Object lastNode = path.getLastPathComponent();
		for (int i = 0; i < tree.getModel().getChildCount(lastNode); i++) {
			Object child = tree.getModel().getChild(lastNode,i);
			TreePath pathToChild = path.pathByAddingChild(child);
			setTreeState(tree,pathToChild,expanded);
		}
		if (expanded) 
			tree.expandPath(path);
		else
			tree.collapsePath(path);


	}
}
