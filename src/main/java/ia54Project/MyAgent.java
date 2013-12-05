package ia54Project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.janusproject.kernel.address.Address;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.channels.Channel;
import org.janusproject.kernel.channels.ChannelInteractable;

public class MyAgent extends Agent implements ChannelInteractable{
	 
	/** First attribute in the state of the agent.
	 */
	private int firstAttribute = 1;
	 
	/** Second attribute in the state of the agent.
	 */
	private final Collection<Object> secondAttribute = new ArrayList<Object>();
	 
	 
	/** This inner class is the implementation of the channel
	 *  for this agent implementation.
	 */
	private class StateChannelImplementation implements AgentStateChannel {
	 
		public int getFirstAttribute() {
			return MyAgent.this.firstAttribute;
		}
		
		public Collection<Object> getSecondAttribute() {
      
			// You must reply an object that is read-only to
			// ensure that the agent's attribute cannot be changed
			// from the outside of the agent.
			return Collections.unmodifiableCollection(MyAgent.this.secondAttribute);
		}
		
		public Address getChannelOwner() {
			
			return null;
		}
	}


	public Set<? extends Class<? extends Channel>> getSupportedChannels() {
		
		return Collections.singleton(AgentStateChannel.class);
	}

	public <C extends Channel> C getChannel(Class<C> channelClass, Object... params) {
		// Check if the given channel type is supported by the agent.
		
	    if (AgentStateChannel.class.isAssignableFrom(channelClass)) {
	 
	      // Create the instance of the channel.
	      AgentStateChannel channelInstance = new StateChannelImplementation();
	 
	      // Reply the channel instance.
	      return channelClass.cast(channelInstance);
	 
	    }
	 
	    // The given channel type is not supported
	    throw new IllegalArgumentException("channelClass");
	}
}
