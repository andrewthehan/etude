package infinotes.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx{
	public static String extract(String needle, String haystack){
		Matcher matcher = Pattern.compile(needle).matcher(haystack);
		return matcher.find() ? matcher.group() : null;
	}
}
