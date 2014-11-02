import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Stock {
	
	@XmlElement(name = "Symbol")
	private String symbol;
	
	@XmlElement(name = "Last")
	private double last;
	
	@XmlElement(name = "Date")
	private String date;
	
	@XmlElement(name = "Time")
	private String time;
	
	@XmlElement(name = "Change")
	private double change;
	
	@XmlElement(name = "Open")
	private double open;
	
	@XmlElement(name = "High")
	private double high;
	
	@XmlElement(name = "Low")
	private double low;
	
	@XmlElement(name = "Volume")
	private int volume;
	
	@XmlElement(name = "MktCap")
	private double marketCapital;
	
	@XmlElement(name = "PreviousClose")
	private double previousClose;
	
	@XmlElement(name = "PercentageChange")
	private String percentageChange;
	
	@XmlElement(name = "AnnRange")
	private String annRange;

	@XmlElement(name = "Earns")
	private double earns;
	
	@XmlElement(name = "P-E")
	private double PE;
	
	@XmlElement(name = "Name")
	private String name;
	
	public Stock() {
		
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getLast() {
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getMarketCapital() {
		return marketCapital;
	}

	public void setMarketCapital(double marketCapital) {
		this.marketCapital = marketCapital;
	}

	public double getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}

	public String getPercentageChange() {
		return percentageChange;
	}

	public void setPercentageChange(String percentageChange) {
		this.percentageChange = percentageChange;
	}

	public String getAnnRange() {
		return annRange;
	}

	public void setAnnRange(String annRange) {
		this.annRange = annRange;
	}

	public double getEarns() {
		return earns;
	}

	public void setEarns(double earns) {
		this.earns = earns;
	}

	public double getPE() {
		return PE;
	}

	public void setPE(double pE) {
		PE = pE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
