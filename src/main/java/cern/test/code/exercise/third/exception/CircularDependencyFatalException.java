package cern.test.code.exercise.third.exception;


/**
 * Exception that occurs when circular dependency is detected.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class CircularDependencyFatalException extends RuntimeException{
	private static final long serialVersionUID = -1634637526424371523L;
	private static final String MESSAGE = "Fatal error! Specified dependency "
			+ "tree is not a tree since a circular detection that involves %s was found.";
	
	/**
     * Constructor that complete the predefined exception message with the package name on which the 
     * circular dependency was detected.
     *
     * @param packageName is the package on which the exception was detected
     */
	public CircularDependencyFatalException (String packageName) {
		super(String.format(MESSAGE, packageName));
	}

}
