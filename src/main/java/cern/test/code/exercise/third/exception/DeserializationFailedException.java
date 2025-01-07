package cern.test.code.exercise.third.exception;

/**
 * Exception that occurs when file content cannot be deserialized as json.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class DeserializationFailedException extends RuntimeException{
	private static final long serialVersionUID = -1635636526424371523L;
	private static final String MESSAGE = "Provided content cannot be correctly deserialized";
	
	/**
     * Constructor that create an exception with a predefined message.
     */
	public DeserializationFailedException (Throwable e) {
		super(MESSAGE, e);
	}

}
