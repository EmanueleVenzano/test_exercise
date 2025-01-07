package cern.test.code.exercise.third.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import cern.test.code.exercise.third.model.Pkg;

/**
 * Utility class to convert Pkg hierarchies into pretty sting
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class TreePrinter {

	/**
     * Method that converts all tree hierarchies into a string. To avoid redundant informations 
     * only independent trees are took
     *
     * @param level represents all the tree package hierarchies
     * @return a string that contains all hierarchy informations
     */
	public static String represent(Set<Pkg> level) {
		Set<String> treeRoots = level.stream().map(Pkg::getName).collect(Collectors.toSet());
		Set<String> validRoots = new HashSet<>();
		for (String potentialRoot: treeRoots) {
			if (!isContained(potentialRoot, level
					.stream()
					.filter(p -> !p.getName().equals(potentialRoot))
					.collect(Collectors.toSet()))) {
				validRoots.add(potentialRoot);
			}
		}
		return printTree(level
				.stream()
				.filter(p -> validRoots.contains(p.getName()))
				.collect(Collectors.toSet()), "");
	}
	
	/**
     * Method that checks whether a package is contained within another tree
     *
     * @param pkgName the name to search
     * @param otherPkgs the package trees where to search
     * @return true if pkgName is contained within otherPkgs, false otherwise
     */
	private static boolean isContained(String pkgName, Set<Pkg> otherPkgs) {
		for (Pkg pkg: otherPkgs) {
			if (pkgName.equals(pkg.getName()) || isContained(pkgName, pkg.getDependencies())) {
				return true;
			}		
		}
		return false;
	}
	
	/**
     * Method that converts independent trees into string
     *
     * @param level represents all the tree package hierarchies
     * @param prefix represents the characters to add before package name to have pretty representation
     * @return a string that contains all hierarchy informations
     */
	private static String printTree(Set<Pkg> level, String prefix) {
        StringBuffer sb = new StringBuffer();
		Iterator<Pkg> iterator = level.iterator();
		while (iterator.hasNext()) {
			Pkg pkg = iterator.next();
			String nextPrefix;
			if (iterator.hasNext()) {
				sb.append(String.format("%s├─%s\n", prefix, pkg.getName()));
				nextPrefix = "│   ";
			}else {
				sb.append(String.format("%s└─%s\n", prefix, pkg.getName()));
				nextPrefix = "    ";
			}
			sb.append(printTree(pkg.getDependencies(), prefix.concat(nextPrefix)));
		}
		return sb.toString();
	}	

}
