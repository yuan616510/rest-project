package com.trizic.restapi.model;

import java.util.List;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.trizic.restapi.validator.EnumString;

/**
 * Validation implemented
 * 
 * name:                  required
 * description:           required
 * cashHoldingPercentage: required
 * driftPercentage:       required
 * modelType:             required,  Enum type:{"QUALIFIED","TAXABLE"}
 * rebalanceFrequency:    required,  Enum type:{"MONTHLY","QUARTERLY","SEMI_ANNUAL","ANNUAL"}
 * 
 * @author Yuan
 *
 */
@GroupSequence({Model.class,Model.SecondCheck.class})   //Second check is to check enum type
public class Model {
	
	private String guid;
	
	private String createdOn;
	
	@NotNull(message="name.required")
	private String name;
	
	@NotNull(message="description.required")
	private String description;
	
	private String advisorId;
	
	@NotNull(message="cashHoldingPercentage.required")
	private int cashHoldingPercentage;
	
	@NotNull(message="driftPercentage.required")
	private int driftPercentage;
	
	@NotNull(message="modelType.required")
	@EnumString(acceptedValues={"QUALIFIED","TAXABLE"},message="modelType.invalid.type",groups=SecondCheck.class)
	private String modelType;
	
	@NotNull(message="rebalanceFrequency.required")
	@EnumString(acceptedValues={
			"MONTHLY",
	        "QUARTERLY",
	        "SEMI_ANNUAL",
	        "ANNUAL"},message="rebalanceFrequency.invalid.type",groups=SecondCheck.class)
	private String rebalanceFrequency;
	
	@NotNull(message="assetAllocations.required")
	@Valid
	private List <AssetAllocation> assetAllocations;
			
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCashHoldingPercentage() {
		return cashHoldingPercentage;
	}
	public void setCashHoldingPercentage(int cashHoldingPercentage) {
		this.cashHoldingPercentage = cashHoldingPercentage;
	}
	public int getDriftPercentage() {
		return driftPercentage;
	}
	public void setDriftPercentage(int driftPercentage) {
		this.driftPercentage = driftPercentage;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getRebalanceFrequency() {
		return rebalanceFrequency;
	}
	public void setRebalanceFrequency(String rebalanceFrequency) {
		this.rebalanceFrequency = rebalanceFrequency;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getAdvisorId() {
		return advisorId;
	}
	public void setAdvisorId(String advisorId) {
		this.advisorId = advisorId;
	}
	public List <AssetAllocation> getAssetAllocations() {
		return assetAllocations;
	}
	public void setAssetAllocations(List <AssetAllocation> assetAllocations) {
		this.assetAllocations = assetAllocations;
	}
	public interface SecondCheck{}
}


