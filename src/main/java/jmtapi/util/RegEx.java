
package jmtapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegEx{
  public static final String extract(String needle, String haystack){
    Matcher matcher = Pattern.compile(needle).matcher(haystack);
    return matcher.find() ? matcher.group() : null;
  }
}
