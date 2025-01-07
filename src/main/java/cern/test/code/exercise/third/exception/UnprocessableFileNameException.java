package cern.test.code.exercise.third.exception;

/**
 * Exception that occurs when specified filename is empty or null, thus it cannot be used to process the request.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class UnprocessableFileNameException extends RuntimeException{
	private static final long serialVersionUID = -1634636526424374523L;
	private static final String MESSAGE = "Provided filename cannot be processed";
	
	/**
     * Constructor that create an exception with a predefined message.
     */
	public UnprocessableFileNameException () {
		super(MESSAGE);
	}

}
