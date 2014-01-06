package org.ia54Project.dataModel;

import java.util.Collection;
import java.util.Vector;

import org.janusproject.kernel.address.AgentAddress;

/**
 * The order class defines a way to give order to an executant
 * OrderType contains what type of action to execute on a collection of agents
 * @author Arnaud Roblin, Julien Benichou
 *
 */
public class Order {
	private Collection<AgentAddress> agents; // we will execute the action on those agents
	private OrderType action;
	


	public Order(AgentAddress agent, OrderType action) {
	    agents = new Vector<AgentAddress>();
	    agents.add(agent);
		setAction(action);
	}
	
	public Order(Collection<AgentAddress> agents, OrderType action) {
		setAgents(agents);
		setAction(action);
	}
	
	public Collection<AgentAddress> getAgents() {
		return agents;
	}
	
	
	public void setAgents(Collection<AgentAddress> agents) {
		this.agents = agents;
	}
	
	
	public OrderType getAction() {
		return action;
	}
	
	
	public void setAction(OrderType action) {
		this.action = action;
	}
	
}