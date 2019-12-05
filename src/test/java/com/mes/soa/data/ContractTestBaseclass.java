package com.mes.soa.data;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import com.mes.soa.data.controller.BatchInformationController;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ContractTestBaseclass {

	@Autowired
	BatchInformationController batchInformationController;;

	@Before
	public void setup() throws Exception {

		StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(batchInformationController);
		RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
		
	}
}