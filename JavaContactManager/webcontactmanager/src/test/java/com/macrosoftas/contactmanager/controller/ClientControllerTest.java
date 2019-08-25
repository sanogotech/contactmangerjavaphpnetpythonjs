package com.macrosoftas.contactmanager.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(ClientController.class)
///@TestPropertySource(locations = "classpath:application-dev.properties")
//@DataJpaTest
//@ContextConfiguration(classes = WebConfig.class)
public class ClientControllerTest {

	
	@Autowired
	private MockMvc mvc;
	
	//@Autowired
	//private TestEntityManager entityManager;
	
	//@Autowired
	//ClientController clientController;
	
	@Before
	public void setUp() {
	
	
	}

	
	@Test
	public void testAddClient() throws Exception {
		
		//mvc.perform(get("/client/add")).andExpect(status().isOk());
		
		
	}
	
	
	@Test
	public void testFindByClientName_thenReturnClient() {
	    // given
	  //  Client client = new Client();
	    //entityManager.persist(client);
	    //entityManager.flush();
	 
	    // when
	    //Client clientSave = employeeRepository.findByName(alex.getName());
	 
	    // then
	   // assertThat(found.getName())
	     // .isEqualTo(alex.getName());
	}

}
