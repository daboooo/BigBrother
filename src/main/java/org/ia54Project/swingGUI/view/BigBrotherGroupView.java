package org.ia54Project.swingGUI.view;

import java.awt.GridLayout;

import javax.swing.JLabel;

public class BigBrotherGroupView extends BigBrotherDetailView{
	private static final long serialVersionUID = -396700965229827736L;

	// TODO
	private final static JLabel name_desc = new JLabel("Instance of organization :");
	private final static JLabel address_desc = new JLabel("Group Address: ");
	
	private JLabel name;
	private JLabel address;
	
	public BigBrotherGroupView(GroupModel group) {
		name = new JLabel();
		address = new JLabel();
		
		setLayout(new GridLayout(1,1));
		add(name_desc);
		add(name);
		add(address_desc);
		add(address);
		
		setModel(group);
		setVisible(true);
	}
	
	public void setModel(GroupModel group) {
		name.setText(group.getName());
		address.setText(group.getGroupAddress().toString());
		
	}
}
