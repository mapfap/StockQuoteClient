import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Main JFrame, holds whole application in it.
 * 
 * @author Sarun Wongtanakarn 5510546166
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 8456560429229699542L;
	private JButton goButton;
	private JTextField textField;
	private JLabel symbolLabel;
	private JLabel lastLabel;
	private JLabel dateLabel;
	private JLabel timeLabel;
	private JLabel changeLabel;
	private JLabel openLabel;
	private JLabel highLabel;
	private JLabel lowLabel;
	private JLabel volumeLabel;
	private JLabel marketCapitalLabel;
	private JLabel previousCloseLabel;
	private JLabel percentageChangeLabel;
	private JLabel annRangeLabel;
	private JLabel earnsLabel;
	private JLabel PELabel;
	private JLabel nameLabel;

	/**
	 * Create and setup the main frame.
	 * @param rssReader a controller for fetching rss.
	 */
	public MainFrame() {
		super("RSSReader");

		initUI();
		setListeners();

		pack();
		setVisible(true);
	}

	private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		goButton = new JButton("Go");
		textField = new JTextField(10);
		symbolLabel = new JLabel("-");
		lastLabel = new JLabel("-");
		dateLabel = new JLabel("-");
		timeLabel = new JLabel("-");
		changeLabel = new JLabel("-");
		openLabel = new JLabel("-");
		highLabel = new JLabel("-");
		lowLabel = new JLabel("-");
		volumeLabel = new JLabel("-");
		marketCapitalLabel = new JLabel("-");
		previousCloseLabel = new JLabel("-");
		percentageChangeLabel = new JLabel("-");
		annRangeLabel = new JLabel("-");
		earnsLabel = new JLabel("-");
		PELabel = new JLabel("-");
		nameLabel = new JLabel("-");

		add(new JLabel("Enter URL of the RSS feed"));
		add(textField);
		add(goButton);
		
		add(symbolLabel);
		add(lastLabel);
		add(dateLabel);
		add(timeLabel);
		add(changeLabel);
		add(openLabel);
		add(highLabel);
		add(lowLabel);
		add(volumeLabel);
		add(marketCapitalLabel);
		add(previousCloseLabel);
		add(percentageChangeLabel);
		add(annRangeLabel);
		add(earnsLabel);
		add(PELabel);
		add(nameLabel);
		
		Dimension fixedDimension = new Dimension(650, 400);
		setPreferredSize(fixedDimension);
		setResizable(false);
	}

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
	
	public void updateGUI(StockQuote stockQuote) {
		Stock stock = stockQuote.getStock();
		symbolLabel.setText(stock.getSymbol());
		lastLabel.setText(stock.getLast() + "");
		dateLabel.setText(stock.getDate());
		timeLabel.setText(stock.getTime());
		changeLabel.setText(stock.getChange() + "");
//		closeLabel.setText(stock.getClose());
		openLabel.setText(stock.getOpen() + "");
		highLabel.setText(stock.getHigh() + "");
		lowLabel.setText(stock.getLow() + "");
		volumeLabel.setText(stock.getVolume() + "");
		marketCapitalLabel.setText(stock.getMarketCapital() + "");
		previousCloseLabel.setText(stock.getPreviousClose() + "");
		percentageChangeLabel.setText(stock.getPercentageChange());
		annRangeLabel.setText(stock.getAnnRange());
		earnsLabel.setText(stock.getEarns() + "");
		PELabel.setText(stock.getPE() + "");
		nameLabel.setText(stock.getName());
	}

	protected void retrieve() {
		RequestWorker requestWorker = new RequestWorker(this);
		requestWorker.execute();
		System.out.println("Loading");
	}

//	/**
//	 * This prevent users from get stuck with enter loop of alert.
//	 * 
//	 * @param text to display
//	 */
//	private void alert(String text) {
//		textField.setFocusable(false);
//		if( JOptionPane.showConfirmDialog( null, text, null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.OK_OPTION ) { 
//			textField.setFocusable(true);
//		} else {
//			textField.setFocusable(true);			
//		}
//	}

}
