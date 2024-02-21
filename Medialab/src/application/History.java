package application;

public class History {
	
	public RotatingQueue<HistoryItem> history;
	
	public History() {
		this.history = new RotatingQueue<>(5);
	}
	
	public void add(String word, int tries, String winner) {
		HistoryItem h = new HistoryItem(word, tries, winner);
		this.history.insertElement(h);
	}
	
	public String getString() {
		String ret = new String();
		for (int i=0; i<this.history.mostRecentItem+1; i++) {
			System.out.println("debug");
			ret += this.history.getElement(i).toString();
			ret += "\n";
		}
		return ret;
	}
	
	private class HistoryItem {
		private String word;
		private int tries;
		private String winner;
		
		HistoryItem(String word, int tries, String winner) {
			this.word = word;
			this.tries = tries;
			this.winner = winner;
		}
		public String toString() {
			String ret = new String();
			ret = "Word: "+this.word+ "   Tries: " + Integer.toString(this.tries) + "   Winner: " + winner + "\n";
			return ret;
		}
	}
}
