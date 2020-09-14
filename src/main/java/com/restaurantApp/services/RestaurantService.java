package com.restaurantApp.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurantApp.dao.RoleDao;
import com.restaurantApp.dao.TablesDao;
import com.restaurantApp.dao.UserDao;
import com.restaurantApp.dto.AddTableRequest;
import com.restaurantApp.dto.FormRegisterDto;
import com.restaurantApp.dto.JwtResponse;
import com.restaurantApp.dto.LoginUser;
import com.restaurantApp.dto.ResponseMessage;
import com.restaurantApp.model.Role;
import com.restaurantApp.model.Table;
import com.restaurantApp.model.User;
import com.restaurantApp.security.UserPrinciple;
import com.restaurantApp.security.jwt.JwtProvider;

@Service
public class RestaurantService {

	@Autowired
	TablesDao tablesDao;
	@Autowired
	UserDao userdao;
	@Autowired
	RoleDao roledao;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	PasswordEncoder encoder;
	private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class.getName());

	public ResponseEntity<?> loginUser(LoginUser loginReq) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getEmail(), userDetails.getAuthorities()));
	}

	public  ResponseEntity<?>  register(FormRegisterDto registerRequest) {
		logger.info("inside register service");
		if (userdao.existsByEmail(registerRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(registerRequest.getEmail(),encoder.encode(registerRequest.getPassword()),
				registerRequest.getName(),registerRequest.getMobileNumber(),true);
		Role role = roledao.findByRole("ROLE_ADMIN");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		userdao.save(user);
		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);

	}

	public Table registerTable(Table addTableRequest) {
		Table table = tablesDao.findById(addTableRequest.getTableId()).get();
		table.setFrom(addTableRequest.getFrom());
		table.setTo(addTableRequest.getTo());
		table.setBooked(true);
		System.out.println(table.getTableId());
		return tablesDao.save(table);
	}

	public List<Table> findAll() {
		return tablesDao.findByBookedIsFalse();
	}

	public void addTable(AddTableRequest bookRequest) {
		Table table = new Table();
		table.setFrom(bookRequest.getFrom());
		table.setNumOfSeats(bookRequest.getNumberOfSeats());
		table.setTableNum(tablesDao.count() + 1);
		table.setTo(bookRequest.getTo());
		table.setUserId(-1l);
		tablesDao.save(table);
	}

	public Table findTable(String tableId) {
		return tablesDao.findById(tableId).get();
	}
}
