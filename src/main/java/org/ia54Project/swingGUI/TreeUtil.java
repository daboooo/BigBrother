package org.ia54Project.swingGUI;

import java.util.Collection;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.ia54Project.dataModel.AgentModel;
import org.ia54Project.dataModel.GroupModel;
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
				buildGroupNode(orgNode, orgModel);
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

	public static void buildGroupNode(DefaultMutableTreeNode orgNode, OrganizationModel orgModel) {
		Collection<GroupModel> groups = orgModel.getGroupList();
		if(groups != null) {
			for (GroupModel groupModel : groups) {
				DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(groupModel);
				orgNode.add(groupNode);
				buildRoleModelNode(groupNode, groupModel);
			}
		}
	}
	
	public static void buildRoleModelNode(DefaultMutableTreeNode orgNode, GroupModel groupModel) {
		Collection<RoleModel> roles = groupModel.getRoleList();
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
	
	// return the treePath leading to the object to find in the node
	// the object to find is searched in node and his child 
	// to determine a node match the object, the test is done between the userContent in the node and the object based on there toString equivalent
	public static TreePath findInNode(DefaultMutableTreeNode node, Object toFind) {
		DefaultMutableTreeNode root = node;
		//System.out.println(root);
		for (int i = 0; i < root.getChildCount(); i++)
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
			System.out.println("search in:" + child.getUserObject());
			if (child.getUserObject().toString().equals(toFind.toString())) {
				System.out.println("FOUND");
				System.out.println("Path :" + child.getPath());
				return new TreePath(((DefaultMutableTreeNode) child.getParent()).getPath());
			} else  {
				if(!root.getChildAt(i).isLeaf())
					return findInNode((DefaultMutableTreeNode) root.getChildAt(i), toFind);
			}
		}
		return null;	
	}

	// attempt to expand a tree to a specified path
	// the given path can be a path from an other tree
	// the key here is that we consider equals 2 objects when there toString method give the same result
	public static void attemptToExpandToPath(TreePath pathFromOldTree, JTree newTree) {
		Object[] objs = pathFromOldTree.getPath();
		Object rootI = newTree.getModel().getRoot();
		if(!(rootI instanceof DefaultMutableTreeNode) || objs == null)
			return;
		expandIfFound(objs, (DefaultMutableTreeNode) rootI, newTree);
		
	}
	
	public static void expandIfFound(Object toFind[], DefaultMutableTreeNode node, JTree tree) {
		if(toFind != null) {
			if(compareNodeWithObjet(node,toFind[0])) {
				if(node.isLeaf()) {
					System.out.println("FOUND EXPAND to :" + node.getPath());
					tree.expandPath(new TreePath(node.getPath()));
				} else{
					if(toFind.length > 1) { // seek in child
						
						// build a new table of object to find based on toFind minus the object we found
						Object[] newToFind = new Object[toFind.length-1];
						for(int i = 1 ; i< toFind.length ; i++) {
							newToFind[i-1] = toFind[i];
						}
						
						// search the new table in every child
						for(int i = 0; i < node.getChildCount(); i++) {
							expandIfFound(newToFind, (DefaultMutableTreeNode) node.getChildAt(i), tree);
						}
					} else { // nothing more to seek for
						tree.expandPath(new TreePath(node.getPath()));
					}
				}
			} else {
				
			}
		}
	}
	
	// return true if the userObject of node is equals to obj based on toString
	public static Boolean compareNodeWithObjet(DefaultMutableTreeNode node, Object obj) {
		System.out.println("compare " + node.getUserObject().toString() + " and: " + obj.toString());
		return(node.getUserObject().toString().equals(obj.toString())) ;
	}
	
	public static void expandToObject(JTree tree, Object obj) {
		
		// step 1: find the object in the current tree
		System.out.println("TO find:" + obj);
		TreePath expandPath = findInNode((DefaultMutableTreeNode) tree.getModel().getRoot(), obj);
		// step2: expand the path to the object
		if(expandPath != null) {
			System.out.println("attempt to expand to :" + expandPath);
			tree.expandPath(expandPath);
		}
		
	}
	
}
