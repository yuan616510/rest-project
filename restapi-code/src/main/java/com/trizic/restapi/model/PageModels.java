package com.trizic.restapi.model;

import java.util.List;

public class PageModels {
	
	private int pageNumber;
	
	private int pageSize;
	
	private int numberOfPages;
	
	private int totalNumberOfElements;
	
	private List<Model> page;
	
	public static final int DEFAULT_PAGE_SIZE =10;
	
	public static final int DEFAULT_PAGE_NUM =0;
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getTotalNumberOfElements() {
		return totalNumberOfElements;
	}
	public void setTotalNumberOfElements(int totalNumberOfElements) {
		this.totalNumberOfElements = totalNumberOfElements;
	}
	public int getNumberOfPages() {
		return numberOfPages;
	}
	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<Model> getPage() {
		return page;
	}
	public void setPage(List<Model> page) {
		this.page = page;
	}
	
}
