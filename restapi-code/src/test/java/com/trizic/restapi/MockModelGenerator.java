package com.trizic.restapi;

import java.util.ArrayList;
import java.util.List;
import com.trizic.restapi.model.AssetAllocation;
import com.trizic.restapi.model.Model;

/**
 * serve for unit test
 * @author Yuan
 *
 */
public class MockModelGenerator {
	
	private Model m1;
	
	public MockModelGenerator(){
		m1=generateModel();
	}
	
	private Model generateModel(){
		m1= new Model();
		m1.setCashHoldingPercentage(20);
		m1.setDescription("mock model with tech stocks");
		m1.setDriftPercentage(10);
		m1.setModelType("TAXABLE");
		m1.setName("model test");
		m1.setRebalanceFrequency("QUARTERLY");
		
		List <AssetAllocation> assetAllocations1 = new ArrayList <AssetAllocation>();
		AssetAllocation asset1 = new AssetAllocation();
		asset1.setSymbol("HP");
		asset1.setPercentage(30);		
		AssetAllocation asset2 = new AssetAllocation();
		asset2.setSymbol("IBM");
		asset2.setPercentage(50);		
		AssetAllocation asset3 = new AssetAllocation();
		asset3.setSymbol("FB");
		asset3.setPercentage(20);		
		assetAllocations1.add(asset1);
		assetAllocations1.add(asset2);
		assetAllocations1.add(asset3);
		m1.setAssetAllocations(assetAllocations1);
		return m1;
	}
	
	public Model getMockModel_correct() {
		m1.setName("Model example");
		m1.getAssetAllocations().get(0).setPercentage(30);
		m1.setModelType("QUALIFIED");
		m1.getAssetAllocations().get(0).setSymbol("HP");
		return m1;
	}
	
	public Model getMockModel_percentNot100() {
		m1.setName("Model example");
		m1.getAssetAllocations().get(0).setPercentage(120);  // make it invalid for test case
		m1.setModelType("QUALIFIED");
		m1.getAssetAllocations().get(0).setSymbol("HP");
		return m1;
	}
	
	public Model getMockModel_invalidEnumType() {
		m1.setName("Model example");
		m1.getAssetAllocations().get(0).setPercentage(30);  
		m1.setModelType("dog");   // make it invalid for test case
		m1.getAssetAllocations().get(0).setSymbol("HP");
		return m1;
	}
	
	public Model getMockModel_missingName() {
		m1.setName(null);    // make this field missing for test case        
		m1.getAssetAllocations().get(0).setPercentage(30);  
		m1.setModelType("QUALIFIED"); 
		m1.getAssetAllocations().get(0).setSymbol("HP");

		return m1;
	}
	
	public Model getMockModel_nestedObject_missingSymbol() {
		m1.setName("Model example");          
		m1.getAssetAllocations().get(0).setPercentage(30);  
		m1.setModelType("QUALIFIED"); 
		m1.getAssetAllocations().get(0).setSymbol(null);// make nested object's field missing for test case
		return m1;
	}
	
	public List <Model> genMockModels(int numOfModel){
		List <Model>models = new ArrayList <Model>();
		int index=0;
		while(numOfModel>0){
			Model model =generateModel();
			model.setName(model.getName()+index);
			models.add(model);
			index++;
			numOfModel--;
		}
		return models;
	}
}
