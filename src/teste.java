import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class teste {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		
      DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
      Date dt1 = df.parse ("01/04/2007");
      Date dt2 = df.parse ("31/05/2014");
      Calendar cal = Calendar.getInstance();
      cal.setTime (dt1);
      for (Date dt = dt1; dt.compareTo(dt2) <= 0; ) {
      	DateFormat df2 = new SimpleDateFormat ("MM/yyyy", new Locale ("pt", "BR"));
      	System.out.println (df2.format(dt).toLowerCase());

          cal.add (Calendar.MONTH, +1);
          dt = cal.getTime();
          
      }
      

	}

}
