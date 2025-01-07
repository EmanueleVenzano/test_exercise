package cern.test.code.exercise.second;

import cern.test.code.exercise.second.utils.ValueType;
import cern.test.code.exercise.second.utils.ValueTypeRecognizer;

public class Cell {
	private Object value;
	private ValueType type;
	
	public Cell(Object value) {
		setValue(value);
	}
	
	public Object getValue() {
		return value;
	}
	
	public ValueType getType () {
		return type;
	}
	
	public void setValue(Object value) {
		this.type = ValueTypeRecognizer.fromValue(value);		
		if (ValueType.INTEGER.equals(type) && value instanceof String valueString) {
			this.value = valueString.trim();
		}else {
			this.value = value;			
		}
	}

}
