package com.trizic.restapi.service;


import com.trizic.restapi.model.Advisor;
import com.trizic.restapi.model.Model;
import com.trizic.restapi.model.PageModels;

public interface ModelService {

	public Advisor findAdvisorById (String id);
	
	public Model saveModel(Model model,String advisorId);
	
	public PageModels findModelsByAdvisor(String advisorId, int pageNumber, int pageSize);
}
