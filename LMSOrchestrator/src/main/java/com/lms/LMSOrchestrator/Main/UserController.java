//package com.lms.LMSOrchestrator.Main;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/user/**")
//public class UserController
//{
//	@Autowired
//	JdbcUserDetailsManager jdbcudm;
//	
//	@Autowired
//	PasswordEncoder passwordEncoder;
//	
//	
//	@GetMapping(path = "username/{username}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
//	public ResponseEntity<UserDetails> readUserByName(@PathVariable String username)
//	{
//		UserDetails result = jdbcudm.loadUserByUsername(username);
//		return new ResponseEntity<UserDetails>(result,HttpStatus.OK);
//	}
//	
//	@PostMapping(path = "admin/username/{userName}/password/{password}")
//	public ResponseEntity<UserDetails> createAdmin(@PathVariable("userName") String userName, @PathVariable("password") String password)
//	{
//		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//		
//		UserDetails newUser = new User(
//				userName,
//				passwordEncoder.encode(password), 
//				authorities);
//		
//		jdbcudm.createUser(newUser);
//		return new ResponseEntity<UserDetails>(HttpStatus.NO_CONTENT);
//
//	}
//	
//	@PostMapping(path = "librarian/username/{userName}/password/{password}")
//	public ResponseEntity<UserDetails> createLibrarian(@PathVariable("userName") String userName, @PathVariable("password") String password)
//	{
//		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_LIBRARIAN"));
//
//		UserDetails newUser = new User(
//				userName,
//				passwordEncoder.encode(password), 
//				authorities);
//		
//		jdbcudm.createUser(newUser);
//		return new ResponseEntity<UserDetails>(HttpStatus.NO_CONTENT);
//
//	}
//	
//	@PostMapping(path = "borrower/username/{userName}/password/{password}")
//	public ResponseEntity<UserDetails> createBorrower(@PathVariable("userName") String userName, @PathVariable("password") String password)
//	{
//		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_BORROWER"));
//
//		UserDetails newUser = new User(
//				userName,
//				passwordEncoder.encode(password), 
//				authorities);
//		
//		jdbcudm.createUser(newUser);
//		return new ResponseEntity<UserDetails>(HttpStatus.NO_CONTENT);
//
//	}
//}