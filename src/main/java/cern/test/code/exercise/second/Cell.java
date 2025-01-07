package cern.test.code.exercise.second;

import cern.test.code.exercise.second.utils.ValueType;
import cern.test.code.exercise.second.utils.ValueTypeRecognizer;

/**
 * Defines the value of a cell that is made by the value itself and its type
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class Cell {
	private Object value;
	private ValueType type;
	
	/**
	 * Constructor that applies same logic as setter
	 *
	 * @param value to store in the cell
	 */
	public Cell(Object value) {
		setValue(value);
	}
	
	/**
	 * Method to read cell value.
	 *
	 * @return the value stored in the cell
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Method to read cell value.
	 *
	 * @return the type of the value stored in the cell
	 */
	public ValueType getType () {
		return type;
	}
	
	/**
	 * Method to set the cell value. Its value is automatically recognized by the value and integers 
	 * are trimmed to avoid blank spaces
	 *
	 * @param value the object to store in the cell
	 */
	public void setValue(Object value) {
		this.type = ValueTypeRecognizer.fromValue(value);		
		if (ValueType.INTEGER.equals(type) && value instanceof String valueString) {
			this.value = valueString.trim();
		}else {
			this.value = value;			
		}
	}

}
