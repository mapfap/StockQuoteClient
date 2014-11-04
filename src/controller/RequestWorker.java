package controller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import view.MainFrame;
import model.StockQuote;
import net.webservicex.StockQuoteSoap;


public class RequestWorker extends SwingWorker<StockQuote, Void> {

	private MainFrame frame;
	private String input;
	
	public RequestWorker(MainFrame frame, String input) {
		this.frame = frame;
		this.input = input;
	}
	
	@Override
	protected StockQuote doInBackground() throws Exception {
		net.webservicex.StockQuote factory = new net.webservicex.StockQuote();
		StockQuoteSoap proxy = factory.getStockQuoteSoap();
		String data = proxy.getQuote(input);
		StockQuote stockQuote = convertBytesToStockQuote(data.getBytes());
		return stockQuote;
	}
	
	@Override
	protected void done() {
		try {
			frame.doneLoading(get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Convert byte[] of XML string to StockQuote. 
	 * @param byteArray array of byte data.
	 * @return StockQuote parsed from byteArray.
	 */
	private static StockQuote convertBytesToStockQuote( byte[] byteArray ) {
		InputStream stream = new ByteArrayInputStream( byteArray );
		try {
			StockQuote stockQuote = new StockQuote();
			JAXBContext context = JAXBContext.newInstance( StockQuote.class ) ;
			Unmarshaller unmarshaller = context.createUnmarshaller();	
			stockQuote = (StockQuote) unmarshaller.unmarshal( stream );
			return stockQuote;
		} catch ( JAXBException e ) {
			e.printStackTrace();
		}
		return null;
	}

}
