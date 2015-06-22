package de.smba.compression;

import de.smba.compression.frontend.IFrontend;

/**
 * * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 */
public class Product {

	public static void main(String[] args) {
		
		//configuration minimalGUI.config
		//IFrontend frontend = ProductFactory.createFrontend_G();
		
		//configuration minimalConsole.config
		//IFrontend frontend = ProductFactory.createFrontend_C();
		
		//configuration completeGUI.config
		//IFrontend frontend = ProductFactory.createFrontend_G_B_GD_Com();
		
		//configuration completeConsole.config
		//IFrontend frontend = ProductFactory.createFrontend_C_B_CD_Com();
		
		//configuration userVersion.config
		IFrontend frontend = ProductFactory.createFrontend_G_GD_Com();
		frontend.run();
		
	}
}
