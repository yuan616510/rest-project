package com.trizic.restapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trizic.restapi.dao.ModelDao;
import com.trizic.restapi.model.Advisor;
import com.trizic.restapi.model.Model;
import com.trizic.restapi.model.PageModels;

@Service("modelService")
public class ModelServiceImpl implements ModelService{

	@Autowired
	private ModelDao modelDao;

	@Override
	public Advisor findAdvisorById(String id) {
		return modelDao.findAdvisorById(id);
	}

	@Override
	public Model saveModel(Model model, String advisorId) {
		if(modelExist(model.getName()))
			return modelDao.updateModel(model);
		else{
			model.setAdvisorId(advisorId);
			return modelDao.createModel(model);
		}
	}	

	@Override
	public PageModels findModelsByAdvisor(String advisorId, int pageNumber, int pageSize) {	
		if(pageNumber<=0) //use default page number 0 if invalid
			pageNumber=PageModels.DEFAULT_PAGE_NUM;
		if(pageSize<=0)   //use default page size 10 if invalid
			pageSize=PageModels.DEFAULT_PAGE_SIZE;
		int total = modelDao.countModelByAdvisor(advisorId);
		int numberOfPages= total==0?0:(total-1)/pageSize+1; //calculate number of pages
		PageModels pageModels = new PageModels();	
		pageModels.setTotalNumberOfElements(total);
		pageModels.setNumberOfPages(numberOfPages);
		pageModels.setPageNumber(pageNumber);
		pageModels.setPageSize(pageSize);
		if (pageNumber+1 > numberOfPages){  //if page number given exceeds total number of pages, return page without models
			pageModels.setPage(new ArrayList<Model>());
			return pageModels;
		}
		//calculate range of models that are needed
		int begIndex = pageNumber*pageSize;	
		int endIndex = total <begIndex +pageSize? total:begIndex +pageSize;
		List <Model> retModels = modelDao.findModelsByAdvisor(advisorId,begIndex, endIndex);
		pageModels.setPage(retModels);		
		return pageModels;
	}
		
	private boolean modelExist(String name){
		Model model =modelDao.findModelByName(name);
		return model!=null;
	}
}
