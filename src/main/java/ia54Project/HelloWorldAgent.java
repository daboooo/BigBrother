package ia54Project;

import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.status.Status;

public class HelloWorldAgent extends Agent{
	public Status live() {
	    print("hello world!\n");
	    return null;
	  }
}
