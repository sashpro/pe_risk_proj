package com.bst.riskcalculation.util;

import com.bst.riskcalculation.dataobject.RuleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Operations {
    public static Double calculate(String op, List<Double> inputs) {
        if (inputs == null) return null;
        if (op.equalsIgnoreCase("sum")) {
            Double sum = inputs.stream().mapToDouble(i -> i).sum();
            return sum;
        }
        return null;
    }

    public static boolean isValidAnswer(String[] answers, String givenAnswer) {
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].equals(givenAnswer)) {
                return true;
            }
        }
        return false;
    }

    public static Double ruleResponseAggregation(String aggregationType, RuleResponse ruleResponse, String fieldName) {
        return ruleResponseAggregation(aggregationType, ruleResponse, null, null, fieldName);
    }

    public static Double ruleResponseAggregation(String aggregationType, RuleResponse ruleResponse, String categoryType, String fieldName) {
        return ruleResponseAggregation(aggregationType, ruleResponse, null, categoryType, fieldName);
    }

    public static Double ruleResponseAggregation(String aggregationType, RuleResponse ruleResponse, Double minValue, String fieldName) {
        return ruleResponseAggregation(aggregationType, ruleResponse, minValue, null, fieldName);
    }

    public static Double ruleResponseAggregation(String aggregationType, RuleResponse ruleResponse, Double minValue, String categoryType, String fieldName) {
        List<Double> riskScores = new ArrayList<Double>();
        Double result = 0.0;
        System.out.println("ruleResponse => " + ruleResponse);
        if (ruleResponse != null && ruleResponse.getData() != null) {
            List<Map<String, Object>> riskCategories = (List<Map<String, Object>>) ruleResponse.getData().get("RiskCategories");
            for (Map<String, Object> riskCategory : riskCategories) {
                String categoryName = (String) riskCategory.get("CategoryName");
                if (categoryType != null && !categoryName.equals(categoryType)) continue;
                if (categoryType != null && !categoryName.equalsIgnoreCase("aggregated") &&
                        riskCategory.get("RiskFactors") != null) {
                    List<Map<String, Object>> riskFactors = (List<Map<String, Object>>) riskCategory.get("RiskFactors");
                    for (Map<String, Object> riskFactor : riskFactors) {
                        Double riskScore = Double.parseDouble(riskFactor.get(fieldName).toString());
                        if (minValue != null) {
                            if (riskScore > minValue) riskScores.add(riskScore);
                        } else {
                            riskScores.add(riskScore);
                        }
                    }
                } else if(categoryType == null) {
                    System.out.println("riskCategory => " + riskCategory);
                    Double riskScore = Double.parseDouble(riskCategory.get(fieldName).toString());
                    if (minValue != null) {
                        if (riskScore > minValue) riskScores.add(riskScore);
                    } else {
                        riskScores.add(riskScore);
                    }
                }
            }
        }
        if (aggregationType.equalsIgnoreCase("avg")) {
            for (Double score : riskScores) result += score;
            result /= riskScores.size();
        } else if (aggregationType.equalsIgnoreCase("sum")) {
            for (Double score : riskScores) result += score;
        } else if (aggregationType.equalsIgnoreCase("max")) {
            for (Double score : riskScores) if (score > result) result = score;
        }
        return result;
    }
    public static Boolean isRecordAlreadyExists(RuleResponse ruleResponse, String categoryName, Map<String, Object> riskRecord) {
        if(ruleResponse == null) return false;
        if (ruleResponse.getData() == null) return false;
        Object riskCategoriesObj = ruleResponse.getData().get("RiskCategories");
        if (riskCategoriesObj != null) {
            List<Object> riskCategories = (List<Object>) riskCategoriesObj;
            for (Object riskCategory : riskCategories){
                if (riskCategory == null) continue;
                Map<String, Object> category = (Map<String, Object>)riskCategory;
                if (!category.get("CategoryName").toString().equals(categoryName)) continue;
                List<Object> riskFactorsObjs = (List<Object>)(category.get("RiskFactors"));
                if (riskFactorsObjs == null) continue;
                for (Object riskFactor : riskFactorsObjs) {
                    if (riskFactor == null) continue;
                    Map<String, String> risk = (Map<String, String>) riskFactor;
                    if (risk.get("Risk name").equals(riskRecord.get("Risk name")) &&
                            Double.parseDouble(risk.get("Risk value").toString()) >= Double.parseDouble(riskRecord.get("Risk value").toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}