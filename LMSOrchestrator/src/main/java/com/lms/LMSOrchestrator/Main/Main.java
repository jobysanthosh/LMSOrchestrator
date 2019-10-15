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
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.lms.LMSOrchestrator.POJO.LibraryBranch;

@RestController
public class Main {
	String uri = "http://localhost:8090";
	
	@Autowired
	RestTemplate restTemplate = new RestTemplate();

	@GetMapping
	(value = "/LMSOrchestrator/branch/{branchId}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<LibraryBranch> readAuthorById(@RequestHeader("Accept") String accept,
												@PathVariable Integer branchId)
	{
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", accept);
        
		return restTemplate.exchange(
                uri+"/LMSLibrarian/librarybranch/" + branchId, HttpMethod.GET, new HttpEntity<Object>(headers),
                LibraryBranch.class);
		
	}
	
	@PutMapping
	(value = "/LMSOrchestrator/LMSLibrarian/librarybranch/{branchid}/name/{name}/address/{address}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> UpdateBranch(@RequestHeader("Accept") String accept,
			@PathVariable(value = "branchid") Integer branchId, @PathVariable(value = "name") String name,@PathVariable(value = "address") String address)
	{
	MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
	headers.add("Accept", accept);
	
	try {
	return  restTemplate.exchange(
	uri+"/LMSLibrarian/librarybranch/"+branchId.toString()+"/name/"+name+"/address/"+address, HttpMethod.PUT, new HttpEntity<Object>(headers),
	String.class);
	}
	catch(HttpStatusCodeException e) {
        return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                .body(e.getResponseBodyAsString());
    }

	}		
	
	@PutMapping
	(value = "/LMSOrchestrator/LMSLibrarian/librarybranch/{branchid}/bookid/{bookid}/copy/{copy}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> AddCopies(@RequestHeader("Accept") String accept,
			@PathVariable(value = "branchid") Integer branchId, @PathVariable(value = "bookid") Integer bookId,@PathVariable(value = "copy") Integer copy)
	{
	MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
	headers.add("Accept", accept);

	try {
		return restTemplate.exchange(uri+"/LMSLibrarian/librarybranch/"+branchId.toString()+"/bookid/"+bookId.toString()+"/copy/"+copy.toString(), HttpMethod.PUT, new HttpEntity<Object>(headers),
		String.class);
	}
	catch(HttpStatusCodeException e) {
        return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                .body(e.getResponseBodyAsString());
    }

	}	
}
	
