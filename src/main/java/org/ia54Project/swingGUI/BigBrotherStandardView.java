package org.ia54Project.swingGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.ia54Project.agent.BigBrotherChannel;
import org.ia54Project.dataModel.AgentModel;
import org.ia54Project.dataModel.BigBrotherDataChangeEvent;
import org.ia54Project.dataModel.BigBrotherListener;
import org.ia54Project.dataModel.DataModel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.MachineModel;
import org.ia54Project.dataModel.OrganizationModel;
import org.ia54Project.dataModel.RoleModel;
import org.ia54Project.swingGUI.view.BigBrotherAgentView;
import org.ia54Project.swingGUI.view.BigBrotherDetailView;
import org.ia54Project.swingGUI.view.BigBrotherGroupView;
import org.ia54Project.swingGUI.view.BigBrotherKernelView;
import org.ia54Project.swingGUI.view.BigBrotherMachineView;
import org.ia54Project.swingGUI.view.BigBrotherOrganizationView;
import org.ia54Project.swingGUI.view.BigBrotherRoleView;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.RoleAddress;

public class BigBrotherStandardView extends JSplitPane implements BigBrotherListener, TreeSelectionListener{
	private static final long serialVersionUID = -1511108527028192641L;
	private final WeakReference<BigBrotherChannel> bbChannel;
	private Dimension minimumSize = new Dimension(70,200);
	private JScrollPane leftScroll;
	private JViewport leftPane ;
	private JScrollPane rightScroll;
	private JViewport rightPane;
	private JTree tree;
	private BigBrotherDetailView detailView;
	private DataModel data;
	private Object treeSelectionAddress;



	BigBrotherStandardView (BigBrotherChannel bbChannel) {	
		super();
		this.bbChannel = new WeakReference<BigBrotherChannel>(bbChannel);
		bbChannel.addBigBrotherListener(this);
		this.data = (DataModel) bbChannel.getData();

		setDividerLocation(HORIZONTAL_SPLIT);
		setDoubleBuffered(true);
		setOneTouchExpandable(true);
		setDividerLocation(200);

		initLeftPane(bbChannel.getData());
		initRightPane();
		
	}

	public void initLeftPane(DataModel data) {
		leftScroll = new JScrollPane();
		leftPane = leftScroll.getViewport();
		leftPane.setMinimumSize(minimumSize);
		//Create a tree that allows one treeSelection at a time.
		tree = new JTree(buildTree());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		leftPane.add(tree,BorderLayout.LINE_START);
		tree.addTreeSelectionListener(this);
		TreeUtil.setTreeState(tree, true);
		this.setLeftComponent(leftPane);
	}

	public void initRightPane() {
		rightScroll = new JScrollPane();
		rightPane = rightScroll.getViewport();
//		detailView = new AgentView(tree,data);
		rightPane.add(detailView);
		rightPane.setMinimumSize(minimumSize);
		this.setRightComponent(rightScroll);
	}

	public TreeNode buildTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Hosts");
		Collection<MachineModel> vmm = data.getContent();
		if(vmm != null) {
			for (MachineModel machineModel : vmm) {
				DefaultMutableTreeNode machineNode = new DefaultMutableTreeNode(machineModel);
				root.add(machineNode);
				TreeUtil.buildKernelModelNode(machineNode, machineModel);
			}
		}
		return root;

	}
	

	
	public void onDataChange(BigBrotherDataChangeEvent evt) {
		printDebugStuff(data);
		rebuildTree();
		updateDetailView();
	}

	public void rebuildTree() {

		((DefaultTreeModel)tree.getModel()).setRoot(buildTree());
		((DefaultTreeModel)tree.getModel()).reload();
		TreeUtil.setTreeState(tree, true);
	}
	
	public void updateDetailView() {
		// attempt to retrieve the selected agent and his info
		if(treeSelectionAddress != null) {
			if(treeSelectionAddress instanceof AgentAddress) {
				// search in kernels
				Collection<KernelModel>  allKernels = data.getAllKernels();
				System.out.println("address selected: " + treeSelectionAddress);
				if(allKernels != null) {

					System.out.println(" !  ALL Kernel : " + allKernels);
					for (KernelModel kernelModel : allKernels) {
						if(treeSelectionAddress == kernelModel.getKernelAddress()) {
							System.out.println("address setted: " + kernelModel.getKernelAddress());
							((BigBrotherKernelView) detailView).setModel(kernelModel);
							return;
						}
					}
				}
				// search in normal agent
				Collection<AgentModel>  allAgents = data.getAllAgents();
				System.out.println("address selected: " + treeSelectionAddress);
				if(allAgents != null) {

					System.out.println(" !  ALL AGENT : " + allAgents);
					for (AgentModel agentModel : allAgents) {
						if(treeSelectionAddress == agentModel.getAddress()) {
							System.out.println("address setted: " + agentModel.getAddress());
							((BigBrotherAgentView) detailView).setModel(agentModel);
							return;
						}
					}
				}
			} else if(treeSelectionAddress instanceof RoleAddress) {
				Collection<RoleModel>  allRoles = data.getAllRoles();
				System.out.println("ROLEaddress selected: " + treeSelectionAddress);
				if(allRoles != null) {

					System.out.println(" !  ALL ROLE : " + allRoles);
					for (RoleModel roleModel : allRoles) {
						if(treeSelectionAddress == roleModel.getRoleAddress()) {
							System.out.println("address setted: " + roleModel.getRoleAddress());
							((BigBrotherRoleView) detailView).setModel(roleModel);
							return;
						}
					}
				}
			} else if(treeSelectionAddress instanceof String) {
				// Search in machines using ip address
				Collection<MachineModel>  allMachines = data.getAllMachines();
				System.out.println("Machine address selected: " + treeSelectionAddress);
				if(allMachines != null) {
					
					System.out.println(" !  ALL HOSTS : " + allMachines);
					for (MachineModel machineModel : allMachines) {
						if(treeSelectionAddress.equals(machineModel.getIp())) {
							System.out.println("address setted: " + machineModel.getIp());
							((BigBrotherMachineView) detailView).setModel(machineModel);
							return;
						}
					}
				}
				// search in organization
				Collection<OrganizationModel> allOrgs = data.getAllOrgs();
				if(allOrgs != null) {
					
					for (OrganizationModel organizationModel : allOrgs) {
						if(treeSelectionAddress.equals(organizationModel.getName())) {
							((BigBrotherOrganizationView) detailView).setModel(organizationModel);
							return;
						}
					}
				}
				
			} else if(treeSelectionAddress instanceof GroupAddress) {
				Collection<GroupAddress> allGroups = data.getAllGroups();
				if(allGroups != null) {
					System.out.println(" !  ALL GROUP : " + allGroups);
					for (GroupModel groupModel : allGroups) {
						if(treeSelectionAddress == groupModel.getGroupAddress()) {
							System.out.println("address setted: " + groupModel.getGroupAddress());
							((BigBrotherGroupView) detailView).setModel(groupModel);
							return;
						}
					}
				}
			}
		}
	}

	public void updateOnSelectDetailView(Object treeSelection) {
		System.out.println("updt");
		if(treeSelection != null) {
			if(treeSelection instanceof AgentModel) {
				System.out.println("updt AGENTMODEL");
				if(detailView instanceof BigBrotherAgentView) {
					treeSelectionAddress = ((AgentModel) treeSelection).getAddress();
					BigBrotherAgentView.class.cast(detailView).setModel((AgentModel) treeSelection);
				} else {
					removeDetailViewIfExists();
					detailView = new BigBrotherAgentView(AgentModel.class.cast(treeSelection));
					rightPane.add(detailView);
					this.setVisible(true);
				}
				treeSelectionAddress = ((AgentModel) treeSelection).getAddress();
			
			} else if (treeSelection instanceof RoleModel) {
				if(detailView instanceof BigBrotherRoleView) {
					BigBrotherRoleView.class.cast(detailView).setModel((RoleModel) treeSelection);
				} else {
					removeDetailViewIfExists();
					detailView = new BigBrotherRoleView(RoleModel.class.cast(treeSelection));
					rightPane.add(detailView);
					System.out.println("updt ROLEMODEL");
					this.setVisible(true);
				}
				treeSelectionAddress = ((RoleModel) treeSelection).getRoleAddress();
			} else if (treeSelection instanceof KernelModel){
				if(detailView instanceof BigBrotherKernelView) {
					BigBrotherKernelView.class.cast(detailView).setModel((KernelModel) treeSelection);
				} else {
					removeDetailViewIfExists();
					detailView = new BigBrotherKernelView(KernelModel.class.cast(treeSelection));
					rightPane.add(detailView);
					System.out.println("updt ROLEMODEL");
					this.setVisible(true);
				}
				treeSelectionAddress = ((KernelModel) treeSelection).getKernelAddress();
			} else if (treeSelection instanceof MachineModel){
				if(detailView instanceof BigBrotherMachineView) {
					BigBrotherMachineView.class.cast(detailView).setModel((MachineModel) treeSelection);
				} else {
					removeDetailViewIfExists();
					detailView = new BigBrotherMachineView(MachineModel.class.cast(treeSelection));
					rightPane.add(detailView);
					System.out.println("updt HOSTS");
					this.setVisible(true);
				}
				treeSelectionAddress = ((MachineModel) treeSelection).getIp();
			}  else if (treeSelection instanceof OrganizationModel){
				if(detailView instanceof BigBrotherOrganizationView) {
					BigBrotherOrganizationView.class.cast(detailView).setModel((OrganizationModel) treeSelection);
				} else {
					removeDetailViewIfExists();
					detailView = new BigBrotherOrganizationView(OrganizationModel.class.cast(treeSelection));
					rightPane.add(detailView);
					System.out.println("updt HOSTS");
					this.setVisible(true);
				}
				treeSelectionAddress = ((OrganizationModel) treeSelection).getName();
			} else if (treeSelection instanceof GroupModel){
				if(detailView instanceof BigBrotherGroupView) {
					BigBrotherGroupView.class.cast(detailView).setModel((GroupModel) treeSelection);
				} else {
					removeDetailViewIfExists();
					detailView = new BigBrotherGroupView(GroupModel.class.cast(treeSelection));
					rightPane.add(detailView);
					System.out.println("updt HOSTS");
					this.setVisible(true);
				}
				treeSelectionAddress = ((GroupModel) treeSelection).getGroupAddress();
				
			}
		}
	}
	
	public void removeDetailViewIfExists() {
		Component[] cp =  rightPane.getComponents();
		for(int i = 0; i< cp.length; i++) {
			if(cp[i] == detailView) {
				rightPane.remove(detailView);												
			}
		}
	}
	
	public void printDebugStuff(DataModel data) {
		System.out.println("change !");
		Collection<MachineModel> vmm = data.getContent();
		for (MachineModel machineModel : vmm) {
			Collection<KernelModel> kList = machineModel.getKernelList();
			for (KernelModel kernelModel : kList) {
				System.out.println("name: "+ kernelModel.getName());	
			}
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		if(e!= null) {
			TreePath selectionPath = e.getNewLeadSelectionPath();
			if(selectionPath != null) {
				Object component = selectionPath.getLastPathComponent();
				if(selectionPath != null) {
					if(component instanceof DefaultMutableTreeNode) {
						DefaultMutableTreeNode node = DefaultMutableTreeNode.class.cast(component);
						if(node != null) {
							Object treeSelection = node.getUserObject();
							System.out.println("Select " + treeSelection);
							updateOnSelectDetailView(treeSelection);
						}
					}
				}
			}
		}
		
	}

}