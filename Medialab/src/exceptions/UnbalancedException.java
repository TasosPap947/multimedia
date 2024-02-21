package exceptions;

import resources.AlertBox;

public class UnbalancedException extends Exception {
	
	public UnbalancedException() {
		AlertBox.display("Error", "Could not add Dictionary.\n Long and short words are unbalanced. Please try again");
	}
}
