package util;


public class Dateutil {
	public static Boolean isLegal(String year,String month,String day) {
		if (Stringutil.isNumeric(year) && Stringutil.isNumeric(month) && Stringutil.isNumeric(day)) {
			if (Integer.parseInt(year)>=2019 && Integer.parseInt(month)>0 && Integer.parseInt(month)<=12 && Integer.parseInt(day)>0 && Integer.parseInt(day)<=31) {
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}
}
