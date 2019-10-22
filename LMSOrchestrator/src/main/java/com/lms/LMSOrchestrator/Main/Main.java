package com.lms.LMSOrchestrator.Main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lms.LMSOrchestrator.POJO.*;


@RestController

@RequestMapping("/lms")
public class Main {
	
	 @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class, NullPointerException.class})
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	 public String handle(Exception e) {
		 return "Invalid Request";
	 }
	
	String librarianUri = "http://librarian-service";
	String borrowerUri = "http://borrower-service";
	String adminUri = "http://admin-service/admin";

	@Autowired
	RestTemplate restTemp;

	/*------------------------------------------------------------------------------------
	 * 									LIBRARIAN
	 * --------------------------------------------------------------------------------------
	 */
	@GetMapping
			(value = "/librarian/branches",
			consumes = {"application/xml", "application/json"}) 
	public ResponseEntity<String> getAllBranches ( 	@RequestHeader("Accept") String accept,
													@RequestHeader("Content-Type") String content){
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		headers.add("Content-Type", content);
		
		 HttpEntity<LibraryBranch> request = new HttpEntity<LibraryBranch>(headers);
		
		try {
			return  restTemp.exchange(
					librarianUri+
					"/librarian/branches", HttpMethod.GET, request,
					String.class);
		}
		catch(HttpStatusCodeException e) {
			return new ResponseEntity<String>(e.getStatusCode());
		}

	}	
	
	@GetMapping
			(value = "/librarian/branches/{branchId}",
			consumes = {"application/xml", "application/json"}) 
	public ResponseEntity<LibraryBranch> getABranch ( 	@RequestHeader("Accept") String accept, 
														@RequestHeader("Content-Type") String content,
														@PathVariable Integer branchId){
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		headers.add("Content-Type", content);
		
		 HttpEntity<LibraryBranch> request = new HttpEntity<LibraryBranch>(headers);
		
		try {
			return  restTemp.exchange(
					librarianUri+"/librarian/branches/"+branchId, HttpMethod.GET, request,
					LibraryBranch.class);
		}
		catch(HttpStatusCodeException e) {
			return new ResponseEntity<LibraryBranch>(e.getStatusCode());
		}

	}	
//	
//	@GetMapping("/admin/users")
//	public List<User> users(){
//		return this.userRepository.findAll();
//	}
	
	@PutMapping
	(value = "/librarian/branches/{branchId}", consumes = {"application/xml", "application/json"})
	public ResponseEntity<LibraryBranch> UpdateBranch(	@RequestHeader("Accept") String accept,
														@RequestHeader("Content-Type") String content,
														@PathVariable Integer branchId,
														@RequestBody LibraryBranch branch)
	{
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		headers.add("Content-Type", content);
		
		 HttpEntity<LibraryBranch> request = new HttpEntity<LibraryBranch>(branch, headers);
		
		try {
			return  restTemp.exchange(
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
										@RequestHeader("Content-Type") String content,
										@RequestBody BookCopies bookCopy) 
	{
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		headers.add("Content-Type", content);
		
		HttpEntity<BookCopies> request = new HttpEntity<BookCopies>(bookCopy, headers);

		try {
			return restTemp.exchange(librarianUri+"/librarian/branches/copies", HttpMethod.PUT, request,
					BookCopies.class);
		}
		catch(HttpStatusCodeException e) {
			return new ResponseEntity<BookCopies>(e.getStatusCode());
		}
	}	
	
	/*------------------------------------------------------------------------------------
	 * 									BORROWER
	 * --------------------------------------------------------------------------------------
	 */
	
    //Borrower checkout
    @PutMapping (value = "/borrower/checkout",
    		consumes = {"application/xml", "application/json"})
    
    public ResponseEntity<BookLoans> mainWriteLoans(@RequestHeader("Accept") String accept,
    		@RequestHeader("Content-Type") String contentType,
    		@RequestBody BookLoans bookLoans)
    		{    
	    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
	    headers.add("Content-Type", contentType);
	    headers.add("Accept", accept);
	    HttpEntity<BookLoans> request = new HttpEntity<BookLoans>(bookLoans, headers); 
	    try {
		    return restTemp.exchange(
		    		borrowerUri+"/borrower/checkout",
		    		HttpMethod.PUT,  request,
		    		BookLoans.class  );
		}
	    catch(HttpStatusCodeException e) {
	        return new ResponseEntity<BookLoans> (e.getStatusCode());
	    }
    }  

    //Borrower return
    @PutMapping (value = "/borrower/return", 
    		consumes= {"application/xml", "application/json"})
    
    public ResponseEntity<BookLoans> mainWriteReturn(@RequestHeader("Accept") String accept,
    		@RequestHeader("Content-Type") String contentType,
    		@RequestBody BookLoans bookLoans)
    		{
	    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
	    headers.add("Content-Type", contentType);
	    headers.add("Accept", accept);
	    HttpEntity<BookLoans> request = new HttpEntity<BookLoans>(bookLoans, headers);
	    
	    try {
		    return restTemp.exchange(
		    borrowerUri+"/borrower/return", 
		    HttpMethod.PUT,  request,
		    BookLoans.class);
	    }
		catch(HttpStatusCodeException e) {
		    return new ResponseEntity <BookLoans> (e.getStatusCode());
		}
    }
	
	/*------------------------------------------------------------------------------------
	 * 									ADMIN
	 * --------------------------------------------------------------------------------------
	 */
	
	/*
	 * Author Orchestrator
	 */
	
	//Create author
	@PostMapping("/admin/authors")
	public ResponseEntity<?> insertAuthor(@RequestHeader("Accept") String accept, 
			@RequestHeader("Content-Type") String contentType, @RequestBody Author author) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/authors", HttpMethod.POST, 
					new HttpEntity<Author>(author, headers), Author.class);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Author>(e.getStatusCode());
		}
	}
	
	//Update author
	@PutMapping("/admin/authors/{authorId}")
	public ResponseEntity<?> updateAuthor(@RequestHeader("Accept") String accept, 
			@RequestHeader("Content-Type") String contentType, @PathVariable Integer authorId, @RequestBody Author author) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/authors/{authorId}", HttpMethod.PUT, 
					new HttpEntity<Author>(author, headers), Author.class, authorId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Author>(e.getStatusCode());
		}
	}
	
	//Delete author
	@DeleteMapping("/admin/authors/{authorId}")
	public ResponseEntity<?> deleteAuthor(@RequestHeader("Accept") String accept, 
			@RequestHeader("Content-Type") String contentType, @PathVariable Integer authorId) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/authors/{authorId}", HttpMethod.DELETE, 
					new HttpEntity<Author>(headers), Author.class, authorId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Author>(e.getStatusCode());
		}
	}
	
	//View one author
	@GetMapping("/admin/authors/{authorId}")
	public ResponseEntity<?> getAuthorById(@RequestHeader("Accept") String accept, 
			@RequestHeader("Content-Type") String contentType, @PathVariable Integer authorId) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/authors/{authorId}", HttpMethod.GET, 
					new HttpEntity<Author>(headers), Author.class, authorId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Author>(e.getStatusCode());
		}
	}

	//View all authors
	@GetMapping("/admin/authors")
	public ResponseEntity<Iterable<Author>> getAllAuthors(@RequestHeader("Accept") String accept) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		
		try {
		return restTemp.exchange(adminUri + "/authors", HttpMethod.GET, new HttpEntity<Object>(headers), 
				new ParameterizedTypeReference<Iterable<Author>>(){});
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Iterable<Author>>(e.getStatusCode());
		}
	}
	
	
	/*
	 * Book Orchestrator
	 */
	
	//Create book
	@PostMapping("/admin/books")
	public ResponseEntity<?> insertBook(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@RequestBody Book book) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/books", HttpMethod.POST, 
					new HttpEntity<Book>(book, headers), Book.class);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Book>(e.getStatusCode());
		}
	}
	
	//Update book
	@PutMapping("/admin/books/{bookId}")
	public ResponseEntity<?> updateBook(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@PathVariable Integer bookId, @RequestBody Book book) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/books/{bookId}", HttpMethod.PUT, 
					new HttpEntity<Book>(book, headers), Book.class, bookId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Book>(e.getStatusCode());
		}
	}
	
	//Delete book
	@DeleteMapping("/admin/books/{bookId}")
	public ResponseEntity<?> deleteBook(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@PathVariable Integer bookId) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/books/{bookId}", HttpMethod.DELETE, 
					new HttpEntity<Object>(headers), Book.class, bookId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Book>(e.getStatusCode());
		}
	}
	
	//View one book
	@GetMapping("/admin/books/{bookId}")
	public ResponseEntity<?> getBookById(@RequestHeader("Accept") String accept, 
			@RequestHeader("Content-Type") String contentType, @PathVariable Integer bookId) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/books/{bookId}", HttpMethod.GET, 
					new HttpEntity<Book>(headers), Book.class, bookId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Book>(e.getStatusCode());
		}
	}
	
	//View all books
	@GetMapping("/admin/books")
	public ResponseEntity<Iterable<Book>> getAllBooks(@RequestHeader("Accept") String accept) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/books", HttpMethod.GET, new HttpEntity<Object>(headers), 
				new ParameterizedTypeReference<Iterable<Book>>(){});
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Iterable<Book>>(e.getStatusCode());
		}
	}
	
	
	/*
	 * Borrower Orchestrator
	 */
	
	//Create borrower
	@PostMapping("/admin/borrowers")
	public ResponseEntity<?> insertBorr(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@RequestBody Borrower borrower) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/borrowers", 
				HttpMethod.POST, new HttpEntity<Borrower>(borrower, headers), Borrower.class);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Borrower>(e.getStatusCode());
		}
	}
	
	//Update borrower
	@PutMapping("/admin/borrowers/{cardNo}")
	public ResponseEntity<?> updateBorr(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@PathVariable Integer cardNo, @RequestBody Borrower borrower) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/borrowers/{cardNo}", HttpMethod.PUT, 
					new HttpEntity<Borrower>(borrower, headers), Borrower.class, cardNo);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Borrower>(e.getStatusCode());
		}
	}
	
	//Delete borrower
	@DeleteMapping("/admin/borrowers/{cardNo}")
	public ResponseEntity<?> deleteBorr(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@PathVariable Integer cardNo) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/borrowers/{cardNo}", HttpMethod.DELETE, 
					new HttpEntity<Borrower>(headers), Borrower.class, cardNo);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Borrower>(e.getStatusCode());
		}
	}
	
	//View one borrower
	@GetMapping("/admin/borrowers/{cardNo}")
	public ResponseEntity<?> getBorrById(@RequestHeader("Accept") String accept, 
			@RequestHeader("Content-Type") String contentType, @PathVariable Integer cardNo) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/borrowers/{cardNo}", HttpMethod.GET, 
					new HttpEntity<Borrower>(headers), Borrower.class, cardNo);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Borrower>(e.getStatusCode());
		}
	}
	
	//View all borrower
	@GetMapping("/admin/borrowers")
	public ResponseEntity<Iterable<Borrower>> getAllBorrs(@RequestHeader("Accept") String accept) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/borrowers", HttpMethod.GET, new HttpEntity<Object>(headers), 
				new ParameterizedTypeReference<Iterable<Borrower>>(){});
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Iterable<Borrower>>(e.getStatusCode());
		}
	}
	
	
	/*
	 * Library Branch Orchestrator
	 */
	
	//Create branch
	@PostMapping("/admin/branches")
	public ResponseEntity<?> insertBranch(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@RequestBody LibraryBranch branch) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/branches", 
				HttpMethod.POST, new HttpEntity<LibraryBranch>(branch, headers), LibraryBranch.class);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<LibraryBranch>(e.getStatusCode());
		}
	}
	
	//Update branch
	@PutMapping("/admin/branches/{branchId}")
	public ResponseEntity<?> updateBranch(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@PathVariable Integer branchId, @RequestBody LibraryBranch branch) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/branches/{branchId}", HttpMethod.PUT, 
					new HttpEntity<LibraryBranch>(branch, headers), LibraryBranch.class, branchId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<LibraryBranch>(e.getStatusCode());
		}
	}
	
	//Delete branch
	@DeleteMapping("/admin/branches/{branchId}")
	public ResponseEntity<?> deleteBranch(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@PathVariable Integer branchId) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/branches/{branchId}", HttpMethod.DELETE, 
					new HttpEntity<LibraryBranch>(headers), LibraryBranch.class, branchId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<LibraryBranch>(e.getStatusCode());
		}
	}
	
	//View one branch
	@GetMapping("/admin/branches/{branchId}")
	public ResponseEntity<?> getBranchById(@RequestHeader("Accept") String accept, 
			@RequestHeader("Content-Type") String contentType, @PathVariable Integer branchId) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/branches/{branchId}", HttpMethod.GET, 
					new HttpEntity<LibraryBranch>(headers), LibraryBranch.class, branchId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<LibraryBranch>(e.getStatusCode());
		}
	}
	
	//View all branches
	@GetMapping("/admin/branches")
	public ResponseEntity<Iterable<LibraryBranch>> getAvailableBranch(@RequestHeader("Accept") String accept) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/branches", HttpMethod.GET, 
				new HttpEntity<Object>(headers), new ParameterizedTypeReference<Iterable<LibraryBranch>>(){});
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Iterable<LibraryBranch>>(e.getStatusCode());
		}
	}
	
	
	
	/*
	 * Publisher Orchestrator
	 */
	
	//Create pub
	@PostMapping("/admin/publishers")
	public ResponseEntity<?> insertPub(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@RequestBody Publisher pub) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/publishers", 
				HttpMethod.POST, new HttpEntity<Publisher>(pub, headers), Publisher.class);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Publisher>(e.getStatusCode());
		}
	}
	
	//Update pub
	@PutMapping("/admin/publishers/{publisherId}")
	public ResponseEntity<?> updatePub(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@PathVariable Integer publisherId, @RequestBody Publisher pub) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/publishers/{publisherId}", HttpMethod.PUT, 
					new HttpEntity<Publisher>(pub, headers), Publisher.class, publisherId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Publisher>(e.getStatusCode());
		}
	}
	
	//Delete pub
	@DeleteMapping(value = "/admin/publishers/{publisherId}")
	public ResponseEntity<?> deletePub(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@PathVariable Integer publisherId) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/publishers/{publisherId}", HttpMethod.DELETE, 
					new HttpEntity<Publisher>(headers), Publisher.class, publisherId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Publisher>(e.getStatusCode());
		}
	}
	
	//View one publisher
	@GetMapping("/admin/publishers/{publisherId}")
	public ResponseEntity<?> getPubById(@RequestHeader("Accept") String accept, 
			@RequestHeader("Content-Type") String contentType, @PathVariable Integer publisherId) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/publishers/{publisherId}", HttpMethod.GET, 
					new HttpEntity<Publisher>(headers), Publisher.class, publisherId);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Publisher>(e.getStatusCode());
		}
	}
	
	//View publishers
	@GetMapping("/admin/publishers")
	public ResponseEntity<Iterable<Publisher>> getAllPubs(@RequestHeader("Accept") String accept) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		
		try {
		return restTemp.exchange(adminUri + "/publishers", HttpMethod.GET, 
				new HttpEntity<Object>(headers), new ParameterizedTypeReference<Iterable<Publisher>>(){});
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Iterable<Publisher>>(e.getStatusCode());
		}
	}
	
	
	
	/*
	 * Override Orchestrator
	 */
	
	//Override due date
	@PutMapping("/admin/bookloans/duedate")
	public ResponseEntity<?> overDueDate(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
			@RequestBody BookLoans loans) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("Accept", accept);
		
		try {
			return restTemp.exchange(adminUri + "/bookloans/duedate", HttpMethod.PUT, 
					new HttpEntity<BookLoans>(loans, headers), BookLoans.class);
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<BookLoans>(e.getStatusCode());
		}
	}
	
	//View book loans
	@GetMapping("/admin/bookloans")
	public ResponseEntity<Iterable<BookLoans>> getBookLoans(@RequestHeader("Accept") String accept) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", accept);
		
		try {
		return restTemp.exchange(adminUri + "/bookloans", HttpMethod.GET, 
				new HttpEntity<Object>(headers), new ParameterizedTypeReference<Iterable<BookLoans>>(){});
		} catch(HttpStatusCodeException e) {
			return new ResponseEntity<Iterable<BookLoans>>(e.getStatusCode());
		}
	}
	
}

