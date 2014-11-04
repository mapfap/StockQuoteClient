package controller;

import javax.xml.ws.WebServiceException;

import net.webservicex.StockQuote;
import net.webservicex.StockQuoteSoap;

public class StockQuoteProxyFactory {

	private static StockQuoteProxyFactory instance;
	private StockQuoteSoap stockQuoteProxy;

	private StockQuoteProxyFactory() {

	}

	public static StockQuoteProxyFactory getInstance() {
		if (instance == null) {
			instance = new StockQuoteProxyFactory();
		}
		return instance;
	}

	public StockQuoteSoap createStockQuoteProxy() throws WebServiceException {
		StockQuote factory = new StockQuote();
		stockQuoteProxy = factory.getStockQuoteSoap();
		return stockQuoteProxy;
	}

}
