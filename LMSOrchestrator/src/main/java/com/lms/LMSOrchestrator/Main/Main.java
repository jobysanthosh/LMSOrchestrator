package com.lms.LMSOrchestrator.Main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lms.LMSOrchestrator.POJO.BookCopiesBL;
import com.lms.LMSOrchestrator.POJO.LibraryBranch;

@RestController
public class Main {
	String librarianUri = "http://localhost:8080";
	String borrowerUri = "http://localhost:8090";
	String AdminUri = "http://localhost:8070";

	@Autowired
	RestTemplate restTemplate = new RestTemplate();

	@PutMapping
	(value = "/LMSLibrarian/branches/{branchId}/name/{name}/address/{address}", consumes = {"application/xml", "application/json"})
	public ResponseEntity<LibraryBranch> UpdateBranch(@RequestHeader("Accept") String accept,
			@RequestBody LibraryBranch branch) throws JsonProcessingException
	{
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		try {
			return  restTemplate.exchange(
					librarianUri+"/LMSLibrarian/branches/{branchId}/name/{name}/address/{address}", HttpMethod.PUT, new HttpEntity<LibraryBranch>(branch, headers),
					LibraryBranch.class, branch.getBranchId().toString(), branch.getBranchName().toString(), branch.getBranchAddress().toString());
		}
		catch(HttpStatusCodeException e) {
			return new ResponseEntity<LibraryBranch>(e.getStatusCode());
		}
	}		

	@PutMapping
	(value = "/LMSLibrarian/branches/{branchId}/bookId/{bookId}/copies/{copies}",
	consumes = {"application/xml", "application/json"})
	public ResponseEntity<?> AddCopies(@RequestHeader("Accept") String accept,
			@RequestBody BookCopiesBL bookCopy) 
	{
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);

		try {
			return restTemplate.exchange(librarianUri+"/LMSLibrarian/branches/{branchId}/bookId/{bookId}/copies/{copies}", HttpMethod.PUT, new HttpEntity<BookCopiesBL>(bookCopy, headers),
					BookCopiesBL.class, bookCopy.getBranchId(), bookCopy.getBookId(), bookCopy.getNoOfCopies());
		}
		catch(HttpStatusCodeException e) {
			return new ResponseEntity<BookCopiesBL>(e.getStatusCode());
		}
	}	
	
	//checkout
	   @GetMapping
	   (value = "/LMSOrchestrator/LMSBorrower/cardNo/{cardNo}/checkout/branch/{branch}/book/{book}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	   public ResponseEntity<String> mainWriteLoans(@RequestHeader("Accept") String accept,
	           @PathVariable(value = "cardNo") Integer cardNo, @PathVariable(value = "branch") Integer branchId,@PathVariable(value = "book") Integer bookId)
	   {
	        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
	        headers.add("Accept", accept);
	            
	        try {
	            return restTemplate.exchange(
	            borrowerUri+"/LMSBorrower/cardNo/"+cardNo+"/checkout/branch/"+branchId+"/book/"+bookId, HttpMethod.GET, new HttpEntity<Object>(headers),
	            String.class);
	        }
	        
	        catch(HttpStatusCodeException e) {
	            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
	                    .body(e.getResponseBodyAsString());
	        }
	   }
	   //return
	   @GetMapping
	   (value = "/LMSOrchestrator/LMSBorrower/cardNo/{cardNo}/return/branch/{branch}/book/{book}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	   public ResponseEntity<String> mainWriteReturn(@RequestHeader("Accept") String accept,
	           @PathVariable(value = "cardNo") Integer cardNo, @PathVariable(value = "branch") Integer branchId, @PathVariable(value = "book") Integer bookId)
	   {
	        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
	        headers.add("Accept", accept);
	        
	        try {
	            return restTemplate.exchange(
	            borrowerUri+"/LMSBorrower/cardNo/"+cardNo+"/return/branch/"+branchId+"/book/"+bookId, HttpMethod.GET, new HttpEntity<Object>(headers),
	            String.class);
	        }
	        
	        catch(HttpStatusCodeException e) {
	            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
	                    .body(e.getResponseBodyAsString());
	        }
	   }
}

