package main;

import facebook_Access.facebook;

public class Main {

	public static void main(String[] args) {
		
		try {
			facebook facebook = new facebook();
			
			facebook.getFacebookInstance();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			facebook.facebook.close();
			facebook.facebook.quit();
		}

	}

}
