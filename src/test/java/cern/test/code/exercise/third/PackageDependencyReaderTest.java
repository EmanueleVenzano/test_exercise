package cern.test.code.exercise.third;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cern.test.code.exercise.third.exception.CircularDependencyFatalException;
import cern.test.code.exercise.third.exception.DeserializationFailedException;
import cern.test.code.exercise.third.exception.FileNotFoundException;
import cern.test.code.exercise.third.exception.UnprocessableFileNameException;
import cern.test.code.exercise.third.model.Pkg;
import cern.test.code.exercise.third.utils.FileReader;

/**
 * Test class to verify all meaningful scenarios.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
@ExtendWith(MockitoExtension.class) 
public class PackageDependencyReaderTest {

    @Mock
    private FileReader fileReaderMock; 

    @InjectMocks
    private PackageDependencyReader packageDependencyReader; 
    
	/**
     * Assert FileNotFoundException is thrown when file cannot be found in the filesystem.
     * 
     */
    @Test
    void notExistingFile_shouldThrowException() {
        assertThrows(FileNotFoundException.class, () -> packageDependencyReader.readPackageDependencies("C:\\Users\\user\\Documents\\files\\file.txt"));
    }
    
	/**
     * Assert DeserializationFailedException is thrown when file is found but is not a json.
     * 
     */
    @Test
    void notDesiarilizable_shouldThrowException() throws IOException {
		when(fileReaderMock.readFileFromFileSystem(anyString())).thenReturn(readFileFromFileSystem("NotDeserializable.txt"));
        assertThrows(DeserializationFailedException.class, () -> packageDependencyReader.readPackageDependencies("fictive"));
    	
    }
    
	/**
     * Assert UnprocessableFileNameException is thrown when filename is null or empty.
     * 
     */
    @ParameterizedTest
    @NullAndEmptySource
    void invalidFileName_shouldThrowException(String fileName) {
    	assertThrows(UnprocessableFileNameException.class, () -> packageDependencyReader.readPackageDependencies(fileName));
    }
    
	/**
     * Assert result is given and it is valid according to the specifications when input data are valid
     * 
     * @param testData represent input and expected output to verify behavior on different scenarios
     */
    @ParameterizedTest
    @MethodSource
    void testValidInputs(TestData testData) throws IOException {
        when(fileReaderMock.readFileFromFileSystem(anyString())).thenReturn(createTempFileWithContent(testData.getInput()));

        Set<Pkg> actualResult = packageDependencyReader.readPackageDependencies("fictive");

        assertNotNull(actualResult);      
        validatePkgTree(testData.getOutput(), actualResult);
        
    }
    
    /**
     * Provides test data to testValidInputs method. Both input and expected output are specified
     * 
     * @return testData to verify
     */
    private static Stream<TestData> testValidInputs() {
    	return Stream.of(
    	        new TestData(
    	                "{}",
    	                Set.of()),
    	        new TestData(
    	                "{\"pkg1\": []}",
    	                Set.of(new Pkg("pkg1"))),
    	        new TestData(
    	                "{\"pkg1\": [\"pkg2\", \"pkg3\"]}",
    	                Set.of(
    	                		new Pkg("pkg1", 
    	                				Set.of(new Pkg("pkg2"), new Pkg("pkg3"))),
    	                		new Pkg("pkg2"),
    	                		new Pkg("pkg3"))),
    	        new TestData(
    	                "{\"pkg1\": [\"pkg2\", \"pkg3\"], \"pkg2\": [\"pkg3\"], \"pkg3\": []}",
    	                Set.of(new Pkg("pkg1", 
    	                		Set.of(new Pkg("pkg2", 
    	                				Set.of(new Pkg("pkg3"))), new Pkg("pkg3"))),
    	                		new Pkg("pkg2", Set.of(new Pkg("pkg3"))),
    	                        new Pkg("pkg3"))),
    	        new TestData(
    	                "{\"pkg1\": [\"pkg2\"], \"pkg2\": [\"pkg3\"], \"pkg3\": [\"pkg4\"], \"pkg4\": []}",
    	                Set.of(
    	                		new Pkg("pkg1", Set.of(new Pkg("pkg2", Set.of(new Pkg("pkg3", Set.of(new Pkg("pkg4"))))))),
    	                		new Pkg("pkg2", Set.of(new Pkg("pkg3", Set.of(new Pkg("pkg4"))))),
    	                        new Pkg("pkg3", Set.of(new Pkg("pkg4"))),
    	                        new Pkg("pkg4"))),
    	        new TestData(
    	                "{\"pkg1\": [], \"pkg2\": [], \"pkg3\": []}",
    	                Set.of(
    	                		new Pkg("pkg1"),
    	                		new Pkg("pkg2"),
    	                		new Pkg("pkg3"))),
    	        new TestData(
    	                "{\"pkg1\": [\"\"]}",
    	                Set.of(
    	                		new Pkg("pkg1", Set.of(new Pkg(""))),
    	                		new Pkg(""))),
    	        new TestData(
    	        		"{\"pkg1\": [\"pkg2\", \"pkg3\", \"pkg5\"],\"pkg2\": [\"pkg3\", \"pkg4\"],\"pkg3\": [\"pkg4\"],\"pkg4\": []}",
    	        		Set.of(
    	        				new Pkg("pkg1", Set.of(new Pkg("pkg5"), new Pkg("pkg3", Set.of(new Pkg("pkg4"))), new Pkg("pkg2", Set.of(new Pkg("pkg4"), new Pkg("pkg3", Set.of(new Pkg("pkg4"))))))),
    	        				new Pkg("pkg2", Set.of(new Pkg("pkg4"), new Pkg("pkg3", Set.of(new Pkg("pkg4"))))),
    	        				new Pkg("pkg3", Set.of(new Pkg("pkg4"))),
    	        				new Pkg("pkg4"),
    	        				new Pkg("pkg5"))),
    	        new TestData(
    	                "{\"pkg1\": [\"pkg2\", \"pkg3\", \"pkg4\", \"pkg5\", \"pkg6\"]}",
    	                Set.of(
    	                		new Pkg("pkg1", Set.of(new Pkg("pkg2"),new Pkg("pkg3"),new Pkg("pkg4"),new Pkg("pkg5"),new Pkg("pkg6"))),
    	        				new Pkg("pkg2"),
    	        				new Pkg("pkg3"),
    	        				new Pkg("pkg4"),
    	        				new Pkg("pkg5"),
    	        				new Pkg("pkg6")))
    	);
    }

    /**
     * Assert CircularDependencyFatalException is thrown when circular dependency are present
     * 
     * @param testData represent input where circular dependencies are present
     */
    @ParameterizedTest
    @MethodSource
    void testCircularDependency(String testData) throws IOException {
        when(fileReaderMock.readFileFromFileSystem(anyString())).thenReturn(createTempFileWithContent(testData));
        assertThrows(CircularDependencyFatalException.class, () -> packageDependencyReader.readPackageDependencies("fictive"));
    }
    
    /**
     * Provides test data to testCircularDependency method
     * 
     * @return strings that represent scenarios with circular dependencies
     */
    private static Stream<String> testCircularDependency() {
    	return Stream.of(
    	                "{\"pkg1\": [\"pkg2\"], \"pkg2\": [\"pkg1\"]}",
    	                "{\"pkg1\": [\"pkg1\"]}",
    	                "{\"pkg1\": [\"pkg2\"], \"pkg2\": [\"pkg3\", \"pkg4\"], \"pkg4\": [\"pkg1\"]}");
    }

	/**
     * Validate behavior with the scenario present into exercise specification. This time the file is read from src/resources 
     * folder instead of from its path
     * 
     */
    @Test
    void readFileFromResourcesFolder() throws IOException {
		when(fileReaderMock.readFileFromFileSystem(anyString())).thenReturn(readFileFromFileSystem("Dependencies.json"));

        Set<Pkg> result = packageDependencyReader.readPackageDependencies("fictive");
        Set<Pkg> expected = Set.of(
        		new Pkg("pkg1", 
        				Set.of(new Pkg("pkg2", 
        						Set.of(new Pkg("pkg3"))))),
        		new Pkg("pkg2", 
        				Set.of(new Pkg("pkg3"))), 
        		new Pkg("pkg3"));

        assertNotNull(result);
        validatePkgTree(expected, result);
    }
    
	/**
     * Validate dependency tree construction logic comparing expected and actual values
     * 
     * @param expected represents the tree hierarchy defined in the test as expected output
     * @param actual represents the result from PackageDependencyReader elaboration
     */
    private void validatePkgTree(Set<Pkg> expected, Set<Pkg> actual) {
    	assertEquals(expected.size(), actual.size());
    	Map<String, Pkg> expectedPkgs = expected.stream().collect(Collectors.toMap(Pkg::getName, Function.identity()));
    	Map<String, Pkg> actualPkgs = expected.stream().collect(Collectors.toMap(Pkg::getName, Function.identity()));
    	
    	for (Entry<String, Pkg> expect: expectedPkgs.entrySet()) {
    		assertTrue(actualPkgs.containsKey(expect.getKey()));
    		validatePkgTree(expect.getValue().getDependencies(), actualPkgs.get(expect.getKey()).getDependencies());
    	}
    	
    }
    
    /**
     * Class that encapsulate test input and output to verify expected behavior
     * 
     * @author Emanuele Venzano
     * @version 1.0
     * @since 2025-01-07
     */
    private static class TestData {
    	private final String input;
    	private final Set<Pkg> output;
    	
    	public TestData (final String input, final Set<Pkg> output) {
    		this.input = input;
    		this.output = output;
    	}
    	
    	public String getInput() {
    		return input;
    	}
    	
    	public Set<Pkg> getOutput(){
    		return output;
    	}

		@Override
		public String toString() {
			return "TestData [input=" + input + ", output=" + output + "]";
		}	
    }

	/**
     * Creates a file from its string content to mock reading operation and test the behavior on multiple inputs
     * 
     * @param content represents the content that is assumed to be written in the file
     * @return a fictive file conaining the content
     */
    private File createTempFileWithContent(String content) throws IOException {
        File tempFile = Files.createTempFile("temp", ".json").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }
        return tempFile;
    }
    
    
	/**
     * Reads a file from src/resources through its filename
     * 
     * @param filename to search
     * @return a file instance of the requested file
     */
    private File readFileFromFileSystem(String filename) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        
        if (inputStream == null) {
            throw new IOException("File not found in resources: " + filename);
        }

        Path tempFilePath = Files.createTempFile("temp", ".json");
        Files.copy(inputStream, tempFilePath, StandardCopyOption.REPLACE_EXISTING);

        return tempFilePath.toFile();
    }
}
