package com.lms.LMSOrchestrator.Main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.lms.LMSOrchestrator.POJO.*;
import com.lms.LMSOrchestrator.db.UserRepository;


@RestController
@RequestMapping("/orchestrator")
public class Main {
	
	private UserRepository userRepository;
	
	public Main(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	String librarianUri = "http://localhost:8081";
	String borrowerUri = "http://localhost:8090";
	String AdminUri = "http://localhost:8070";

	@Autowired
	RestTemplate restTemplate = new RestTemplate();

	@GetMapping
	(value = "/librarian/branches") 
	public ResponseEntity<String> getAllBranches ( @RequestHeader("Accept") String accept){
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		
		 HttpEntity<LibraryBranch> request = new HttpEntity<LibraryBranch>(headers);
		
		try {
			return  restTemplate.exchange(
					librarianUri+"/librarian/branches", HttpMethod.GET, request,
					String.class);
		}
		catch(HttpStatusCodeException e) {
			return new ResponseEntity<String>(e.getStatusCode());
		}

	}	
	
	@GetMapping
	(value = "/borrower/branches/{branchId}") 
	public ResponseEntity<LibraryBranch> getABranch ( @RequestHeader("Accept") String accept, @PathVariable Integer branchId){
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		
		 HttpEntity<LibraryBranch> request = new HttpEntity<LibraryBranch>(headers);
		
		try {
			return  restTemplate.exchange(
					librarianUri+"/librarian/branches/"+branchId, HttpMethod.GET, request,
					LibraryBranch.class);
		}
		catch(HttpStatusCodeException e) {
			return new ResponseEntity<LibraryBranch>(e.getStatusCode());
		}

	}	
	
	@GetMapping("/admin/users")
	public List<User> users(){
		return this.userRepository.findAll();
	}
	
	@PutMapping
	(value = "/librarian/branches/{branchId}", consumes = {"application/xml", "application/json"})
	public ResponseEntity<LibraryBranch> UpdateBranch(	@RequestHeader("Accept") String accept,
														@PathVariable Integer branchId,
														@RequestBody LibraryBranch branch)
	{
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		
		 HttpEntity<LibraryBranch> request = new HttpEntity<LibraryBranch>(branch, headers);
		
		try {
			return  restTemplate.exchange(
					librarianUri+"/librarian/branches/"+branchId, HttpMethod.PUT, request,
					LibraryBranch.class);
		}
		catch(HttpStatusCodeException e) {
			return new ResponseEntity<LibraryBranch>(e.getStatusCode());
		}

	}		

	@PutMapping
	(value = "/librarian/branches/copies",
	consumes = {"application/xml", "application/json"})
	public ResponseEntity<?> AddCopies(	@RequestHeader("Accept") String accept,
										@RequestBody BookCopies bookCopy) 
	{
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		HttpEntity<BookCopies> request = new HttpEntity<BookCopies>(bookCopy, headers);

		try {
			return restTemplate.exchange(librarianUri+"/librarian/branches/copies", HttpMethod.PUT, request,
					BookCopies.class);
		}
		catch(HttpStatusCodeException e) {
			return new ResponseEntity<BookCopies>(e.getStatusCode());
		}
	}	
	
	
}

