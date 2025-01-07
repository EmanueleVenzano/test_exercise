package cern.test.code.exercise.second.utils;

/**
 * Utility class that recognize the ValueType from the value inserted in the cell
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class ValueTypeRecognizer {

	/**
	 * Static method to return the type of a specified value.
	 *
	 * @param value the object to check
	 * @return ValueType of the object
	 */
	public static ValueType fromValue(Object value) {
	    if (value instanceof Integer) {
	        return ValueType.INTEGER;
	    }
	    if (value instanceof String valueString) {
	        // Check if the string can be parsed as an integer
	        if (isParsableAsInteger(valueString)) {
	            return ValueType.INTEGER;
	        }
	        if (valueString.startsWith("=")) {
	            return ValueType.FORMULA;
	        }
	        return ValueType.STRING;
	    }
	    return null;
	}

	/**
	 * Utility method to check if a string can be parsed as an integer.
	 *
	 * @param value the string to check
	 * @return true if the string can be parsed as an integer, false otherwise
	 */
	private static boolean isParsableAsInteger(String value) {
	    if (value == null) {
	        return false; // Null cannot be parsed as an integer
	    }
	    try {
	        Integer.parseInt(value.trim()); // Trim the input string before parsing
	        return true;
	    } catch (NumberFormatException e) {
	        return false; // Parsing failed, not an integer
	    }
	}

}
