package com.bst.riskcalculation.dataobject;

public class GenericData{

	private String type;

	private java.util.Map<String, String> dataValues;

	public GenericData() {
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public java.util.Map<String, String> getDataValues() {
		return dataValues;
	}

	public void setDataValues(
			java.util.Map<String, String> dataValues) {
		this.dataValues = dataValues;
	}

	public GenericData(String type,
                       java.util.Map<String, String> dataValues) {
		this.type = type;
		this.dataValues = dataValues;
	}

	public String getValue(String key) {
		return dataValues.get(key);
	}

	public Double getDoubleValue(String key) {
		return Double.valueOf(getValue(key));
	}

	public GenericData(String type) {
		this.type = type;
	}
}