package musalatask;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import musalatask.controller.DeviceController;
import musalatask.model.Device;
import musalatask.model.Gateway;
import musalatask.repository.DeviceRepo;
 
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
     
public class DeviceControllerTest 
{
	@InjectMocks
	DeviceController deviceController;
	
    @Mock
    DeviceRepo deviceRepo;
     
    @Test
    public void testAddDevice() 
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
         
        Device device = new Device();
        device.setId(100);
        device.setVendor("Test Name");
        device.setStatus("192.168.1.1");
        device.setCreationDate(new Date());
        device.setGateway(new Gateway());
        

        when(deviceRepo.save(any(Device.class))).thenReturn(device);
        
        ResponseEntity<?> responseEntity = deviceController.addDevice(device);
         
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }
    
     
    @Test
    public void testFindAll() 
    {
        
    	Device device1 = new Device();
    	device1.setId(100);
    	device1.setVendor("Test Name");
    	device1.setStatus("192.168.1.1");
    	device1.setCreationDate(new Date());
    	device1.setGateway(new Gateway());
    	
        Device device2 = new Device();
        device2.setId(100);
        device2.setVendor("Test Name");
        device2.setStatus("192.168.1.1");
        device2.setCreationDate(new Date());
        device2.setGateway(new Gateway());
    	
		List<Device> list = new ArrayList<Device>();
		list.addAll(Arrays.asList(device1, device2));

		when(deviceRepo.findAll()).thenReturn(list);

		
		List<Device> result = deviceController.getAllDevices();

		
		assertThat(result.size()).isEqualTo(2);
		
		assertThat(result.get(0).getVendor())
						.isEqualTo(device1.getVendor());
		
		assertThat(result.get(1).getVendor())
						.isEqualTo(device2.getVendor());
    }
}