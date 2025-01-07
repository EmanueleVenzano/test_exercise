package cern.test.code.exercise.second;

/**
 * Class that initialize a new spreadsheet given its dimensions.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class Office {

	/**
	 * Method to create a spreadsheet given its dimensions
	 *
	 * @param rows number of rows of the spreadsheet
	 * @param columns number of columns of the spreadsheet
	 * @return newly created spreadsheet 
	 */
	public static SpreadsheetImpl newSpreadsheet(int rows, int columns) {
		return new SpreadsheetImpl(rows, columns);
	}
}
