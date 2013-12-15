package org.ia54Project.swingGUI.view;

import java.awt.GridLayout;

import javax.swing.JLabel;

import org.ia54Project.dataModel.OrganizationModel;

public class BigBrotherOrganizationView extends BigBrotherDetailView {
	private static final long serialVersionUID = -3479003022298012506L;

	private final static JLabel name_desc = new JLabel("Class name :");
	private final static JLabel count_desc = new JLabel("Number of instance(s) :");
	private JLabel name;
	private JLabel count;
	
	private final String[] listDataDesc;
	private String[] listDataInfo;
	
	public BigBrotherOrganizationView(OrganizationModel org) {
//		name = new JLabel();
//		count = new JLabel();
//		
//		setLayout(new GridLayout(2,2));
//		add(name_desc);
//		add(name);
//		add(count_desc);
//		add(count);
		
		listDataDesc = new String[2];
		listDataDesc[0] = "Host name :";
		listDataDesc[1] = "Ip :";
		
		listDataInfo = new String[2];
		listDataInfo[0] = "";
		listDataInfo[1] = "";
		
		desc.setListData(listDataDesc);
		info.setListData(listDataInfo);
		setModel(org);
		setVisible(true);
	}
	
	public void setModel(OrganizationModel org) {
//		name.setText(org.toString());
//		count.setText(org.getNbInstance().toString());
		listDataInfo = new String[2];
		listDataInfo[0] = org.toString();
		listDataInfo[1] = org.getNbInstance().toString();
		
		info.setListData(listDataInfo);
	}
}
