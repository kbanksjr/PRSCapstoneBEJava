package com.bootcamp.capstone.prs.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductsController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Product>> getProducts() {
		var products = productRepo.findAll();
		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id) {
		var prod = productRepo.findById(id);
		if(prod.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(prod.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Product> postProduct(@RequestBody Product product) {
		if(product == null || product.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var prod =productRepo.save(product);
		return new ResponseEntity<Product>(prod, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{productId}")
	public ResponseEntity putProduct(@PathVariable int id, @RequestBody Product product) {
		if(product.getId() == 0 || id != product.getId()) 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		var prod = productRepo.findById(product.getId());
		if(prod.isEmpty()) 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		productRepo.save(product);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{productId}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		var prod = productRepo.findById(id);
		if(prod.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		productRepo.delete(prod.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
