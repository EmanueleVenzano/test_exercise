package cern.test.code.exercise.third;

import java.io.IOException;
import java.util.Set;

import cern.test.code.exercise.third.exception.DeserializationFailedException;
import cern.test.code.exercise.third.exception.FileNotFoundException;
import cern.test.code.exercise.third.exception.UnprocessableFileNameException;
import cern.test.code.exercise.third.model.Pkg;
import cern.test.code.exercise.third.utils.FileReader;
import static cern.test.code.exercise.third.utils.TreePrinter.represent;

/**
 * Main class to read data from file and print the tree hierarchy. 
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class Main {
	/**
     * Main method to show PackageDependencyReader usage. The method require one argument which is the path to the json file
     *
     * @param filePath to the json file
     */
	public static void main(String[] args) {
			try {
				Set<Pkg> read = new PackageDependencyReader(new FileReader()).readPackageDependencies(args[0]);
				System.out.println(represent(read));
			} catch (UnprocessableFileNameException e) {
				System.err.println(String.format("Invalid filename. %s", e.getMessage()));
			} catch (DeserializationFailedException e) {
				System.err.println(String.format("Cannot deserialize. %s", e.getMessage()));
			} catch (FileNotFoundException e) {
				System.err.println(String.format("File not found. %s", e.getMessage()));
			} catch (IOException e) {
				System.err.println(String.format("IOException. %s", e.getMessage()));
			}	
	}
	
}
