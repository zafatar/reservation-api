package com.zafatar.reservation.api.v1.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.zafatar.reservation.api.v1.repository.TableRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TableRestControllerTest {	
	@LocalServerPort
    private int port;

	private MockMvc mockMvc;

	@Autowired
	private TableRepository tableRepository;
	
    @Autowired
    private TestRestTemplate restTemplate;
    
	@Autowired
	private TableRestController tableRestController;
	
    @Test
    public void contexLoads() throws Exception {
        assertThat(tableRestController).isNotNull();
    }
	
    @Test
    public void NoDataLoadedShouldReturn404() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/table/1",
                String.class)).contains("NOT_FOUND");
    }
}
