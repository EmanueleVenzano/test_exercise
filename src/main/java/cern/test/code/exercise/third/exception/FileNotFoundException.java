package cern.test.code.exercise.third.exception;

/**
 * Exception that occurs when specified file cannot be found.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class FileNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -1634636526424371523L;
	private static final String MESSAGE = "Cannot find specified file %s";
	
	/**
     * Constructor that complete the predefined exception message with the file name that was
     * not found.
     *
     * @param filename is the path of the file that was not found
     * @param e is the original exception that contains further details
     */
	public FileNotFoundException (String filename, Throwable e) {
		super(String.format(MESSAGE, filename), e);
	}

}
