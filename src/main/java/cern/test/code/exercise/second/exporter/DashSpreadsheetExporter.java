package cern.test.code.exercise.second.exporter;

import java.util.List;
import java.util.stream.Collectors;

import cern.test.code.exercise.second.SpreadsheetImpl;

/**
 * Exporter class that separate cell values with -.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class DashSpreadsheetExporter {
	private static final String DELIMITER = "-";
	private final SpreadsheetImpl sheet;
	
	public DashSpreadsheetExporter(SpreadsheetImpl sheet) {
		this.sheet = sheet;
	}

	/**
     * Export spreadsheet data as %d,%d#%s where the first two integers are the number of rows and columns and
     * the string is the spreadsheet data separated by '-'
     * 
     * @return the a string with the sheet formatted as defined
     */
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
