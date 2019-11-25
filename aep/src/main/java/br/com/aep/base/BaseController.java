package br.com.aep.base;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseController<ENTITY extends BaseEntity, REPOSITORY extends BaseRepository<ENTITY>>  {
	
	@Autowired
	private REPOSITORY repo;
	

	public REPOSITORY getRepo() {
		return repo;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ENTITY> getById(@PathVariable("id") String id) {
		if(!repo.findById(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(repo.findById(id).get(),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ENTITY> post(@RequestBody ENTITY object) {
		if(!repo.findById(object.getId()).isPresent()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		if(object.toString().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ENTITY> put(@PathVariable String id, @RequestBody ENTITY object) {
		if(!Objects.equals(id, object.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(!repo.findById(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ENTITY> delete(@PathVariable String id) {
		if (!repo.findById(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
