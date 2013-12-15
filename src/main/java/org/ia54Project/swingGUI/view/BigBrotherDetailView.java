package org.ia54Project.swingGUI.view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JViewport;

public class BigBrotherDetailView extends JPanel {
	private static final long serialVersionUID = 6571315940317225398L;
	private static final Dimension stdDim = new Dimension(1300,700);
	private LayoutManager layout;
	protected JList desc;
	protected JList info;
	
	public BigBrotherDetailView() {
		this.setPreferredSize(stdDim);
		layout = new BoxLayout(this,BoxLayout.LINE_AXIS);
		this.setLayout(layout);
		this.add(desc = new JList());
		this.add(info = new JList());

	}
}
