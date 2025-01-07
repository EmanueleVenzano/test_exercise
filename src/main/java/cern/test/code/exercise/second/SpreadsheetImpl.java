package cern.test.code.exercise.second;

import cern.test.code.exercise.second.utils.ValueType;

public class SpreadsheetImpl {
	private final Cell[][] sheet;
	
	public SpreadsheetImpl (int rows, int columns) {
		this.sheet = new Cell[rows][columns];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				this.sheet[r][c] = new Cell("");
			}
		}
	}

	public Object get(int row, int column) throws IndexOutOfBoundsException {
		if (row >= sheet.length)
			throw new IndexOutOfBoundsException();
		if (column >= sheet[0].length)
			throw new IndexOutOfBoundsException();
		return this.sheet[row][column].getValue();
	}

	public void put(int row, int column, Object value) throws IndexOutOfBoundsException {
		if (row >= sheet.length)
			throw new IndexOutOfBoundsException();
		if (column >= sheet[0].length)
			throw new IndexOutOfBoundsException();
		this.sheet[row][column].setValue(value);;
	}
	
	public ValueType getValueType(int row, int column) {
		return this.sheet[row][column].getType();
	}
}
  