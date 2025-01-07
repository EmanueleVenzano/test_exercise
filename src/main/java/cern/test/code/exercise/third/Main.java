package cern.test.code.exercise.third;

import java.io.IOException;
import java.util.Set;

import cern.test.code.exercise.third.exception.DeserializationFailedException;
import cern.test.code.exercise.third.exception.UnprocessableFileNameException;
import cern.test.code.exercise.third.model.Pkg;
import cern.test.code.exercise.third.utils.FileReader;
import static cern.test.code.exercise.third.utils.TreePrinter.represent;

public class Main {
	public static void main(String[] args) {
			try {
				Set<Pkg> read = new PackageDependencyReader(new FileReader()).readPackageDependencies("C:\\Users\\emanu\\Documents\\Projects\\Code\\test_exercise\\src\\main\\resources\\Dependencies.json");
				System.out.println(represent(read));
			} catch (UnprocessableFileNameException e) {
				System.err.println(String.format("Invalid filename. %s", e.getMessage()));
			} catch (DeserializationFailedException e) {
				System.err.println(String.format("Cannot deserialize. %s", e.getMessage()));
			} catch (IOException e) {
				System.err.println(String.format("IOException. %s", e.getMessage()));
			}	
	}
	
}
