package musalatask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import musalatask.model.Device;
import musalatask.repository.DeviceRepo;

@RestController
@RequestMapping("/device")
public class DeviceController {

	@Autowired
	DeviceRepo deviceRepo;

	@GetMapping("/getAllDevices")
	public List<Device> getAllDevices() {
		return this.deviceRepo.findAll();
	}

	@GetMapping("/getGatewayDevices")
	public List<Device> getGatewayDevices() {
		return this.deviceRepo.findAll();
	}

	@PostMapping("/addDevice")
	public ResponseEntity<?> addDevice(@RequestBody Device device) {
//		System.out.println("device>>>>>> "+ device.getGateway().getDevices().size());
		if (device.getGateway().getDevices().size() == 10)
			return new ResponseEntity<String>("No more than 10 peripheral devices are allowed for a gateway.",
					HttpStatus.EXPECTATION_FAILED);
//		System.out.println("device>>>>>> "+ device.getGateway().getIpv4());
//		device.setGateway(device.getGateway());
		return new ResponseEntity<>(this.deviceRepo.save(device), HttpStatus.CREATED);
	}

	@DeleteMapping("/deleteDevice/{id}")
	public ResponseEntity<?> deleteDevice(@PathVariable Integer id) {
		if (this.deviceRepo.findById(id).isPresent()) {
			this.deviceRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

	}

	@PutMapping("/updateDevice/{id}")
	public ResponseEntity<?> updateDevice(@PathVariable Integer id, @RequestBody Device device) {
		if (this.deviceRepo.findById(id).isPresent()) {
			device.setId(id);

			return new ResponseEntity<>(this.deviceRepo.save(device), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

	}

}
