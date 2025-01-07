package cern.test.code.exercise.first.exception;

/**
 * Represents the exception thrown when DuplicateFinder analysis cannot be processed.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class UnprocessableDuplicateFinderException extends RuntimeException {
	private static final long serialVersionUID = -1010058523160751971L;
	
	private static final String MESSAGE = "Cannot find duplicate because provided input is unprocessable";
	
	public UnprocessableDuplicateFinderException() {
		super(MESSAGE);
	}

	public UnprocessableDuplicateFinderException(Throwable e) {
		super(MESSAGE, e);
	}
}
