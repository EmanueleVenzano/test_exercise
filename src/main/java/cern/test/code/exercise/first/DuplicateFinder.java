package cern.test.code.exercise.first;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cern.test.code.exercise.first.exception.UnprocessableDuplicateFinderException;


/**
 * Represents a utility class to detect duplicate objects.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class DuplicateFinder {
	
	/**
     * Returns returns a list that contains only those that are duplicated, in the order that
     *  they appeared for the first time in the original list.
     * 
     * @param input the object list to analyze
     * @return the list of objects that occur more than once
     * @throws UnprocessableDuplicateFinderException if the input is null
     */
	public List<Object> analyze(List<Object> input) throws UnprocessableDuplicateFinderException{
		if (input == null)
			throw new UnprocessableDuplicateFinderException();
		
		Set<Object> uniqueValues = new LinkedHashSet<>(input);
		
		List<Object> result = new ArrayList<>();

		uniqueValues.stream().forEach(v -> {
			if (Collections.frequency(input, v) > 1) {
				result.add(v);
			}
		});
		
		return result;
	}
}