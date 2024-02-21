package exceptions;

import resources.AlertBox;

public class UndersizeException extends Exception {
	
	public UndersizeException() {
		AlertBox.display("Error", "Could not add Dictionary.\n Not enough words. Please try again");
	}
}