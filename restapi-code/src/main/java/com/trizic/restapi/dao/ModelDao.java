package com.trizic.restapi.dao;

import java.util.List;

import com.trizic.restapi.model.Advisor;
import com.trizic.restapi.model.Model;

public interface ModelDao {
	
	public Model createModel(Model model);

	public Advisor findAdvisorById(String id);

	public List<Model> findModelsByAdvisor(String advisorId, int begIndex, int endIndex);

	public Model updateModel(Model model);

	public Model findModelByName(String name);

	public int countModelByAdvisor(String advisorId);
}
