package com.bootcamp.capstone.prs.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UsersController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<User>> getUsers() {
		var users = userRepo.findAll();
		return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {
		var user = userRepo.findById(id);
		if(user.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	
	@GetMapping("{username}/{password}")
	public ResponseEntity<User> loginUser(@PathVariable String username, @PathVariable String password) {
		var user = userRepo.findByUsernameAndPassword(username, password);
		if(user.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<User> postUser(@RequestBody User user) {
		if(user.getId() != 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		userRepo.save(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{userId}")
	public ResponseEntity putUser(@PathVariable int userId, @RequestBody User user) {
		if(userId != user.getId() || user.getId() == 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		var _user = userRepo.findById(user.getId());
		if(_user.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		userRepo.save(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{userId}")
	public ResponseEntity deleteUser(@PathVariable int userId) {
		var user = userRepo.findById(userId);
		if(user.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		userRepo.delete(user.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
