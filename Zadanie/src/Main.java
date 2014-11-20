import java.math.BigDecimal;


public class Main {
	public static void main(String [] args)
	{
		if(args.length != 1)
			System.err.println("Do programu przekazano niepoprawną liczbę argumentów");
		else {
			try {
				DataAmount dA= new DataAmount(args[0], "UTF8");
				BigDecimal sumAmount = dA.getAmount();
				System.out.println("Suma kwot: " + sumAmount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
