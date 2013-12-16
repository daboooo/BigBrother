package org.ia54Project.swingGUI.old;
//package org.ia54Project.swingGUI.view;
//
//import java.awt.Dimension;
//import java.awt.GridLayout;
//import java.util.Vector;
//
//import javax.swing.JLabel;
//import javax.swing.JList;
//
//import org.ia54Project.dataModel.AgentModel;
//
//public class BigBrotherAgentView_OLD extends BigBrotherDetailView {//implements BigBrotherListener{
//	private static final long serialVersionUID = 7202078840060491298L;
//	private JLabel title;
//	
//	private JLabel lname;
//	private JLabel address;
//	private JLabel creationDate;
//	private JLabel creatorAddress;
//	private JLabel heaveyOrLight;
//	private JLabel aliveStatus;
//	private JLabel compoundStatus;
//	private JLabel canRecruit;
//	private JLabel isSleeping;
//	private JLabel canCommitSuicide;
//	private JList  listOfRole;
//	
//	private static JLabel name_def;
//	private static JLabel address_def;
//	private static JLabel creationDate_def;
//	private static JLabel creatorAddress_def;
//	private static JLabel heaveyOrLight_def;
//	private static JLabel aliveStatus_def;
//	private static JLabel compoundStatus_def;
//	private static JLabel canRecruit_def;
//	private static JLabel isSleeping_def;
//	private static JLabel canCommitSuicide_def;
//	private static JLabel listOfRole_def;
//	private String[] listDataDesc;
//	private String[] listDataInfo;
//	
//	public BigBrotherAgentView_OLD(AgentModel agent) {
//		super();
//		
////		name_def 				= new JLabel("Name :");
////		address_def 			= new JLabel("Address :");
////		creationDate_def		= new JLabel("Creation date :");
////		heaveyOrLight_def 		= new JLabel("Execution ressource type :");
////		aliveStatus_def 		= new JLabel("isAlive :");
////		compoundStatus_def 		= new JLabel("isCompound : ");
////		canRecruit_def 			= new JLabel("recruitement capacity :");
////		isSleeping_def 			= new JLabel("isSleeping :");
////		canCommitSuicide_def 	= new JLabel ("can commit suicide :");
////		listOfRole_def 			= new JLabel ("Role list :");
////		
////		lname 				= new JLabel();
////		address 			= new JLabel();
////		creationDate		= new JLabel();
////		heaveyOrLight 		= new JLabel();
////		aliveStatus 		= new JLabel();
////		compoundStatus		= new JLabel();
////		canRecruit 			= new JLabel();
////		isSleeping			= new JLabel();
////		canCommitSuicide 	= new JLabel ();
////		listOfRole 			= new JList();	
//		//this.setLayout(new GridLayout(10,2));
////		this.add(name_def);
////		this.add(lname);
////		this.add(address_def);
////		this.add(address);
////		this.add(creationDate_def);
////		this.add(creationDate);
////		this.add(heaveyOrLight_def);
////		this.add(heaveyOrLight);
////		this.add(aliveStatus_def);
////		this.add(aliveStatus);
////		this.add(compoundStatus_def);
////		this.add(compoundStatus);
////		this.add(canRecruit_def);
////		this.add(canRecruit);
////		this.add(isSleeping_def);
////		this.add(isSleeping);
////		this.add(canCommitSuicide_def);
////		this.add(canCommitSuicide);
////		this.add(listOfRole_def);
////		this.add(listOfRole);
//		listDataDesc = new String[10];
//		listDataDesc[0] = "Name :";
//		listDataDesc[1] = "Address :";
//		listDataDesc[2] = "Creation date :";
//		listDataDesc[3] = "Execution ressource type :";
//		listDataDesc[4] = "isAlive :";
//		listDataDesc[5] = "isCompound : ";
//		listDataDesc[6] = "recruitement capacity :";
//		listDataDesc[7] = "isSleeping :";
//		listDataDesc[8] = "can commit suicide :";
//		listDataDesc[9] = "Role list :";
//
//		desc.setListData(listDataDesc);
//
//		listDataInfo = new String[10];
//		listDataInfo[0] = "";
//		listDataInfo[1] = "";
//		listDataInfo[2] = "";
//		listDataInfo[3] = "";
//		listDataInfo[4] = "";
//		listDataInfo[5] = "";
//		listDataInfo[6] = "";
//		listDataInfo[7] = "";
//		listDataInfo[8] = "";
//		listDataInfo[9] = "";
//		info.setListData(listDataInfo);
//		
//		setModel(agent);
//		this.setVisible(true);		
//	}
//	
//	public void setModel(AgentModel agent) {
//		Float date = agent.getCreationDate();
////		getLName()				.setText	(agent.toString()							);		
////		address					.setText	(agent.getAddress().toString()				);
////		creationDate			.setText	(date.toString()							);
////		heaveyOrLight			.setText	(agent.getIsHeavyAgent().toString()			);
////		aliveStatus				.setText	(agent.getIsAlive().toString()				);
////		compoundStatus			.setText	(agent.getIsCompound().toString()			);
////		canRecruit				.setText	(agent.getIsRecruitementAllowed().toString());
////		isSleeping				.setText	(agent.getIsSleeping().toString()			);
////		canCommitSuicide		.setText	(agent.getCanCommitSuicide().toString()		);
////		listOfRole				.setListData(agent.getListOfRole()						);
//		
//		listDataInfo[0] = agent.toString()							  ;
//		listDataInfo[1] = agent.getAddress().toString()				  ;
//		listDataInfo[2] = date.toString()							  ;
//		listDataInfo[3] = agent.getIsHeavyAgent().toString()		  ;
//		listDataInfo[4] = agent.getIsAlive().toString()				  ;
//		listDataInfo[5] = agent.getIsCompound().toString()			  ;
//		listDataInfo[6] = agent.getIsRecruitementAllowed().toString() ;
//		listDataInfo[7] = agent.getIsSleeping().toString()			  ;
//		listDataInfo[8] = agent.getCanCommitSuicide().toString()	  ;
//		listDataInfo[9] = buildRoleString(agent.getListOfRole());
//		info.setListData(listDataInfo);
//		
//	}
//
//	public String buildRoleString(Vector<String> roles) {
//		StringBuilder strBld = new StringBuilder("");
//		if(roles != null ) {
//			for (String string : roles) {
//				strBld.append(string);
//				strBld.append("_-_");
//				strBld.append("\n");
//			}
//		}
//		return strBld.toString();
//	}
//	
//	public JLabel getName_def() {
//		return name_def;
//	}
//
//	public void setName_def(JLabel name_def) {
//		this.name_def = name_def;
//	}
//
//	public JLabel getAddress_def() {
//		return address_def;
//	}
//
//	public void setAddress_def(JLabel address_def) {
//		this.address_def = address_def;
//	}
//
//	public JLabel getAddress() {
//		return address;
//	}
//
//	public void setAddress(JLabel address) {
//		this.address = address;
//	}
//
//	public JLabel getCreationDate_def() {
//		return creationDate_def;
//	}
//
//	public void setCreationDate_def(JLabel creationDate_def) {
//		this.creationDate_def = creationDate_def;
//	}
//
//	public JLabel getCreationDate() {
//		return creationDate;
//	}
//
//	public void setCreationDate(JLabel creationDate) {
//		this.creationDate = creationDate;
//	}
//
//	public JLabel getLName() {
//		return lname;
//	}
//	
//	public void setName(JLabel name_) {
//		this.lname = name_;
//	}
//
//}
