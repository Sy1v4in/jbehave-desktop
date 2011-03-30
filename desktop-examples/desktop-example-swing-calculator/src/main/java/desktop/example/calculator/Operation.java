package desktop.example.calculator;

/**
 * Operation enum.
 * 
 * @author Cristiano Gavião
 * 
 */
public enum Operation {
	sum("+"), subtraction("-"), division("/"), multiplication("*");

	private String symbol;

	private Operation(String symbol) {
		this.setSymbol(symbol);
	}

	private void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
}
