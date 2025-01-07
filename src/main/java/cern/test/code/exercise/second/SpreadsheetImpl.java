package cern.test.code.exercise.second;

import java.util.ArrayList;
import java.util.List;

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
		if (row >= getRowSize())
			throw new IndexOutOfBoundsException();
		if (column >= getColumnSize())
			throw new IndexOutOfBoundsException();
		return this.sheet[row][column].getValue();
	}

	public void put(int row, int column, Object value) throws IndexOutOfBoundsException {
		if (row >= getRowSize())
			throw new IndexOutOfBoundsException();
		if (column >= getColumnSize())
			throw new IndexOutOfBoundsException();
		this.sheet[row][column].setValue(value);;
	}
	
	public ValueType getValueType(int row, int column) {
		return this.sheet[row][column].getType();
	}
	
	public int getRowSize() {
		return sheet.length;
	}
	
	public int getColumnSize() {
		return sheet[0].length;
	}
	
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
}
  