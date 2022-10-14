package com.bootcamp.capstone.prs.requestline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.capstone.prs.request.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/requestlines")
public class RequestlinesController {
	
	@Autowired
	private RequestlineRepository requestlineRepo;
	
	@Autowired
	private RequestRepository requestRepo;
	
	@SuppressWarnings({ "rawtypes"})
	private ResponseEntity recalcRequestTotal(int requestId) {
		var requestOpt = requestRepo.findById(requestId);
		if(requestOpt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var request = requestOpt.get();
		var requestTotal = 0;
		for(var requestline : request.getRequestlines()) {
			requestTotal += requestline.getProduct().getPrice() * requestline.getQuantity();
		}
		request.setTotal(requestTotal);
		requestRepo.save(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Requestline>> getRequestlines() {
		var requestlines = requestlineRepo.findAll();
		return new ResponseEntity<Iterable<Requestline>>(requestlines, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Requestline> getRequestline(@PathVariable int id) {
		var requestline = requestlineRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Requestline>(requestline.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Requestline> postRequestline(@RequestBody Requestline requestline) throws Exception{
		if(requestline.getId() !=0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var rline = requestlineRepo.save(requestline);
		var respEntity = this.recalcRequestTotal(rline.getRequest().getId());
		if(respEntity.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Recalculating the request total has failed!");
		}
		return new ResponseEntity<Requestline>(rline, HttpStatus.CREATED);		
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequestline(@PathVariable int id, @RequestBody Requestline requestline) throws Exception {
		if(id != requestline.getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var rlineOpt = requestlineRepo.findById(id);
		if(rlineOpt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		requestlineRepo.save(requestline);// changed rline to requestline
		var respEntity = this.recalcRequestTotal(rlineOpt.get().getRequest().getId());
		if(respEntity.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Recalculating the request total has failed!");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequestline(@PathVariable int id) throws Exception {
		var rlineOpt = requestlineRepo.findById(id);
		if(rlineOpt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		requestlineRepo.delete(rlineOpt.get());
		var respEntity = this.recalcRequestTotal(rlineOpt.get().getRequest().getId());
		if(respEntity.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Relcalculating the request total has failed!");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
