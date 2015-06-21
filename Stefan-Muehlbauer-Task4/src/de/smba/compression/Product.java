package de.smba.compression;

import de.smba.compression.frontend.IFrontend;

/**
 * * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 */
public class Product {

	public static void main(String[] args) {

		IFrontend frontend = ProductFactory.createFrontend_G_B_GD_Com();
		
		frontend.run();
		
	}
}
