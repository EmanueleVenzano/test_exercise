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

public class PackageDependencyReader {
	private final FileReader fileReader;
	private final ObjectMapper objectMapper;
	private final Comparator<Pkg> pkgComparator = Comparator.comparing(Pkg::getName);

	
	public PackageDependencyReader(final FileReader fileReader) {
		this.objectMapper = new ObjectMapper();
		this.fileReader = fileReader;
	}
	
	/**
     * Reads a JSON file containing package dependencies and returns it as a Map.
     *
     * @param filePath the path to the JSON file
     * @return a map where the key is the package name and the value is the list of dependencies
     * @throws IOException if the file cannot be read or the content is invalid
     */
    public Set<Pkg> readPackageDependencies(String filePath) throws UnprocessableFileNameException, DeserializationFailedException, IOException {
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
