package com.trizic.restapi.model;

import javax.validation.constraints.NotNull;

/**
 * Validation implemented
 * symbol:     required
 * percentage: required
 */
public class AssetAllocation {
	
	@NotNull(message="assetAllocation.symbol.required")
	private String symbol;
	
	@NotNull(message="assetAllocation.percentage.required")
	private int percentage;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
}
