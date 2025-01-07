package cern.test.code.exercise.third.exception;

public class FileNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -1634636526424371523L;
	private static final String MESSAGE = "Cannot find specified file %s";
	
	public FileNotFoundException (String filename, Throwable e) {
		super(String.format(MESSAGE, filename), e);
	}

}
