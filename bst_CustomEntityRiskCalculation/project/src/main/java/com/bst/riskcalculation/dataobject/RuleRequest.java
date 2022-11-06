package com.bst.riskcalculation.dataobject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RuleRequest {

	private String type;

	private java.util.Map<String, String> data;

	public RuleRequest() {
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public java.util.Map<String, String> getData() {
		return data;
	}

	public void setData(
			java.util.Map<String, String> data) {
		this.data = data;
	}

	public RuleRequest(String type,
                       java.util.Map<String, String> data) {
		this.type = type;
		this.data = data;
	}

	public String getValue(String key) {
		return data.get(key);
	}

	public Double getDoubleValue(String key) {
	    String value = getValue(key);
	    if (value != null)
		    return Double.valueOf(getValue(key));
		else return null;
	}

    public java.util.Date getDateValue(String key) throws ParseException {
	    String value = getValue(key);
	    if (value != null || !value.equals(""))
	        return new SimpleDateFormat("dd-MM-yyyy").parse(value);
	    else return null;
	}
	
	public List<String> getListValue(String key) {
	    List<String> list = new ArrayList<String>();
	    String value = getValue(key);
	    if (value != null) {
	       list = Arrays.asList(value.split(","));
	    }
	    return list;
	}

	public RuleRequest(String type) {
		this.type = type;
	}
	
	public List<String> keys() {
		List<String> keys = new ArrayList<String>();
		if (this.data != null) keys.addAll(this.data.keySet());
		return keys;
	}
}