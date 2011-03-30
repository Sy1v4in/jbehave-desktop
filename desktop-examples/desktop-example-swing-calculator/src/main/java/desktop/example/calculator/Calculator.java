package desktop.example.calculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Calculator {

	private State currentState;
	private Processor processor;
	private StringBuffer firstOperand = new StringBuffer();
	private Operator operator;
	private StringBuffer secondOperand = new StringBuffer();
	public static final DecimalFormat FORMAT = new DecimalFormat("0.########");
	private static final int MAX_CHARATER_COUNT = 10;
	private NumberFormat numberFormat;

	public Calculator() {
		processor = new EnteringFirstOperandState('0');
		numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
		currentState = State.active;
	}

	public void enter(char c) {
		switch (c) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			processor.processDigit(c);
			break;
		case '.':
			processor.processDot();
			break;
		case '+':
		case '-':
		case '/':
		case '*':
			processor.processOperator(c);
			break;
		case '=':
			processor.processEquals();
			break;
		case 'C':
			processor = new EnteringFirstOperandState('0');
			currentState = State.active;
			break;
		default:
			throw new IllegalArgumentException("Character '" + c
					+ "' is not supported");
		}
	}

	public String getCurrentValue() {
		return processor.getCurrentValue();
	}

	private interface Operator {
		public BigDecimal apply(BigDecimal a, BigDecimal b);
	}

	private interface Processor {
		void processDigit(char c);

		void processOperator(char c);

		void processEquals();

		String getCurrentValue();

		void processDot();
	}

	public abstract class AbstractOperandProcessor implements Processor {
		private StringBuffer buffer;

		protected AbstractOperandProcessor(StringBuffer buffer, char c) {
			this.buffer = buffer;
			buffer.setLength(0);
			buffer.append(c);
		}

		public final void processDigit(char c) {
			if (buffer.toString().length() == MAX_CHARATER_COUNT) {
				return;
			}
			if (buffer.toString().equals("0")) {
				buffer.setCharAt(0, c);
			} else {
				buffer.append(c);
			}
		}

		public final String getCurrentValue() {
			return buffer.toString();
		}

		public void processDot() {
			if (buffer.toString().length() == MAX_CHARATER_COUNT) {
				return;
			}
			if (buffer.toString().indexOf(".") == -1) {
				buffer.append(".");
			}
		}
	}

	public class EnteringFirstOperandState extends AbstractOperandProcessor {
		public EnteringFirstOperandState(char c) {
			super(firstOperand, c);
			if (c != 0)
				currentState = State.processing;

		}

		public void processOperator(char c) {
			processor = new EnteringOperatorState(c);
		}

		public void processEquals() {
		}
	}

	public class EnteringOperatorState implements Processor {
		public EnteringOperatorState(char c) {
			processOperator(c);
		}

		public void processDigit(char c) {
			processor = new EnteringSecondOperandState(c);
		}

		public String getCurrentValue() {
			return firstOperand.toString();
		}

		public void processDot() {
			processor = new EnteringSecondOperandState('0');
			processor.processDot();
		}

		public void processOperator(char c) {
			switch (c) {
			case '+':
				operator = OPERATOR_PLUS;
				break;
			case '-':
				operator = OPERATOR_MINUS;
				break;
			case '/':
				operator = OPERATOR_DIVIDE;
				break;
			case '*':
				operator = OPERATOR_TIMES;
				break;
			default:
				throw new RuntimeException("unexpected operator");
			}
		}

		public void processEquals() {
			secondOperand.setLength(0);
			secondOperand.append(firstOperand);
			processor = new ShowingResultState();
		}
	}

	public class EnteringSecondOperandState extends AbstractOperandProcessor {
		public EnteringSecondOperandState(char c) {
			super(secondOperand, c);
		}

		public void processOperator(char c) {
			applyOperator();
			processor = new EnteringOperatorState(c);
		}

		public void processEquals() {
			processor = new ShowingResultState();
		}
	}

	public class ShowingResultState implements Processor {

		public ShowingResultState() {
			applyOperator();
		}

		public void processDigit(char c) {
			firstOperand.setLength(0);
			secondOperand.setLength(0);
			firstOperand.append('0');
			processor = new EnteringFirstOperandState(c);
		}

		public void processOperator(char c) {
			processor = new EnteringOperatorState(c);
		}

		public void processEquals() {
			applyOperator();
		}

		public String getCurrentValue() {
			return firstOperand.toString();
		}

		public void processDot() {
			processDigit('0');
			processor.processDot();
		}
	}

	private void applyOperator() {
		if (operator == null) {
			throw new RuntimeException("No operator defined");
		}
		BigDecimal firstValue = new BigDecimal(firstOperand.toString());
		BigDecimal secondValue = new BigDecimal(secondOperand.toString());
		try {
			BigDecimal result = operator.apply(firstValue, secondValue);
			firstOperand = new StringBuffer(numberFormat.format(result
					.doubleValue()));
		} catch (Exception e) {
			firstOperand = new StringBuffer(e.getMessage());
		}
	}

	public State getCurrentState() {
		return currentState;
	}

	private final Operator OPERATOR_PLUS = new Operator() {
		public BigDecimal apply(BigDecimal a, BigDecimal b) {
			return a.add(b);
		}
	};
	private final Operator OPERATOR_MINUS = new Operator() {
		public BigDecimal apply(BigDecimal a, BigDecimal b) {
			return a.subtract(b);
		}
	};
	private final Operator OPERATOR_DIVIDE = new Operator() {
		public BigDecimal apply(BigDecimal a, BigDecimal b) {
			if (b.intValue() == 0) {
				throw new ArithmeticException("Division by zero");
			}
			return a.divide(b, 5, BigDecimal.ROUND_HALF_DOWN);
		}
	};
	private final Operator OPERATOR_TIMES = new Operator() {
		public BigDecimal apply(BigDecimal a, BigDecimal b) {
			return a.multiply(b);
		}
	};
}
