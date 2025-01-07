package cern.test.code.exercise.second;

import java.util.ArrayList;
import java.util.List;

import cern.test.code.exercise.second.utils.ValueType;

/**
 * Exporter class that separate cell values with -.
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class SpreadsheetImpl {
	private final Cell[][] sheet;
	
	/**
	 * Constructor that creates the cell bi-dimensional array and initialize all cells with empty string.
	 *
	 * @param rows represents the number of rows
	 * @param columns represents the number of columns
	 * @throws IndexOutOfBoundsException when specified dimensions are unprocessable
	 */
	public SpreadsheetImpl (int rows, int columns) throws IndexOutOfBoundsException{
		if (rows <= 0 || columns <= 0) {
			throw new IndexOutOfBoundsException();
		}
		
		this.sheet = new Cell[rows][columns];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				this.sheet[r][c] = new Cell("");
			}
		}
	}

	/**
	 * Get method that reads the specified cell value.
	 *
	 * @param row represents the row index to read
	 * @param column represents the row index to read
	 * @return value stored in the specified cell
	 * @throws IndexOutOfBoundsException when specified dimensions are unprocessable
	 */
	public Object get(int row, int column) throws IndexOutOfBoundsException {
		validateCellIndexes(row, column);
		return this.sheet[row][column].getValue();
	}

	/**
	 * Put method that set the specified cell value.
	 *
	 * @param row represents the row index to read
	 * @param column represents the row index to read
	 * @param value to store in the specified cell
	 * @throws IndexOutOfBoundsException when specified dimensions are unprocessable
	 */
	public void put(int row, int column, Object value) throws IndexOutOfBoundsException {
		validateCellIndexes(row, column);
		this.sheet[row][column].setValue(value);;
	}
	
	/**
	 * Get method that reads the specified cell value.
	 *
	 * @param row represents the row index to read
	 * @param column represents the row index to read
	 * @return type of the value stored in the specified cell
	 * @throws IndexOutOfBoundsException when specified dimensions are unprocessable (even 
	 * if not strictly needed by TDD since it was defined in tests)
	 */
	public ValueType getValueType(int row, int column) throws IndexOutOfBoundsException {
		validateCellIndexes(row, column);
		return this.sheet[row][column].getType();
	}
	
	/**
	 * Method to get number of the rows in the spreadsheet
	 *
	 * @return number of rows
	 */
	public int getRowSize() {
		return sheet.length;
	}
	
	/**
	 * Method to get number of the columns in the spreadsheet
	 *
	 * @return number of columns
	 */
	public int getColumnSize() {
		return sheet[0].length;
	}
	
	/**
	 * Method to export spreadsheet data as string to be written on a text file.
	 *
	 * @return spreadsheet string values organized as list of rows, each of them containing 
	 * one value for each column
	 */
	public List<List<String>> export() {
        List<List<String>> data = new ArrayList<>(getRowSize());

        for (Cell[] row : sheet) {
            List<String> rowData = new ArrayList<>(getColumnSize());
            for (Cell cell : row) {
                rowData.add(cell.getValue().toString());
            }
            data.add(rowData);
        }

        return data;
    }
	
	/**
	 * Common method to validate requested indexes with respect to spreadsheet dimension.
	 * @param row represents the row index to read
	 * @param column represents the row index to read
	 * 	 * @throws IndexOutOfBoundsException when specified dimensions are unprocessable
	 */
	private void validateCellIndexes(int row, int column) throws IndexOutOfBoundsException{
		if (row >= getRowSize() || row < 0 || column >= getColumnSize() || column < 0)
			throw new IndexOutOfBoundsException();		
	}
}
  