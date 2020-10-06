package me.atticuszambrana.rikosaukurauchi.util;

import java.util.Arrays;

public class StringUtil {
	
	/*
	 * Utility class for working with Strings
	 * Author: Atticus Zambrana
	 */
	
	public static String removeSpaces(String message) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < message.length(); i++) {
			String c = String.valueOf(message.charAt(i));
			if(c.equals(" ")) {
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}
	/**
	 * Convert a string separated by spaces to a String[]
	 * @param in
	 * @return
	 */
	public static String[] toArray(String in) {
		return in.split(" ");
	}
	/**
	 * Do the exact same thing, but completely ignore the first item in the array, better for getting arguments from a command executed
	 * @param in
	 * @return
	 */
	public static String[] toArrayWithoutFirst(String in) {
		String[] first = in.split(" ");
		return Arrays.copyOfRange(first, 1, first.length);
	}
	
	public static String combine(String[] arr, int startPos) {
        StringBuilder str = new StringBuilder();

        for(int i = startPos; i < arr.length; ++i) {
           str = str.append(arr[i] + " ");
        }
        return str.toString();
	}
}