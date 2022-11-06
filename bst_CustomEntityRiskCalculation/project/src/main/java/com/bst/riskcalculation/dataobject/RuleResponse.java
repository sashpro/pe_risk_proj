package com.bst.riskcalculation.dataobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleResponse {

	Map<String, Object> data;
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void addData(String key, Object value) {
		if (this.data == null) this.data = new HashMap<>();
		List<Map<String, Object>> riskCategories;
		if (this.data.get("RiskCategories") == null)
			riskCategories = new ArrayList<Map<String, Object>>();
		else
			riskCategories = (List<Map<String, Object>>) this.data.get("RiskCategories");
		Map<String, Object> existingRiskCategory = null;
		for (Map<String, Object> riskCategory : riskCategories) {
			if (riskCategory.get("CategoryName").equals(key)) {
				existingRiskCategory = riskCategory;
				break;
			}
		}
		if (existingRiskCategory != null) {
			List<Object> riskFactors = (List<Object>) existingRiskCategory.get("RiskFactors");
			riskFactors.add(value);
		}
		else{
			Map<String, Object> newRiskCategory = new HashMap<String, Object>();
			newRiskCategory.put("CategoryName", key);
			List<Object> newRiskFactors = new ArrayList<>();
			newRiskFactors.add(value);
			newRiskCategory.put("RiskFactors", newRiskFactors);
			riskCategories.add(newRiskCategory);
		}
		this.data.put("RiskCategories", riskCategories);
	}

	public void addMapData(String key, Map<String,Object> mapValues) {
		if (this.data == null) this.data = new HashMap<String, Object>();
		//this.data.put(key, mapValues);
		//this.data.put("MAPVALUES", "MAPVALUES");
	}

}
