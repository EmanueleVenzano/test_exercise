package cern.test.code.exercise.third.exception;

public class DeserializationFailedException extends RuntimeException{
	private static final long serialVersionUID = -1634636526424371523L;
	private static final String MESSAGE = "Provided content cannot be correctly deserialized";
	
	public DeserializationFailedException (Throwable e) {
		super(MESSAGE, e);
	}

}
