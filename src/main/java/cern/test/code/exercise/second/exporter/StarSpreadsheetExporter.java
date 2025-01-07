package cern.test.code.exercise.second.exporter;

import java.util.List;
import java.util.stream.Collectors;

import cern.test.code.exercise.second.SpreadsheetImpl;

public class StarSpreadsheetExporter {
	private static final String DELIMITER = "*";
	private final SpreadsheetImpl sheet;
	
	public StarSpreadsheetExporter(SpreadsheetImpl sheet) {
		this.sheet = sheet;
	}

	public String export() {
		return String.format("%d,%d#%s", sheet.getRowSize(), sheet.getColumnSize(), formatSpreadsheet());
	}
	
	private String formatSpreadsheet() {
		String result = sheet.export().stream()
				.flatMap(List::stream)                 
				.collect(Collectors.joining(DELIMITER)); 
		result += DELIMITER;
		return result;

	}
}
