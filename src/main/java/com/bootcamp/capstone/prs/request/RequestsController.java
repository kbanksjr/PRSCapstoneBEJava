package com.bootcamp.capstone.prs.request;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestsController {
	
	private final String NEW = "NEW";
	private final String REVIEW = "REVIEW";
	private final String APPROVED = "APPROVED";
	private final String REJECTED = "REJECTED";
	

	@Autowired
	private RequestRepository requestRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Request>> getRequests() {
		Iterable<Request> requests = requestRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Request> getRequest(@PathVariable int id) {
		Optional<Request> request = requestRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
	}
	
	@GetMapping("reviews/{userId}")
	public ResponseEntity<Iterable<Request>> getReviews(@PathVariable int userId) {
		Iterable<Request> requests = requestRepo.findByStatusAndUserIdNot(REVIEW, userId);
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request) {
		if(request.getId() != 0 || request == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		request.setStatus(NEW);
		var newRequest = requestRepo.save(request);
		return new ResponseEntity<Request>(newRequest, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequest(@PathVariable int id, @RequestBody Request request) {
		if(request.getId() == 0 || request == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var _request = requestRepo.findById(request.getId());
		if(_request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		requestRepo.save(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity reviewRequest(@PathVariable int id, @RequestBody Request request) {
		var status = request.getTotal() <= 50 ? APPROVED : REVIEW;
		request.setStatus(status);
		return putRequest(id, request);
	}
	
 	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveRequest(@PathVariable int id, @RequestBody Request request)  {
		request.setStatus(APPROVED);
		return putRequest(id, request);
		
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectRequest(@PathVariable int id, @RequestBody Request request)  {
		request.setStatus(REJECTED);
		return putRequest(id, request);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequest(@PathVariable int id) {
		var request = requestRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		requestRepo.delete(request.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
