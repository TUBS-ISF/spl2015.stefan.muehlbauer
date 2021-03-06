import de.smba.compression.frontend.GUI;
import de.smba.compression.frontend.Frontend;

//tested 13/07/2015

public aspect GUIFrontend {
	
	/**
	 * If the main method is executed, we infer the execution of the Console main() method.
	 */
	pointcut mainExecution(): 
		execution(void Frontend.main(String[]));
	
	void around(): mainExecution() {
		GUI.main(new String[0]);
		proceed();
	}
}
