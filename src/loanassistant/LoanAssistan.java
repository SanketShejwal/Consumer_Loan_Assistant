package loanassistant;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;


public class LoanAssistan extends JFrame {
		
	double balance, interest, payment;
	int months;
	double monthlyInterest, multiplier;
	double loanBalance, finalPayment;
	boolean computePayment;

	private JPanel contentPane;
	private JTextField balanceTextField;
	private JTextField interestTextField;
	private JTextField monthsTextField;
	private JTextField paymentTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	//	new LoanAssistan().show();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanAssistan frame = new LoanAssistan();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public LoanAssistan() {
		setTitle("Loan Assistant");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 661, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel balanceLabel = new JLabel("Loan Balance:");
		balanceLabel.setBounds(10, 11, 86, 14);
		contentPane.add(balanceLabel);
		
		JLabel interestLabel = new JLabel("Interest Rate:");
		interestLabel.setBounds(10, 36, 104, 14);
		contentPane.add(interestLabel);
		
		JLabel monthsLabel = new JLabel("Number of Payment:");
		monthsLabel.setBounds(10, 61, 115, 14);
		contentPane.add(monthsLabel);
		
		JLabel paymentLabel = new JLabel("Monthly Payment:");
		paymentLabel.setBounds(10, 86, 104, 14);
		contentPane.add(paymentLabel);
		
		balanceTextField = new JTextField();
		balanceTextField.setBounds(144, 8, 86, 20);
		contentPane.add(balanceTextField);
		balanceTextField.setColumns(10);
		
		interestTextField = new JTextField();
		interestTextField.setColumns(10);
		interestTextField.setBounds(144, 33, 86, 20);
		contentPane.add(interestTextField);
		
		monthsTextField = new JTextField();
		monthsTextField.setColumns(10);
		monthsTextField.setBounds(144, 58, 86, 20);
		contentPane.add(monthsTextField);
		
		paymentTextField = new JTextField();
		paymentTextField.setColumns(10);
		paymentTextField.setBounds(144, 83, 86, 20);
		contentPane.add(paymentTextField);
		
		JTextArea analysisTextArea = new JTextArea();
		analysisTextArea.setLineWrap(true);
		analysisTextArea.setBounds(309, 36, 317, 212);
		contentPane.add(analysisTextArea);
		
		JButton newLoanButton = new JButton("New Loan Analysis");
		newLoanButton.setBounds(49, 173, 171, 23);
		contentPane.add(newLoanButton);
		
		JButton ComputeButton = new JButton("Compute Monthly Payment");
		ComputeButton.setBounds(30, 139, 210, 23);
		contentPane.add(ComputeButton);
		
				
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(340, 266, 89, 23);
		contentPane.add(exitButton);
		
		JLabel analysisLabel = new JLabel("Loan Analysis");
		analysisLabel.setBounds(309, 11, 80, 14);
		contentPane.add(analysisLabel);
		
		JButton monthsButton = new JButton("X");
		monthsButton.setBounds(242, 57, 57, 23);
		contentPane.add(monthsButton);
		
		JButton paymentButton = new JButton("X");
		paymentButton.setBounds(242, 82, 57, 23);
		contentPane.add(paymentButton);
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		ComputeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				if (validateDecimalNumber(balanceTextField)){
					balance = Double.valueOf(balanceTextField.getText()).doubleValue();
				}else{
					JOptionPane.showConfirmDialog(null, "Invalid or empty Loan Balance entry.\nPlease correct.",
							"Balance Input Error", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				
				if (validateDecimalNumber(interestTextField)){
					interest = Double.valueOf(interestTextField.getText()).doubleValue();
				}else{
					JOptionPane.showConfirmDialog(null, "Invalid or empty Interest Rate entry.\nPlease correct.", 
							"Interest Input Error", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				
				monthlyInterest = interest / 1200;
				
				
				if (computePayment){
					// Compute loan payment
					if (validateDecimalNumber(monthsTextField)){
						months = Integer.valueOf(monthsTextField.getText()).intValue();
					}else{
						JOptionPane.showConfirmDialog(null, "Invalid or empty Number of Payments entry.\nPlease correct.", "Number of Payments Input Error",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					if (interest == 0){
						payment = balance / months;
					}else{
						multiplier = Math.pow(1 + monthlyInterest, months);
						payment = balance * monthlyInterest * multiplier / (multiplier - 1);
					}
				   paymentTextField.setText(new DecimalFormat("0.00").format(payment));
				}else{
					// Compute number of payments
					if (validateDecimalNumber(paymentTextField)){
						payment = Double.valueOf(paymentTextField.getText()).doubleValue();
						if (payment <= (balance * monthlyInterest + 1.0)){
							if (JOptionPane.showConfirmDialog(null, "Minimum payment must be $" +
							new DecimalFormat("0.00").format((int)(balance * monthlyInterest + 1.0)) + 
							"\n" + "Do you want to use the minimum payment?", "Input Error", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							paymentTextField.setText(new DecimalFormat("0.00").format((int)(balance *monthlyInterest + 1.0)));
							payment = Double.valueOf(paymentTextField.getText()).doubleValue();
						}else{
							paymentTextField.requestFocus();
							return;
						}
					}
				}
				else{
				JOptionPane.showConfirmDialog(null, "Invalid or empty Monthly Payment entry.\nPlease correct.", "Payment Input Error", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
				return;
				}
					
					
				if (interest == 0){
				months = (int)(balance / payment);
				}else{
				months = (int)((Math.log(payment) - Math.log(payment - balance * monthlyInterest)) / Math.log(1 + monthlyInterest));
				}
				monthsTextField.setText(String.valueOf(months));
				}
				
								
				// reset payment prior to analysis to fix at two decimals
				payment = Double.valueOf(paymentTextField.getText()).doubleValue();
				
				
				// show analysis (Text Area 1)
				analysisTextArea.setText(" Loan Balance:\t\t $" + new DecimalFormat("0.00").format(balance));
				analysisTextArea.append("\n" + " Interest Rate:\t\t " + new DecimalFormat("0.00").format(interest) + "%");
				
				// process all but last payment
				loanBalance = balance;
				
				for (int paymentNumber = 1; paymentNumber <= months - 1; paymentNumber++)
				{
				loanBalance += loanBalance * monthlyInterest - payment;
				}
				
				// find final payment
				finalPayment = loanBalance;
				
				if (finalPayment > payment)
				{
				// apply one more payment
				loanBalance += loanBalance * monthlyInterest - payment;
				finalPayment = loanBalance;
				months++;
				monthsTextField.setText(String.valueOf(months));
				}
				
				// show analysis (Text Area 2)
				analysisTextArea.append("\n\n" + String.valueOf(months - 1) + "  Payments of\t $" + new DecimalFormat("0.00").format(payment));
				analysisTextArea.append("\n" + " Final Payment of:\t $" + new DecimalFormat("0.00").format(finalPayment));
				analysisTextArea.append("\n" + " Total Payments:\t $" + new DecimalFormat("0.00").format((months - 1) * payment + finalPayment));
				analysisTextArea.append("\n" + " Interest Paid\t\t $" + new DecimalFormat("0.00").format((months - 1) * payment + finalPayment - balance));
				ComputeButton.setEnabled(false);
				newLoanButton.setEnabled(true);
				newLoanButton.requestFocus();
				}});
				
		
		newLoanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (computePayment)
				{
				paymentTextField.setText("");
				}
				else
				{
				monthsTextField.setText("");
				}
				analysisTextArea.setText("");
				ComputeButton.setEnabled(true);
				newLoanButton.setEnabled(false);
				balanceTextField.requestFocus();
			}
		});
				
		monthsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				computePayment = false;
				paymentButton.setVisible(true);
				monthsButton.setVisible(false);
				monthsTextField.setText("");
				monthsTextField.setEditable(false);
				monthsTextField.setBackground(Color.YELLOW);
				monthsTextField.setFocusable(false);
				paymentTextField.setEditable(true);
				paymentTextField.setBackground(Color.WHITE);
				paymentTextField.setFocusable(true);
			    
				ComputeButton.setText("Compute Number of Payments");
				balanceTextField.requestFocus();
			}
		});
		
		paymentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// will compute payment
				computePayment = true;
				paymentButton.setVisible(false);
				monthsButton.setVisible(true);
				monthsTextField.setEditable(true);
				monthsTextField.setBackground(Color.WHITE);
				monthsTextField.setFocusable(true);
				paymentTextField.setText("");
				paymentTextField.setEditable(false);
				paymentTextField.setBackground(Color.YELLOW);
				paymentTextField.setFocusable(false);
				ComputeButton.setText("Compute Monthly Payment");
				balanceTextField.requestFocus();
			}
		});
	}

	
private boolean validateDecimalNumber(JTextField tf){
	// checks to see if text field contains
	// valid decimal number with only digits and a single decimal point
	String s = tf.getText().trim();
	boolean hasDecimal = false;
	boolean valid = true;
	if (s.length() == 0){
		valid = false;
	}
	else{
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if (c >= '0' && c <= '9'){
				continue;
				}
			else if (c == '.' && !hasDecimal){
				hasDecimal = true;
			}
			else
			{
				// invalid character found
				valid = false;
			}
			}
		}
		tf.setText(s);
		if (!valid){
			tf.requestFocus();
		}
		return (valid);
	}
}
