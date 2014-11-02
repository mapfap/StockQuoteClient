import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="StockQuotes")
@XmlAccessorType(XmlAccessType.FIELD)
public class StockQuote {
	
	@XmlElement(name="Stock")
	private Stock stock;

	public StockQuote() {
		
	}
	
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
}
