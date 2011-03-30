package desktop.example.calculator;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Main panel for Calculator example.
 * 
 * @author Cristiano Gavião
 * 
 */
@SuppressWarnings("serial")
public class CalculatorPanel extends JPanel {

	private Calculator calculator = new Calculator();
	private JTextField textField = new JTextField();
	private GridBagLayout gridBagLayout = new GridBagLayout();

	public CalculatorPanel() {
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		setName("calculator_panel");
		updateCurrentValue();
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setEditable(false);
		textField.setBackground(Color.WHITE);
		textField.setName("result");
		add(textField, 0, 0, 3, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.NORTH, new Insets(5, 5, 5, 5));
		add(createButton('C'), 3, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.NORTH, new Insets(5, 5, 5, 5));

		addButtons(gridBagLayout, new char[][] { { '7', '8', '9', '+' },
				{ '4', '5', '6', '-' }, { '1', '2', '3', '*' },
				{ '0', '.', 0, '/' } });
		add(createButton('='), 2, 5, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.NORTH, new Insets(5, 5, 5, 5));
	}

	public State getCurrentState()
	{
		return getCalculator().getCurrentState();
	}
	
	private void addButtons(GridBagLayout gridBag, char[][] chars) {
		for (int colIndex = 0; colIndex < chars.length; colIndex++) {
			char[] row = chars[colIndex];
			for (int rowIndex = 0; rowIndex < row.length; rowIndex++) {
				if (chars[colIndex][rowIndex] != 0) {
					add(createButton(row[rowIndex]), rowIndex, colIndex + 1, 1,
							1, 1, rowIndex == 3 ? 10 : 1,
							GridBagConstraints.BOTH, GridBagConstraints.CENTER,
							new Insets(5, 5, 5, 5));
				}
			}
		}
	}

	private void add(Component component, int gridx, int gridy, int gridwidth,
			int gridheight, double weightx, double weighty, int fill,
			int anchor, Insets insets) {
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.gridx = gridx;
		constraint.gridy = gridy;
		constraint.gridwidth = gridwidth;
		constraint.gridheight = gridheight;
		constraint.fill = fill;
		constraint.anchor = anchor;
		constraint.weightx = weightx;
		constraint.weighty = weighty;
		if (insets != null) {
			constraint.insets = insets;
		}
		gridBagLayout.setConstraints(component, constraint);
		add(component, constraint);
	}

	private JButton createButton(final char buttonChar) {
		JButton button = new JButton(String.valueOf(buttonChar));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				calculator.enter(buttonChar);
				updateCurrentValue();
			}
		});
		return button;
	}

	private Calculator getCalculator() {
		return calculator;
	}

	private void updateCurrentValue() {
		textField.setText(calculator.getCurrentValue());
	}

	public static void main(String[] args) throws Exception {
		SampleUtils.show(new CalculatorPanel(), "Calculator");
	}
}
