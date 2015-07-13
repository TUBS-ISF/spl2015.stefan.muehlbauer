import de.smba.compression.frontend.Frontend;
import de.smba.compression.frontend.Console;

//tested 13/07/2015

public aspect ConsoleFrontend {
	
	/**
	 * If the main method is executed, we infer the execution of the Console main() method.
	 */
	pointcut mainExecution(): 
		execution(void Frontend.main(String[]));
	
	void around(): mainExecution() {
		Console.main(new String[0]);
		proceed();
	}
}
