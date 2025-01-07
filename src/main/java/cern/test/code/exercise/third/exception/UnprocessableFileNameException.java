package cern.test.code.exercise.third.exception;

public class UnprocessableFileNameException extends RuntimeException{
	private static final long serialVersionUID = -1634636526424371523L;
	private static final String MESSAGE = "Provided filename cannot be processed";
	
	public UnprocessableFileNameException () {
		super(MESSAGE);
	}

}
