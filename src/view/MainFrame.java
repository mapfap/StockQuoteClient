package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.webservicex.StockQuoteSoap;
import model.Stock;
import model.StockQuote;
import controller.RequestWorker;

/**
 * Main JFrame,
 * Initialize all the UI components for user input of 'Stock Symbol'
 * and update the result retrieved from the remote service.
 * 
 * @author Sarun Wongtanakarn 5510546166
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 8456560455229699542L;
	private JButton goButton;
	private JTextField textField;
	private JLabel lastLabel;
	private JLabel timeLabel;
	private JLabel changeLabel;
	private JLabel openLabel;
	private JLabel highLabel;
	private JLabel lowLabel;
	private JLabel volumeLabel;
	private JLabel marketCapitalLabel;
	private JLabel previousCloseLabel;
	private JLabel annRangeLabel;
	private JLabel earnsLabel;
	private JLabel PELabel;
	private JLabel nameLabel;
	
	private JLabel loadingLabel;
	
	private boolean loading = false;
	private RequestWorker requestWorker;
	private StockQuoteSoap proxy;

	/**
	 * Create and setup the main frame.
	 * @param proxy stock quote SOAP service.
	 */
	public MainFrame(StockQuoteSoap proxy) {
		super("Stock Quote");

		this.proxy = proxy;

		initUI();
		setListeners();

		pack();
		setVisible(true);
		
	}

	/**
	 * Initialize the UI components.
	 */
	private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		JPanel headPanel = new JPanel();
		add(headPanel, BorderLayout.NORTH);
		
		add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH);
		
		JPanel bodyPanel = new JPanel();
		add(bodyPanel, BorderLayout.CENTER);
		
		JPanel paddingLeftPanel = new JPanel();
		paddingLeftPanel.setPreferredSize(new Dimension(100, 100));
		add(paddingLeftPanel, BorderLayout.WEST);
		
//		JPanel paddingBottomPanel = new JPanel();
//		paddingBottomPanel.setPreferredSize(new Dimension(50, 50));
//		add(paddingBottomPanel, BorderLayout.SOUTH);
		
		headPanel.setLayout(new GridLayout(0,1));
		bodyPanel.setLayout(new GridLayout(0,4));
		
		goButton = new JButton("Go");
		goButton.setFocusable(false);
		textField = new JTextField(10);
		
		initLabel();
		
		emphasiseLabel(new JLabel[] { changeLabel, nameLabel });
		
		JPanel inputPanel = new JPanel();
		headPanel.add(inputPanel);
		inputPanel.add(new JLabel("Enter stock symbol "));
		inputPanel.add(textField);
		inputPanel.add(goButton);
		
		JPanel outputHeadPanel = new JPanel();
		headPanel.add(outputHeadPanel);
		outputHeadPanel.add(nameLabel);
		outputHeadPanel.add(loadingLabel);
		
		JLabel[] properties = new JLabel[]{
			new JLabel("Last"), lastLabel,
			new JLabel("Change"), changeLabel,
			new JLabel("Previous Close"), previousCloseLabel,
			new JLabel("Open"), openLabel,
			new JLabel("High"), highLabel,
			new JLabel("Low"), lowLabel,
			new JLabel("Volume"), volumeLabel,
			new JLabel("Market Capital"), marketCapitalLabel,
			new JLabel("P/E"), PELabel,
			new JLabel("Ann. Range"),annRangeLabel,
			new JLabel("Earns"), earnsLabel,
			new JLabel("Updated At"), timeLabel
		};
		
		for (JLabel label : properties) {
			bodyPanel.add(label);
		}
		
		Dimension fixedDimension = new Dimension(650, 400);
		setPreferredSize(fixedDimension);
		setResizable(false);
	}

	/**
	 * Initialize the label.
	 */
	private void initLabel() {
		nameLabel = new JLabel("");
		loadingLabel = new JLabel("");
		
		timeLabel = new JLabel("-");
		lastLabel = new JLabel("-");
		changeLabel = new JLabel("-");
		openLabel = new JLabel("-");
		highLabel = new JLabel("-");
		lowLabel = new JLabel("-");
		volumeLabel = new JLabel("-");
		marketCapitalLabel = new JLabel("-");
		previousCloseLabel = new JLabel("-");
		annRangeLabel = new JLabel("-");
		earnsLabel = new JLabel("-");
		PELabel = new JLabel("-");
	}
	
	/**
	 * Clear the text on all label.
	 */
	private void clearText() {
		nameLabel.setText("");
		loadingLabel.setText("");
		                
		timeLabel.setText("-");
		lastLabel.setText("-");
		changeLabel.setText("-");
		openLabel.setText("-");
		highLabel.setText("-");
		lowLabel.setText("-");
		volumeLabel.setText("-");
		marketCapitalLabel.setText("-");
		previousCloseLabel.setText("-");
		annRangeLabel.setText("-");
		earnsLabel.setText("-");
		PELabel.setText("-");
	}
	
	/**
	 * Emphasise (bold) all the label given in the array.
	 * @param labels array of label to be emphasised.
	 */
	private void emphasiseLabel(JLabel[] labels) {
		Font boldFont = new Font("Sans Serif", Font.BOLD, 16);
		
		for (JLabel label : labels) {
			label.setFont(boldFont);
		}
		
	}

	/**
	 * Set the listeners for input action.
	 */
	private void setListeners() {
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					retrieve();
				}
			}
			public void keyTyped(KeyEvent e) { }
			public void keyPressed(KeyEvent e) { }
		});

		goButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				retrieve();
			}
		});
	}
	
	/**
	 * This is called when the worker done loading the data from remote service.
	 * It's update the UI according to the data retrieve.
	 */
	public void doneLoading(StockQuote stockQuote) {
		loading = false;
		loadingLabel.setText("");
		Stock stock = stockQuote.getStock();
		
		if (stock.getDate().equals("N/A")) {
			alert("'" + stock.getName() + "' is not valid stock symbol");
			return;
		}
		
		lastLabel.setText(stock.getLast() + "");
		timeLabel.setText(stock.getDate() + " " + stock.getTime());
		openLabel.setText(stock.getOpen() + "");
		highLabel.setText(stock.getHigh() + "");
		lowLabel.setText(stock.getLow() + "");
		volumeLabel.setText(stock.getVolume() + "");
		marketCapitalLabel.setText(stock.getMarketCapital() + "");
		previousCloseLabel.setText(stock.getPreviousClose() + "");
		annRangeLabel.setText(stock.getAnnRange());
		earnsLabel.setText(stock.getEarns() + "");
		PELabel.setText(stock.getPE() + "");
		nameLabel.setText(stock.getName() + " (" + stock.getSymbol() + ")");
		
		String color = (stock.getChange() < 0) ? "red" : "green";
		changeLabel.setText("<html><div style='color: "+ color +";'>" + stock.getChange() + " (" + stock.getPercentageChange() + ")" + "<div></html>");
	}

	/**
	 * Initiate the worker to retrieve the data.
	 */
	protected void retrieve() {
		if (loading) {
			textField.setFocusable(false);
			if ( JOptionPane.showConfirmDialog( null, "Abort previous task and start a new one?", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.OK_OPTION) {
				textField.setFocusable(true);
				try {
					requestWorker.cancel(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				textField.setFocusable(true);
				return;
			}
		}
		textField.setFocusable(true);
		
		clearText();
		String input = textField.getText();
		requestWorker = new RequestWorker(this, input, proxy);
		requestWorker.execute();
		loadingLabel.setText("Loading " + input + "..");
		loading = true;
	}

	/**
	 * This prevent users from get stuck with enter loop of alert.
	 * 
	 * @param text to display
	 */
	private void alert(String text) {
		textField.setFocusable(false);
		if( JOptionPane.showConfirmDialog( null, text, null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.OK_OPTION ) { 
			textField.setFocusable(true);
		} else {
			textField.setFocusable(true);			
		}
	}

}
