package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sring的工具类
 * @author Administrator
 *
 */
public class Stringutil {
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isEmpty(str)) {
			return false;
		}
        if( !isNum.matches() ){
            return false;
        }
        return true;
 }
	
}
