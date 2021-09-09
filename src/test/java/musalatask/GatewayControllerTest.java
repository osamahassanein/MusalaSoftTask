package musalatask;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import musalatask.controller.GatewayController;
import musalatask.model.Device;
import musalatask.model.Gateway;
import musalatask.repository.GatewayRepo;

@WebMvcTest(GatewayController.class)
public class GatewayControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;

	@MockBean
	GatewayRepo gatewayRepo;

	Set<Device> devices;

	Gateway gateway1 = new Gateway(100, "gateway1", "192.168.1.1", devices);
	Gateway gateway2 = new Gateway(101, "gateway2", "192.168.2.1", devices);
	Gateway gateway3 = new Gateway(102, "gateway3", "192.168.3.1", devices);
	// ... Test methods TBA

	@Test
	public void getAllGateways() throws Exception {
		List<Gateway> records = new ArrayList<>(Arrays.asList(gateway1, gateway2, gateway3));

		Mockito.when(gatewayRepo.findAll()).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/gateway/getAllGateways").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].name", is("gateway1")));
	}

	@Test
	public void getGatewayById() throws Exception {
		Mockito.when(gatewayRepo.findById(gateway1.getId())).thenReturn(java.util.Optional.of(gateway1));

		mockMvc.perform(MockMvcRequestBuilders.get("/gateway/getGateway/100").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("gateway1")));
	}

	@Test
	public void addGateway() throws Exception {
		Gateway gateway = new Gateway(200, "gateway200", "192.168.200.1", devices);

		Mockito.when(gatewayRepo.save(gateway)).thenReturn(gateway);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/gateway/addGateway")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(gateway));

		mockMvc.perform(mockRequest).andExpect(status().isCreated()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("gateway200")));
	}

	@Test
	public void updateGateway() throws Exception {
		Gateway gateway = new Gateway(100, "gateway255", "192.168.255.1", devices);

		Mockito.when(gatewayRepo.findById(gateway1.getId())).thenReturn(Optional.of(gateway1));
//		gateway.setId(gateway1.getId());
		Mockito.when(gatewayRepo.save(gateway)).thenReturn(gateway);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/gateway/updateGateway/{id}", 100)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(gateway));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("gateway255")));

	}

	@Test
	public void updateGatewayNullId() throws Exception {
		Gateway gateway = new Gateway(null, "gateway255", "192.168.255.1", devices);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.put("/gateway/updateGateway/{id}", gateway.getId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(gateway));

		mockMvc.perform(mockRequest).andExpect(status().isNotFound());
	}

	@Test
	public void updateGatewayNotFound() throws Exception {
		Gateway gateway = new Gateway(250, "gateway250", "192.168.250.1", devices);

		Mockito.when(gatewayRepo.findById(gateway.getId())).thenReturn(null);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/gateway/updateGateway/{id}", 250)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(gateway));

		mockMvc.perform(mockRequest).andExpect(status().isNotFound());
	}

	@Test
	public void deleteGateway() throws Exception {
		Mockito.when(gatewayRepo.findById(gateway2.getId())).thenReturn(Optional.of(gateway2));

		mockMvc.perform(MockMvcRequestBuilders.delete("/gateway/deleteGateway/{id}", 101).contentType(MediaType.ALL))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteGatewayNotFound() throws Exception {
//		Gateway gateway = new Gateway();
//		Optional.of(gateway)
		
		Mockito.when(gatewayRepo.findById(500)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.delete("/gateway/deleteGateway/{id}", 500)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}