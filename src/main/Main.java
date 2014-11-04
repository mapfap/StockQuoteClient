package main;

import javax.swing.SwingUtilities;

import net.webservicex.StockQuoteSoap;
import view.MainFrame;

/**
 * Main class of this application, only invoke the MainFrame.
 * @author Sarun Wongtanakarn
 *
 */
public class Main {

	public static void main(String[] args) {
		
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				net.webservicex.StockQuote factory = new net.webservicex.StockQuote();
				StockQuoteSoap proxy = factory.getStockQuoteSoap();
				new MainFrame(proxy);
			}
		});

	}
	
}
