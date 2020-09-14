package com.restaurantApp.controler;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.restaurantApp.dto.AddTableRequest;
import com.restaurantApp.dto.FormRegisterDto;
import com.restaurantApp.dto.LoginUser;
import com.restaurantApp.model.Table;
import com.restaurantApp.services.RestaurantService;

@RestController
@CrossOrigin()
public class AppController {
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);

	@Autowired
	public RestaurantService restaurantService;

	@RequestMapping(value = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginUser loginRequest) {

		return restaurantService.loginUser(loginRequest);
	}

	@RequestMapping(value = "/auth/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public  ResponseEntity<?>  submitUser(@Valid @RequestBody FormRegisterDto user) {
		
		try {
			Preconditions.checkNotNull(user);
		} catch (NullPointerException ex) {
			logger.error("request body  is missing ", ex);
		}
		try {
			Preconditions.checkNotNull(user.getName());
		} catch (NullPointerException ex) {
			logger.error("user name  is missing ", ex);
			// throw custome exception
		}
		try {
			Preconditions.checkNotNull(user.getEmail());
		} catch (NullPointerException ex) {
			logger.error("email  is missing ", ex);
			// throw custome exception
		}
		try {
			Preconditions.checkNotNull(user.getMobileNumber());
		} catch (NullPointerException ex) {
			logger.error("mobile number is missing ", ex);
			// throw custome exception
		}
		try {
			Preconditions.checkNotNull(user.getPassword());
		} catch (NullPointerException ex) {
			logger.error("password  is missing ", ex);
			// throw custome exception
		}

		return restaurantService.register(user);
	}

	@RequestMapping("/access-denied")
	public String showAccessDenied() {
		logger.info("inside access-denied");
		return "access-denied";

	}

	@RequestMapping(value = "/availableTables", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<Table> getAvailableTables() {
		logger.info("inside getAvailableTables");
		List<Table> tables = null;
		try {
			tables = restaurantService.findAll();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return tables;
	}

	
	@RequestMapping(value = "/table/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Table> getTable(@PathVariable String id) {
		logger.info("inside getTable with id " + id);
		return new ResponseEntity<Table>(restaurantService.findTable(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/bookTable", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Table> bookTable(@RequestBody Table bookRequest) {
		logger.info("inside bookTable");

		return new ResponseEntity<Table>(restaurantService.registerTable(bookRequest), HttpStatus.OK);

	}

	@RequestMapping(value = "/addTable", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public void addTable(@RequestBody AddTableRequest addRequest) {
		logger.info("inside add table ");
		restaurantService.addTable(addRequest);
	}

	@RequestMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String showAdmin(Model model, Principal principal) {
		logger.info("inside showAdmin");
//		List<Bill> bills = billService.findAll(principal.getName());
//		model.addAttribute("billList", bills);
		return "admin";
	}
}
