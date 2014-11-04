package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.WebServiceException;

import view.MainFrame;
import model.StockQuote;
import net.webservicex.StockQuoteSoap;

/**
 * Swing Worker that connect to the SOAP service and update the UI.
 * @author Sarun Wongtanakarn
 *
 */
public class RequestWorker extends SwingWorker<StockQuote, Void> {

	private MainFrame frame;
	private String input;
	private StockQuoteSoap proxy;
	private boolean isTimeout = false;
	private Timer timer;
	private static final int timeout = 5000; //ms
	
	public RequestWorker(MainFrame frame, String input, StockQuoteSoap proxy) {
		this.frame = frame;
		this.input = input;
		this.proxy = proxy;
	}
	
	/**
	 * Do retrieve data from the remote service in the background.
	 */
	@Override
	protected StockQuote doInBackground() throws Exception {
		setTimeout(timeout);
		
		try {			
			String data = proxy.getQuote(input);
			StockQuote stockQuote  = convertBytesToStockQuote(data.getBytes());
			return stockQuote;
		} catch (WebServiceException e) {
			return null;
		}
		
	}
	
	/**
	 * If the time reach limit, then terminate the task.
	 * @param timeout time in milliseconds before termination
	 */
	private void setTimeout(int timeout) {
		final RequestWorker worker = this;
		 timer = new Timer(timeout, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isTimeout = true;
				worker.cancel(true);
			}
		});
		timer.start();
	}

	/**
	 * When data is done loading, call the update on UI.
	 */
	@Override
	protected void done() {
		timer.stop();
		if (isTimeout) {
			frame.doneLoading(null);
			return;
		}
		if (!isCancelled()) {
			try {
				frame.doneLoading(get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
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
