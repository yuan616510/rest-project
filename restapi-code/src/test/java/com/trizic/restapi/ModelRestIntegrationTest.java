package com.trizic.restapi;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trizic.restapi.controller.ModelRestController;
import com.trizic.restapi.dao.ModelDao;
import com.trizic.restapi.model.Advisor;
import com.trizic.restapi.model.Model;
import com.trizic.restapi.service.ModelService;
import com.trizic.restapi.service.ModelServiceImpl;
import com.trizic.restapi.validator.ModelValidator;

public class ModelRestIntegrationTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private ModelDao modelDao;
	
	@Spy@InjectMocks
	private ModelService modelService = new ModelServiceImpl();
	
	@Spy
	private ModelValidator modelValidator;
	
	@InjectMocks	
	private ModelRestController modelController;
	
	private MockModelGenerator modelGen = new MockModelGenerator(); 
	
	private Advisor advisor = getMockAdvisor();
	
	private final String VALID_ID ="1";
	
	private final String NOT_FOUND_ID ="10";
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(modelController)
                .build();
    }
	
	/**************************************************************************************
	****************************      CREATE A MODEL       ********************************
	***************************************************************************************/

	// ======================= case 1: advisor not found  ==============================
	@Test
	public void test_create_model_fail_adviosor_not_found() throws Exception{
		Model model = modelGen.getMockModel_correct();
		when(modelDao.findAdvisorById(NOT_FOUND_ID)).thenReturn(null);
		mockMvc.perform(
				put("/v1/advisor/{advisorId}/model",NOT_FOUND_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(model)))
		        .andExpect(status().isNotFound())     //test http response status code 
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		        .andExpect(jsonPath("$.errorCode", is("advisor.not.found")));   //test error code for business logic
        verify(modelDao, times(1)).findAdvisorById(NOT_FOUND_ID);
        verifyNoMoreInteractions(modelDao);
	}
	
	//==================== case 2: allocation percentage total not 100 =====================
	@Test
	public void test_creat_model_fail_allocation_percent_not_100() throws Exception{
		when(modelDao.findAdvisorById(VALID_ID)).thenReturn(advisor);
		Model model = modelGen.getMockModel_percentNot100();
		mockMvc.perform(
				put("/v1/advisor/{advisorId}/model",VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(model)))
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath("$.errorCode", is("allocation.percentage.total.invalid")));
        verify(modelDao, times(1)).findAdvisorById(VALID_ID);
        verifyNoMoreInteractions(modelDao);
	}
	
	//====================== case 3: required field missing, e.g.{name:null} =====================
	@Test
	public void test_create_model_fail_required_missing() throws Exception{
		when(modelDao.findAdvisorById(VALID_ID)).thenReturn(advisor);
		Model model = modelGen.getMockModel_missingName();
		mockMvc.perform(
				put("/v1/advisor/{advisorId}/model",VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(model)))
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath("$.errorCode", is("name.required")));
        verify(modelDao, times(1)).findAdvisorById(VALID_ID);
        verifyNoMoreInteractions(modelDao);
	}	
	
	//====================== case 4: invalid enum type, e.g.{modelType:dog} =====================
	@Test
	public void test_create_model_fail_invalid_enum_type() throws Exception{
		when(modelDao.findAdvisorById(VALID_ID)).thenReturn(advisor);
		Model model = modelGen.getMockModel_invalidEnumType();
		mockMvc.perform(
				put("/v1/advisor/{advisorId}/model",VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(model)))
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath("$.errorCode", is("modelType.invalid.type")));
        verify(modelDao, times(1)).findAdvisorById(VALID_ID);
        verifyNoMoreInteractions(modelDao);
	}
	
	//====================== case 5: nested object's field missing, e.g.{assetAllocation:symbol} =====================
	@Test
	public void test_create_model_fail_requeired_nested_field_missing() throws Exception{
		when(modelDao.findAdvisorById(VALID_ID)).thenReturn(advisor);
		Model model = modelGen.getMockModel_nestedObject_missingSymbol();
		mockMvc.perform(
				put("/v1/advisor/{advisorId}/model",VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(model)))
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath("$.errorCode", is("assetAllocation.symbol.required")));
        verify(modelDao, times(1)).findAdvisorById(VALID_ID);
        verifyNoMoreInteractions(modelDao);
	}
	
	//====================== case 6: new model created success =====================
	@Test
	public void test_create_new_model_success() throws Exception{
		Model model = modelGen.getMockModel_correct();
		when(modelDao.findAdvisorById(VALID_ID)).thenReturn(advisor);
		when(modelDao.createModel(Mockito.any(Model.class))).then(AdditionalAnswers.returnsFirstArg());
		when(modelDao.findModelByName(model.getName())).thenReturn(null);
		
		mockMvc.perform(
				put("/v1/advisor/{advisorId}/model",VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(model)))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.advisorId", is(advisor.getId())));
        verify(modelDao, times(1)).findAdvisorById(VALID_ID);
        verify(modelDao, times(1)).createModel(Mockito.any(Model.class));
        verify(modelDao, times(1)).findModelByName(model.getName());
        verifyNoMoreInteractions(modelDao);
	}
	
	//====================== case 7: existing model updated success =====================
	@Test
	public void test_updated_existing_model_success() throws Exception{
		Model model = modelGen.getMockModel_correct();
		when(modelDao.findAdvisorById(VALID_ID)).thenReturn(advisor);
		when(modelDao.updateModel(Mockito.any(Model.class))).then(AdditionalAnswers.returnsFirstArg());
		when(modelDao.findModelByName(model.getName())).thenReturn(model);
		
		mockMvc.perform(
				put("/v1/advisor/{advisorId}/model",VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(model)))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.name", is(model.getName())));
        verify(modelDao, times(1)).findAdvisorById(VALID_ID);
        verify(modelDao, times(1)).updateModel(Mockito.any(Model.class));
        verify(modelDao, times(1)).findModelByName(model.getName());
        verifyNoMoreInteractions(modelDao);
	}
	
	
	/**************************************************************************************
	****************************      GET A MODEL       ***********************************
	***************************************************************************************/
	
	//====================== case 8: advisor not found =====================
	@Test
	public void test_get_models_fail_adviosor_not_found() throws Exception{
		when(modelDao.findAdvisorById(NOT_FOUND_ID)).thenReturn(null);
		mockMvc.perform(
				get("/v1/advisor/{advisorId}/model",NOT_FOUND_ID))
		        .andExpect(status().isNotFound())
		        .andExpect(jsonPath("$.errorCode", is("advisor.not.found")));
        verify(modelDao, times(1)).findAdvisorById(NOT_FOUND_ID);
        verifyNoMoreInteractions(modelDao);
	}
	
	//====================== case 9: success in getting models without page number and size =====================
	@Test
	public void test_get_models_success_no_parameters() throws Exception{
		List <Model> modelList= modelGen.genMockModels(5);
		when(modelDao.findAdvisorById(VALID_ID)).thenReturn(advisor);
		when(modelDao.countModelByAdvisor(VALID_ID)).thenReturn(5);
		when(modelDao.findModelsByAdvisor(VALID_ID,0,5)).thenReturn(modelList);
		mockMvc.perform(
				get("/v1/advisor/{advisorId}/model",VALID_ID))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.numberOfPages").value(1))
		        .andExpect(jsonPath("$.totalNumberOfElements").value(5));
        verify(modelDao, times(1)).findAdvisorById(VALID_ID);
        verify(modelDao, times(1)).countModelByAdvisor(VALID_ID);
        verify(modelDao, times(1)).findModelsByAdvisor(VALID_ID,0,5);
        verifyNoMoreInteractions(modelDao);
	}
	
	//====================== case 10: success in getting models with page number and size =====================
	@Test
	public void test_get_models_success_with_parameters() throws Exception{
		List <Model> modelList= modelGen.genMockModels(5);
		List <Model> ret = new ArrayList<Model>();
		ret.add(modelList.get(4));
		when(modelDao.findAdvisorById(VALID_ID)).thenReturn(advisor);
		when(modelDao.countModelByAdvisor(VALID_ID)).thenReturn(5);
		when(modelDao.findModelsByAdvisor(VALID_ID,4,5)).thenReturn(ret);
		mockMvc.perform(
				get("/v1/advisor/{advisorId}/model?pageSize=4&pageNumber=1",VALID_ID))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.numberOfPages").value(2))
		        .andExpect(jsonPath("$.page[0].name").value(ret.get(0).getName()));
        verify(modelDao, times(1)).findAdvisorById(VALID_ID);
        verify(modelDao, times(1)).countModelByAdvisor(VALID_ID);
        verify(modelDao, times(1)).findModelsByAdvisor(VALID_ID,4,5);
        verifyNoMoreInteractions(modelDao);
	}
	
	//============================= end test cases ============================
	
	private Advisor getMockAdvisor(){
		advisor = new Advisor();
		advisor.setId("1");
		return advisor;
	}
	
	private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
