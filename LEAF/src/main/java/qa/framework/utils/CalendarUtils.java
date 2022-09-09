package qa.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
	
	private static CalendarUtils objCalendarUtils;
	
	private CalendarUtils() {
		
	}
	
	public synchronized static CalendarUtils getCalendarUtilsObject() {
		return (objCalendarUtils == null) ? new CalendarUtils() : objCalendarUtils;
	}

	/**
	 * <b>Note:</b> ":" should not be given in dateFormat
	 * 
	 * @author BathriYo
	 * @return String
	 */
	public static String getTimeStamp(String dateFormat) {
		Date currentDate = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
		return dateFormatter.format(currentDate);
	}

	/**
	 * function will return prvious and future date based on days
	 * 
	 * @author 10650956
	 * @param futureDate
	 * @return futureDate(ex: if you pass +2 function will return you future date of
	 *         2 days, same with previous date you have to pass -2)
	 */
	public static String getPreviousAndFutureDate(String futureDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
		calendar.add(Calendar.DATE, Integer.parseInt(futureDate));
		return dateFormatter.format(calendar.getTime());
	}
	
	/**
	 * Convert date to Stirng
	 * 
	 * @author 10650956
	 * @param format : eg. dd/MM/yyyy
	 * @param date
	 * @return
	 */
	public static String dateToString(String format,Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		String strDate = dateFormatter.format(date);
		
		return strDate;
	}
	
	/**
	 * Convert String to Date
	 * 
	 * @author 10650956
	 * @param format : eg. dd/MM/yyyy
	 * @param strDate
	 * @return
	 */
	public static Date stringToDate(String format,String strDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(strDate);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Get futur date
	 * 
	 * @author 10650956
	 * @param format : eg. dd/MM/yyyy
	 * @param numberOfDayAhead
	 * @return
	 */
	public static String getFuturnDate(String format, int numberOfDayAhead) {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, numberOfDayAhead);
		Date tomorrow = cal.getTime();
		SimpleDateFormat  dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.format(tomorrow);
	}
	
	public static String getFutureDate(Date date, String format, int numberOfDayAhead, boolean skipWeekend) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		
		if(numberOfDayAhead>0) {
			if(skipWeekend) {
				for(int i=1;i<=numberOfDayAhead;i++) {
					calendar.add(Calendar.DATE, 1);
					
					if(calendar.get(Calendar.DAY_OF_WEEK)==1||calendar.get(Calendar.DAY_OF_WEEK)==7) {
						numberOfDayAhead++;
					}
				}
			}else {
				calendar.add(Calendar.DATE, numberOfDayAhead);
			}
		}
		
		return dateFormatter.format(calendar.getTime());
	}
	
	public static Date millisecondToDate(long millisecond) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millisecond);
		return cal.getTime();
	}
}
