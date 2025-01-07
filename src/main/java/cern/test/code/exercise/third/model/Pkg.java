package cern.test.code.exercise.third.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import cern.test.code.exercise.third.exception.CircularDependencyFatalException;

/**
 * Represents a package and its dependencies. It is used to reconstruct the dependency hierarchy.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class Pkg {
	private final String name;
	private Set<Pkg> dependencies;
	
	/**
     * Constructor that converts the package name into Pkg class. Dependencies are 
     * initialized as empty and further set with ad hoc method
     *
     * @param name is the package name
     */
	public Pkg(final String name) {
		this.name = name;
		this.dependencies = new HashSet<>();
	}

	/**
     * Constructor that creates an already completed Pkg both with name and dependencies
     *
     * @param name is the package name
     * @param dependencies are already created at construction time
     */
	public Pkg(final String name, Set<Pkg> dependencies) {
		this.name = name;
		this.dependencies = dependencies;
	}

	/**
     * Setter that update package dependencies. Validation on circular dependencies is done as well
     *
     * @param dependencies of the current package
     * @throws CircularDependencyFatalException when circular dependency is detected
     */
	public void setDependencies(Set<Pkg> dependencies) {
		Set<String> strings = dependencies
				.stream()
				.map(Pkg::getAllDependencies)
				.flatMap(Set::stream)
				.collect(Collectors.toSet());
		if (strings.contains(this.name)) {
			throw new CircularDependencyFatalException(this.name);
		}
		this.dependencies = dependencies;
	}
	
	/**
     * Getter that reads package name
     *
     * @return package name
     */
	public String getName() {
		return name;
	}
	
	/**
     * Getter that reads package dependencies
     *
     * @return dependencies of the current package
     */
	public Set<Pkg> getDependencies() {
		return dependencies;
	}
	
	/**
     * Getter that reads recursively all package dependencies 
     *
     * @return all the names of all the dependencies
     */
	public Set<String> getAllDependencies (){
		Set<String> strings = this.dependencies
				.stream()
				.map(Pkg::getAllDependencies)
				.flatMap(Set::stream)
				.collect(Collectors.toSet());
		strings.add(this.name);
		return strings;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dependencies, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pkg other = (Pkg) obj;
		return Objects.equals(dependencies, other.dependencies) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Pkg [name=" + name + ", dependencies=" + dependencies + "]";
	}	
}
