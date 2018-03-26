package Utility;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommonUtils {

	public static String handleStr(String str) throws Exception {

		if (str.contains("//")) {
			str = str.replace("//", "");
		}
		if (str.contains("(")) {
			str = str.replace("(", "");
		}
		if (str.contains(")")) {
			str = str.replace(")", "");
		}
		return str;

	}

	public static int getRandomNumber(int NumberOfDigits) {
		Random r = new Random();
		int number = 0;
		if (NumberOfDigits == 2) {
			number = r.nextInt(989 - 900) + 10;
		}
		if (NumberOfDigits == 3) {
			number = r.nextInt(9899 - 9000) + 100;
		}
		if (NumberOfDigits == 4) {
			number = r.nextInt(98999 - 90000) + 1000;
		}
		if (NumberOfDigits == 5) {
			number = r.nextInt(98999 - 90000) + 10000;
		}
		if (NumberOfDigits == 6) {
			number = r.nextInt(9899999 - 9000000) + 100000;
		}
		if (NumberOfDigits == 7) {
			number = r.nextInt(98999999 - 90000000) + 1000000;
		}
		if (NumberOfDigits == 8) {
			number = r.nextInt(989999999 - 900000000) + 10000000;
		}

		return number;
	}

	public static HashMap<String,String> getRandomDOB(int startYear, int EndYear)
	{
		Random random = new Random();
		int minDay = (int) LocalDate.of(startYear, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(EndYear, 1, 1).toEpochDay();
		long randomDay = minDay + random.nextInt(maxDay - minDay);
		LocalDate randomBirth = LocalDate.ofEpochDay(randomDay);
		/**
		 * retrieving month
		 */
		DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern( "MMMM" );
		/**
		 * retrieving year
		 */
		DateTimeFormatter YearFormat = DateTimeFormatter.ofPattern( "uuuu" );
		/**
		 * retrieving day
		 */
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern( "d" );

		 HashMap<String,String> dob = new HashMap<String,String>();
		  dob.put("month",randomBirth.format( monthFormat ));
		  dob.put("year",randomBirth.format( YearFormat ));
		  dob.put("day",randomBirth.format( dateFormat ));

		  return  dob;

	}

	public static String addNumberOfBuisnessDaysToCurrentDate(int days) {
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<days;)
		{
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			if(calendar.get(Calendar.DAY_OF_WEEK) >= Calendar.MONDAY && calendar.get(Calendar.DAY_OF_WEEK) <= Calendar.FRIDAY)
			{
				i++;
			}
		}
		date=calendar.getTime();
		return s.format(date);
	}

	public static String getPreviousDate(int days, String format) {
		final Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(format);
		cal.add(Calendar.DATE, -days);
		return  dateFormat.format(cal.getTime());
	}

	public static String getCurrentDate(String format) {
		final Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(format);
		return  dateFormat.format(cal.getTime());
	}

	public static String getUTCCurrentDate(String format) {
		final Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return  dateFormat.format(cal.getTime());
	}

	public static String formatNumberString(String format, String value){
		if(StringUtils.isNotEmpty(value)) {
			return String.format(format, new BigDecimal(value));
		}else{
			return String.format(format, new BigDecimal("0"));
		}
	}

	public static String getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		String dayName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
		return dayName.toUpperCase();

	}

	public static String getMonthNumber(String monthName) throws ParseException
	{
		SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM");
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputFormat.parse(monthName));
		SimpleDateFormat outputFormat = new SimpleDateFormat("MM"); // 01-12
		return (outputFormat.format(cal.getTime()));
	}

	public static boolean verifyElementAbsent(WebDriver driver, By by)
			throws Exception {

		try {
			driver.findElement(by);
			return false;

		} catch (NoSuchElementException e) {
			return true;
		}
	}

	public static boolean verifyElementPresent(WebDriver driver, By by)
			throws Exception {



		try {
			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {
			return false;
		}
	}

	// Take snapshot
	public static void takeScreenshot(WebDriver driver, String fileName) throws Exception {
		try {
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File screenshot = ((TakesScreenshot) augmentedDriver)
					.getScreenshotAs( OutputType.FILE);
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			File file = new File("..\\cweb\\TestResult\\"+fileName+"_"+timeStamp+".png");
			FileUtils.copyFile(screenshot, file);
		} catch (Exception e) {
			throw (e);
		}
	}

	// used to check if a string value(for example get from data pool file) is
	// bigger than the number
	public static boolean verifyBiggerThanNumber(String str, int number)
			throws Exception {

		try {
			if ((str == null) || (str.isEmpty()) || (str.endsWith("a"))
					|| (str.endsWith("A"))) {
				return false;
			} else {
				int iParseFromStr = Integer.parseInt(str);
				if (iParseFromStr > number) {
					return true;
				} else {
					return false;
				}
			}

		} catch (Exception e) {
			return false;
		}
	}

	public static int parseInt(String str) throws Exception {

		try {
			if ((str == null) || (str.isEmpty()) || (str.endsWith("a"))
					|| (str.endsWith("A"))) {
				return 0;
			} else {
				int iParseFromStr = Integer.parseInt(str);
				return iParseFromStr;

			}
		} catch (Exception e) {
			return 0;
		}
	}

	public static String removeEndStr(String fullStr, String endStr)
			throws Exception {
		try {
			String processedStr = fullStr;
			if ((fullStr != null) && (endStr != null)) {
				if (fullStr.endsWith(endStr)) {
					int len = fullStr.length() - endStr.length();
					processedStr = fullStr.substring(0, len);
				}
			}
			return processedStr;

		} catch (Exception e) {
			return fullStr;
		}

	}

	public static String convertFloatToStringWithFormat(float fValue)
			throws Exception {
		try {
			return String.format("%.2f", fValue);

		} catch (Exception e) {
			return null;
		}

	}
	
	//Get last part of substring after split with symbols "."
	public static String getLastSubString(String fullStr)
			throws Exception {
		try {
			String lastSubString="";
			String [] array = null;
			int length=0;
			if ((fullStr != null)) {
				array=fullStr.split("\\.");
			}
			length=array.length;
			if(length>0){
			lastSubString=array[length-1];}
			return lastSubString;

		} catch (Exception e) {
			return fullStr;
		}

	}

}
