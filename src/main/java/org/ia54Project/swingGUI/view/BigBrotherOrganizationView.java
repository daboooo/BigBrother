package org.ia54Project.swingGUI.view;

import java.awt.GridLayout;

import javax.swing.JLabel;

import org.ia54Project.dataModel.OrganizationModel;

public class BigBrotherOrganizationView extends BigBrotherDetailView {
	private static final long serialVersionUID = -3479003022298012506L;

	private final static JLabel name_desc = new JLabel("Instance of organization :");
	private JLabel name;
	
	public BigBrotherOrganizationView(OrganizationModel org) {
		name = new JLabel();
		
		setLayout(new GridLayout(1,1));
		add(name_desc);
		add(name);
		
		setModel(org);
		setVisible(true);
	}
	
	public void setModel(OrganizationModel org) {
		name.setText(org.getName());
	}
}
