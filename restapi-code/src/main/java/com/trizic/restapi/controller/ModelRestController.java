package com.trizic.restapi.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trizic.restapi.model.Advisor;
import com.trizic.restapi.model.ErrorResponse;
import com.trizic.restapi.model.Model;
import com.trizic.restapi.model.PageModels;
import com.trizic.restapi.service.ModelService;
import com.trizic.restapi.validator.ModelValidator;

@RestController
@RequestMapping("/v1/advisor/{advisorId}/model")
public class ModelRestController {
	
	@Autowired
	ModelService modelService;
	
	@Autowired
	ModelValidator modelValidator;
	
	//------------------- create a model -------------------------	//validation of request body implemented
	@RequestMapping(method=RequestMethod.PUT,produces="application/json")
	public ResponseEntity<Object> saveModel(@PathVariable("advisorId") String id, @Valid @RequestBody Model model, BindingResult result){		
		Advisor advisor = modelService.findAdvisorById(id);
		if(advisor == null)
			return new ResponseEntity<Object>(new ErrorResponse("advisor.not.found"), HttpStatus.NOT_FOUND);
		modelValidator.validate(model, result);    //custom validation results combined with Hibernate validation results
		if (result.hasErrors()){
			return new ResponseEntity<Object>(new ErrorResponse(result.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
		}
		Model ret =modelService.saveModel(model,id);
		return new ResponseEntity<Object>(ret, HttpStatus.OK);
	}

	//------------------  get models for an advisor ------------
	@RequestMapping(method=RequestMethod.GET,produces="application/json")
	public ResponseEntity<Object> getModels(@PathVariable("advisorId") String id, 
			@RequestParam("pageNumber") Optional <Integer> pageNumber, @RequestParam("pageSize") Optional <Integer> pageSize){
		
		Advisor advisor = modelService.findAdvisorById(id);
		if(advisor == null)
			return new ResponseEntity<Object>(new ErrorResponse("advisor.not.found"), HttpStatus.NOT_FOUND);
		int pageNumberParam = pageNumber.isPresent()?pageNumber.get():PageModels.DEFAULT_PAGE_NUM;    //use default value if no page number specified
		int pageSizeParam = pageSize.isPresent()?pageSize.get():PageModels.DEFAULT_PAGE_SIZE;      // use default value if no page size specified
		PageModels pageModels= modelService.findModelsByAdvisor(id, pageNumberParam, pageSizeParam);
		return new ResponseEntity<Object>(pageModels, HttpStatus.OK);	
	}
}
