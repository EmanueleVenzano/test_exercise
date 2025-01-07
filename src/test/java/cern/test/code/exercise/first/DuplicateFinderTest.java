package cern.test.code.exercise.first;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import cern.test.code.exercise.first.exception.UnprocessableDuplicateFinderException;

/**
 * Test to verify DuplicateFinder behavior
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
class DuplicateFinderTest {

	private final DuplicateFinder duplicateFinder;
	
	public DuplicateFinderTest() {
		this.duplicateFinder = new DuplicateFinder();
	}
	
	@ParameterizedTest
	@NullSource
	void isNull_ShouldThrowUnprocessableInputException(List<Object> input) {
		Exception exception = assertThrows(UnprocessableDuplicateFinderException.class, 
				() -> duplicateFinder.analyze(input));
		assertEquals("Cannot find duplicate because provided input is unprocessable", exception.getMessage());
	}
	
	@ParameterizedTest
	@MethodSource
	void testValidInput(TestData testData) {
		List<Object> actualOutput = duplicateFinder.analyze(testData.getInput());
		List<Object> expectedOutput = testData.getOutput();
		
		assertEquals(expectedOutput.size(), actualOutput.size());
		
		for (int iString = 0; iString<actualOutput.size(); iString++) {
			assertEquals(expectedOutput.get(iString), actualOutput.get(iString));
		}		
	}
	
	private static Stream<TestData> testValidInput(){
		return Stream.of(
		        // Test with an empty list (edge case)
		        new TestData(List.of(), List.of()),

		        // Test with the provided list 
		        new TestData(List.of("b", "a", "c", "c", "e", "a", "c", "d", "c", "d"), List.of("a", "c", "d")),

		        // Test with all elements duplicated in reverse order
		        new TestData(List.of("a", "b", "c", "c", "b", "a"), List.of("a", "b", "c")),

		        // Test with no duplicates in the list
		        new TestData(List.of("a", "b", "c"), List.of()),

		        // Test with all elements being the same
		        new TestData(List.of("a", "a", "a", "a"), List.of("a")),

		        // Test with a single duplicate
		        new TestData(List.of("a", "b", "a", "c", "d"), List.of("a")),

		        // Test with a duplicate at the end
		        new TestData(List.of("a", "b", "c", "d", "d"), List.of("d")),

		        // Test with a large list with complex patterns of duplicates
		        new TestData(List.of("x", "y", "x", "z", "y", "w", "z", "x"), List.of("x", "y", "z")),

		        // Test with only one element in the list (edge case)
		        new TestData(List.of("a"), List.of()),

		        // Test with two different elements, no duplicates
		        new TestData(List.of("a", "b", new TestClass("c"), new TestClass("d")), List.of()),

		        // Test with two identical elements
		        new TestData(List.of("a", "a"), List.of("a")),

		        // Test with non-string elements (integers)
		        new TestData(List.of(1, 2, 3, 1, 4, 2, 5), List.of(1, 2)),

		        // Test with mixed types (edge case)
		        new TestData(List.of("a", 1, "b", 1, "a"), List.of("a", 1)),

		        // Test with mixed types (edge case)
		        new TestData(List.of("a", false, true, 1, false, "b", "a", "2", 2, 1), List.of("a", false, 1)),

		        // Test with mixed types (edge case)
		        new TestData(List.of("a", 1, "b", new TestClass("c"), "a", new TestClass("c"), 2, 1), List.of("a", 1, new TestClass("c"))),

		        
		        // Test with an already sorted list
		        new TestData(List.of("a", "a", "b", "b", "c", "c"), List.of("a", "b", "c")),

		        // Test with case-sensitive duplicates
		        new TestData(List.of("A", "a", "A", "b", "B", "b"), List.of("A", "b")),

		        // Test with duplicates appearing non-consecutively
		        new TestData(List.of("a", "b", "a", "c", "d", "b", "d"), List.of("a", "b", "d")),

		        // Test with special characters
		        new TestData(List.of("!", "@", "#", "@", "!", "$"), List.of("!", "@")),

		        // Test with a palindrome-like sequence
		        new TestData(List.of("a", "b", "c", "b", "a"), List.of("a", "b"))
		);
	}
	
	/**
	 * Class that encapsulate test input and output to verify expected behavior
	 * 
	 * @author Emanuele Venzano
	 * @version 1.0
	 * @since 2025-01-07
	 */
	private static final class TestData{
		private List<Object> input;
		private List<Object> output;
		
		public TestData(List<Object> input, List<Object> output) {
			this.input = input;
			this.output = output;
		}
		
		public List<Object> getInput(){
			return input;
		}
		
		public List<Object> getOutput(){
			return output;
		}

		@Override
		public String toString() {
			return "TestData [input=" + input + ", output=" + output + "]";
		}
		
		
	}
	
	/**
	 * Test class used to verify behavior on class objects 
	 * 
	 * @author Emanuele Venzano
	 * @version 1.0
	 * @since 2025-01-07
	 */
	private static final class TestClass{
		private String value;
		
		public TestClass(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return "TestClass [value=" + value+"]";
		}		
		
		@Override
		public int hashCode() {
			return Objects.hash(value);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TestClass other = (TestClass) obj;
			return Objects.equals(value, other.value);
		}
	}
}
