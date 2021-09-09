package musalatask.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.InetAddressValidator;
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
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import musalatask.model.Gateway;
import musalatask.repository.GatewayRepo;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

	@Autowired
	GatewayRepo gatwayRepo;

	private final InetAddressValidator ipv4Validator = new InetAddressValidator();

	@GetMapping("/getAllGateways")
	public List<Gateway> getAllGateways() {
		return this.gatwayRepo.findAll();
	}

	@PostMapping("/addGateway")
	public ResponseEntity<?> addGateway(@RequestBody Gateway gateway) {
		if (!ipv4Validator.isValidInet4Address(gateway.getIpv4())) {
			return new ResponseEntity<String>("IPV4 Format is not correct (ex. 192.168.1.1)",
					HttpStatus.EXPECTATION_FAILED);
		} else {
			return new ResponseEntity<>(this.gatwayRepo.save(gateway), HttpStatus.CREATED);
		}
	}

	@DeleteMapping("/deleteGateway/{id}")
	public ResponseEntity<?> deleteGateway(@PathVariable Integer id) {

//		System.out.println("deleteGateway >>>>>>>> " + id);

		if (this.gatwayRepo.findById(id) != null) {

//			System.out.println("deleteGatewayFound >>>>>>>>>>>>>>>>>> " + id);
			this.gatwayRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);

		} else if (this.gatwayRepo.findById(id) == null) {

//			System.out.println("deleteGatewayNotFound >>>> null >>>> " + id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} else {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/updateGateway/{id}")
	public ResponseEntity<?> updateGateway(@PathVariable Integer id, @RequestBody Gateway gateway) {
//		if (this.gatwayRepo.findById(id).isPresent()) {
		if (this.gatwayRepo.findById(id) != null) {
			gateway.setId(id);

			return new ResponseEntity<>(this.gatwayRepo.save(gateway), HttpStatus.OK);

		} else if (this.gatwayRepo.findById(id) == null) {

//			System.out.println("updateGatewayNotFound >>>> null >>>> " + id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} else {
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/getGateway/{id}")
	public ResponseEntity<?> getGateway(@PathVariable Integer id) {

		if (this.gatwayRepo.findById(id).isPresent()) {

			return new ResponseEntity<>(this.gatwayRepo.findById(id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
	}

}
