package cern.test.code.exercise.third;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cern.test.code.exercise.third.exception.DeserializationFailedException;
import cern.test.code.exercise.third.exception.FileNotFoundException;
import cern.test.code.exercise.third.exception.UnprocessableFileNameException;
import cern.test.code.exercise.third.model.Pkg;
import cern.test.code.exercise.third.utils.FileReader;

/**
 * Core class that returns the dependency hierarchy from the specified filePath.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class PackageDependencyReader {
	private final FileReader fileReader;
	private final ObjectMapper objectMapper;
	private final Comparator<Pkg> pkgComparator = Comparator.comparing(Pkg::getName);

	
	/**
     * Constructor that allows to inject FileReader.
     *
     * @param fileReader that converts filePath into File
     */
	public PackageDependencyReader(final FileReader fileReader) {
		this.objectMapper = new ObjectMapper();
		this.fileReader = fileReader;
	}
	
	/**
     * Reads a JSON file containing package dependencies and returns it as a set of tree hierarchies.
     *
     * @param filePath the path to the JSON file
     * @return a set of all the packages present in the file. For each of them, name and all dependencies are present
     * @throws UnprocessableFileNameException if the file is null or empty
     * @throws DeserializationFailedException if the file cannot be correctly deserialized as json
     * @throws FileNotFoundException if the file cannot be found in the file system
     * @throws IOException if other input-output exception occurs
     */
    public Set<Pkg> readPackageDependencies(String filePath) throws UnprocessableFileNameException, DeserializationFailedException, FileNotFoundException, IOException {
    	if (filePath == null || filePath.isEmpty())
    		throw new UnprocessableFileNameException();

        try {
        	Map<String, List<String>> jsonDeserialization = objectMapper.readValue(fileReader.readFileFromFileSystem(filePath), new TypeReference<>() {});

            Set<Pkg> hierarchy = jsonDeserialization.keySet().stream().map(Pkg::new).collect(Collectors.toCollection(() -> new TreeSet<>(pkgComparator)));
        	Map<String, Pkg> inventory = hierarchy.stream().collect(Collectors.toMap(Pkg::getName, Function.identity()));

        	for (Entry<String, List<String>> entry: jsonDeserialization.entrySet()) {
        		Set<Pkg> dependencies = new TreeSet<>(pkgComparator);
        		for (String dependency: entry.getValue()) {
        			if (!inventory.containsKey(dependency)) {
        				inventory.put(dependency, new Pkg(dependency));
        				hierarchy.add(inventory.get(dependency));
        			}
    				dependencies.add(inventory.get(dependency));
        		}
        		inventory.get(entry.getKey()).setDependencies(dependencies);        		
        	}
        	
        	return hierarchy;
        }catch (JsonParseException e) {
        	throw new DeserializationFailedException(e);
		} catch (IllegalArgumentException e) {
			throw new FileNotFoundException(filePath, e);
		}
    }
}
