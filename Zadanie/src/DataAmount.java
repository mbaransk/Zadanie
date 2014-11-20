import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;


public class DataAmount {
	BufferedReader in;
	String fileName;
	
	DataAmount(String fileName, String encoding) throws Exception {
		this.fileName = fileName;
		
		try {
			//in = new BufferedReader(new FileReader(fileName));
			in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), encoding));
			
		} catch(FileNotFoundException e) {
			System.err.println("Program nie może otworzyć pliku: " + fileName);
			throw e;
		} catch (UnsupportedEncodingException e) {
			System.err.println("Program nie wspiera podanego kodowania");
			throw e;
		} catch (Exception e) {
			//Plik udało sie otworzyć, ale wystapił jakiś inny wyjątek
			try {
				in.close();
			} catch (IOException e1) {
				System.err.println("Nie udało się zamknąć pliku: " + fileName);
				throw e;
			}	
		}
	}
	
	private String getLine() {
		String line;
		try {
			line = in.readLine();
		} catch(IOException e) {
			throw new RuntimeException("Nie udało się odczytać linii pliku. Błąd: readLine()");
		}
		return line;
	}
	
	public BigDecimal getAmount() {
		String amountStr, line, amountTag = "@amount", colon=":", currency="PLN";
		int amountInd, currencyInd = 0;
		BigDecimal amountSum = new BigDecimal(0);//dobre do operacji na pieniądzach
		
		while((line = getLine()) != null) {
			amountInd = line.indexOf(amountTag);
			currencyInd = line.indexOf(currency);
			
			if(amountInd == -1 || currencyInd == -1)
				continue;
			
			amountStr = line.substring(amountInd + amountTag.length() + colon.length(), currencyInd);
			amountSum = amountSum.add(new BigDecimal(amountStr.replace(',', '.')));
		}
		
		closeFile();
		return amountSum;
	}
	
	public void closeFile() {
		try {
			in.close();
		} catch (IOException e) {
			throw new RuntimeException("Nie udało się zamknąć pliku: " + fileName);
		}
	}
	
}
