package cern.test.code.exercise.third.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import cern.test.code.exercise.third.model.Pkg;

public class TreePrinter {
	public static String represent(Set<Pkg> level) {
		Set<String> treeRoots = level.stream().map(Pkg::getName).collect(Collectors.toSet());
		Set<String> validRoots = new HashSet<>();
		for (String potentialRoot: treeRoots) {
			if (!isContained(potentialRoot, level.stream().filter(p -> !p.getName().equals(potentialRoot)).collect(Collectors.toSet()))) {
				validRoots.add(potentialRoot);
			}
		}
		return printTree(level.stream().filter(p -> validRoots.contains(p.getName())).collect(Collectors.toSet()), "");
	}
	
	private static boolean isContained(String pkgName, Set<Pkg> otherPkgs) {
		for (Pkg pkg: otherPkgs) {
			if (pkgName.equals(pkg.getName()) || isContained(pkgName, pkg.getDependencies())) {
				return true;
			}		
		}
		return false;
	}
	
	private static String printTree(Set<Pkg> level, String prefix) {
        StringBuffer sb = new StringBuffer();
		Iterator<Pkg> iterator = level.iterator();
		while (iterator.hasNext()) {
			Pkg pkg = iterator.next();
			if (iterator.hasNext()) {
				sb.append(String.format("%s├─%s\n", prefix, pkg.getName()));
			}else {
				sb.append(String.format("%s└─%s\n", prefix, pkg.getName()));
			}
			sb.append(printTree(pkg.getDependencies(), String.format("%s%s%s", prefix, prefix.equals("")?" ":"│", "   ")));
		}
		return sb.toString();
	}	

}
