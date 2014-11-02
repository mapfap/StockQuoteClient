import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.webservicex.StockQuoteSoap;


public class RequestWorker extends SwingWorker<StockQuote, Void> {

	private MainFrame frame;
	
	public RequestWorker(MainFrame frame) {
		this.frame = frame;
	}
	
	@Override
	protected StockQuote doInBackground() throws Exception {
		net.webservicex.StockQuote factory = new net.webservicex.StockQuote();
		StockQuoteSoap proxy = factory.getStockQuoteSoap();
		String data = proxy.getQuote("GOOGL");
		StockQuote stockQuote = convertBytesToStockQuote(data.getBytes());
		return stockQuote;
	}
	
	@Override
	protected void done() {
		try {
			frame.updateGUI(get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
//	private static void retrive() {
//		Stock stock = stockQuote.getStock();
//		System.out.println(stock.getSymbol());
//		System.out.println(stock.getLast());
//		System.out.println(stock.getDate());
//		System.out.println(stock.getTime());
//		System.out.println(stock.getChange());
//		System.out.println(stock.getOpen());
//		System.out.println(stock.getHigh());
//		System.out.println(stock.getLow());
//		System.out.println(stock.getVolume());
//		System.out.println(stock.getMarketCapital());
//		System.out.println(stock.getPreviousClose());
//		System.out.println(stock.getPercentageChange());
//		System.out.println(stock.getAnnRange());
//		System.out.println(stock.getEarns());
//		System.out.println(stock.getPE());
//		System.out.println(stock.getName());
//	}

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
