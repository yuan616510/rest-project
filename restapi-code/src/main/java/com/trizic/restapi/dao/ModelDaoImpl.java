package com.trizic.restapi.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trizic.restapi.model.Advisor;
import com.trizic.restapi.model.AssetAllocation;
import com.trizic.restapi.model.Model;

/**
 * Persist data in memory so far
 */

@Repository
@Transactional
public class ModelDaoImpl implements ModelDao{
	
	//store advisors
	private List <Advisor> advisors = new ArrayList<Advisor>();
	
	//store models
	private List <Model> models = new ArrayList<Model>(); 
	
	//beginning number of Gui, just for simulating   
	private int guiNumber=100000;
	
	public ModelDaoImpl(){
		populateDummyModels();
	}
    
	@Override
	public Model createModel(Model model) {
	    DateFormat df = new SimpleDateFormat("yy/MM/dd");
	    Date dateobj = new Date();
	    if(model.getCreatedOn()==null)
	    	model.setCreatedOn(df.format(dateobj));
	    if(model.getGuid()==null)
	    	model.setGuid(String.valueOf(guiNumber++));
		models.add(model);
		return model;
	}
	
	@Override
	public Model updateModel(Model arg) {
		Model model = findModelByName(arg.getName());
		model.setCashHoldingPercentage(arg.getCashHoldingPercentage());
		model.setCreatedOn(arg.getCreatedOn());
		model.setDescription(arg.getDescription());
		model.setDriftPercentage(arg.getDriftPercentage());
		model.setModelType(arg.getModelType());
		model.setAssetAllocations(arg.getAssetAllocations());
		model.setRebalanceFrequency(arg.getRebalanceFrequency());
	    if(arg.getCreatedOn()!=null)
	    	model.setCreatedOn(arg.getCreatedOn());
	    if(arg.getGuid()!=null)
	    	model.setGuid(arg.getGuid());
	    if(arg.getAdvisorId()!=null)
	    	model.setAdvisorId(arg.getAdvisorId());
		return model;
	}
	
	@Override
	public Advisor findAdvisorById(String id) {
		for(Advisor adv : advisors)
			if(adv.getId().equals(id))
				return adv;
		return null;
	}

	@Override
	public List<Model> findModelsByAdvisor(String advisorId, int begIndex, int endIndex) {
		List <Model> tempList = new ArrayList <Model>();
		for(Model model :models)
			if(model.getAdvisorId().equals(advisorId))
				tempList.add(model);
		if(endIndex>tempList.size())
			endIndex=tempList.size();
		return tempList.subList(begIndex, endIndex);
	}

	@Override
	public Model findModelByName(String name) {
		for(Model model :models)
			if(model.getName().equals(name))
				return model;
		return null;
	}

	@Override
	public int countModelByAdvisor(String advisorId) {
		int count=0;
		for(Model model :models)
			if(model.getAdvisorId().equals(advisorId))
			    count++;
		return count;
	}	
	
	//by default generate two models for testing
	private void populateDummyModels() {
		Advisor a1 = new Advisor();
		a1.setId("1");
		Model m1= new Model();
		m1.setGuid("1111-22222-aaaaa");
		m1.setCashHoldingPercentage(20);
		m1.setCreatedOn("2017-01-01");
		m1.setDescription("example model with tech stocks");
		m1.setDriftPercentage(10);
		m1.setModelType("TAXABLE");
		m1.setName("model 1");
		m1.setRebalanceFrequency("QUARTERLY");
		m1.setAdvisorId("1");
		
		List <AssetAllocation> assetAllocations1 = new ArrayList <AssetAllocation>();
		AssetAllocation asset1 = new AssetAllocation();
		asset1.setSymbol("APPLE");
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
		
		Model m2= new Model();
		m2.setGuid("33333-44444-bbbbb");
		m2.setCashHoldingPercentage(40);
		m2.setCreatedOn("2017-03-01");
		m2.setDescription("example model with finance");
		m2.setDriftPercentage(50);
		m2.setModelType("TAXABLE");
		m2.setName("model 2");
		m2.setRebalanceFrequency("QUARTERLY");
		m2.setAdvisorId("1");
		List <AssetAllocation> assetAllocations2 = new ArrayList <AssetAllocation>();

		
		AssetAllocation asset4 = new AssetAllocation ();
		asset4.setSymbol("GOOG");
		asset4.setPercentage(70);	
		AssetAllocation asset5 = new AssetAllocation ();
		asset5.setSymbol("TWIT");
		asset5.setPercentage(30);
		
		assetAllocations2.add(asset4);
		assetAllocations2.add(asset5);
		m2.setAssetAllocations(assetAllocations2);

		advisors.add(a1);
		models.add(m1);
		models.add(m2);	
		
		Advisor a2 = new Advisor();
		a2.setId("2");
		advisors.add(a2);
	}
}
