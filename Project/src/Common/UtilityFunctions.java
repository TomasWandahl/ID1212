package eu.vandahl.common;

public class UtilityFunctions {
	 public static String removeCharAt(String s, int pos) {
	      return s.substring(0, pos) + s.substring(pos + 1);
	   }
}
