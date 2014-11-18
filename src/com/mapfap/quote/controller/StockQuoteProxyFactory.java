package com.mapfap.quote.controller;

import javax.xml.ws.WebServiceException;

import net.webservicex.StockQuote;
import net.webservicex.StockQuoteSoap;

/**
 * This singleton factory class take responsibility for connecting to the soap service
 * using the code that generated from `wsimport`.
 * @author Sarun Wongtanakarn
 *
 */
public class StockQuoteProxyFactory {

	private static StockQuoteProxyFactory instance;
	private StockQuoteSoap stockQuoteProxy;

	private StockQuoteProxyFactory() {
		// singleton
	}

	public static StockQuoteProxyFactory getInstance() {
		if (instance == null) {
			instance = new StockQuoteProxyFactory();
		}
		return instance;
	}

	/**
	 * Invoke the code generated from `wsimport`.
	 * @return SOAP client.
	 * @throws WebServiceException if there's problem on connecting to service.
	 */
	public StockQuoteSoap createStockQuoteProxy() throws WebServiceException {
		StockQuote factory = new StockQuote();
		stockQuoteProxy = factory.getStockQuoteSoap();
		return stockQuoteProxy;
	}

}
