package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.xml.ws.WebServiceException;

import model.Stock;
import model.StockQuote;
import net.webservicex.StockQuoteSoap;
import controller.RequestWorker;
import controller.StockQuoteProxyFactory;

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
	
	private static final Font boldFont = new Font("Verdana", Font.BOLD, 16);
	
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
		
		add(new JLabel("<html><div style='padding: 5px; height: 18px; width: 700px; text-align:left; background: #2a2a2a; color: white;'>Developed by Sarun Wongtanakarn</div></html>"), BorderLayout.SOUTH);
		
		headPanel.add(new JLabel("<html><div style='height: 30px; line-height: 20px; width: 700px; text-align:center; background: #2a2a2a; color: white; font-size: 18px; font-weight: bold;'>Stock Quote</div></html>"));
		
		headPanel.setLayout(new GridLayout(0,1));
		bodyPanel.setLayout(new GridLayout(0,4));
		
		goButton = new JButton("Go");
		goButton.setFocusable(false);
		textField = new JTextField(20);
		
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
		for (JLabel label : labels) {
			label.setFont(boldFont);
		}
	}

	/**
	 * Set the listeners for input action.
	 */
	private void setListeners() {
		ActionListener retrieveAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				retrieve();
			}
		};
		
		textField.addActionListener(retrieveAction);
		goButton.addActionListener(retrieveAction);
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
			JOptionPane.showMessageDialog(null, "'" + stock.getName() + "' is not valid stock symbol");
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
		nameLabel.setText("<html><div style='background: #58b1ff; color: white; padding: 2px;'>" + stock.getName() + " (" + stock.getSymbol() + ")" + "</div></html>");
		
		String color = (stock.getChange() < 0) ? "red" : "green";
		changeLabel.setText("<html><div style='color: "+ color +";'>" + stock.getChange() + " (" + stock.getPercentageChange() + ")" + "<div></html>");
	}

	/**
	 * Initiate the worker to retrieve the data.
	 */
	protected void retrieve() {
		if (loading) {
			if ( JOptionPane.showConfirmDialog( null, "Abort previous task and start a new one?", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.OK_OPTION) {
				try {
					requestWorker.cancel(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				return;
			}
		}
		
		clearText();
		String input = textField.getText();
		
		if (proxy == null) {
			try {
				proxy = StockQuoteProxyFactory.getInstance().createStockQuoteProxy();
			} catch (WebServiceException e) {
				JOptionPane.showMessageDialog(null, "No network connection");
				return;
			}
		}

		requestWorker = new RequestWorker(this, input, proxy);
		requestWorker.execute();
		loadingLabel.setText("Loading '" + input + "'..");
		loading = true;
	}

		

}
