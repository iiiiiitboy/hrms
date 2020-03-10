package ex.aaronfae.graduation.base.util;

import java.util.Calendar;
import java.util.Date;

public abstract class DateUtil {

	public static int deductWorkingHours(Date subtracted, Date subtraction) {
		long min = (subtracted.getTime() - subtraction.getTime()) / 1000 / 60;
		long result = min / 60;
		return (int) (result != 0 ? result : 1);
	}

	public static String diffTime(Date subtracted, Date subtraction) {

//		Calendar tempCalendar = Calendar.getInstance();
//		tempCalendar.setTime(subtraction);
//		
//		Calendar subtractionCalendar = Calendar.getInstance();
//		subtractionCalendar.setTime(subtracted);
//		
//		subtractionCalendar.set(11, tempCalendar.get(11));
//		subtractionCalendar.set(12, tempCalendar.get(12));
//		subtractionCalendar.set(13, tempCalendar.get(13));
//		
//		long diff = subtracted.getTime() - subtractionCalendar.getTime().getTime();
		long diff = subtracted.getTime() - subtraction.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);
		return hours + "小时" + minutes + "分钟" + seconds + "秒";
	}

	public static boolean isWeekend(Calendar calendar) {
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}
		return false;
	}

	public static int getMaxDaysOfYear(Calendar calendar) {
		if (isLeapYear(calendar)) {
			return 366;
		}
		return 365;
	}

	public static boolean isLeapYear(Calendar calendar) {
		int year = calendar.get(1);
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		}
		return false;
	}

}
