package main;

import javax.swing.SwingUtilities;
import javax.xml.ws.WebServiceException;

import controller.StockQuoteProxyFactory;
import net.webservicex.StockQuoteSoap;
import view.MainFrame;

/**
 * Main class of this application.
 * Prepare the SOAP client and initiate the java swing GUI.
 * @author Sarun Wongtanakarn
 *
 */
public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			private StockQuoteSoap stockQuoteProxy = null;

			@Override
			public void run() {
				StockQuoteProxyFactory factory = StockQuoteProxyFactory.getInstance();
				try {					
					stockQuoteProxy = factory.createStockQuoteProxy();
				} catch (WebServiceException e) {
					// If there's no network connection, Ignore it and try to connect again later.
				}
				
				new MainFrame(stockQuoteProxy);
			}
		});

	}

}
