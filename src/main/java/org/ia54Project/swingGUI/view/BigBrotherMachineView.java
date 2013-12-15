package org.ia54Project.swingGUI.view;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import org.ia54Project.dataModel.MachineModel;

public class BigBrotherMachineView extends BigBrotherDetailView {
	private static final long serialVersionUID = 5689658876841440199L;
	
	private static JLabel name_desc;
	private static JLabel ip_desc;
	private JLabel name;
	private JLabel ip;
	
	private final String[] listDataDesc;
	private String[] listDataInfo;
	public BigBrotherMachineView(MachineModel machine) {
//		name_desc 	= new JLabel("Host name: ");
//		ip_desc 	= new JLabel("Ip :");
//		name 		= new JLabel();
//		ip 			= new JLabel();
//		this.setLayout(new GridLayout(2, 2));
//		this.add(name_desc);
//		this.add(name);
//		this.add(ip_desc);
//		this.add(ip);
		
		listDataDesc = new String[2];
		listDataDesc[0] = "Host name :";
		listDataDesc[1] = "Ip :";
		
		listDataInfo = new String[2];
		listDataInfo[0] = "";
		listDataInfo[1] = "";
		
		desc.setListData(listDataDesc);
		info.setListData(listDataInfo);
		this.setModel(machine);
		this.setVisible(true);
	}
	
	public void setModel(MachineModel machine) {
//		ip.setText(machine.getIp());
//		name.setText(machine.getName());
		listDataInfo[0] = machine.getName();
		listDataInfo[1] = machine.getIp();

		info.setListData(listDataInfo);
	}
	
}
