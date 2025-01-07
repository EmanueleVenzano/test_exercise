package cern.test.code.exercise.second.utils;

public class ValueTypeRecognizer {
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
	    try {
	        Integer.parseInt(value);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

}
