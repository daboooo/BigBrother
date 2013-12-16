package org.ia54Project.swingGUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.lang.ref.WeakReference;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.arakhne.afc.vmutil.locale.Locale;
import org.ia54Project.BigBrotherUtil;
import org.ia54Project.agent.BigBrotherChannel;
import org.ia54Project.swingGUI.view.BigBrotherStandardView;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.crio.core.GroupAddress;

public class BigBrotherFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = -1141199078767515860L;
	private final WeakReference<BigBrotherChannel> bbChannel;
	private JTabbedPane tabPane;
	private BigBrotherStandardView stdView;
	private JComponent orgView;
	
	
	public BigBrotherFrame(AgentAddress guiManagerPlayer,GroupAddress orgAddr) {
		this(BigBrotherUtil.getChannelForGUI(guiManagerPlayer, orgAddr));
	}

	public BigBrotherFrame(BigBrotherChannel bbChannelArg) {
		assert(bbChannelArg!=null);
		this.bbChannel = new WeakReference<BigBrotherChannel>(bbChannelArg);
		setTitle("BigBrother Monitor UI"); //$NON-NLS-1$
		setPreferredSize(new Dimension(1300, 600));		
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent winEvt) {
				quit();
			}
		});
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu bigBrotherMenu = new JMenu(Locale.getString(BigBrotherFrame.class, "Menu")); //$NON-NLS-1$
		menuBar.add(bigBrotherMenu);
		
		JMenuItem item;
		
		item = new JMenuItem(Locale.getString(BigBrotherFrame.class, "Update view")); //$NON-NLS-1$
		item.setActionCommand("UPDATE_VIEW");
		item.addActionListener(this);
		bigBrotherMenu.add(item);
		
		item = new JMenuItem(Locale.getString(BigBrotherFrame.class, "Suspend/Resume")); //$NON-NLS-1$
		item.setActionCommand("PAUSE");
		item.addActionListener(this);
		bigBrotherMenu.add(item);

		item = new JMenuItem(Locale.getString(BigBrotherFrame.class, "Quit")); //$NON-NLS-1$
		item.setActionCommand("QUIT");
		item.addActionListener(this);
		bigBrotherMenu.add(item);

		// TAB
		tabPane = new JTabbedPane(JTabbedPane.TOP);
		stdView = new BigBrotherStandardView(bbChannel.get());
		tabPane.addTab("Standard View", stdView);
		tabPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		orgView = new JPanel();
		tabPane.addTab("Organizational View", orgView);
		tabPane.setMnemonicAt(1, KeyEvent.VK_2);
		getContentPane().add(tabPane);
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
			stdView.rebuildTree();
		} else if (cmd.equals("QUIT")) {
			quit();
		} else if(cmd.equals("PAUSE")) {
			((BigBrotherChannel) bbChannel.get()).switchPause();
		}
	}

	public WeakReference<BigBrotherChannel> getBbChannel() {
		return bbChannel;
	}

	// GUI 

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
