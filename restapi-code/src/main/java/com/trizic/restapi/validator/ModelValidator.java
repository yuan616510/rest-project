package com.trizic.restapi.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trizic.restapi.model.AssetAllocation;
import com.trizic.restapi.model.Model;

@Component
public class ModelValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Model.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errs) {
		Model model = (Model)target;
		//validate asset allocation percentage total:100 is valid
		int total =0;
		for(AssetAllocation asset : model.getAssetAllocations())
			total+=asset.getPercentage();	
		if(total!=100)
			errs.rejectValue("assetAllocations", "errorCode", "allocation.percentage.total.invalid");
	}

}
