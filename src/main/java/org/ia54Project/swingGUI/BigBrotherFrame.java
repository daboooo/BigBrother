package org.ia54Project.swingGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.lang.ref.WeakReference;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.arakhne.afc.vmutil.locale.Locale;
import org.ia54Project.BigBrotherUtil;
import org.ia54Project.agent.BigBrotherChannel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.MachineModel;
import org.janusproject.kernel.KernelListener;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.crio.core.GroupAddress;

public class BigBrotherFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = -1141199078767515860L;
	private final WeakReference<BigBrotherChannel> bbChannel;

	public BigBrotherFrame(AgentAddress guiManagerPlayer,GroupAddress orgAddr) {
		this(BigBrotherUtil.getChannelForGUI(guiManagerPlayer, orgAddr));
	}

	public BigBrotherFrame(BigBrotherChannel bbChannelArg) {
		assert(bbChannelArg!=null);
		this.bbChannel = new WeakReference<BigBrotherChannel>(bbChannelArg);
		setTitle("BigBrother Monitor UI"); //$NON-NLS-1$
		setPreferredSize(new Dimension(800, 600));		
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent winEvt) {
				quit();
			}
		});
		//getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu bigBrotherMenu = new JMenu(Locale.getString(BigBrotherFrame.class, "Menu")); //$NON-NLS-1$
		menuBar.add(bigBrotherMenu);

		JMenuItem item;

		item = new JMenuItem(Locale.getString(BigBrotherFrame.class, "Update view")); //$NON-NLS-1$
		item.setActionCommand("UPDATE_VIEW");
		item.addActionListener(this);
		bigBrotherMenu.add(item);

		item = new JMenuItem(Locale.getString(BigBrotherFrame.class, "Quit")); //$NON-NLS-1$
		item.setActionCommand("QUIT");
		item.addActionListener(this);
		bigBrotherMenu.add(item);
		JButton test = new JButton("yo");
		test.setActionCommand("UPDATE_PRESSED");
		test.addActionListener(this);
		getContentPane().add(test);
		// TAB
//		JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);
//		JComponent stdView = makeSplitPanel("");
//		tabPane.addTab("Standard View", stdView);
//		tabPane.setMnemonicAt(0, KeyEvent.VK_1);
//		
//		
//
//		JPanel orgView = new JPanel();
//		tabPane.addTab("Organizational View", orgView);
//		tabPane.setMnemonicAt(1, KeyEvent.VK_2);
//		getContentPane().add(tabPane);
		setSize(getPreferredSize());


	}

	protected void quit() {
		Kernels.killAll();
		setVisible(false);
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException e1) {
			//
		}
		System.exit(0);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("UPDATE_PRESSED")) {
			JButton src = (JButton)e.getSource();
			BigBrotherChannel chan = getBbChannel().get();
			if(chan == null) {
				src.setText("chan null");
			} else {
				src.setText(chan.getFirstAgentName());
			}
		} else if (cmd.equals("UPDATE_VIEW")) {

		} else if (cmd.equals("QUIT")) {
			quit();
		}
	}

	public WeakReference<BigBrotherChannel> getBbChannel() {
		return bbChannel;
	}

	// GUI 
	protected JComponent makeSplitPanel(String text) {
		JPanel leftPane = new JPanel();
	
		JScrollPane rightPane = new JScrollPane();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPane,rightPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(200);

		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(180, 50);
		leftPane.setMinimumSize(minimumSize);
		rightPane.setMinimumSize(minimumSize);
		DefaultMutableTreeNode top =
				new DefaultMutableTreeNode("Hosts");
		DefaultMutableTreeNode kernel = null;
		DefaultMutableTreeNode machine = null;

		Vector<MachineModel> vmm = bbChannel.get().getMachineInfos();
		for (MachineModel machineModel : vmm) {
			machine = new DefaultMutableTreeNode(machineModel.getName());
			top.add(machine);
			Vector<KernelModel> kList = machineModel.getKernelList();
			for (KernelModel kernelModel : kList) {
				//original Tutorial
				kernel = new DefaultMutableTreeNode(kernelModel.getName());
				machine.add(kernel);

			}
		}

		//Create a tree that allows one selection at a time.
		JTree tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode
		(TreeSelectionModel.SINGLE_TREE_SELECTION);
		leftPane.add(tree,BorderLayout.LINE_START);

		
		return splitPane;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = BigBrotherFrame.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

}
