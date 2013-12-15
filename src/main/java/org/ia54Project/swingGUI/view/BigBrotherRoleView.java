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
	
	private String[] listDataDesc;
	private String[] listDataInfo;
	
	public BigBrotherRoleView(RoleModel role) {
//		title					= new JLabel("Role detail");
//		name_des 				= new JLabel("Name :");
//		roleAddress_des 		= new JLabel("RoleAdress :");
//		groupAdress_des			= new JLabel("GroupAddress :");
//		hasMesage_des 			= new JLabel("hasMessage(s) :");
//		isReleased_des 			= new JLabel("isReleased :");
//		isSleeping_des 			= new JLabel("isSleeping :");
//		
//		name 					= new JLabel();
//		roleAddress			 	= new JLabel();
//		groupAdress			 	= new JLabel();
//		hasMesage 				= new JLabel();
//		isReleased 				= new JLabel();
//		isSleeping 				= new JLabel();
//		
//		this.setLayout(new GridLayout(6,2));
//		this.add(name_des);
//		this.add(name);
//		this.add(roleAddress_des);
//		this.add(roleAddress);
//		this.add(groupAdress_des);
//		this.add(groupAdress);
//		this.add(hasMesage_des);
//		this.add(hasMesage);
//		this.add(isReleased_des);
//		this.add(isReleased);
//		this.add(isSleeping_des);
//		this.add(isSleeping);
		listDataDesc = new String[6];
		//listDataDesc[0]="Role detail";
		listDataDesc[0]="Name :";
		listDataDesc[1]="RoleAdress :";
		listDataDesc[2]="GroupAddress :";
		listDataDesc[3]="hasMessage(s) :";
		listDataDesc[4]="isReleased :";
		listDataDesc[5]="isSleeping :";
		
		listDataInfo = new String[6];
		listDataInfo[0]="";
		listDataInfo[1]="";
		listDataInfo[2]="";
		listDataInfo[3]="";
		listDataInfo[4]="";
		listDataInfo[5]="";

		
		
		desc.setListData(listDataDesc);
		info.setListData(listDataInfo);
		setModel(role);
		this.setVisible(true);
	}
	
	public void setModel(RoleModel role) {
//		name 		.setText(role.toString()					);
//		roleAddress	.setText(role.getRoleAddress().toString()	);
//		groupAdress	.setText(role.getGroupAdress().toString()	);
//		hasMesage 	.setText(role.getHasMesage().toString()		);
//		isReleased 	.setText(role.getIsReleased().toString()	);
//		isSleeping 	.setText(role.getIsSleeping().toString()	);
		
		listDataInfo[0]=role.toString()					;
		listDataInfo[1]=role.getRoleAddress().toString()	;
		listDataInfo[2]=role.getGroupAdress().toString()	;
		listDataInfo[3]=role.getHasMesage().toString()		;
		listDataInfo[4]=role.getIsReleased().toString()	;
		listDataInfo[5]=role.getIsSleeping().toString()	;
		info.setListData(listDataInfo);
	}
	

}
