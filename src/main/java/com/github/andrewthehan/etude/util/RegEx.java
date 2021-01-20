
package com.github.andrewthehan.etude.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegEx {
  private RegEx() {
    throw new AssertionError();
  }

  public static final String extract(String needle, String haystack) {
    Matcher matcher = Pattern.compile(needle).matcher(haystack);
    return matcher.find() ? matcher.group() : null;
  }
}
