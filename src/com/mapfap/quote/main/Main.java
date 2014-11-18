package com.mapfap.quote.main;

import javax.swing.SwingUtilities;
import javax.xml.ws.WebServiceException;

import com.mapfap.quote.controller.StockQuoteProxyFactory;
import com.mapfap.quote.view.MainFrame;

import net.webservicex.StockQuoteSoap;

/**
 * Main class of this application.
 * Prepare the SOAP client and initiate the java swing GUI.
 * @author Sarun Wongtanakarn
 *
 */
public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StockQuoteProxyFactory factory = StockQuoteProxyFactory.getInstance();
				new MainFrame(factory);
			}
		});

	}

}
