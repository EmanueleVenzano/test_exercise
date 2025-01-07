package cern.test.code.exercise.third.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import cern.test.code.exercise.third.exception.CircularDependencyFatalException;

public class Pkg {
	private final String name;
	private Set<Pkg> dependencies;
	
	public Pkg(final String name) {
		this.name = name;
		this.dependencies = new HashSet<>();
	}

	public Pkg(final String name, Set<Pkg> dependencies) {
		this.name = name;
		this.dependencies = dependencies;
	}

	public void setDependencies(Set<Pkg> dependencies) {
		Set<String> strings = dependencies.stream().map(Pkg::getAllDependencies).flatMap(Set::stream).collect(Collectors.toSet());
		if (strings.contains(this.name)) {
			throw new CircularDependencyFatalException();
		}
		this.dependencies = dependencies;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<Pkg> getDependencies() {
		return dependencies;
	}
	
	public Set<String> getAllDependencies (){
		Set<String> strings = this.dependencies.stream().map(Pkg::getAllDependencies).flatMap(Set::stream).collect(Collectors.toSet());
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
