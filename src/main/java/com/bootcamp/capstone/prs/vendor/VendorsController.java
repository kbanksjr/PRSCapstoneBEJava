package com.bootcamp.capstone.prs.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorsController {
	
	@Autowired
	private VendorRepository vendorRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getVendors() {
		var vendors = vendorRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(vendors, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Vendor> getVendor(@PathVariable int id) {
		var vendor = vendorRepo.findById(id);
		if(vendor.isEmpty()) 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Vendor> postVendor(@RequestBody Vendor vendor) {
		if(vendor.getId() != 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		vendorRepo.save(vendor);
		return new ResponseEntity<Vendor>(vendor, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{vendorId")
	public ResponseEntity putVendor(@PathVariable int vendorId, @RequestBody Vendor vendor) {
		if(vendorId != vendor.getId() || vendor.getId() == 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		var _vendor = vendorRepo.findById(vendor.getId());
		if(_vendor.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		vendorRepo.save(vendor);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{vendorId}")
	public ResponseEntity deleteVendor(@PathVariable int vendorId) {
		var vendor = vendorRepo.findById(vendorId);
		if(vendor.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		vendorRepo.delete(vendor.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
