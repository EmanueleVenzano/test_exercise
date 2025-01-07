package cern.test.code.exercise.third.exception;

public class CircularDependencyFatalException extends RuntimeException{
	private static final long serialVersionUID = -1634636526424371523L;
	private static final String MESSAGE = "Fatal error! Specified dependency tree is not a tree since a circular detection was found.";
	
	public CircularDependencyFatalException () {
		super(MESSAGE);
	}

}
