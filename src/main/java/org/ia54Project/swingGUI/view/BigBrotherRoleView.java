package org.ia54Project.swingGUI.view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;

import org.ia54Project.dataModel.RoleModel;

public class BigBrotherRoleView extends BigBrotherDetailView{
	private static final long serialVersionUID = -6094518696085598441L;
	private JLabel name;
	private JLabel roleAddress;
	private JLabel groupAdress;
	private JLabel box;
	private JLabel hasMesage;
	private JLabel isReleased;
	private JLabel isSleeping;
	
	private static JLabel  title;
	private static JLabel  name_des;
	private static JLabel  roleAddress_des;
	private static JLabel  groupAdress_des;
	private static JLabel  box_des;
	private static JLabel  hasMesage_des;
	private static JLabel  isReleased_des;
	private static JLabel  isSleeping_des;	
	public BigBrotherRoleView(RoleModel role) {
		title					= new JLabel("Role detail");
		name_des 				= new JLabel("Name :");
		roleAddress_des 		= new JLabel("RoleAdress :");
		groupAdress_des			= new JLabel("GroupAddress :");
		box_des 				= new JLabel("Mailbox :");
		hasMesage_des 			= new JLabel("hasMessage(s) :");
		isReleased_des 			= new JLabel("isReleased :");
		isSleeping_des 			= new JLabel("isSleeping :");
		
		name 					= new JLabel();
		roleAddress			 	= new JLabel();
		groupAdress			 	= new JLabel();
		box 					= new JLabel();
		hasMesage 				= new JLabel();
		isReleased 				= new JLabel();
		isSleeping 				= new JLabel();
		
		this.setLayout(new GridLayout(7,2));
		this.setPreferredSize(new Dimension(250, 400));
		//this.setMinimumSize(new Dimension(300,600));
		this.add(name_des);
		this.add(name);
		this.add(roleAddress_des);
		this.add(roleAddress);
		this.add(groupAdress_des);
		this.add(groupAdress);
		this.add(box_des);
		this.add(box);
		this.add(hasMesage_des);
		this.add(hasMesage);
		this.add(isReleased_des);
		this.add(isReleased);
		this.add(isSleeping_des);
		this.add(isSleeping);
		setModel(role);
		this.setVisible(true);
	}
	
	public void setModel(RoleModel role) {
		name 		.setText(role.getName());
		roleAddress	.setText(role.getRoleAddress().toString());
		groupAdress	.setText(role.getGroupAdress().toString());
		box 		.setText(role.getBox().toString());
		hasMesage 	.setText(role.getHasMesage().toString());
		isReleased 	.setText(role.getIsReleased().toString());
		isSleeping 	.setText(role.getIsSleeping().toString());

	}
	

}
