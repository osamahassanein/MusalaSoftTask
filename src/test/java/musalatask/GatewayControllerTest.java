package musalatask;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import musalatask.controller.GatewayController;
import musalatask.model.Gateway;
import musalatask.repository.GatewayRepo;
 
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class GatewayControllerTest 
{
    @InjectMocks
    GatewayController gatewayController;
     
    @Mock
    GatewayRepo gatewayRepo;
     
    @Test
    public void testAddGateway() 
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
         
        Gateway gateway = new Gateway();
        gateway.setId(100);
        gateway.setName("Test Name");
        gateway.setIpv4("192.168.1.1");
        

        when(gatewayRepo.save(any(Gateway.class))).thenReturn(gateway);
        
        ResponseEntity<?> responseEntity = gatewayController.addGateway(gateway);
         
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }
    
     
    @Test
    public void testFindAll() 
    {
        
    	Gateway gateway1 = new Gateway();
        gateway1.setId(101);
        gateway1.setName("Test 1");
        gateway1.setIpv4("192.168.1.1");
    	Gateway gateway2 = new Gateway();
        gateway2.setId(102);
        gateway2.setName("Test 2");
        gateway2.setIpv4("192.168.2.1");
		List<Gateway> list = new ArrayList<Gateway>();
		list.addAll(Arrays.asList(gateway1, gateway2));

		when(gatewayRepo.findAll()).thenReturn(list);

		
		List<Gateway> result = gatewayController.getAllGateways();

		
		assertThat(result.size()).isEqualTo(2);
		
		assertThat(result.get(0).getName())
						.isEqualTo(gateway1.getName());
		
		assertThat(result.get(1).getName())
						.isEqualTo(gateway2.getName());
    }
}